package de.telran.warehouse.service;

import de.telran.warehouse.dto.PricesDto;
import de.telran.warehouse.entity.Prices;
import de.telran.warehouse.repository.PricesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PricesService {
    private final PricesRepository pricesRepository;
    private final ProductsService productsService;

    private PricesDto toDTO(Prices prices) {
        PricesDto pricesDto = new PricesDto();
        pricesDto.setPriceId(prices.getPriceId());
        pricesDto.setChangeAt(prices.getChangeAt());
        pricesDto.setPrice(prices.getPrice());
        pricesDto.setCreatedAt(prices.getCreatedAt());
      //  pricesDto.setProduct(productsService.toDTO(prices.getProduct()));
        return pricesDto;
    }

    private Prices toEntity(PricesDto pricesDto) {
        Prices prices = new Prices();
        prices.setPriceId(pricesDto.getPriceId());
        prices.setChangeAt(pricesDto.getChangeAt());
        prices.setPrice(pricesDto.getPrice());
        prices.setCreatedAt(pricesDto.getCreatedAt());
      //  prices.setProduct(productsService.toEntity(pricesDto.getProduct()));
        return prices;
    }

    public List<PricesDto> getPrices() {
        List<Prices> pricesList = pricesRepository.findAll();

        return pricesList.stream()
                .map(f -> toDTO(f))
                .collect(Collectors.toList());
    }

    public PricesDto getPricesById(Long id) {
        Optional<Prices> pricesOptional = pricesRepository.findById(id);
        PricesDto pricesDto = null;
        if (pricesOptional.isPresent()) {
            Prices price = pricesOptional.get();
            pricesDto = toDTO(price);
        }
        return pricesDto;
    }

    public void deletePriceById(Long id) {
        Optional<Prices> prices = pricesRepository.findById(id);
        if (prices.isPresent()) {
            pricesRepository.delete(prices.get());
        }
    }

    public PricesDto insertPrice(PricesDto pricesDto) {
        Prices newPrices = toEntity(pricesDto);
        pricesRepository.save(newPrices);
        return pricesDto;
    }

    public PricesDto updatePrice(PricesDto pricesDto) {
        if (pricesDto.getPriceId() <= 0) {
            return null;
        }
        Optional<Prices> pricesOptional = pricesRepository.findById(pricesDto.getPriceId());
        if (!pricesOptional.isPresent()) {
            return null;
        }
        Prices prices = toEntity(pricesDto);
        Prices savedPrices = pricesRepository.save(prices);

        return toDTO(savedPrices);
    }
}
