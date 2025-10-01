package com.griddynamics.mamaievm.samplestoreapp.controller;

import com.griddynamics.mamaievm.samplestoreapp.domain.CartProduct;
import com.griddynamics.mamaievm.samplestoreapp.domain.CartSummaryDto;
import com.griddynamics.mamaievm.samplestoreapp.domain.OrderDto;
import com.griddynamics.mamaievm.samplestoreapp.dto.CartDto;
import com.griddynamics.mamaievm.samplestoreapp.service.CartService;
import com.griddynamics.mamaievm.samplestoreapp.service.ProductInventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(CartController.BASE_URL)
@RequiredArgsConstructor
public class CartController {

    public static final String BASE_URL = "/api/v1/cart";
    private final CartService cartService;
    private final ProductInventoryService productInventoryService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    CartDto addProductToCart(@RequestBody @NonNull @Valid CartProduct cartProduct) {
        return cartService.addProductToCart(cartProduct);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    CartSummaryDto getCartContents() {
        return cartService.getCartContents();
    }

    @DeleteMapping("/remove/{itemNo}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    CartSummaryDto removeProductFromCart(@PathVariable @NonNull String itemNo) {
        int index = -1;
        try {
            index = Integer.parseInt(itemNo);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        if (index < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, itemNo + " is not a valid item number");
        return cartService.removeProductFromCart(index);
    }

    @PutMapping("/update/{itemNo}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    CartSummaryDto updateCartProduct(@PathVariable @NonNull Integer itemNo, @RequestBody @NonNull @Valid CartProduct cartProduct) {
        if (itemNo < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, itemNo + " is not a valid item number");
        return cartService.updateCartProduct(itemNo, cartProduct);
    }

    @GetMapping("/checkout")
    @ResponseStatus(HttpStatus.OK)
    OrderDto checkout() {
        try {
            if (productInventoryService.checkInventory()) {
                return cartService.checkout();
            } else
                throw new ResponseStatusException(HttpStatus.INSUFFICIENT_STORAGE, "Not enough inventory");
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
