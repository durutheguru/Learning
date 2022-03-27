package com.julianduru.learning.reactive.ms.productreactiveservice.util;

import com.julianduru.learning.reactive.ms.productreactiveservice.dto.ProductDto;
import com.julianduru.learning.reactive.ms.productreactiveservice.entity.Product;
import org.springframework.beans.BeanUtils;

/**
 * created by julian on 19/03/2022
 */
public class EntityDtoUtil {


    public static ProductDto toDto(Product product) {
        var dto = new ProductDto();

        dto.setId(product.getId());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());

        return dto;
    }


    public static Product toEntity(ProductDto dto) {
        var product = new Product();
        BeanUtils.copyProperties(dto, product);

        return product;
    }


}
