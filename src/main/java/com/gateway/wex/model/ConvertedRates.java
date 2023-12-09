package com.gateway.wex.model;

public class ConvertedRates 
{
    private String exchangeRate;
    private String convertedAmount;
   
    public ConvertedRates()
    {}
    
    public ConvertedRates(String exchangeRate, String convertedAmount)
    {
	   this.exchangeRate = exchangeRate;
	   this.convertedAmount = convertedAmount;	   
    }

	public String getExchangeRate() {
		return exchangeRate;
	}
	
	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	public String getConvertedAmount() {
		return convertedAmount;
	}
	
	public void setConvertedAmount(String convertedAmount) {
		this.convertedAmount = convertedAmount;
	}
	   
   
   
}
