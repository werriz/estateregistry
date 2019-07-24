package eu.werriz.estateregistry.service;

import eu.werriz.estateregistry.form.EstatePropertyForm;

import java.util.List;

public interface SearchEstatesService {

    List<EstatePropertyForm> searchSimilarEstates(Long estateId);
}
