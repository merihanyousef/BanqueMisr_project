package com.gp.currencyexchange.service.serviceImpl;

import com.gp.currencyexchange.dto.response.ConversionDto;
import com.gp.currencyexchange.feignClient.Exchange;
import com.gp.currencyexchange.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


@RequiredArgsConstructor
@Service
public class ExchangeServiceImpl implements ExchangeService {

    private final Exchange exchange;


    public ConversionDto convert(String base, String target, String amount) {
        ConversionDto pair =  exchange.getRate(base, target);
        pair.setConversion_rate((Double.parseDouble(pair.getConversion_rate()) * Double.parseDouble(amount)) + "");
        pair.setAmount(amount);
        return pair;
    }







}
