package de.telran.warehouse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Categories {
    @Id
    @Column(name = "CategoryID") //имя поля в БД
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryID;

    @Column(name = "Name")
    private String name;

//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
//    private Set<Products> products = new HashSet<>();

}
