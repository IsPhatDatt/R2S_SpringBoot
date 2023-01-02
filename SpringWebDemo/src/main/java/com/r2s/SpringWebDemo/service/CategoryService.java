package com.r2s.SpringWebDemo.service;

import com.r2s.SpringWebDemo.dto.request.CreateCategoryRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateCategoryRequestDTO;
import com.r2s.SpringWebDemo.dto.response.CategoryResponseDTO;
import com.r2s.SpringWebDemo.dto.response.UpdateCategoryResponseDTO;
import com.r2s.SpringWebDemo.entity.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDTO> getAllCategory(Integer page, Integer size);

    CategoryResponseDTO getCategoryById(Integer cateId);

    CategoryResponseDTO createCategory(CreateCategoryRequestDTO createCategoryRequestDTO);

    UpdateCategoryResponseDTO updateCategory(Integer cateId, UpdateCategoryRequestDTO updateCategoryRequestDTO);

    Boolean deleteCategory(Integer cateId);

    Boolean deleteProductTemporarily(Integer cateId);

}
