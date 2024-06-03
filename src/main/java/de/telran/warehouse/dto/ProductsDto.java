package de.telran.warehouse.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductsDto {
    private long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageURL;
    private BigDecimal discountPrice;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int quantity;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("category")
    private CategoriesDto category;
}
