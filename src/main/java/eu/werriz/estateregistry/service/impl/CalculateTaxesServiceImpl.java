package eu.werriz.estateregistry.service.impl;

import eu.werriz.estateregistry.model.EstateProperty;
import eu.werriz.estateregistry.repository.EstatePropertyRepository;
import eu.werriz.estateregistry.service.CalculateTaxesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CalculateTaxesServiceImpl implements CalculateTaxesService {

    private final EstatePropertyRepository estatePropertyRepository;

    @Autowired
    public CalculateTaxesServiceImpl(final EstatePropertyRepository estatePropertyRepository) {
        this.estatePropertyRepository = estatePropertyRepository;
    }

    @Override
    @Transactional
    public BigDecimal calculateTaxesForOwner(Long ownerId) {
        List<EstateProperty> ownerProperties = estatePropertyRepository.findAllByOwnerId(ownerId);
        BigDecimal sum = new BigDecimal(0);
        for (EstateProperty property : ownerProperties) {
            BigDecimal tax = property.getMarketValue().multiply(
                    new BigDecimal(property.getType().getRate()));
            sum = sum.add(tax);
        }
        return sum;
    }
}
