package eu.werriz.estateregistry.model;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS")
public class Address
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String street;
	private int houseNr;
	private String city;

	@OneToOne(mappedBy = "address")
	private EstateProperty estateProperty;

	@OneToOne(mappedBy = "address")
	private Owner owner;

	public Long getId()
	{
		return id;
	}

	public void setId(final Long id)
	{
		this.id = id;
	}

	public String getStreet()
	{
		return street;
	}

	public void setStreet(final String street)
	{
		this.street = street;
	}

	public int getHouseNr()
	{
		return houseNr;
	}

	public void setHouseNr(final int houseNr)
	{
		this.houseNr = houseNr;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(final String city)
	{
		this.city = city;
	}

	public EstateProperty getEstateProperty() {
		return estateProperty;
	}

	public void setEstateProperty(EstateProperty estateProperty) {
		this.estateProperty = estateProperty;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}
}
