package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyController {
    private static final String EXTERNAL_API_URL = "https://v6.exchangerate-api.com/v6/ecf10bab01b34bf0de9636e1/latest/USD";

    @GetMapping("/currencies")
    public ResponseEntity<Object> getCurrencies() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(EXTERNAL_API_URL, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                // Parse JSON response
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.getBody());

                // Get the conversion rates node
                JsonNode conversionRatesNode = rootNode.get("conversion_rates");

                // Create a map to hold currency codes and conversion rates
                Map<String, Double> currencyMap = new HashMap<>();

                // Loop through all the specified currency codes
                for (CurrencyCode currencyCode : CurrencyCode.values()) {
                    String code = currencyCode.name();
                    if (conversionRatesNode.has(code)) {
                        double conversionRate = conversionRatesNode.get(code).asDouble();
                        currencyMap.put(code, conversionRate);
                    }
                }

                return ResponseEntity.ok(currencyMap);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body("Failed to parse currency data.");
            }
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Failed to fetch currency data.");
        }
    }
}
