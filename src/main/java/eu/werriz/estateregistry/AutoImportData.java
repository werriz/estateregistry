package eu.werriz.estateregistry;

import eu.werriz.estateregistry.model.Address;
import eu.werriz.estateregistry.model.EstateProperty;
import eu.werriz.estateregistry.model.EstatePropertyType;
import eu.werriz.estateregistry.model.Owner;
import eu.werriz.estateregistry.repository.EstatePropertyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * This class is not for reviewing, it is made to generate starting data,
 * so clean code is not something I followed here :)
 */
@Component
public class AutoImportData implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(AutoImportData.class);

    private static final Double HOUSE_VALUE = 54000.00;
    private static final Double APARTMENT_VALUE = 125000.00;

    private final EstatePropertyRepository estatePropertyRepository;

    @Autowired
    public AutoImportData(final EstatePropertyRepository estatePropertyRepository) {
        this.estatePropertyRepository = estatePropertyRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        logger.trace("Starting auto import");
        importEstateProperties();
        logger.info("Auto-importing data completed.");
    }

    public static List<EstateProperty> getInitialData() {
        EstateProperty estateProperty1 = new EstateProperty();
        estateProperty1.setSize(new BigDecimal(80.00));
        estateProperty1.setMarketValue(new BigDecimal(APARTMENT_VALUE));
        estateProperty1.setType(EstatePropertyType.APARTMENT);

        Address address1 = new Address();
        address1.setCity("Vilnius");
        address1.setStreet("Zalgirio");
        address1.setHouseNr(97);
        estateProperty1.setAddress(address1);

        Owner owner1 = new Owner();
        owner1.setName("Vardenis");
        owner1.setSurname("Pavardenis");
        owner1.setPersonalCode("38801011122");
        owner1.setAddress(address1);
        estateProperty1.setOwner(owner1);

        EstateProperty estateProperty2 = new EstateProperty();
        estateProperty2.setSize(new BigDecimal(85.00));
        estateProperty2.setMarketValue(new BigDecimal(HOUSE_VALUE));
        estateProperty2.setType(EstatePropertyType.HOUSE);

        Address address2 = new Address();
        address2.setCity("Siauliai");
        address2.setStreet("Aukstelke");
        address2.setHouseNr(1);
        estateProperty2.setAddress(address2);
        estateProperty2.setOwner(owner1);

        Owner anotherOwner = new Owner();
        anotherOwner.setName("Saulius");
        anotherOwner.setSurname("Poska");
        anotherOwner.setPersonalCode("37912317777");
        Address anotherAddress = new Address();
        anotherAddress.setCity("Vilnius");
        anotherAddress.setStreet("Turniskiu");
        anotherAddress.setHouseNr(1);
        anotherOwner.setAddress(anotherAddress);
        Stream<EstateProperty> estateStream = IntStream.range(5, 13).mapToObj(index -> {
            EstateProperty estate = new EstateProperty();
            boolean isHouse = index % 2 == 0;
            estate.setOwner(anotherOwner);
            estate.setMarketValue(new BigDecimal(isHouse ? HOUSE_VALUE + index * 100 : APARTMENT_VALUE + index * 100));
            estate.setSize(new BigDecimal(70.00 + index));
            estate.setType(isHouse ? EstatePropertyType.HOUSE : EstatePropertyType.APARTMENT);
            Address address = new Address();
            address.setCity("Vilnius");
            address.setStreet("Zalgirio");
            address.setHouseNr(index);
            estate.setAddress(address);
            return estate;
        });

        return Stream.concat(Stream.of(estateProperty1, estateProperty2), estateStream)
                .collect(Collectors.toList());
    }
    private void importEstateProperties() {
        estatePropertyRepository.saveAll(getInitialData());
    }
}
