package com.r2s.SpringWebDemo.service.impl;

import com.r2s.SpringWebDemo.dto.request.CreateCategoryRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateCategoryRequestDTO;
import com.r2s.SpringWebDemo.dto.response.*;
import com.r2s.SpringWebDemo.entity.Category;
import com.r2s.SpringWebDemo.entity.Product;
import com.r2s.SpringWebDemo.mapper.EntityMapper;
import com.r2s.SpringWebDemo.repository.CategoryRepository;
import com.r2s.SpringWebDemo.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.r2s.SpringWebDemo.constants.Constants.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public PagingResponseDTO getAllCategory(Pageable pageable) {

        Page<Category> categoryPage = this.categoryRepository.findAllByIsDeleted(CATEGORY_IS_DELETED_FALSE, pageable)
                .orElseThrow(() -> new RuntimeException("Can't get category by paging"));

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        pagingResponseDTO.setPage(categoryPage.getNumber());
        pagingResponseDTO.setTotalPages(categoryPage.getTotalPages());
        pagingResponseDTO.setSize(categoryPage.getSize());
        pagingResponseDTO.setTotalRecords(categoryPage.getTotalElements());

//        List<CategoryResponseDTO> categoryResponseDTOList = this.categoryMapper.convertEntitiesToResponseDTOs(categoryPage.getContent());
        List<CategoryResponseDTO> categoryResponseDTOList = categoryPage.stream()
                .map((category) -> this.modelMapper.map(category, CategoryResponseDTO.class)).collect(Collectors.toList());

        pagingResponseDTO.setResponseObjectList(categoryResponseDTOList);

        return pagingResponseDTO;
    }

    @Override
    public CategoryResponseDTO getCategoryById(Integer cateId) {
        //isPresent() kiểm tra có không rỗng hay không.
        try {
            Optional<Category> category = this.categoryRepository.findById(cateId);
            if(category.isPresent() && category.get().getIsDeleted() == CATEGORY_IS_DELETED_FALSE) {
                return this.modelMapper.map(category.get(), CategoryResponseDTO.class);
            } else {
                throw new NoSuchElementException("Can't find categoryId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public CategoryResponseDTO createCategory(CreateCategoryRequestDTO createCategoryRequestDTO) {

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
                category.setIsDeleted(CATEGORY_IS_DELETED_FALSE);

                this.categoryRepository.save(category);

                return this.modelMapper.map(category, CategoryResponseDTO.class);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, IllegalArgumentException.class, Throwable.class})
    public UpdateCategoryResponseDTO updateCategory(Integer cateId, UpdateCategoryRequestDTO updateCategoryRequestDTO) {

        try {
            if(!this.categoryRepository.existsById(cateId)) {
                throw new IllegalArgumentException("CategoryId is invalid!");
            } else {
                Category category = this.categoryRepository.findById(cateId).get();
                if(!category.getIsDeleted() == CATEGORY_IS_DELETED_FALSE) {
                    throw new Exception("The category is unavailable!");
                } else {
                    if(updateCategoryRequestDTO.getName().isEmpty()) {
                        throw new Exception("Category name is required!");
                    } else {
                        if(!category.getName().equals(updateCategoryRequestDTO.getName())) {
                            category.setName(updateCategoryRequestDTO.getName());
                        }
                        category.setUpdatedDate(new Date());

                        this.categoryRepository.save(category);
                        return this.modelMapper.map(category, UpdateCategoryResponseDTO.class);
                    }
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = {IllegalArgumentException.class, Throwable.class})
    public Boolean deleteCategory(Integer cateId) {
        //isPresent() kiểm tra có không rỗng hay không.
        try {
            this.categoryRepository.findById(cateId)
                    .orElseThrow(() -> new IllegalArgumentException("CategoryId is invalid!"));
            this.categoryRepository.deleteById(cateId);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = {IllegalArgumentException.class, Throwable.class})
    public Boolean deleteCategoryTemporarily(Integer cateId) {
        try {
            Optional<Category> category = this.categoryRepository.findById(cateId);
            if(category.isPresent() && category.get().getIsDeleted() == CATEGORY_IS_DELETED_FALSE) {
                category.get().setIsDeleted(CATEGORY_IS_DELETED_TRUE);
                this.categoryRepository.save(category.get());
                return true;
            } else {
                throw new IllegalArgumentException("Can't find categoryId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public ProductOfCategoryResponseDTO getProductByCategoryId(Integer cateId) {

        try {
            if(!this.categoryRepository.existsById(cateId)) {
                throw new IllegalArgumentException("CategoryId is invalid!");
            } else {
                Category category = this.categoryRepository.findById(cateId)
                        .orElseThrow(() -> new IllegalArgumentException("Can't find categoryId!"));
                if(!category.getIsDeleted() == CATEGORY_IS_DELETED_FALSE) {
                    throw new Exception("The category is unavailable!");
                } else {
                    Set<Product> products = category.getProducts();
                    Set<ProductResponseDTO> productResponseDTOList = products.stream()
                            .map((product) -> this.modelMapper.map(product, ProductResponseDTO.class)).collect(Collectors.toSet());

                    ProductOfCategoryResponseDTO productOfCategoryResponseDTO = this.modelMapper.map(category, ProductOfCategoryResponseDTO.class);
//                    productOfCategoryResponseDTO.setProducts(productResponseDTOList);
                    return productOfCategoryResponseDTO;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


}
