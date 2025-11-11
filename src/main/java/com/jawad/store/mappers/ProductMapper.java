package com.jawad.store.mappers;

import com.jawad.store.dtos.ProductDto;
import com.jawad.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    // When converting from Product to ProductDto, take the value of product.getCategory().getId() and put it into productDto.setCategoryId()
    @Mapping(target="categoryId",source="category.id")
    ProductDto toDto(Product product);

    Product toEntity(ProductDto request);


    //target is product entity from product DTO
    //productDto -> product
    //productDto don't have id ,so user id become like productDto id=null
    //we should ignore mapping id
    @Mapping(target="id",ignore=true)
    void update(ProductDto request, @MappingTarget Product product);

}
