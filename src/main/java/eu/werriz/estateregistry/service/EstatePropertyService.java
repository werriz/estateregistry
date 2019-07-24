package eu.werriz.estateregistry.service;

import eu.werriz.estateregistry.form.EstatePropertyForm;

import java.util.List;

public interface EstatePropertyService {

    List<EstatePropertyForm> getEstates();

    void delete(Long id);

    Long createUpdate(EstatePropertyForm form);
}
