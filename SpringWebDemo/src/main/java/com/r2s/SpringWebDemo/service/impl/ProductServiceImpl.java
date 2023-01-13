package com.r2s.SpringWebDemo.service.impl;

import com.r2s.SpringWebDemo.dto.request.CreateProductRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateProductRequestDTO;
import com.r2s.SpringWebDemo.dto.response.*;
import com.r2s.SpringWebDemo.entity.Category;
import com.r2s.SpringWebDemo.entity.Product;
import com.r2s.SpringWebDemo.repository.CategoryRepository;
import com.r2s.SpringWebDemo.repository.ProductRepository;
import com.r2s.SpringWebDemo.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.r2s.SpringWebDemo.constants.Constants.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public PagingResponseDTO getAllProduct(Pageable pageable) {

        Page<Product> productPage = this.productRepository.findAllByIsDeleted(PRODUCT_IS_DELETED_FALSE, pageable)
                .orElseThrow(() -> new RuntimeException("Can't get product by paging"));

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        pagingResponseDTO.setPage(productPage.getNumber());
        pagingResponseDTO.setTotalPages(productPage.getTotalPages());
        pagingResponseDTO.setSize(productPage.getSize());
        pagingResponseDTO.setTotalRecords(productPage.getTotalElements());

        List<ProductResponseDTO> productResponseDTOList = productPage.stream()
                .map((product) -> this.modelMapper.map(product, ProductResponseDTO.class)).collect(Collectors.toList());

        pagingResponseDTO.setResponseObjectList(productResponseDTOList);

        return pagingResponseDTO;
    }

    @Override
    public ProductResponseDTO getProductById(Integer productId) {

        ProductResponseDTO productResponseDTO;

        try {
            Optional<Product> product = this.productRepository.findById(productId);
            if(product.isPresent() && product.get().getIsDeleted() == PRODUCT_IS_DELETED_FALSE) {
                productResponseDTO = this.modelMapper.map(product.get(), ProductResponseDTO.class);
                return productResponseDTO;
            } else {
                throw new NoSuchElementException("Can't find productId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ProductResponseDTO createProduct(CreateProductRequestDTO createProductRequestDTO) {

        Product product = new Product();

        try {
            if(createProductRequestDTO.getName().isEmpty()) {
                throw new Exception("Product name is required!");
            }
            if(this.productRepository.existsByName(createProductRequestDTO.getName())) {
                throw new Exception("Product name is existed!");
            }
            else {
                Category category = this.categoryRepository.findById(createProductRequestDTO.getCategory().getId())
                        .orElseThrow(() -> new IllegalArgumentException("CategoryId is invalid!"));
                CategoryResponseDTO categoryResponseDTO = this.modelMapper.map(category, CategoryResponseDTO.class);
                product.setName(createProductRequestDTO.getName());
                product.setPrice(createProductRequestDTO.getPrice());
                product.setQuantity(createProductRequestDTO.getQuantity());
                if(product.getCreatedDate() == null) {
                    product.setCreatedDate(new Date());
                }
                if(product.getUpdatedDate() == null) {
                    product.setUpdatedDate(new Date());
                }
                product.setIsDeleted(PRODUCT_IS_DELETED_FALSE);
                product.setCategoryId(category);

                this.productRepository.save(product);

                ProductResponseDTO productResponseDTO = this.modelMapper.map(product, ProductResponseDTO.class);
                return productResponseDTO;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, IllegalArgumentException.class, Throwable.class})
    public UpdateProductResponseDTO updateProduct(Integer productId, UpdateProductRequestDTO updateProductRequestDTO) {

        try {
            if(!this.productRepository.existsById(productId)) {
                throw new IllegalArgumentException("ProductId is invalid!");
            }
            if(!this.categoryRepository.existsById(updateProductRequestDTO.getCategory().getId())) {
                throw new IllegalArgumentException("CategoryId is invalid!");
            } else {
                Product product = this.productRepository.findById(productId).get();
                Category category = this.categoryRepository
                        .findById(updateProductRequestDTO
                                .getCategory().getId()).get();
                if(!product.getIsDeleted() == PRODUCT_IS_DELETED_FALSE) {
                    throw new Exception("The product is unavailable!");
                }
                if(!category.getIsDeleted() == CATEGORY_IS_DELETED_FALSE) {
                    throw new Exception("The category is unavailable");
                } else {
                    if(updateProductRequestDTO.getName().isEmpty()) {
                        throw new Exception("Product name is required!");
                    } else {
                        if(!product.getName().equals(updateProductRequestDTO.getName())) {
                            product.setName(updateProductRequestDTO.getName());
                        }
                        if(!product.getPrice().equals(updateProductRequestDTO.getPrice())) {
                            product.setPrice(updateProductRequestDTO.getPrice());
                        }
                        if(!product.getQuantity().equals(updateProductRequestDTO.getQuantity())) {
                            product.setQuantity(updateProductRequestDTO.getQuantity());
                        }
                        if(!product.getCategoryId().getId()
                                .equals(updateProductRequestDTO.getCategory().getId())) {
                            product.setCategoryId(category);
                        }
                        product.setUpdatedDate(new Date());

                        this.productRepository.save(product);
                        CategoryResponseDTO updateCategoryResponseDTO = this.modelMapper.map(category, CategoryResponseDTO.class);
                        UpdateProductResponseDTO updateProductResponseDTO = this.modelMapper.map(product, UpdateProductResponseDTO.class);
                        updateProductResponseDTO.setCategory(updateCategoryResponseDTO);
                        return updateProductResponseDTO;
                    }
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = {IllegalArgumentException.class, Throwable.class})
    public Boolean deleteProduct(Integer productId) {

        try {
            this.productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("ProductId is invalid!"));
            this.productRepository.deleteById(productId);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = {IllegalArgumentException.class, Throwable.class})
    public Boolean deleteProductTemporarily(Integer productId) {
        try {
            Optional<Product> product = this.productRepository.findById(productId);
            if(product.isPresent() && product.get().getIsDeleted() == CATEGORY_IS_DELETED_FALSE) {
                product.get().setIsDeleted(PRODUCT_IS_DELETED_TRUE);
                this.productRepository.save(product.get());
                return true;
            } else {
                throw new IllegalArgumentException("Can't find productId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
