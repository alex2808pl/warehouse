package de.telran.warehouse.service;

import de.telran.warehouse.config.MapperUtil;
import de.telran.warehouse.dto.PricesDto;
import de.telran.warehouse.entity.Prices;
import de.telran.warehouse.mapper.Mappers;
import de.telran.warehouse.repository.PricesRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PricesService {
    private final PricesRepository pricesRepository;
    private final Mappers mappers;


    public List<PricesDto> getPrices() {
        List<Prices> pricesList = pricesRepository.findAll();
        List<PricesDto> pricesDtoList = MapperUtil.convertList(pricesList, mappers::convertToPricesDto);

        return pricesDtoList;
    }

    public PricesDto getPricesById(Long id) {
        Optional<Prices> pricesOptional = pricesRepository.findById(id);
        PricesDto pricesDto = null;
        if (pricesOptional.isPresent()) {
            pricesDto = pricesOptional.map(mappers :: convertToPricesDto).orElse(null);
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
        Prices newPrices = mappers.converToPrices(pricesDto);
        newPrices.setPriceId(0);
        Prices savedPrices = pricesRepository.save(newPrices);
        return mappers.convertToPricesDto(savedPrices);
    }

    public PricesDto updatePrice(PricesDto pricesDto) {
        if (pricesDto.getPriceId() <= 0) {
            return null;
        }
        Optional<Prices> pricesOptional = pricesRepository.findById(pricesDto.getPriceId());
        if (!pricesOptional.isPresent()) {
            return null;
        }
        Prices prices = pricesOptional.get();
        prices.setPrice(pricesDto.getPrice());
        Prices savedPrices = pricesRepository.save(prices);

        return mappers.convertToPricesDto(savedPrices);
    }
}
