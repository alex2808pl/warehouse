package de.telran.warehouse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Products {

    @Id
    @Column(name = "ProductID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productID;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "Price")
    private double price;

    @Column(name = "CategoryID")
    private int categoryID;

    @Column(name = "ImageURL")
    private String imageURL;

    @Column(name = "DiscountPrice")
    private double discountPrice;

    @Column(name = "CreatedAt")
    private Timestamp createdAt;

    @Column(name = "UpdatedAt")
    private Timestamp updatedAt;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "CategoryID")
//    private Categories category;
//
//    @OneToMany(mappedBy = "product")
//    private Set<OrderItems> orderItem = new HashSet<>();
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    private Set<Favorites> favorites = new HashSet<>();
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    private Set<OrderItems> orderItems = new HashSet<>();
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    private Set<CartItems> cartItems = new HashSet<>();

}
