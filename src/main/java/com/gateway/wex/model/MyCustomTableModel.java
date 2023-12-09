package com.gateway.wex.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class MyCustomTableModel extends AbstractTableModel
{
	private final List<Purchase> items;	
	private final String[] columnNames;
	
	public MyCustomTableModel(String[] columnNames, List<Purchase> items) 
	{
		this.columnNames = columnNames;
		this.items = items;
	}

	@Override
	public int getRowCount() 
	{
		return items == null ? 0 : items.size();
	}

	@Override
	public int getColumnCount() 
	{
		return columnNames.length;
	}

	@Override
	public String getColumnName(int col) 
	{
		switch (col) 
		{
			case 0 :
				return columnNames[0];
			case 1 :
				return columnNames[1];
			case 2 :
				return columnNames[2];
			case 3 :
				return columnNames[3];
			default :
				return null;
		}
	}	
	
	@Override
	public Object getValueAt(int row, int col) 
	{

		if (row < 0 || row >= items.size()) 
		{
			return null;
		}
		
		if (col < 0 || col >= columnNames.length) 
		{
			return null;
		}
		
		Purchase purchase = (Purchase) items.get(row);
		
		switch (col) 
		{
			case 0 :
				return purchase.getId();
			case 1 :
				return purchase.getDescription();
			case 2 :
				return purchase.getTransaction_date();
			case 3 :
				return purchase.getAmount();
			default :
				return null;
		}
	}	
	
	
}
