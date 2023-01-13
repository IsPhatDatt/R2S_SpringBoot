package com.r2s.SpringWebDemo.dto.response;

import com.r2s.SpringWebDemo.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO implements Serializable {

    private Integer id;

    private String name;

    private Double price;

    private Integer quantity;
}
