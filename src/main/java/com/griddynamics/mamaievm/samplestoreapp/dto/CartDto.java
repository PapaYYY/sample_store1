package com.griddynamics.mamaievm.samplestoreapp.dto;

import com.griddynamics.mamaievm.samplestoreapp.domain.CartProduct;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    public List<CartProduct> products = new ArrayList<>();
}
