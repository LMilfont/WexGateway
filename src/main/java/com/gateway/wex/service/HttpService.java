package com.gateway.wex.service;

import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Response;

public class HttpService 
{
   // Provide http query functionality.
	
   private static final Integer HTTP_TIMEOUT = 10;
   private static OkHttpClient myOkhttpClient = null;
   private static Builder myOkhttpBuilder = null;
   private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
   private static final MediaType textPlainMT = MediaType.parse("text/plain; charset=utf-8");
   private static final MediaType MEDIA_PLAIN_TEXT_JSON = MediaType.parse("application/json");

   public static Object query(String url) throws Exception
   {
	  // Generic method to do http requests.
	  // This method uses OkHttp library.

      String result = null;
      Request request = null;

      // Initialize a single shared instance of okHttp client (and builder).
      initOkhttp();
      OkHttpClient client = myOkhttpClient;
      Builder myBuilder = myOkhttpBuilder;

      request = new Request.Builder().url(url).build();

      // Construct the builder;
      myBuilder.connectTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
         .writeTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
         .readTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
         .build();

      try
      {
         Response response = client.newCall(request).execute();
         result = response.body().string();
      } 
      catch (Exception e)
      {
    	 JOptionPane.showMessageDialog(null, "Please check your Internet connection and try again"); 
         result = null;
      }

      return result;
   }
	
   
   public static void initOkhttp() throws Exception
   {
      if(myOkhttpClient == null)
      {
         myOkhttpClient = new OkHttpClient();

         if(myOkhttpBuilder == null)
         {
            myOkhttpBuilder = myOkhttpClient.newBuilder();
         }
      }
   }
   
	
}
