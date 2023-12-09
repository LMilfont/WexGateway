package com.gateway.wex.model;

public class RateOfExchangeDTO 
{
	private String record_date;
	private String country;
	private String currency;
	private String country_currency_desc;
	private String exchange_rate;
	private String effective_date;
	private String src_line_nbr;
	private String record_fiscal_year;
	private String record_fiscal_quarter;
	private String record_calendar_year;
	private String record_calendar_quarter;
	private String record_calendar_month;
	private String record_calendar_day;
	
	public RateOfExchangeDTO(String record_date, String country, String currency, String country_currency_desc,
			String exchange_rate, String effective_date, String src_line_nbr, String record_fiscal_year,
			String record_fiscal_quarter, String record_calendar_year, String record_calendar_quarter,
			String record_calendar_month, String record_calendar_day) 
	{

		this.record_date = record_date;
		this.country = country;
		this.currency = currency;
		this.country_currency_desc = country_currency_desc;
		this.exchange_rate = exchange_rate;
		this.effective_date = effective_date;
		this.src_line_nbr = src_line_nbr;
		this.record_fiscal_year = record_fiscal_year;
		this.record_fiscal_quarter = record_fiscal_quarter;
		this.record_calendar_year = record_calendar_year;
		this.record_calendar_quarter = record_calendar_quarter;
		this.record_calendar_month = record_calendar_month;
		this.record_calendar_day = record_calendar_day;
	}

	public String getRecord_date() {
		return record_date;
	}

	public void setRecord_date(String record_date) {
		this.record_date = record_date;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public String getExchange_rate() {
		return exchange_rate;
	}

	public void setExchange_rate(String exchange_rate) {
		this.exchange_rate = exchange_rate;
	}

	public String getEffective_date() {
		return effective_date;
	}

	public void setEffective_date(String effective_date) {
		this.effective_date = effective_date;
	}

	public String getSrc_line_nbr() {
		return src_line_nbr;
	}

	public void setSrc_line_nbr(String src_line_nbr) {
		this.src_line_nbr = src_line_nbr;
	}

	public String getRecord_fiscal_year() {
		return record_fiscal_year;
	}

	public void setRecord_fiscal_year(String record_fiscal_year) {
		this.record_fiscal_year = record_fiscal_year;
	}

	public String getRecord_fiscal_quarter() {
		return record_fiscal_quarter;
	}

	public void setRecord_fiscal_quarter(String record_fiscal_quarter) {
		this.record_fiscal_quarter = record_fiscal_quarter;
	}

	public String getRecord_calendar_year() {
		return record_calendar_year;
	}

	public void setRecord_calendar_year(String record_calendar_year) {
		this.record_calendar_year = record_calendar_year;
	}

	public String getRecord_calendar_quarter() {
		return record_calendar_quarter;
	}

	public void setRecord_calendar_quarter(String record_calendar_quarter) {
		this.record_calendar_quarter = record_calendar_quarter;
	}

	public String getRecord_calendar_month() {
		return record_calendar_month;
	}

	public void setRecord_calendar_month(String record_calendar_month) {
		this.record_calendar_month = record_calendar_month;
	}

	public String getRecord_calendar_day() {
		return record_calendar_day;
	}

	public void setRecord_calendar_day(String record_calendar_day) {
		this.record_calendar_day = record_calendar_day;
	}
	

	
}
