package de.dspindler.graveox.ui;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

import de.dspindler.graveox.util.UnitUtil;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ValueField extends AnchorPane
{
	private static final char			DECIMAL_SEPARATOR = new DecimalFormatSymbols(Locale.getDefault()).getDecimalSeparator();
	
	public static enum			UnitType
	{
		LENGTH,
		MASS,
		TIME,
		DENSITY
	};
	
	private ArrayList<ChangeListener<Number>>		listeners;
	
	private TextField			inputField;
	private boolean				allowZero;
	private UnitType			unitType;
	
	public ValueField(boolean allowZero, UnitType type)
	{
		this.listeners = new ArrayList<ChangeListener<Number>>();
		
		this.unitType = type;
		
		this.inputField = new TextField();
		this.inputField.setOnAction(new InputFieldHandler(type));
		this.inputField.setMinWidth(60.0d);
		this.inputField.setPrefWidth(100.0d);
		this.inputField.setMaxWidth(Double.MAX_VALUE);
		
		AnchorPane.setLeftAnchor(inputField, 0.0d);
		AnchorPane.setTopAnchor(inputField, 0.0d);
		AnchorPane.setBottomAnchor(inputField, 0.0d);
		AnchorPane.setRightAnchor(inputField, 0.0d);
		
		// Whether or not the number "0" is a valid input
		this.allowZero = allowZero;
		
		super.getChildren().add(inputField);
	}
	
	public void setValue(double value)
	{
		String unit = "";
		
		switch(unitType)
		{
		case MASS:
		{
			unit = "kg";
			
			break;
		}
		case LENGTH:
		{
			unit = "m";
			
			break;
		}
		case TIME:
		{
			unit = "s";
			
			break;
		}
		}
		
		this.inputField.setText(Double.toString(value).replace('.', DECIMAL_SEPARATOR) + " " + unit);
	}
	
	public void addValueListener(ChangeListener<Number> listener)
	{
		this.listeners.add(listener);
	}
	
	public void removeValueListener(ChangeListener<Number> listener)
	{
		this.listeners.remove(listener);
	}
	
	private class InputFieldHandler implements EventHandler<ActionEvent>
	{
		private static final int		SCANNING_NUMBER = 1;
		private static final int		SCANNING_DECIMAL_NUMBER = 2;
		private static final int		SCANNING_UNIT = 3;
		
		private UnitType			unitType;
		
		public InputFieldHandler(UnitType type)
		{
			this.unitType = type;
		}
		
		@Override
		public void handle(ActionEvent e)
		{
			// Check if field has content
			if(inputField.getText() == null || inputField.getText().length() == 0)
			{
				inputField.setText("1");
				return;
			}
			
			char[] data = inputField.getText().toCharArray();
			char current;
			StringBuilder value = new StringBuilder();
			StringBuilder unit = new StringBuilder();
			int decimalPlace = 0;
			double decimalValue = 0.0d;
			
			int state = SCANNING_NUMBER;
			for(int i = 0; i < data.length; ++i)
			{
				current = data[i];
				
				switch(state)
				{
				case SCANNING_NUMBER:
				{
					if(isNumber(current))
					{
						value.append(current);
						++decimalPlace;
					}
					else if(current == DECIMAL_SEPARATOR)
					{
						state = SCANNING_DECIMAL_NUMBER;
						decimalPlace = 0;
					}
					else if(current == ' ')
					{
						state = SCANNING_UNIT;
					}
					else if(isLetter(current))
					{
						state = SCANNING_UNIT;
						--i;
					}
					
					break;
				}
				case SCANNING_DECIMAL_NUMBER:
				{
					if(isNumber(current))
					{
						decimalValue += Math.pow(10.0d, -(decimalPlace + 1)) * (double)(current - 48);
						++decimalPlace;
					}
					else if(current == ' ')
					{
						state = SCANNING_UNIT;
					}
					else if(isLetter(current))
					{
						state = SCANNING_UNIT;
						--i;
					}
					
					break;
				}
				case SCANNING_UNIT:
				{
					if(isLetter(current))
					{
						unit.append(current);
					}
					
					break;
				}
				}
			}
			
			double val = Integer.parseInt(value.toString()) + decimalValue;
			
			if(!allowZero && val == 0.0d)
			{
				val = 1.0d;
			}
			
			if(unit.length() <= 0 || unit == null || unit.toString().equals(" "))
			{
				unit = new StringBuilder();
				
				switch(unitType)
				{
				case LENGTH:
				{
					unit.append("m");
					
					break;
				}
				case MASS:
				{
					unit.append("kg");
					
					break;
				}
				case TIME:
				{
					unit.append("s");
					
					break;
				}
				}
			}
			
			System.out.println("Unit: " + unit);
			
			DecimalFormat format = new DecimalFormat("#");
			format.setMaximumFractionDigits(16);
			inputField.setText(String.format("%s %s", format.format(val), unit).replace('.', DECIMAL_SEPARATOR));
			
			for(ChangeListener<Number> l : listeners)
			{
				switch(unitType)
				{
				case LENGTH:
				{
					l.changed(null, 0, UnitUtil.convertLength(val, unit.toString(), "m"));
					break;
				}
				case MASS:
				{
					l.changed(null, 0, UnitUtil.convertMass(val, unit.toString(), "kg"));
					break;
				}
				case TIME:
				{
					l.changed(null, 0, UnitUtil.convertTime(val, unit.toString(), "s"));
					break;
				}
				case DENSITY:
				{
					// TODO: make conversion for density!
					l.changed(null, 0, val);
					break;
				}
				}
			}
		}
		
		private boolean isNumber(char c)
		{
			return c >= 48 && c <= 57;
		}
		
		private boolean isLetter(char c)
		{
			char c2 = Character.toLowerCase(c);
			
			return c2 >= 97 && c2 <= 122;
		}
	}
}
