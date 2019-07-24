package eu.werriz.estateregistry.service;

import java.math.BigDecimal;

public interface CalculateTaxesService {

    BigDecimal calculateTaxesForOwner(Long ownerId);
}
