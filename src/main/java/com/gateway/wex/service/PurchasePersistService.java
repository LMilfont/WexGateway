package com.gateway.wex.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.stereotype.Service;

import com.gateway.wex.model.Purchase;
import com.google.gson.Gson;
import java.io.File;

@Service
public class PurchasePersistService 
{
    // This class is responsible for persisting the data.
	// For the sake of simplicity, the chosen method was to store json objects in a text file,
	// each one separate by a newline character.
	
	public PurchasePersistService()
	{
	}

	public ArrayList<Purchase> loadPurchases()
	{
		// Purchase list load method.
		// This method will read the JSON text file and fetch items.
		// Items are separated by newline.
		
		ArrayList<Purchase> itemsList = new ArrayList<Purchase>();
		
		try
		{
			BufferedReader reader;
			
			String filePath = System.getProperty("user.dir") + "\\database.json";
			File f = new File(filePath);
			if(f.exists() && !f.isDirectory())
			{ 
			
				reader = new BufferedReader(new FileReader(filePath));
				String line = reader.readLine();
				Gson gson = new Gson();
	
				while (line != null)
				{
					itemsList.add(gson.fromJson(line, Purchase.class));
					line = reader.readLine();
				}
				reader.close();			
				
				// Reverse list order so that newer items appear first.
				Collections.reverse(itemsList);
				
	            return itemsList;
			}
			else
			{
				// File does not exist.
				return null;
			}
			
		}
		catch (Exception e) 
		{
			// System.out.println(e.getMessage());
			return null;
		}
	}
	
	
	public Boolean storePurchase(Purchase purchase)
	{
		// Purchase persist method. Here it's being used a JSON text file solution.
		// This method can be changed to other desired store method alternatives.
		
		try
		{
			String filePath = System.getProperty("user.dir") + "\\database.json";
			Gson gson = new Gson();
			FileWriter writer = new FileWriter(filePath, true);
			gson.toJson(purchase, writer);
			
			writer.write("\r\n");
			writer.flush();
			writer.close();
						
			return true;
		}
		catch (Exception e) 
		{
			// System.out.println(e.getMessage());
			return false;
		}
	}
}
