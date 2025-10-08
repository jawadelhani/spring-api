package com.jawad.store.mappers;

import com.jawad.store.dtos.ProductDto;
import com.jawad.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface   ProductMapper {

    // When converting from Product to ProductDto, take the value of product.getCategory().getId() and put it into productDto.setCategoryId()
    @Mapping(target="categoryId",source="category.id")
    ProductDto toDto(Product product);
}
