package com.r2s.SpringWebDemo.service.impl;

import com.r2s.SpringWebDemo.dto.request.CreateCategoryRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateCategoryRequestDTO;
import com.r2s.SpringWebDemo.dto.response.CartResponseDTO;
import com.r2s.SpringWebDemo.dto.response.CategoryResponseDTO;
import com.r2s.SpringWebDemo.dto.response.UpdateCategoryResponseDTO;
import com.r2s.SpringWebDemo.entity.Category;
import com.r2s.SpringWebDemo.repository.CategoryRepository;
import com.r2s.SpringWebDemo.service.CategoryService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.r2s.SpringWebDemo.constants.Constants.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponseDTO> getAllCategory(Integer page, Integer size) {
        List<CategoryResponseDTO> categoryResponseDTOList = new ArrayList<>();
        List<Category> categories = categoryRepository.getAllCategory(page, size);
        for (Category category : categories) {
            CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
            categoryResponseDTO.setName(category.getName());
            categoryResponseDTO.setId(category.getId());
            categoryResponseDTOList.add(categoryResponseDTO);
        }

        return categoryResponseDTOList;
    }

    @Override
    public CategoryResponseDTO getCategoryById(Integer cateId) {
        CategoryResponseDTO cateResponseDTO = new CategoryResponseDTO();
        //isPresent() kiểm tra có không rỗng hay không.
        try {
            Optional<Category> category = categoryRepository.findById(cateId);
            if(category.isPresent() && category.get().getIsDeleted() == CategoryIsDeleteFalse) {
                cateResponseDTO.setId(category.get().getId());
                cateResponseDTO.setName(category.get().getName());
            } else {
                throw new NoSuchElementException("Can't find categoryId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return cateResponseDTO;
    }

    @Override
    public CategoryResponseDTO createCategory(CreateCategoryRequestDTO createCategoryRequestDTO) {
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        Category category = new Category();

        try {
            if(createCategoryRequestDTO.getName().isEmpty()) {
                throw new Exception("Category name is required!");
            }
            if(this.categoryRepository.existsByName(createCategoryRequestDTO.getName())) {
                throw new Exception("Category name is existed!");
            }
            else {
                category.setName(createCategoryRequestDTO.getName());
                if(category.getCreatedDate() == null) {
                    category.setCreatedDate(new Date());
                }
                if(category.getUpdatedDate() == null) {
                    category.setUpdatedDate(new Date());
                }
                category.setIsDeleted(CategoryIsDeleteFalse);
                categoryResponseDTO.setId(category.getId());
                categoryResponseDTO.setName(category.getName());

                categoryRepository.save(category);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return categoryResponseDTO;
    }

    @Override
    public UpdateCategoryResponseDTO updateCategory(Integer cateId, UpdateCategoryRequestDTO updateCategoryRequestDTO) {
        UpdateCategoryResponseDTO updateCategoryResponseDTO = new UpdateCategoryResponseDTO();
        Optional<Category> category = this.categoryRepository.findById(cateId);

        try {
            if(category.isEmpty() || category.get().getIsDeleted() == CategoryIsDeleteTrue) {
                throw new Exception("Can't find categoryId");
            }
            if(updateCategoryRequestDTO.getName().isEmpty()) {
                throw new Exception("Category name is required!");
            }
            if(this.categoryRepository.existsByName(updateCategoryRequestDTO.getName())) {
                throw new Exception("Category name is existed!");
            }
            else {
                category.get().setId(cateId);
                if(!category.get().getName().equals(updateCategoryRequestDTO.getName())) {
                    category.get().setName(updateCategoryRequestDTO.getName());
                }
                category.get().setUpdatedDate(new Date());
                updateCategoryResponseDTO.setId(category.get().getId());
                updateCategoryResponseDTO.setName(category.get().getName());

                categoryRepository.save(category.get());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return updateCategoryResponseDTO;
    }

    @Override
    public Boolean deleteCategory(Integer cateId) {
        //isPresent() kiểm tra có không rỗng hay không.
        try {
            Optional<Category> category = categoryRepository.findById(cateId);
            if(category.isPresent()) {
                this.categoryRepository.deleteById(cateId);
                return true;
            } else {
                throw new NoSuchElementException("Can't find categoryId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean deleteProductTemporarily(Integer cateId) {
        try {
            Optional<Category> category = categoryRepository.findById(cateId);
            if(category.isPresent()) {
                this.categoryRepository.deleteById(cateId);
                return true;
            } else {
                throw new NoSuchElementException("Can't find categoryId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }


}
