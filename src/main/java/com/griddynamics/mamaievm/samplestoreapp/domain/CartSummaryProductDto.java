package com.griddynamics.mamaievm.samplestoreapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CartSummaryProductDto {
    Long id;
    Integer no;
    String title;
    Integer quantity;
}
