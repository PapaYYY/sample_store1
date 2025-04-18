package com.griddynamics.mamaievm.samplestoreapp.domain;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"quantity"})
public class CartProduct implements Serializable {
    Long id;
    
    Integer quantity;

}