package com.r2s.SpringWebDemo.service;

import com.r2s.SpringWebDemo.dto.request.CreateCategoryRequestDTO;
import com.r2s.SpringWebDemo.dto.request.CreateProductRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateCategoryRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateProductRequestDTO;
import com.r2s.SpringWebDemo.dto.response.CategoryResponseDTO;
import com.r2s.SpringWebDemo.dto.response.ProductResponseDTO;
import com.r2s.SpringWebDemo.dto.response.UpdateCategoryResponseDTO;
import com.r2s.SpringWebDemo.dto.response.UpdateProductResponseDTO;
import com.r2s.SpringWebDemo.entity.Product;

import java.util.List;

public interface ProductService {

    List<ProductResponseDTO> getAllProduct(Integer page, Integer size);

    ProductResponseDTO getProductById(Integer productId);

    ProductResponseDTO createProduct(CreateProductRequestDTO createProductRequestDTO);

    UpdateProductResponseDTO updateProduct(Integer productId, UpdateProductRequestDTO updateProductRequestDTO);

    Boolean deleteProduct(Integer productId);
}
