package com.griddynamics.mamaievm.samplestoreapp.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Value;

/**
 * DTO for {@link com.griddynamics.mamaievm.samplestoreapp.entity.Product}
 */
@Value
public class ProductDto implements Serializable {

    Long id;
    String title;
    BigDecimal price;
    Integer productInventoryAvailable;

}