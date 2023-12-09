package com.gateway.wex.service;

import org.springframework.stereotype.Service;

import com.gateway.wex.helper.GlobalUtils;
import com.gateway.wex.model.RateOfExchangeDTO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@Service
public class ExchangeRatesService 
{

	public static RateOfExchangeDTO getRateOfExchange(String currency, String type, String purchaseDate)
	{
		// Consume the external web service from FiscalData Treasury Reporting API.
		
		RateOfExchangeDTO rateOfExchangeDTO = null;
		
		// FilterType : if "name", filter by "currency", otherwise by "country_currency_desc".
		String filterType = (type == "name" ? "currency" : "country_currency_desc"); 
		
		// Calculate "fromDate" : 6 months before purchaseDate.
		String fromDate = GlobalUtils.dateMonthsBefore(purchaseDate, 6);
		
		// Specify Sort order.
		String sortOrder = "&sort=-record_date"; // Most recent first.
		
		String params = "?filter=record_date:gte:" + fromDate + ",record_date:lte:" + purchaseDate + "," + filterType + ":eq:" + currency + sortOrder;
		
	    try 
	    {
			 String response = (String) HttpService.query(GlobalUtils.ENDPOINT_URL + params);
			 Gson gson = new Gson();
			 JsonParser parser = new JsonParser();
			 JsonElement jsonElement = parser.parse(response);
			 JsonObject rootObject = jsonElement.getAsJsonObject();  
			 JsonArray dataArray = (JsonArray) rootObject.get("data");
			 if (dataArray != null)
			 {
				 if (dataArray.size() > 0)
				 {
					 JsonObject dataObject = (JsonObject) dataArray.get(0);
		 			 rateOfExchangeDTO = gson.fromJson(dataObject, RateOfExchangeDTO.class);
				 }
			 }
		} 
	    catch (Exception e) 
	    {
		}
		
		return rateOfExchangeDTO;
	}
	
}
