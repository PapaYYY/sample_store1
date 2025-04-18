package com.griddynamics.mamaievm.samplestoreapp.mapper;

import com.griddynamics.mamaievm.samplestoreapp.dto.ProductDto;
import com.griddynamics.mamaievm.samplestoreapp.entity.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    @Mapping(source = "productInventoryAvailable",
            target = "productInventory.available")
    Product toEntity(ProductDto productDto);

    @Mapping(source = "productInventory.available",
            target = "productInventoryAvailable")
    ProductDto toDto(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "productInventoryAvailable",
            target = "productInventory.available")
    Product partialUpdate(ProductDto productDto, @MappingTarget Product product);

}