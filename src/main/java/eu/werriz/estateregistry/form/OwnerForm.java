package eu.werriz.estateregistry.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class OwnerForm {

    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @Size(min=11, max=11)
    private String personalCode;
    private AddressForm address;

    public OwnerForm() {}

    public OwnerForm(
            final Long id,
            final String name,
            final String surname,
            final String personalCode,
            final AddressForm address
    ) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.personalCode = personalCode;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AddressForm getAddress() {
        return address;
    }

    public String getSurname() {
        return surname;
    }

    public String getPersonalCode() {
        return personalCode;
    }

}
