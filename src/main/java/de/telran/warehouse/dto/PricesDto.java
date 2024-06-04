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
public class PricesDto {
    private long priceId;
    private Timestamp changeAt;
    private BigDecimal price;
    private Timestamp createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("productId")
    private ProductsDto product;
}
