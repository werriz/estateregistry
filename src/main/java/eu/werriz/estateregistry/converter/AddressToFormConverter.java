package eu.werriz.estateregistry.converter;

import eu.werriz.estateregistry.form.AddressForm;
import eu.werriz.estateregistry.model.Address;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AddressToFormConverter implements Converter<Address, AddressForm> {

    @Override
    public AddressForm convert(Address address) {
        return new AddressForm(
                address.getId(),
                address.getStreet(),
                address.getHouseNr(),
                address.getCity()
        );
    }
}
