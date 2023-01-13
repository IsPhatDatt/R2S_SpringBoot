package com.r2s.SpringWebDemo.service;

import com.r2s.SpringWebDemo.dto.request.CreateCategoryRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateCategoryRequestDTO;
import com.r2s.SpringWebDemo.dto.response.CategoryResponseDTO;
import com.r2s.SpringWebDemo.dto.response.PagingResponseDTO;
import com.r2s.SpringWebDemo.dto.response.ProductOfCategoryResponseDTO;
import com.r2s.SpringWebDemo.dto.response.UpdateCategoryResponseDTO;
import com.r2s.SpringWebDemo.entity.Category;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    PagingResponseDTO getAllCategory(Pageable pageable);

    CategoryResponseDTO getCategoryById(Integer cateId);

    CategoryResponseDTO createCategory(CreateCategoryRequestDTO createCategoryRequestDTO);

    UpdateCategoryResponseDTO updateCategory(Integer cateId, UpdateCategoryRequestDTO updateCategoryRequestDTO);

    Boolean deleteCategory(Integer cateId);

    Boolean deleteCategoryTemporarily(Integer cateId);

    ProductOfCategoryResponseDTO getProductByCategoryId(Integer cateId);

}
