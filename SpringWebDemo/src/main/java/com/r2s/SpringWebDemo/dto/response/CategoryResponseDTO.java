package com.r2s.SpringWebDemo.dto.response;

import com.r2s.SpringWebDemo.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO implements Serializable {

    private Integer id;

    private String name;
}
