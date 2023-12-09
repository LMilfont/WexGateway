package com.gateway.wex.service;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.gateway.wex.helper.GlobalUtils;
import com.gateway.wex.model.Currency;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class CurrencyListService 
{
	
	public static ArrayList<Currency> getCurrencyList()
	{
		// Consume the external web service from FiscalData Treasury Reporting API.
		
		// This method aims to get a list of all available world currencies starting from previous year.
		// Sorts by currency name, then by most recent record, the idea being to get the list of most
		// recent currencies in the dataset. If a certain currency does not appear on the list, it's
		// because there was no register in the specified period, which means a possible conversion
		// operation would not be possible anyway. So it makes no difference whether it appears or not
		// on the list.
		
		ArrayList<Currency> currencyList = new ArrayList<Currency>();
		int currentYear = LocalDate.now().getYear();
		int lastYear = currentYear - 1;
        String strYear = String.valueOf(lastYear);
		
		String params = "?filter=record_date:gte:" + strYear + "-01-01&page[size]=10000&sort=currency,-record_date";
		
	    try 
	    {
			 String response = (String) HttpService.query(GlobalUtils.ENDPOINT_URL + params);
			 JsonParser parser = new JsonParser();
			 JsonElement jsonElement = parser.parse(response);
			 JsonObject rootObject = jsonElement.getAsJsonObject();  
			 JsonArray dataArray = (JsonArray) rootObject.get("data");
			 
			 JsonObject dataObject = null;
			 String currency_name = "";
			 String country_currency_desc = "";
			 for (int i=0;i<dataArray.size();i++)
			 {
			    dataObject = (JsonObject) dataArray.get(i);
				currency_name = dataObject.get("currency").toString();
				country_currency_desc = dataObject.get("country_currency_desc").toString();

			    Currency currency = new Currency(currency_name.replace("\"", ""), country_currency_desc.replace("\"", ""));
 			    if (currencyList.contains(currency) == false)
 			    {
			       currencyList.add(currency);
 			    }
			 }

		} 
	    catch (Exception e) 
	    {
			return null;
		}
		
		return currencyList;
	}

}
