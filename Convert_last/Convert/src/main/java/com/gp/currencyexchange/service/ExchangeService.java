package com.gp.currencyexchange.service;

import com.gp.currencyexchange.dto.response.ConversionDto;

import java.util.List;

public interface ExchangeService {



    ConversionDto convert(String base, String target, String amount);

   

}
