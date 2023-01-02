package com.r2s.SpringWebDemo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductResponseDTO implements Serializable {

    private Integer id;

    private String name;

    private Double price;

    private Integer quantity;
}
