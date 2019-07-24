package eu.werriz.estateregistry.converter;

import eu.werriz.estateregistry.form.AddressForm;
import eu.werriz.estateregistry.model.Address;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AddressToEntityConverter implements Converter<AddressForm, Address> {

    @Override
    public Address convert(AddressForm form) {
        Address entity = new Address();
        entity.setId(form.getId());
        entity.setStreet(form.getStreet());
        entity.setHouseNr(form.getHouseNr());
        entity.setCity(form.getCity());
        return entity;
    }
}
