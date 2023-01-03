package com.r2s.SpringWebDemo.controller;

import com.r2s.SpringWebDemo.dto.request.CreateAddressRequestDTO;
import com.r2s.SpringWebDemo.dto.request.CreateCategoryRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateAddressRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateCategoryRequestDTO;
import com.r2s.SpringWebDemo.dto.response.*;
import com.r2s.SpringWebDemo.entity.Category;
import com.r2s.SpringWebDemo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "category")
public class CategoryController implements Serializable {
    @Autowired
    CategoryService categoryService;

    @GetMapping(value = "/get-all-category")
    public ResponseEntity getAllCategory(@RequestParam(value = "page", required = false) Integer page,
                                        @RequestParam(value = "size", required = false) Integer size) {

        PagingResponseDTO pagingCategoryResponseDTO = new PagingResponseDTO();
        pagingCategoryResponseDTO.setResponseObjectList(categoryService.getAllCategory(page, size));
        pagingCategoryResponseDTO.setPage(page);
        pagingCategoryResponseDTO.setSize(size);

        return new ResponseEntity<>(pagingCategoryResponseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{category-id}")
    public ResponseEntity getCategoryById(@PathVariable("category-id") Integer categoryId) {
        CategoryResponseDTO categoryResponse = this.categoryService.getCategoryById(categoryId);

        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity insertCategory(@RequestBody CreateCategoryRequestDTO createCategoryRequestDTO) {
        CategoryResponseDTO categoryResponseDTO = this.categoryService.createCategory(createCategoryRequestDTO);

        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/{category-id}")
    public ResponseEntity updateCategory(@PathVariable("category-id") Integer categoryId,
                                        @RequestBody UpdateCategoryRequestDTO updateCategoryRequestDTO) {
        UpdateCategoryResponseDTO response = this.categoryService.updateCategory(categoryId, updateCategoryRequestDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{category-id}")
    public ResponseEntity deleteCategory(@PathVariable("category-id") Integer categoryId) {
        this.categoryService.deleteCategory(categoryId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
