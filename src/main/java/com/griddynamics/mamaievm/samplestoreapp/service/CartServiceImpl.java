package com.griddynamics.mamaievm.samplestoreapp.service;

import com.griddynamics.mamaievm.samplestoreapp.domain.Cart;
import com.griddynamics.mamaievm.samplestoreapp.domain.CartProduct;
import com.griddynamics.mamaievm.samplestoreapp.domain.CartSummaryDto;
import com.griddynamics.mamaievm.samplestoreapp.domain.CartSummaryProductDto;
import com.griddynamics.mamaievm.samplestoreapp.dto.CartDto;
import com.griddynamics.mamaievm.samplestoreapp.exception.ProductNotAvailableException;
import com.griddynamics.mamaievm.samplestoreapp.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {
    
    private final Cart cart;
    private final ProductRepository productRepository;
    private final ProductInventoryService productInventoryService;

    @Override
    public CartDto addProductToCart(CartProduct cartProduct) {
        if(productInventoryService.isProductInventoryAvailable(cartProduct.getId(), cartProduct.getQuantity())) {
            addProduct(cartProduct);
        }
        else throw new ProductNotAvailableException("Product is not available");
        productInventoryService.adjustInventory(cartProduct.getId(), 0, cartProduct.getQuantity());
        return CartDto.builder().products(cart.getProducts())
                .build();
    }

    @Override
    public CartSummaryDto removeProductFromCart(int index) {
        CartProduct cartProduct = cart.getProducts().get(index - 1);
        productInventoryService.adjustInventory(cartProduct.getId(), cartProduct.getQuantity(), 0);
        cart.getProducts().remove(index - 1);
        return getCartContents();
    }

    @Override
    public CartSummaryDto updateCartProduct(int index, CartProduct cartProduct) {
        if(index < 0 || index > cart.getProducts().size()) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, index + " is not a valid item number");
        CartProduct productInCart = cart.getProducts()
                .get(index - 1);
        if(Objects.equals(cartProduct.getId(), productInCart.getId())) {
            if (productInventoryService.isProductInventoryAvailable(cartProduct.getId(),
                    cartProduct.getQuantity() - productInCart.getQuantity())) {
                productInventoryService.adjustInventory(cartProduct.getId(), productInCart.getQuantity(), cartProduct.getQuantity());
                productInCart.setQuantity(cartProduct.getQuantity());
            }
            if(cartProduct.getQuantity() == 0 ) removeProductFromCart(index - 1);
        } else  throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product id does not match");
        return getCartContents();
    }

    @Override
    public void clearCart() {

    }

    @Override
    public CartSummaryDto getCartContents() {
        List<CartSummaryProductDto> cartProducts = cart.getProducts().stream()
                .map(cartProduct -> CartSummaryProductDto.builder()
                        .title(productRepository.findById(cartProduct.getId()).orElseThrow().getTitle())
                        .quantity(cartProduct.getQuantity())
                        .id(cartProduct.getId())
                        .build()).toList();
        BigDecimal subtotal = cart.getProducts().stream()
                .map(cartProduct -> 
                        productRepository.findById(cartProduct.getId()).orElseThrow().getPrice()
                                .multiply(BigDecimal.valueOf(cartProduct.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        for(int i = 0; i < cartProducts.size(); i++) cartProducts.get(i).setNo(i+1);
        return CartSummaryDto.builder()
                .products(cartProducts)
                .subtotal(subtotal)
                .build();
    }

    @Override
    public CartSummaryDto checkout() {
        cart.getProducts().clear();
        return getCartContents();
    }

    private void addProduct(CartProduct cartProduct) {
        if(cart.getProducts().contains(cartProduct)) {
            Optional<CartProduct> productInCart = cart.getProducts().stream()
                    .filter(item -> item.equals(cartProduct))
                    .findFirst();
            productInCart.get().setQuantity(productInCart.get().getQuantity()+cartProduct.getQuantity());
        } else {
            cart.getProducts().add(cartProduct);
        }
    }

}
