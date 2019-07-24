package eu.werriz.estateregistry.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

public class EstatePropertyForm implements Serializable {

    private Long id;
    @NotNull
    private BigDecimal size;
    @NotNull
    private BigDecimal marketValue;
    @NotEmpty
    private String type;
    private AddressForm address;
    private OwnerForm owner;


    public EstatePropertyForm() {}

    public EstatePropertyForm(
            Long id,
            BigDecimal size,
            BigDecimal marketValue,
            String type) {
        this.id = id;
        this.size =  size;
        this.marketValue = marketValue;
        this.type = type;
    }


    public Long getId() {
        return id;
    }

    public BigDecimal getSize() {
        return size;
    }

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public String getType() {
        return type;
    }

    public AddressForm getAddress() {
        return address;
    }

    public void setAddress(AddressForm address) {
        this.address = address;
    }

    public OwnerForm getOwner() {
        return owner;
    }

    public void setOwner(OwnerForm owner) {
        this.owner = owner;
    }

}
