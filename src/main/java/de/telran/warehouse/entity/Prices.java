package de.telran.warehouse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "Prices")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Prices {
    @Id
    @Column(name = "PriceId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long priceId;

    @Column(name = "changeAt")
    private Timestamp changeAt;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "createdAt")
    private Timestamp createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false)
    private Products product;
}
