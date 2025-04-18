package com.griddynamics.mamaievm.samplestoreapp.service;

public interface ProductInventoryService {
    
    void adjustInventory(long id, int oldQuantity, int newQuantity);
    
    boolean isProductInventoryAvailable(long id, int quantity);
    
    boolean checkInventory();

}
