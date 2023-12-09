package com.gateway.wex.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;
import java.util.Date;
import java.util.Locale;

public class GlobalUtils 
{
	// Define the Fiscal Data Treasury API endpoint as a global constant, to be used throughout the application.
	public final static String ENDPOINT_URL = "https://api.fiscaldata.treasury.gov/services/api/fiscal_service/v1/accounting/od/rates_of_exchange";

	// Character String array to be used as input for creation of short unique IDs. 
    private static String[] CHARS = new String[] { "a", "b", "c", "d", "e",
           "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
           "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2",
           "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E",
           "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
           "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

    // Generate the short unique ID.
    public static String generateShortUuid() {
       StringBuilder shortBuilder = new StringBuilder();
       String uuid = UUID.randomUUID().toString().replace("-", "");
       for (int i = 0; i < 8; i++) {
           String str = uuid.substring(i * 4, i * 4 + 4);
           int x = Integer.parseInt(str, 16);
           shortBuilder.append(CHARS[x % 0x3E]);
       }
       return shortBuilder.toString();
    }   

    // Format a string date to be used in the Web Service filter parameter.
    public static String formatDateForService(String originalDate)
    {
    	String newDate = "";
    	
    	try
    	{

   	 	    if (originalDate == null)
  		    {
  		       throw new Exception("date is required");
  		    }

   	 	    // First, convert from String to Date.
     	 	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
   	        Date date = formatter.parse(originalDate);
   	 	    
   	 	    // Then create a formatted string newDate
    	    String dateFormat = "yyyy-MM-dd";	   
            DateFormat df = new SimpleDateFormat(dateFormat);
            df.setLenient(false);
            newDate = df.format(date);
            
            return newDate;
    	}
    	catch (Exception e) 
    	{
			return "";
		}
    	
    }
 
    // Return a String date relative to a provided date backwards in a certain number of months.
    public static String dateMonthsBefore(String date, int monthsBefore)
    {
 	   // Return a calculated date "n" months before the provided one.
 	   
 	   try 
 	   { 		   
 	 	  if (date == null)
 		  {
 		     throw new Exception("date is required");
 		  }
 	 	  if (monthsBefore < 1)
 		  {
 		     throw new Exception("monthsBefore is required");
 		  }

 		  String newDate = ""; 		   
 		  String[] dateSplit = date.split("-");
          Integer year = Integer.parseInt(dateSplit[0]); 		  
          Integer month = Integer.parseInt(dateSplit[1]);
          Integer day = Integer.parseInt(dateSplit[2]);
 		  
 		  Calendar cal = Calendar.getInstance();
 		  cal.set(year, (month-1), day); // Calendar class represents months starting with 0! (January = 0).
 		   		  
 		  // Passing a negative value to "add" Calendar method makes it calculate backwards (months BEFORE).
 		  cal.add(Calendar.MONTH, (monthsBefore * - 1));
 		  
 		  Date updatedDate = cal.getTime();   
 		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
 		  newDate = sdf.format(updatedDate);  
 		    
 	       // Return the calculated new date.
 	       return newDate;
 	   } 
 	   catch (Exception e) 
 	   {
 	       return null;
 	   }
    }   
      
    
}
