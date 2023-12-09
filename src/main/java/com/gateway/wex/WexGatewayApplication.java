package com.gateway.wex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.gateway.wex.view.WexView;

@SpringBootApplication
public class WexGatewayApplication 
{
	
	public static void main(String[] args) 
	{		
		// Open Graphic User Interface.
		WexView wexView = new WexView();
		wexView.initializeGUI();
		
		// Start Spring application.
		SpringApplication.run(WexGatewayApplication.class, args);			
	}
	
}

