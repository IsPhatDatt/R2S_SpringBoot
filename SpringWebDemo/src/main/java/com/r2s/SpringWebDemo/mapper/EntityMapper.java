package com.r2s.SpringWebDemo.mapper;

import com.r2s.SpringWebDemo.dto.response.CategoryResponseDTO;
import com.r2s.SpringWebDemo.dto.response.UpdateCategoryResponseDTO;
import com.r2s.SpringWebDemo.entity.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityMapper<S, T> {

    public List<CategoryResponseDTO> convertEntitiesToResponseDTOs (List<Category> categoryList) {

        return categoryList.stream().map(this::convertEntityToResponseDTO).toList();
    }

    public CategoryResponseDTO convertEntityToResponseDTO(Category category) {

        CategoryResponseDTO responseDTO = new CategoryResponseDTO();
        BeanUtils.copyProperties(category, responseDTO);

        return responseDTO;
    }

    public UpdateCategoryResponseDTO convertEntityToUpdateResponseDTO(Category category) {

        UpdateCategoryResponseDTO updateResponseDTO = new UpdateCategoryResponseDTO();
        BeanUtils.copyProperties(category, updateResponseDTO);

        return updateResponseDTO;
    }
}
