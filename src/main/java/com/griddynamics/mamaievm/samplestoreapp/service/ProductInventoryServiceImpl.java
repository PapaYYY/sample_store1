package com.griddynamics.mamaievm.samplestoreapp.service;

import com.griddynamics.mamaievm.samplestoreapp.domain.Cart;
import com.griddynamics.mamaievm.samplestoreapp.domain.CartProduct;
import com.griddynamics.mamaievm.samplestoreapp.entity.ProductInventory;
import com.griddynamics.mamaievm.samplestoreapp.repository.ProductInventoryRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@Profile({"sql", "default", "test-data"}) // to specify the profiles for which this bean is injected
@RequiredArgsConstructor
public class ProductInventoryServiceImpl implements ProductInventoryService {
    
    private final ProductInventoryRepository productInventoryRepository;
    private final Cart cart;

    @Override
    public void adjustInventory(long id, int oldQuantityReserved, int newQuantityReserved) {
        ProductInventory productInventory = productInventoryRepository.findById(id).orElseThrow();
        productInventory.setAvailable(productInventory.getAvailable() + oldQuantityReserved - newQuantityReserved);
        productInventoryRepository.save(productInventory);
    }

    @Override
    public boolean isProductInventoryAvailable(long id, int quantity) {
        ProductInventory productInventory = productInventoryRepository.findById(id).orElseThrow();
        return productInventory.getAvailable() > quantity;
    }

    @Override
    public boolean checkInventory() {
        if(cart.getProducts().isEmpty()) throw new IllegalStateException("The cart is empty. Checkout is not possible!");
        List<Long> cartProductIdList = cart.getProducts().stream().map(CartProduct::getId).toList();
        List<ProductInventory> productInventoryList = productInventoryRepository.findAllById(cartProductIdList);
        for(ProductInventory productInventory : productInventoryList) {
            if(productInventory.getAvailable() < 0) return false;
        }
        return true;
    }
}
