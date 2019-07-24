package eu.werriz.estateregistry.model;

public enum EstatePropertyType
{
	APARTMENT(1.5d),
	HOUSE(2d),
	INDUSTRIAL(2.5d),
	COMMERCIAL(3d),
	FARMLAND(1d);

	private Double rate;

	EstatePropertyType(Double rate) {
		this.rate = rate;
	}

	public Double getRate() {
		return rate;
	}
}
