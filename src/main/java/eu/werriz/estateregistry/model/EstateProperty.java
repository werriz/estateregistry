package eu.werriz.estateregistry.model;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
public class EstateProperty
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;

	@ManyToOne(cascade = CascadeType.ALL)
	private Owner owner;
	private BigDecimal size;

	private BigDecimal marketValue;

	@Enumerated
	@Column(columnDefinition = "smallint")
	private EstatePropertyType type;

	public Long getId()
	{
		return id;
	}

	public void setId(final Long id)
	{
		this.id = id;
	}

	public Address getAddress()
	{
		return address;
	}

	public void setAddress(final Address address)
	{
		this.address = address;
	}

	public Owner getOwner()
	{
		return owner;
	}

	public void setOwner(final Owner owner)
	{
		this.owner = owner;
	}

	public BigDecimal getSize()
	{
		return size;
	}

	public void setSize(final BigDecimal size)
	{
		this.size = size;
	}

	public BigDecimal getMarketValue()
	{
		return marketValue;
	}

	public void setMarketValue(final BigDecimal marketValue)
	{
		this.marketValue = marketValue;
	}

	public EstatePropertyType getType()
	{
		return type;
	}

	public void setType(final EstatePropertyType type)
	{
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EstateProperty)) {
			return false;
		}
		return this.id.equals(((EstateProperty) obj).getId());
	}
}
