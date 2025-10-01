package com.griddynamics.mamaievm.samplestoreapp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Long id;

    @Column(name = "title")
    @NotEmpty
    private String title;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "description")
    @Lob
    private BigDecimal description;

    @Column(name = "image")
    @Lob
    private Byte[] image;
    
    @Enumerated(value = EnumType.STRING)
    private ProductType productType;

    @OneToOne(mappedBy = "product",
            cascade = CascadeType.ALL,
            optional = false,
            orphanRemoval = true)
    private ProductInventory productInventory;

    @OneToMany(mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<OrderItem> orderItems = new LinkedHashSet<>();

    public void setProductInventory(ProductInventory productInventory) {
        productInventory.setProduct(this);
        this.productInventory = productInventory;
    }
}