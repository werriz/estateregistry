package eu.werriz.estateregistry.converter;

import eu.werriz.estateregistry.form.EstatePropertyForm;
import eu.werriz.estateregistry.form.OwnerForm;
import eu.werriz.estateregistry.model.EstateProperty;
import eu.werriz.estateregistry.model.EstatePropertyType;
import eu.werriz.estateregistry.model.Owner;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EstatePropertyToEntityConverter implements Converter<EstatePropertyForm, EstateProperty> {

    private final AddressToEntityConverter addressConverter;

    public EstatePropertyToEntityConverter(final AddressToEntityConverter addressConverter) {
        this.addressConverter = addressConverter;
    }

    @Override
    public EstateProperty convert(EstatePropertyForm form) {
        EstateProperty entity = new EstateProperty();
        entity.setId(form.getId());
        entity.setSize(form.getSize());
        entity.setMarketValue(form.getMarketValue());
        entity.setType(EstatePropertyType.valueOf(form.getType()));

        if (form.getAddress() != null) {
            entity.setAddress(addressConverter.convert(form.getAddress()));
        }

        if (form.getOwner() != null) {
            final OwnerForm ownerForm = form.getOwner();
            Owner owner = new Owner();
            owner.setId(ownerForm.getId());
            owner.setAddress(addressConverter.convert(ownerForm.getAddress()));
            owner.setName(ownerForm.getName());
            owner.setSurname(ownerForm.getSurname());
            owner.setPersonalCode(ownerForm.getPersonalCode());
            entity.setOwner(owner);
        }
        return entity;
    }

}
