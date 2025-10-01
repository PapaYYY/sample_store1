package com.griddynamics.mamaievm.samplestoreapp.service;

import com.griddynamics.mamaievm.samplestoreapp.domain.CartProduct;
import com.griddynamics.mamaievm.samplestoreapp.domain.CartSummaryDto;
import com.griddynamics.mamaievm.samplestoreapp.domain.OrderDto;
import com.griddynamics.mamaievm.samplestoreapp.dto.CartDto;

public interface CartService {
    CartDto addProductToCart(CartProduct cartProduct);

    CartSummaryDto removeProductFromCart(int itemNo);
    
    CartSummaryDto updateCartProduct(int itemNo, CartProduct cartProduct);
    
    void clearCart();
    
    CartSummaryDto getCartContents();
    
    OrderDto checkout();
    
    void initCartOrder();
}
