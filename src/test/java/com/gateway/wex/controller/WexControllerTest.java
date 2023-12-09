package com.gateway.wex.controller;

import com.gateway.wex.service.ExchangeRatesService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import org.springframework.util.Assert;
import com.gateway.wex.WexGatewayApplication;
import com.gateway.wex.helper.GlobalUtils;
import com.gateway.wex.model.RateOfExchangeDTO;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.OkHttpClient.Builder;


class WexControllerTest 
{

	private static final String BASE_URL = "http://localhost:8080";
    private static OkHttpClient myOkhttpClient = null;
    private static Builder myOkhttpBuilder = null;
	    
    @BeforeAll
	// Instantiate the application for testing.
	private static void startApp()
	{
	   String[] args= {""};
	   
	   WexGatewayApplication wa = new WexGatewayApplication();
	   wa.main(args);	   	   
	}
	

	///////////////////////////////////////
	// Test a post with no Description.
	///////////////////////////////////////
	@Test
	void avoidRequestMissingDescription(TestReporter testReporter) throws Exception 
	{

        initOkhttp();
        OkHttpClient client = myOkhttpClient;

		RequestBody formBody = new FormBody.Builder()
			      .add("description", "")
		          .add("transaction_date", "12/08/2023")
		          .add("purchase_amount", "10.00")
		          .build();
		
		Request request = new Request.Builder()
		  .url(BASE_URL + "/purchase")
		  .post(formBody)
		  .build();
		
		Call call = myOkhttpClient.newCall(request);
		Response response = call.execute();
		String errorMessage = response.body().string();
		
		// If response code is anything but 200/201, has passed the test.
	    Assert.isTrue(( (response.code() != 200) && (response.code() != 201) ), errorMessage);
	    testReporter.publishEntry("Got http " + response.code() + " and message \"" + errorMessage + "\"");
	}
	
	/////////////////////////////////////////
	// Test a post with no Transaction Date
	/////////////////////////////////////////
	@Test
	void avoidRequestMissingDate(TestReporter testReporter) throws Exception 
	{

        initOkhttp();
        OkHttpClient client = myOkhttpClient;

		RequestBody formBody = new FormBody.Builder()
			      .add("description", "Wheel")
		          .add("transaction_date", "")
		          .add("purchase_amount", "10.00")
		          .build();
		
		Request request = new Request.Builder()
		  .url(BASE_URL + "/purchase")
		  .post(formBody)
		  .build();
		
		Call call = myOkhttpClient.newCall(request);
		Response response = call.execute();
		String errorMessage = response.body().string();
		
		// If response code is anything but 200/201, has passed the test.
	    Assert.isTrue(( (response.code() != 200) && (response.code() != 201) ), errorMessage);
	    testReporter.publishEntry("Got http " + response.code() + " and message \"" + errorMessage + "\"");
	}

	/////////////////////////////////////////
	// Test a post with no Purchase Amount
	/////////////////////////////////////////
	@Test
	void avoidRequestMissingAmount(TestReporter testReporter) throws Exception 
	{

        initOkhttp();
        OkHttpClient client = myOkhttpClient;

		RequestBody formBody = new FormBody.Builder()
			      .add("description", "Wheel")
		          .add("transaction_date", "12/08/2023")
		          .add("purchase_amount", "")
		          .build();
		
		Request request = new Request.Builder()
		  .url(BASE_URL + "/purchase")
		  .post(formBody)
		  .build();
		
		Call call = myOkhttpClient.newCall(request);
		Response response = call.execute();
		String errorMessage = response.body().string();
		
		// If response code is anything but 200/201, has passed the test.
	    Assert.isTrue(( (response.code() != 200) && (response.code() != 201) ), errorMessage);
	    testReporter.publishEntry("Got http " + response.code() + " and message \"" + errorMessage + "\"");
	}

	////////////////////////////////////////////////////
	// Test a post with Description over 50 characters
	////////////////////////////////////////////////////
	@Test
	void avoidRequestWithDescriptionOver50Chars(TestReporter testReporter) throws Exception 
	{

        initOkhttp();
        OkHttpClient client = myOkhttpClient;

		RequestBody formBody = new FormBody.Builder()
			      .add("description", "The quick brown fox jumps over the lazy dog near the lake")
		          .add("transaction_date", "12/08/2023")
		          .add("purchase_amount", "19.99")
		          .build();
		
		Request request = new Request.Builder()
		  .url(BASE_URL + "/purchase")
		  .post(formBody)
		  .build();
		
		Call call = myOkhttpClient.newCall(request);
		Response response = call.execute();
		String errorMessage = response.body().string();
		
		// If response code is anything but 200/201, has passed the test.
	    Assert.isTrue(( (response.code() != 200) && (response.code() != 201) ), errorMessage);
	    testReporter.publishEntry("Got http " + response.code() + " and message \"" + errorMessage + "\"");
	}

	////////////////////////////////////////////////////
	// Test a post with invalid Transaction Date
	////////////////////////////////////////////////////
	@Test
	void avoidRequestWithInvalidDate(TestReporter testReporter) throws Exception 
	{

        initOkhttp();
        OkHttpClient client = myOkhttpClient;

		RequestBody formBody = new FormBody.Builder()
			      .add("description", "Tires")
		          .add("transaction_date", "02/30/2023")
		          .add("purchase_amount", "19.99")
		          .build();
		
		Request request = new Request.Builder()
		  .url(BASE_URL + "/purchase")
		  .post(formBody)
		  .build();
		
		Call call = myOkhttpClient.newCall(request);
		Response response = call.execute();
		String errorMessage = response.body().string();
		
		// If response code is anything but 200/201, has passed the test.
	    Assert.isTrue(( (response.code() != 200) && (response.code() != 201) ), errorMessage);
	    testReporter.publishEntry("Got http " + response.code() + " and message \"" + errorMessage + "\"");
	}
	
	////////////////////////////////////////////////////////
	// Test a post with negative Purchase Amount
	////////////////////////////////////////////////////////
	@Test
	void avoidRequestWithNegativeAmount(TestReporter testReporter) throws Exception 
	{

        initOkhttp();
        OkHttpClient client = myOkhttpClient;

		RequestBody formBody = new FormBody.Builder()
			      .add("description", "Tires")
		          .add("transaction_date", "12/08/2023")
		          .add("purchase_amount", "-19.99")
		          .build();
		
		Request request = new Request.Builder()
		  .url(BASE_URL + "/purchase")
		  .post(formBody)
		  .build();
		
		Call call = myOkhttpClient.newCall(request);
		Response response = call.execute();
		String errorMessage = response.body().string();
		
		// If response code is anything but 200/201, has passed the test.
	    Assert.isTrue(( (response.code() != 200) && (response.code() != 201) ), errorMessage);
	    testReporter.publishEntry("Got http " + response.code() + " and message \"" + errorMessage + "\"");
	}

	////////////////////////////////////////////////////////
	// Test a post with a zero Purchase Amount
	////////////////////////////////////////////////////////
	@Test
	void avoidRequestWithAmountZero(TestReporter testReporter) throws Exception 
	{

        initOkhttp();
        OkHttpClient client = myOkhttpClient;

		RequestBody formBody = new FormBody.Builder()
			      .add("description", "Tires")
		          .add("transaction_date", "12/08/2023")
		          .add("purchase_amount", "-19.99")
		          .build();
		
		Request request = new Request.Builder()
		  .url(BASE_URL + "/purchase")
		  .post(formBody)
		  .build();
		
		Call call = myOkhttpClient.newCall(request);
		Response response = call.execute();
		String errorMessage = response.body().string();
		
		// If response code is anything but 200/201, has passed the test.
	    Assert.isTrue(( (response.code() != 200) && (response.code() != 201) ), errorMessage);
	    testReporter.publishEntry("Got http " + response.code() + " and message \"" + errorMessage + "\"");
	}

	////////////////////////////////////////////////////////////////////////////////////////
	// Test an Exchange rate conversion without an exchange rate within the last 6 months
	////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	void warnIfExchangeRateNotPresent(TestReporter testReporter) throws Exception 
	{

		// These currencies have no register in the last 6 months.
		String item = "South-Sudanese-Pound";  // Or  "Guatemala-Quetzal"
		String purchaseDate = "12/08/2023";
		
		RateOfExchangeDTO rateOfExchangeDTO = ExchangeRatesService.getRateOfExchange(item, "desc", GlobalUtils.formatDateForService(purchaseDate));
		
		String errorMessage = "Cannot convert to " + item +" as exchange rate could not be found in the last 6 month period!";
		
		// If response code is anything but 200/201, has passed the test.
	    Assert.isTrue( rateOfExchangeDTO == null, "6-Month check routine not working as expected!");
	    testReporter.publishEntry("Got null from method, so : \"" + errorMessage + "\"");
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

