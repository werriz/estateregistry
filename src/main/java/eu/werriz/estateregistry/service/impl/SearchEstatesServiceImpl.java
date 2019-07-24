package eu.werriz.estateregistry.service.impl;

import eu.werriz.estateregistry.converter.EstatePropertyToFormConverter;
import eu.werriz.estateregistry.form.EstatePropertyForm;
import eu.werriz.estateregistry.model.EstateProperty;
import eu.werriz.estateregistry.repository.EstatePropertyRepository;
import eu.werriz.estateregistry.service.SearchEstatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SearchEstatesServiceImpl implements SearchEstatesService {

    private final EstatePropertyRepository estatePropertyRepository;
    private final EstatePropertyToFormConverter estatePropertyToFormConverter;

    @Autowired
    public SearchEstatesServiceImpl(final EstatePropertyRepository estatePropertyRepository,
                                    final EstatePropertyToFormConverter estatePropertyToFormConverter) {
        this.estatePropertyRepository = estatePropertyRepository;
        this.estatePropertyToFormConverter = estatePropertyToFormConverter;
    }

    @Override
    @Transactional
    public List<EstatePropertyForm> searchSimilarEstates(Long estateId) {
        EstateProperty currentEstateProperty = estatePropertyRepository.findById(estateId)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Cannot find estate property with Id: '%s'", estateId)));


        List<EstateProperty> similarEstateProperties =
                estatePropertyRepository.findSimilarProperties(currentEstateProperty.getId(),
                        currentEstateProperty.getAddress().getCity(),
                        currentEstateProperty.getAddress().getStreet(),
                        currentEstateProperty.getType(),
                        currentEstateProperty.getSize(),
                        PageRequest.of(0, 3, Sort.Direction.DESC, "marketValue"));

        return estatePropertyToFormConverter.convertAll(similarEstateProperties);
    }
}
