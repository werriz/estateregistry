package eu.werriz.estateregistry.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

public class AddressForm {

    private Long id;
    @NotEmpty
    private String street;
    @Positive
    private int houseNr;
    private String city;

    public AddressForm() {}

    public AddressForm(Long id, String street, int houseNr, String city) {
        this.id = id;
        this.street = street;
        this.houseNr = houseNr;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public int getHouseNr() {
        return houseNr;
    }

    public String getCity() {
        return city;
    }
}
