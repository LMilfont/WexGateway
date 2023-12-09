package com.gateway.wex.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CheckTools
{
	
   public static Boolean isValidAmount(String param)
   {  
	   // Tests if: 1) Is numeric.
	   //           2) Is a positive value. 
	   //           3) Has two decimal places.
	   
	   try
	   {
		  // Test if there is a param.
		  if (param == null)
		  {
		     throw new Exception("Param is required");
		  }
		   
		  // Test if string param is numeric by parsing it.
		  BigDecimal checkNum = new BigDecimal(param).setScale(2, RoundingMode.HALF_EVEN);
		  
          // Test if it's a positive number;
          if (checkNum.compareTo(BigDecimal.ZERO) <= 0)
          {
             throw new Exception("Not a positive number");
          }
	   }
	   catch (Exception e) 
	   {
	      return false;
	   }
	   
	   return true;
   }
	
   
   public static boolean isDateValid(String date)
   {
	   // Test if date is in valid format.
	   
	   try 
	   {
	 	   if (date == null)
		   {
		      throw new Exception("Date is required");
		   }
	 	   		   
		   String dateFormat = "MM/dd/yyyy";
		   
	       DateFormat df = new SimpleDateFormat(dateFormat);
	       df.setLenient(false);
	       df.parse(date);
	       return true;
	   } 
	   catch (Exception e) 
	   {
	       return false;
	   }
   }   
   
   
}