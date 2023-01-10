package com.r2s.SpringWebDemo.dto.request;

import com.r2s.SpringWebDemo.dto.response.CategoryResponseDTO;
import com.r2s.SpringWebDemo.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequestDTO implements Serializable {

    private String name;

    private Double price;

    private Integer quantity;

    private CategoryResponseDTO category;
}
