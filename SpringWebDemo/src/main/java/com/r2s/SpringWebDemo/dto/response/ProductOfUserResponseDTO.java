package com.r2s.SpringWebDemo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOfUserResponseDTO implements Serializable {

    private Integer id;

    private String firstName;

    private String lastName;

    private String username;

    private Set<ProductResponseDTO> products;
}
