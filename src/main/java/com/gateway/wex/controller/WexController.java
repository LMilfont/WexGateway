package com.gateway.wex.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.gateway.wex.helper.CheckTools;
import com.gateway.wex.helper.GlobalUtils;
import com.gateway.wex.model.ConvertedRates;
import com.gateway.wex.model.Currency;
import com.gateway.wex.model.MyCustomTableModel;
import com.gateway.wex.model.PostBody;
import com.gateway.wex.model.Purchase;
import com.gateway.wex.model.RateOfExchangeDTO;
import com.gateway.wex.service.CurrencyListService;
import com.gateway.wex.service.ExchangeRatesService;
import com.gateway.wex.service.PurchasePersistService;
import com.gateway.wex.view.WexView;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import org.springframework.http.HttpStatus;

@RestController
public class WexController
{
    
	// Rest entrypoint for creation of new purchase.
	@PostMapping(value = "/purchase", params = {"description", "transaction_date", "purchase_amount"})
	public ResponseEntity<Object> purchase(@RequestParam(value = "description") String description,
										   @RequestParam(value = "transaction_date") String transaction_date,
										   @RequestParam(value = "purchase_amount") String purchase_amount)
	{
		
		// Post mapping to receive form-data requests.	
		
       HttpStatus returnStatus = HttpStatus.CREATED;
       String statusMessage = "";
       
       try
       {
    	
    	  // Parameters validation.

     	  // Check if parameters are not null.
     	  if (description == null)
     	  {
     		  throw new Exception("Description is mandatory");
     	  }
     	  if (transaction_date == null)
     	  {
     		  throw new Exception("Transaction Date is mandatory");
     	  }
     	  if (purchase_amount == null)
     	  {
     		  throw new Exception("Purchase Amount is mandatory");
     	  }

    	  // Check if description is present.
    	  if (description.isEmpty() || description.isBlank())
    	  {
    		  throw new Exception("Description is mandatory");
    	  }
    	   
    	  // Check if there are spaces before or after the description string.
    	  if (description.equals(description.trim()) == false)
    	  {
    		  throw new Exception("Space characters before or after the description are not allowed");
    	  }
    	  
    	  // Check description length.
    	  if (description.length() > 50)
    	  {
    		  throw new Exception("Description too long! Must have a maximum of 50 characters.");
    	  }

    	  // Check if date is present.
    	  if (transaction_date.isEmpty() || transaction_date.isBlank())
    	  {
    		  throw new Exception("Transaction Date is mandatory");
    	  }
    	  
		  if (transaction_date.equals(transaction_date.trim()) == false)
		  {
		 	  throw new Exception("Spaces are not allowed in Transaction Date");
		  } 	   
    	  
    	  // Check transaction date.
    	  if (CheckTools.isDateValid(transaction_date) == false)
    	  {
    		  throw new Exception("Transaction Date is invalid! Must be a valid date in MM/DD/YYYY format.");
    	  }
   
    	  // Check if purchase amount is present.
    	  if (purchase_amount.isEmpty() || purchase_amount.isBlank())
    	  {
    		  throw new Exception("Purchase Amount is mandatory");
    	  }
    	  
    	  // Check purchase amount.
    	  if (CheckTools.isValidAmount(purchase_amount) == false)
    	  {
    		  throw new Exception("Purchase Amount must be a positive numeric value after rounded to its nearest cent.");
    	  }
    	  
    	  
    	  // Convert parameters.
    	  BigDecimal amount = new BigDecimal(purchase_amount).setScale(2, RoundingMode.HALF_EVEN);
    	  
	       
	      // All parameters are checked and converted.

	      // Instantiate a new purchase object and persist it.
    	  Purchase purchase = new Purchase(description, transaction_date, amount);
    	  
    	  PurchasePersistService purchasePersistService = new PurchasePersistService();
    	  Boolean result = purchasePersistService.storePurchase(purchase);
    	  
    	  if (result == false)
    	  {
    		  throw new Exception("An error occured while trying to store a new purchase");
    	  }
    	  else
    	  {    		  
    		  statusMessage = "Purchase stored successfully!";
    	  }

    	  
          // Update data.
          WexView.updateTableData();	

          
       }
       catch (Exception e) 
       {
    	   statusMessage = e.getMessage();
    	   
    	   return ResponseEntity
    	           .status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(statusMessage);
       }

		       
	   return new ResponseEntity<>(statusMessage, returnStatus);
		
	}	

	@PostMapping(value = "/purchase")
	public ResponseEntity<Object> purchase(@RequestBody PostBody postBody)
	{
	   // Post mapping to receive raw JSON requests.	
		
       HttpStatus returnStatus = HttpStatus.CREATED;
       String statusMessage = "";
       
       String description = postBody.getDescription();
       String transaction_date = postBody.getTransaction_date();
       String purchase_amount = postBody.getPurchase_amount();
       
       try
       {
    	
    	  // Parameters validation.
    	  
    	  // Check if parameters are not null.
    	  if (description == null)
    	  {
    		  throw new Exception("Description is mandatory");
    	  }
    	  if (transaction_date == null)
    	  {
    		  throw new Exception("Transaction Date is mandatory");
    	  }
    	  if (purchase_amount == null)
    	  {
    		  throw new Exception("Purchase Amount is mandatory");
    	  }

    	  // Check if description is present.
    	  if (description.isEmpty() || description.isBlank())
    	  {
    		  throw new Exception("Description is mandatory");
    	  }
    	   
    	  // Check if there are spaces before or after the description string.
    	  if (description.equals(description.trim()) == false)
    	  {
    		  throw new Exception("Space characters before or after the description are not allowed");
    	  }
    	  
    	  // Check description length.
    	  if (description.length() > 50)
    	  {
    		  throw new Exception("Description too long! Must have a maximum of 50 characters.");
    	  }

    	  // Check if date is present.
    	  if (transaction_date.isEmpty() || transaction_date.isBlank())
    	  {
    		  throw new Exception("Transaction Date is mandatory");
    	  }
    	  
		  if (transaction_date.equals(transaction_date.trim()) == false)
		  {
		 	  throw new Exception("Spaces are not allowed in Transaction Date");
		  } 	   
    	  
    	  // Check transaction date.
    	  if (CheckTools.isDateValid(transaction_date) == false)
    	  {
    		  throw new Exception("Transaction Date is invalid! Must be a valid date in MM/DD/YYYY format.");
    	  }
   
    	  // Check if purchase amount is present.
    	  if (purchase_amount.isEmpty() || purchase_amount.isBlank())
    	  {
    		  throw new Exception("Purchase Amount is mandatory");
    	  }
    	  
    	  // Check purchase amount.
    	  if (CheckTools.isValidAmount(purchase_amount) == false)
    	  {
    		  throw new Exception("Purchase Amount must be a positive numeric value after rounded to its nearest cent.");
    	  }
    	  
    	  
    	  // Convert parameters.
    	  BigDecimal amount = new BigDecimal(purchase_amount).setScale(2, RoundingMode.HALF_EVEN);
    	  
	       
	      // All parameters are checked and converted.

	      // Instantiate a new purchase object and persist it.
    	  Purchase purchase = new Purchase(description, transaction_date, amount);
    	  
    	  PurchasePersistService purchasePersistService = new PurchasePersistService();
    	  Boolean result = purchasePersistService.storePurchase(purchase);
    	  
    	  if (result == false)
    	  {
    		  throw new Exception("An error occured while trying to store a new purchase");
    	  }
    	  else
    	  {    		  
    		  statusMessage = "Purchase stored successfully!";
    	  }

          // Update data.
          WexView.updateTableData();	

       }
       catch (Exception e) 
       {
    	   statusMessage = e.getMessage();
    	   
    	   return ResponseEntity
    	           .status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(statusMessage);
       }

		       
	   return new ResponseEntity<>(statusMessage, returnStatus);
		
	}	
	
	
	

	public static ConvertedRates convertRates(String item, String purchaseDate, String amount)
	{
		RateOfExchangeDTO rateOfExchangeDTO = null;
		ConvertedRates convertedRates = null;
		BigDecimal exchangeRate = null;
		BigDecimal dollarAmount = null;
		BigDecimal convertedAmount = null;
				
		rateOfExchangeDTO = ExchangeRatesService.getRateOfExchange(item, "desc", GlobalUtils.formatDateForService(purchaseDate));
        
		if (rateOfExchangeDTO != null)
		{
			exchangeRate = new BigDecimal(rateOfExchangeDTO.getExchange_rate()).setScale(2, RoundingMode.HALF_EVEN);
	        dollarAmount = new BigDecimal(amount).setScale(2, RoundingMode.HALF_EVEN);
	        convertedAmount = exchangeRate.multiply(dollarAmount).setScale(2, RoundingMode.HALF_EVEN);

	        convertedRates = new ConvertedRates(exchangeRate.toString(), convertedAmount.toString());
	        	        
		}
		else
		{
			// Web service did not return a result.
			
	        // Inform user that currency does not have an exchange rate.
			JOptionPane.showMessageDialog(null, "Cannot convert to " + item +" as exchange rate could not be found in the last 6 month period!");
		}
		
		return convertedRates;
	}
	
}
