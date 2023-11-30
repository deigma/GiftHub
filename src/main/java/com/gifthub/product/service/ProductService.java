package com.gifthub.product.service;

import com.gifthub.product.dto.ProductDto;
import com.gifthub.product.entity.Product;
import com.gifthub.product.enumeration.CategoryName;
import com.gifthub.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Long saveProduct(ProductDto productDto) {
        Product product = productRepository.save(productDto.toProductEntity());
        return product.getId();
    }

    public Long saveAll(List<ProductDto> productDtoList) { // 엑셀파일 받아서 한번에 추가

        List<Product> productList = productDtoList.stream()
                .map(ProductDto::toProductEntity)
                .collect(Collectors.toList());


        List<Product> savedProducts = productRepository.saveAll(productList);

        // 총 넣은 productDto 개수를 return

        return (long) savedProducts.size();
    }

    public List<ProductDto> getAllProduct() {
        return productRepository.findAllProduct();
    }

    public Page<ProductDto> getAllProduct(Pageable pageable) {
        return productRepository.findAllProduct(pageable);
    }

    public ProductDto getProduct(Long productId){
        Product product = productRepository.findById(productId).orElse(null);
        return (product != null) ? product.toProductDto() : null;
    }

    public ProductDto getByProductName(String productName) {
        Product product = productRepository.findProductByName(productName).orElse(null);
        return (product != null) ? product.toProductDto() : null;
    }

    public List<String> getBrandName(CategoryName categoryName) {
        return productRepository.findBrandNameByCategory(categoryName);
    }

    public List<ProductDto> getProductByBrand(String brand) {
        return productRepository.findProductByBrand(brand);
    }

    public Page<ProductDto> getProductByBrand(Pageable pageable, String brand) {
        return productRepository.findProductByBrand(pageable, brand);
    }

    public List<ProductDto> getProductByCategory(String category) {
        return productRepository.findProductByCategory(category).stream().map(Product::toProductDto).toList();
    }

    public Page<ProductDto> getProductByCategory(Pageable pageable, String category) {
        return productRepository.findProductByCategory(pageable, category).map(Product::toProductDto);
    }

    public List<String> getAllCategory() {
        return productRepository.findAllCategory();
    }
}
