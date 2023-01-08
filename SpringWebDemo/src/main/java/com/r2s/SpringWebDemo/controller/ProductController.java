package com.r2s.SpringWebDemo.controller;

import com.r2s.SpringWebDemo.dto.request.CreateCategoryRequestDTO;
import com.r2s.SpringWebDemo.dto.request.CreateProductRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateCategoryRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateProductRequestDTO;
import com.r2s.SpringWebDemo.dto.response.*;
import com.r2s.SpringWebDemo.service.CategoryService;
import com.r2s.SpringWebDemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping(value = "/get-all-product")
    public ResponseEntity getAllProduct(@RequestParam(value = "page", required = false) Integer page,
                                         @RequestParam(value = "size", required = false) Integer size) {
        PagingResponseDTO pagingProductResponseDTO = new PagingResponseDTO();
        pagingProductResponseDTO.setResponseObjectList(productService.getAllProduct(page, size));
        pagingProductResponseDTO.setPage(page);
        pagingProductResponseDTO.setSize(size);

        return new ResponseEntity<>(pagingProductResponseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{product-id}")
    public ResponseEntity getProductById(@PathVariable("product-id") Integer productId) {
        ProductResponseDTO responseProductDTO = this.productService.getProductById(productId);

        return new ResponseEntity<>(responseProductDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity insertProduct(@RequestBody CreateProductRequestDTO createProductRequestDTO) {
        ProductResponseDTO productResponseDTO = this.productService.createProduct(createProductRequestDTO);

        return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/{product-id}")
    public ResponseEntity updateProduct(@PathVariable("product-id") Integer productId,
                                         @RequestBody UpdateProductRequestDTO updateProductRequestDTO) {
        UpdateProductResponseDTO response = this.productService.updateProduct(productId, updateProductRequestDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{product-id}")
    public ResponseEntity deleteProduct(@PathVariable("product-id") Integer productId) {
        this.productService.deleteProduct(productId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
