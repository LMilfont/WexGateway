package com.gateway.wex.model;

public class Currency 
{
	private String currency = null;
	private String country_currency_desc = null;
	
	public Currency(String currency, String country_currency_desc) 
	{
		this.currency = currency;
		this.country_currency_desc = country_currency_desc;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCountry_currency_desc() {
		return country_currency_desc;
	}

	public void setCountry_currency_desc(String country_currency_desc) {
		this.country_currency_desc = country_currency_desc;
	}

	@Override
	public boolean equals(Object object)
	{
		return ( ((Currency) object).getCurrency().equals(getCurrency()) && ((Currency) object).getCountry_currency_desc().equals(getCountry_currency_desc()) );
	}

}
