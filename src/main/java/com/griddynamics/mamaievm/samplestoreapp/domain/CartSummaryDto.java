package com.griddynamics.mamaievm.samplestoreapp.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CartSummaryDto {
    List<CartSummaryProductDto> products = new ArrayList<>();
    BigDecimal subtotal;
}
