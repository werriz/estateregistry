package eu.werriz.estateregistry.service.impl;

import eu.werriz.estateregistry.converter.EstatePropertyToEntityConverter;
import eu.werriz.estateregistry.converter.EstatePropertyToFormConverter;
import eu.werriz.estateregistry.form.EstatePropertyForm;
import eu.werriz.estateregistry.model.EstateProperty;
import eu.werriz.estateregistry.repository.EstatePropertyRepository;
import eu.werriz.estateregistry.service.EstatePropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class EstatePropertyServiceImpl implements EstatePropertyService
{

    private final EstatePropertyRepository repository;
    private final EstatePropertyToFormConverter toFormConverter;
    private final EstatePropertyToEntityConverter toEntityConverter;

    @Autowired
    public EstatePropertyServiceImpl(final EstatePropertyRepository repository,
                                     final EstatePropertyToFormConverter toFormConverter,
                                     final EstatePropertyToEntityConverter toEntityConverter) {
        this.repository = repository;
        this.toFormConverter = toFormConverter;
        this.toEntityConverter = toEntityConverter;
    }

    @Override
    @Transactional
    public List<EstatePropertyForm> getEstates()
    {
        return toFormConverter.convertAll(repository.findAll());
    }

    @Override
    @Transactional
    public Long createUpdate(EstatePropertyForm form) {
        EstateProperty savedObject = repository.save(toEntityConverter.convert(form));
        return savedObject.getId();
    }


    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
