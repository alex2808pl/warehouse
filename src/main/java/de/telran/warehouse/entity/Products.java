package de.telran.warehouse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity // говорит что эта сущьность работает с базой данных
@Table(name = "Products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Products {

    @Id
    @Column(name = "ProductId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "Price")
    private double price;

//    @Column(name = "CategoryID")
//    private long categoryID;

    @Column(name = "ImageURL")
    private String imageURL;

    @Column(name = "DiscountPrice")
    private double discountPrice;

    @Column(name = "CreatedAt")
    private Timestamp createdAt;

    @Column(name = "UpdatedAt")
    private Timestamp updatedAt;

    @Column(name = "Quantity")
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryId")
    private Categories category;

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
