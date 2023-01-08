package com.r2s.SpringWebDemo.service.impl;

import com.r2s.SpringWebDemo.dto.request.CreateProductRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateProductRequestDTO;
import com.r2s.SpringWebDemo.dto.response.CategoryResponseDTO;
import com.r2s.SpringWebDemo.dto.response.ProductResponseDTO;
import com.r2s.SpringWebDemo.dto.response.UpdateCategoryResponseDTO;
import com.r2s.SpringWebDemo.dto.response.UpdateProductResponseDTO;
import com.r2s.SpringWebDemo.entity.Category;
import com.r2s.SpringWebDemo.entity.Product;
import com.r2s.SpringWebDemo.repository.CategoryRepository;
import com.r2s.SpringWebDemo.repository.ProductRepository;
import com.r2s.SpringWebDemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.r2s.SpringWebDemo.constants.Constants.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<ProductResponseDTO> getAllProduct(Integer page, Integer size) {
        List<ProductResponseDTO> productResponseDTOList = new ArrayList<>();
        List<Product> products = productRepository.getAllProduct(page, size);
        for (Product product : products) {
            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            productResponseDTO.setName(product.getName());
            productResponseDTO.setId(product.getId());
            productResponseDTO.setPrice(product.getPrice());
            productResponseDTO.setQuantity(product.getQuantity());
            productResponseDTOList.add(productResponseDTO);
        }

        return productResponseDTOList;
    }

    @Override
    public ProductResponseDTO getProductById(Integer productId) {

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        //isPresent() kiểm tra có không rỗng hay không.
        try {
            Optional<Product> product = productRepository.findById(productId);
            if(product.isPresent() && product.get().getIsDeleted() == ProductIsDeleteFalse) {
                productResponseDTO.setId(product.get().getId());
                productResponseDTO.setName(product.get().getName());
                productResponseDTO.setPrice(product.get().getPrice());
                productResponseDTO.setQuantity(product.get().getQuantity());
            } else {
                throw new NoSuchElementException("Can't find productId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return productResponseDTO;
    }

    @Override
    public ProductResponseDTO createProduct(CreateProductRequestDTO createProductRequestDTO) {

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        Product product = new Product();

        try {
            if(createProductRequestDTO.getName().isEmpty()) {
                throw new Exception("Product name is required!");
            }
            if(this.productRepository.existsByName(createProductRequestDTO.getName())) {
                throw new Exception("Product name is existed!");
            }
            else {
                product.setName(createProductRequestDTO.getName());
                product.setPrice(createProductRequestDTO.getPrice());
                product.setQuantity(createProductRequestDTO.getQuantity());
                if(product.getCreatedDate() == null) {
                    product.setCreatedDate(new Date());
                }
                if(product.getUpdatedDate() == null) {
                    product.setUpdatedDate(new Date());
                }
                product.setIsDeleted(ProductIsDeleteFalse);
                productResponseDTO.setId(product.getId());
                productResponseDTO.setName(product.getName());
                productResponseDTO.setPrice(product.getPrice());
                productResponseDTO.setQuantity(product.getQuantity());

                productRepository.save(product);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return productResponseDTO;
    }

    @Override
    public UpdateProductResponseDTO updateProduct(Integer productId, UpdateProductRequestDTO updateProductRequestDTO) {
        UpdateProductResponseDTO updateProductResponseDTO = new UpdateProductResponseDTO();
        Optional<Product> product = this.productRepository.findById(productId);

        try {
            if(product.isEmpty() || product.get().getIsDeleted() == ProductIsDeleteTrue) {
                throw new Exception("Can't find productId");
            }
            if(updateProductRequestDTO.getName().isEmpty()) {
                throw new Exception("Product name is required!");
            }
            if(this.productRepository.existsByName(updateProductRequestDTO.getName())) {
                throw new Exception("Product name is existed!");
            }
            else {
                product.get().setId(productId);
                if(!product.get().getName().equals(updateProductRequestDTO.getName())) {
                    product.get().setName(updateProductRequestDTO.getName());
                }
                product.get().setUpdatedDate(new Date());
                updateProductResponseDTO.setId(product.get().getId());
                updateProductResponseDTO.setName(product.get().getName());
                updateProductResponseDTO.setPrice(product.get().getPrice());
                updateProductResponseDTO.setQuantity(product.get().getQuantity());

                productRepository.save(product.get());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return updateProductResponseDTO;
    }

    @Override
    public Boolean deleteProduct(Integer productId) {

        try {
            Optional<Product> product = productRepository.findById(productId);
            if(product.isPresent()) {
                this.productRepository.deleteById(productId);
                return true;
            } else {
                throw new NoSuchElementException("Can't find productId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
