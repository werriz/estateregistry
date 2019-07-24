package eu.werriz.estateregistry.converter;

import eu.werriz.estateregistry.form.EstatePropertyForm;
import eu.werriz.estateregistry.form.OwnerForm;
import eu.werriz.estateregistry.model.EstateProperty;
import eu.werriz.estateregistry.model.Owner;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstatePropertyToFormConverter implements Converter<EstateProperty, EstatePropertyForm> {

    private final AddressToFormConverter addressConverter;

    public EstatePropertyToFormConverter(final AddressToFormConverter addressConverter) {
        this.addressConverter = addressConverter;
    }


    @Override
    public EstatePropertyForm convert(final EstateProperty estateProperty) {
        final EstatePropertyForm form = new EstatePropertyForm(
                estateProperty.getId(),
                estateProperty.getSize(),
                estateProperty.getMarketValue(),
                estateProperty.getType().toString()
        );

        if (estateProperty.getAddress() != null) {
            form.setAddress(addressConverter.convert(estateProperty.getAddress()));
        }

        if (estateProperty.getOwner() != null) {
            final Owner owner = estateProperty.getOwner();
            form.setOwner(new OwnerForm(
                    owner.getId(),
                    owner.getName(),
                    owner.getSurname(),
                    owner.getPersonalCode(),
                    addressConverter.convert(owner.getAddress())
            ));
        }

        return form;
    }

    public List<EstatePropertyForm> convertAll(Collection<EstateProperty> estateProperties) {
        if (CollectionUtils.isEmpty(estateProperties)) {
            return Collections.emptyList();
        } else {
            return estateProperties.stream().map(this::convert)
                .collect(Collectors.toList());
        }
    }
}
