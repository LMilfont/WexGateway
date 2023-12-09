package com.gateway.wex.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.gateway.wex.controller.WexController;
import com.gateway.wex.model.ConvertedRates;
import com.gateway.wex.model.Currency;
import com.gateway.wex.model.MyCustomTableModel;
import com.gateway.wex.model.Purchase;
import com.gateway.wex.service.CurrencyListService;
import com.gateway.wex.service.PurchasePersistService;
import java.awt.Image;
import javax.swing.ImageIcon;

public class WexView 
{
	
	static JTable table = null;
	static MyCustomTableModel tableModel = null;
	static String[][] tableData = null; 
    static JTextField txtExchangeRate = null;
    static JTextField txtConvertedAmount = null;
    static JTextField txtAmount = null;
    static JComboBox cmbCurrency = null;
    static JLabel lblConvertedAmount = null;
    static JTextField txtTxDate = null;
    static JLabel lblEmptyTable = null;
	
	public void initializeGUI()
	{
		// Create a Graphic User Interface based on Java Swing.
		
		JFrame frame = new JFrame("WEX Purchases Gateway");
		
        frame.setSize(610, 590);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);		
	    
	    String[] columnNames = {"ID", "Description", "TX Date", "Amount ($)"};
	    
	    // Get items from persisted data.
	    ArrayList<Purchase> purchasesList = new ArrayList<Purchase>();
    	PurchasePersistService purchasePersistService = new PurchasePersistService();	    
	    purchasesList = purchasePersistService.loadPurchases();

	    // Get the number of "Purchase class" properties (will be the number of columns in the data array).
	    List<Field> allFields = Arrays.asList(Purchase.class.getDeclaredFields());
	    int columnCount = allFields.size();
	    int rowCount = 0;
	    
	    if (purchasesList != null)
	    {
    	    rowCount = purchasesList.size();
	    }
	    	    
	    tableModel = new MyCustomTableModel(columnNames, purchasesList);
	    table = new JTable(tableModel) 
	    {
	       // Required so that cells are not editable.	
	       public boolean editCellAt(int row, int column, java.util.EventObject e)
	       {
		      return false;
		   }
        };	    
	    
	    JScrollPane pane = new JScrollPane(table);
	    pane.setBounds(20,25,560,304);
	    
	    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	    table.setRowHeight(table.getRowHeight() + 10);
	    
	    // Ajust columns width.
	    TableColumnModel columnModel = table.getColumnModel();
	    columnModel.getColumn(0).setPreferredWidth(20);
	    columnModel.getColumn(1).setPreferredWidth(100);
	    columnModel.getColumn(2).setPreferredWidth(20);
	    columnModel.getColumn(3).setPreferredWidth(20);
	    	    
	    // Set columns alignment.
	    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
	    rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
	    
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	    
	    columnModel.getColumn(0).setCellRenderer(centerRenderer);
	    columnModel.getColumn(1).setCellRenderer(centerRenderer);
	    columnModel.getColumn(2).setCellRenderer(centerRenderer);
	    columnModel.getColumn(3).setCellRenderer(rightRenderer);
	    
	    // Show label if table is empty.
	    lblEmptyTable = new JLabel("No Stored Purchases Yet");
	    lblEmptyTable.setBounds(190, 140, 300, 30);
	    lblEmptyTable.setFont(new Font("Tahoma", Font.PLAIN, 18));
	    pane.add(lblEmptyTable);
	    pane.setComponentZOrder(lblEmptyTable, 0);
	    if (rowCount == 0)
	    {
	    	lblEmptyTable.setVisible(true);
	    }
	    else
	    {
	    	lblEmptyTable.setVisible(false);
	    }
	    
	    frame.getContentPane().add(pane);	    
	    
	    // Create screen form elements.
		JLabel lblSelectedCurrency = new JLabel("Convert to Currency");
		
		lblSelectedCurrency.setBounds(20,350,200,20);
        frame.getContentPane().add(lblSelectedCurrency);
		
		// Create the currencies combo box.
	    ArrayList<Currency> currencyList = CurrencyListService.getCurrencyList();

	    // Application cannot continue without a currencies list, so halt.
	    if (currencyList == null)
	    {
	    	System.exit(0);
	    }
	    
	    // Convert the ArrayList to String Array.
	    String[] currencies = new String[currencyList.size() + 1];
	    currencies[0] = "Select or type exchange currency...";
	    for(int i=0;i<currencyList.size();i++)
	    {
	       currencies[i+1] = currencyList.get(i).getCurrency() + " (" + currencyList.get(i).getCountry_currency_desc() + ")"; 
	    }
	    
	    // Fill up the combo with available currencies.
	    cmbCurrency = new JComboBox(currencies);
	    
	    // Add a listener to the combo box.
	    class ComboCurrencyListener implements ItemListener 
	    {
		  // Method called if a new item has been selected.
		  public void itemStateChanged(ItemEvent evt) 
		  {
		    Object item = evt.getItem();

		    if (evt.getStateChange() == ItemEvent.SELECTED) 
		    {
		       // User selected a currency from the combo.		      
		       // Consume the web service in order to convert rates.
		       if (item.toString().contains("...") == false)
		       {
		    	    // Clear previusly calculated fields. 
			        clearRateFields();
			      
			        // Retrieve back currency name.
			        String[] parts = (item.toString()).split("\\(");
			        String currencyName = "";
			        String currencyDescription = "";
			        
			        // Test if split has been successful, so to extract the currency description.
			        if (parts.length > 1)
			        {
			           currencyName = parts[0].trim();
 			           currencyDescription = (parts[1].replace("(","")).replace(")","");
			        }
			        
		   	        // Check if all required values are filled.
					if ( ((txtAmount.getText()).isEmpty() == false) && ((txtAmount.getText()).isBlank() == false) )
					{
						if ( (cmbCurrency.getSelectedIndex() > 0) && (currencyDescription.isBlank() == false) )
					  {
					     ConvertedRates convertedRates = null;
					     convertedRates = WexController.convertRates(currencyDescription, txtTxDate.getText(), txtAmount.getText().trim());
					     
					     if (convertedRates != null)
					     {
							txtExchangeRate.setText(convertedRates.getExchangeRate());
							txtConvertedAmount.setText(convertedRates.getConvertedAmount());
						    lblConvertedAmount.setText("Converted To " + currencyDescription);					    	 
					     }
					     
					  }
					  else
					  {
						  clearRateFields();
					  }
			      }
				  else
				  {
			         // Tell user that a required field is missing.
					 JOptionPane.showMessageDialog(null, "Please select a purchase from list first!");			
				  }			      
		    	  
		       }
		    } 
		    else if (evt.getStateChange() == ItemEvent.DESELECTED) 
		    {
		       // Item is no longer selected.
		    	clearRateFields();
		    }
		  }
	    }	    	    
	    ComboCurrencyListener actionListener = new ComboCurrencyListener();
	    cmbCurrency.addItemListener(actionListener);
	    cmbCurrency.setBounds(20,370,560,30);
	    
	    frame.getContentPane().add(cmbCurrency);
	    
		JLabel lblUniqueId = new JLabel("Unique Identifier", SwingConstants.LEFT);
		lblUniqueId.setOpaque(true);
		lblUniqueId.setBounds(20,420,100,20);
		
		frame.getContentPane().add(lblUniqueId);
		
		JTextField txtUniqueId = new JTextField();
		txtUniqueId.setBounds(20,440,100,30);
		
		frame.getContentPane().add(txtUniqueId);
		
		txtUniqueId.setColumns(20);
		txtUniqueId.setEditable(false);
		txtUniqueId.setForeground(Color.black);
		txtUniqueId.setBackground(new Color(228, 228, 228));
		txtUniqueId.setHorizontalAlignment(JTextField.CENTER);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(140,420,200,20);
		
		frame.getContentPane().add(lblDescription);
				
		JTextField txtDescription = new JTextField();
		txtDescription.setBounds(140,440,440,30);
		
		frame.getContentPane().add(txtDescription);
		
		txtDescription.setColumns(20);	    	    
		txtDescription.setEditable(false);
		txtDescription.setForeground(Color.black);
		txtDescription.setBackground(new Color(228, 228, 228));
		txtDescription.setHorizontalAlignment(JTextField.CENTER);

		JLabel lblTxDate = new JLabel("Transaction Date");
		lblTxDate.setBounds(20,480,200,20);
		
		frame.getContentPane().add(lblTxDate);
				
		txtTxDate = new JTextField();
		txtTxDate.setBounds(20,500,100,30);
		
		frame.getContentPane().add(txtTxDate);
				
		txtTxDate.setColumns(20);	    	    
		txtTxDate.setEditable(false);
		txtTxDate.setForeground(Color.black);
		txtTxDate.setBackground(new Color(228, 228, 228));
		txtTxDate.setHorizontalAlignment(JTextField.CENTER);
		
		JLabel lblAmount = new JLabel("Amount ($)");
		lblAmount.setBounds(140,480,200,20);
		
		frame.getContentPane().add(lblAmount);
		
		txtAmount = new JTextField();
		txtAmount.setBounds(140,500,100,30);
		
		frame.getContentPane().add(txtAmount);
				
		txtAmount.setColumns(20);	    	    
		txtAmount.setEditable(false);
		txtAmount.setForeground(Color.black);
		txtAmount.setBackground(new Color(228, 228, 228));
		txtAmount.setHorizontalAlignment(JTextField.CENTER);
		
		JLabel lblExchangeRate = new JLabel("Exchange Rate");
		lblExchangeRate.setBounds(260,480,200,20);
		
		frame.getContentPane().add(lblExchangeRate);
		
		txtExchangeRate = new JTextField();
		txtExchangeRate.setBounds(260,500,100,30);
		
		frame.getContentPane().add(txtExchangeRate);
		
		txtExchangeRate.setColumns(20);
		txtExchangeRate.setEditable(false);
		txtExchangeRate.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtExchangeRate.setBackground(new Color(228, 228, 228));
		txtExchangeRate.setHorizontalAlignment(JTextField.CENTER);

		lblConvertedAmount = new JLabel("Converted Amount");
		lblConvertedAmount.setBounds(380,480,200,20);
		
		frame.getContentPane().add(lblConvertedAmount);
		
		txtConvertedAmount = new JTextField();
		txtConvertedAmount.setBounds(380,500,200,30);
		
		frame.getContentPane().add(txtConvertedAmount);
				
		txtConvertedAmount.setColumns(20);	    	    
		txtConvertedAmount.setEditable(false);
		txtConvertedAmount.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtConvertedAmount.setBackground(new Color(228, 228, 228));
		txtConvertedAmount.setHorizontalAlignment(JTextField.CENTER);
		
		// Add a listener to the table.
	    table.getSelectionModel().addListSelectionListener(new ListSelectionListener()
	    {
	        public void valueChanged(ListSelectionEvent event) 
	        {
			    clearRateFields();
			    
			    String uniqueID = table.getValueAt(table.getSelectedRow(), 0) != null ? table.getValueAt(table.getSelectedRow(), 0).toString() : null;
			    String description = table.getValueAt(table.getSelectedRow(), 1) != null ? table.getValueAt(table.getSelectedRow(), 1).toString() : null;
			    String txDate = table.getValueAt(table.getSelectedRow(), 2) != null ? table.getValueAt(table.getSelectedRow(), 2).toString() : null;
			    String amount = table.getValueAt(table.getSelectedRow(), 3) != null ? table.getValueAt(table.getSelectedRow(), 3).toString() : null;
			    
			    if ( (uniqueID != null) && (description != null) && (txDate != null) && (amount != null) )
			    {
		        	txtUniqueId.setText(uniqueID);
		        	txtDescription.setText(description);
		        	txtTxDate.setText(txDate);
		        	txtAmount.setText(amount);
		        	
		        	if (cmbCurrency.getSelectedItem().toString().isEmpty() == false)
		        	{
		        		// Automatically calculates new value.
	
		        		// Retrieve currency name.
				        String[] parts = (cmbCurrency.getSelectedItem().toString()).split("\\(");
				        String currencyName = "";
				        String currencyDescription = "";
				        
				        // Test if split has been successful, so to extract the currency description.
				        if (parts.length > 1)
				        {
				           currencyName = parts[0].trim();
	   			           currencyDescription = (parts[1].replace("(","")).replace(")","");
				        }
				        
			   	        // Check if all required values are filled.
						if ( ((txtAmount.getText()).isEmpty() == false) && ((txtAmount.getText()).isBlank() == false) )
						{
							if ( (cmbCurrency.getSelectedIndex() > 0) && (currencyDescription.isBlank() == false) )
							{
 						      ConvertedRates convertedRates = null;
						      convertedRates = WexController.convertRates(currencyDescription, txtTxDate.getText(), txtAmount.getText().trim());
						     
						      if (convertedRates != null)
						      {
							 	 txtExchangeRate.setText(convertedRates.getExchangeRate());
								 txtConvertedAmount.setText(convertedRates.getConvertedAmount());
							     lblConvertedAmount.setText("Converted To " + currencyDescription);					    	 
						      }
						      
							}
						    else
						    {
							   clearRateFields();
						    }
							
					    }
						else
						{
					       // Tell user that a required field is missing.
						   JOptionPane.showMessageDialog(null, "Please select a purchase from list first!");			
						}			      
				        
		        	}
 			    }
	        }
	    });		
			    
	    frame.setUndecorated(true);
	    frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
	    
	    // Configure table to have alternate row background colors.
	    UIDefaults defaults = UIManager.getLookAndFeelDefaults();
	    if (defaults.get("Table.alternateRowColor") == null)
	    {
	        defaults.put("Table.alternateRowColor", new Color(240, 240, 240));	    
	    }

        // Add application icon.
	    List<Image> appIcons = new ArrayList<Image>();
	    ImageIcon ic = new ImageIcon(getClass().getResource("../../../../appIcon16.png"));
	    Image im = ic.getImage();
	    appIcons.add(im);
	    ic = new ImageIcon(getClass().getResource("../../../../appIcon32.png"));
	    im = ic.getImage();
	    appIcons.add(im);
	    ic = new ImageIcon(getClass().getResource("../../../../appIcon64.png"));
	    im = ic.getImage();
	    appIcons.add(im);
	    frame.setIconImages(appIcons);
	    
	    frame.setLocationRelativeTo(null);
		frame.setVisible(true);		
	    
	}
	
	public static void updateTableData()
	{
		// This method dynamically updates the table data whenever a new purchase is
		// inserted through the rest API from a post request. 
		
		
		SwingUtilities.invokeLater(new Runnable() 
		{
			public void run() 
			{

			    String[] columnNames = {"ID", "Description", "TX Date", "Amount ($)"};
			    
			    // Get items from persisted data.
			    ArrayList<Purchase> purchasesList = new ArrayList<Purchase>();
		    	PurchasePersistService purchasePersistService = new PurchasePersistService();	    
			    purchasesList = purchasePersistService.loadPurchases();
			    
			    // Get the number of "Purchase class" properties (will be the number of columns in the data array).
			    List<Field> allFields = Arrays.asList(Purchase.class.getDeclaredFields());
			    int columnCount = allFields.size();
			    int rowCount = 0;
			    
			    if (purchasesList != null)
			    {
			       rowCount = purchasesList.size();
			    }

			    // Ajusts "No Purchases Yet" message if table has any elements.
			    if (rowCount == 0)
			    {
			    	lblEmptyTable.setVisible(true);
			    }
			    else
			    {
			    	lblEmptyTable.setVisible(false);
			    }
			    	    
			    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

			    MyCustomTableModel newTableModel = new MyCustomTableModel(columnNames, purchasesList);
			    table.setModel(newTableModel);

			    // Ajust columns width.
			    TableColumnModel columnModel = table.getColumnModel();
			    columnModel.getColumn(0).setPreferredWidth(100);
			    columnModel.getColumn(1).setPreferredWidth(100);
			    columnModel.getColumn(2).setPreferredWidth(100);
			    columnModel.getColumn(3).setPreferredWidth(80);
			    
			    // Set columns alignment.
			    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
			    rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
			    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			    
			    columnModel.getColumn(0).setCellRenderer(centerRenderer);
			    columnModel.getColumn(1).setCellRenderer(centerRenderer);
			    columnModel.getColumn(2).setCellRenderer(centerRenderer);
			    columnModel.getColumn(3).setCellRenderer(rightRenderer);
				
			}
		});
	}

	
	private static void clearRateFields()
	{
		txtExchangeRate.setText("");
		txtConvertedAmount.setText("");
		lblConvertedAmount.setText("Converted Amount");
		
		txtExchangeRate.repaint();
		txtConvertedAmount.repaint();
		lblConvertedAmount.repaint();
	}

}
