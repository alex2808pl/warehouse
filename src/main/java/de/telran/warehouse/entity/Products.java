package de.telran.warehouse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
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
    @Column(name = "productId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "imageURL")
    private String imageURL;

    @Column(name = "discountPrice")
    private BigDecimal discountPrice;

    @CreationTimestamp
    @Column(name = "createdAt")
    private Timestamp createdAt;

    @CreationTimestamp
    @Column(name = "updatedAt")
    private Timestamp updatedAt;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Categories category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Prices> prices = new HashSet<>();

}
