package de.dspindler.graveox.ui;

<<<<<<< HEAD
import java.text.DecimalFormat;
=======
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

<<<<<<< HEAD
import de.dspindler.graveox.util.UnitUtil;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
=======
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ValueField extends AnchorPane
{
<<<<<<< HEAD
	private static final char			DECIMAL_SEPARATOR = new DecimalFormatSymbols(Locale.getDefault()).getDecimalSeparator();
	
	public static enum			UnitType
	{
		LENGTH,
		MASS,
		TIME,
		DENSITY
	};
=======
	private static final char 						DECIMAL_SEPARATOR = new DecimalFormatSymbols(Locale.getDefault()).getDecimalSeparator();
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
	
	private ArrayList<ChangeListener<Number>>		listeners;
	
	private TextField			inputField;
<<<<<<< HEAD
	private boolean				allowZero;
	private UnitType			unitType;
	
	public ValueField(boolean allowZero, UnitType type)
	{
		this.listeners = new ArrayList<ChangeListener<Number>>();
		
		this.unitType = type;
		
		this.inputField = new TextField();
		this.inputField.setOnAction(new InputFieldHandler(type));
=======
	private ComboBox<String>	inputPrefixBox;
	private ComboBox<String>	inputUnitBox;
	private boolean				allowZero;
	
	public ValueField(boolean allowZero)
	{
		this.listeners = new ArrayList<ChangeListener<Number>>();
		
		this.inputField = new TextField();
		this.inputField.setOnAction(new InputFieldHandler());
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
		this.inputField.setMinWidth(60.0d);
		this.inputField.setPrefWidth(100.0d);
		this.inputField.setMaxWidth(Double.MAX_VALUE);
		
		AnchorPane.setLeftAnchor(inputField, 0.0d);
		AnchorPane.setTopAnchor(inputField, 0.0d);
		AnchorPane.setBottomAnchor(inputField, 0.0d);
<<<<<<< HEAD
		AnchorPane.setRightAnchor(inputField, 0.0d);
=======
		AnchorPane.setRightAnchor(inputField, 70.0d + 54.0d);
		
		this.inputUnitBox = new ComboBox<String>();
		this.inputUnitBox.setMinWidth(70.0d);
		this.inputUnitBox.setPrefWidth(70.0d);
		this.inputUnitBox.setMaxWidth(70.0d);
		this.inputUnitBox.getItems().add("g");			// kilogram
		this.inputUnitBox.getItems().add("M\u2609");	// solar mass
		
		AnchorPane.setTopAnchor(inputUnitBox, 0.0d);
		AnchorPane.setBottomAnchor(inputUnitBox, 0.0d);
		AnchorPane.setRightAnchor(inputUnitBox, 0.0d);
		
		this.inputPrefixBox = new ComboBox<String>();
		this.inputPrefixBox.setMinWidth(54.0d);
		this.inputPrefixBox.setPrefWidth(54.0d);
		this.inputPrefixBox.setMaxWidth(54.0d);
		this.inputPrefixBox.getItems().add("k");	// kilo
		this.inputPrefixBox.getItems().add("M");	// mega
		
		AnchorPane.setTopAnchor(inputPrefixBox, 0.0d);
		AnchorPane.setBottomAnchor(inputPrefixBox, 0.0d);
		AnchorPane.setRightAnchor(inputPrefixBox, inputUnitBox.getMinWidth());
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
		
		// Whether or not the number "0" is a valid input
		this.allowZero = allowZero;
		
		super.getChildren().add(inputField);
<<<<<<< HEAD
=======
		super.getChildren().add(inputUnitBox);
		super.getChildren().add(inputPrefixBox);
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
	}
	
	public void setValue(double value)
	{
<<<<<<< HEAD
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
		default:
		{
			break;
		}
		}
		
		this.inputField.setText(Double.toString(value).replace('.', DECIMAL_SEPARATOR) + " " + unit);
=======
		// replace "." with decimal separator
		this.inputField.setText(Double.toString(value).replace('.', DECIMAL_SEPARATOR));
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
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
<<<<<<< HEAD
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
=======
		@Override
		public void handle(ActionEvent e)
		{
			// Check if field is empty
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
			if(inputField.getText() == null || inputField.getText().length() == 0)
			{
				inputField.setText("1");
				return;
			}
			
<<<<<<< HEAD
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
				default:
				{
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
=======
			boolean valid = true;
			int decimalSeparatorCount = 0;
			byte[] b = inputField.getText().getBytes();
			for(int i = 0; i < b.length; ++i)
			{
				// If one decimal separator was found, there should be no thousands separator after that
				if(decimalSeparatorCount > 0 && (b[i] == 44 || b[i] == 46))
				{
					valid = false;
					break;
				}
				
				// Check for multiple decimal separators
				if(b[i] == DECIMAL_SEPARATOR)
				{
					++decimalSeparatorCount;
					
					if(decimalSeparatorCount > 1)
					{
						valid = false;
						break;
					}
				}
				
				// Check if input is number, decimal separator or thousands separator
				if(!((b[i] >= 48 && b[i] <= 57) || (b[i] >= 44 && b[i] <= 46)))
				{
					valid = false;
					break;
				}
			}
			
			if(valid)
			{
				String stringVal = inputField.getText();
				
				// Remove thousands separators
				if(DECIMAL_SEPARATOR == '.')
				{
					stringVal = stringVal.replace(",", "");
				}
				else
				{
					stringVal = stringVal.replace(".", "");
				}
				
				// Replace decimal separator with ".", because Double.parseDouble() can't handle "," as separator
				stringVal = stringVal.replace(DECIMAL_SEPARATOR, '.');
				
				double val = Double.parseDouble(stringVal);
				
				// If input is 0, but 0 is not a valid input, set to 1
				if(val == 0.0d && !allowZero)
				{
					val = 1.0d;
					inputField.setText("1");
				}
				
				for(ChangeListener<Number> l : listeners)
				{
					// TODO: change the observable value maybe?
					l.changed(null, 0, val);
				}
			}
		}
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
	}
}
