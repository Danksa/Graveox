package de.dspindler.graveox.ui;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ValueField extends AnchorPane
{
	private static final char 						DECIMAL_SEPARATOR = new DecimalFormatSymbols(Locale.getDefault()).getDecimalSeparator();
	
	private ArrayList<ChangeListener<Number>>		listeners;
	
	private TextField			inputField;
	private ComboBox<String>	inputUnitBox;
	private boolean				allowZero;
	
	public ValueField(boolean allowZero)
	{
		this.listeners = new ArrayList<ChangeListener<Number>>();
		
		this.inputField = new TextField();
		this.inputField.setOnAction(new InputFieldHandler());
		this.inputField.setMinWidth(60.0d);
		this.inputField.setPrefWidth(100.0d);
		this.inputField.setMaxWidth(Double.MAX_VALUE);
		
		AnchorPane.setLeftAnchor(inputField, 0.0d);
		AnchorPane.setTopAnchor(inputField, 0.0d);
		AnchorPane.setBottomAnchor(inputField, 0.0d);
		AnchorPane.setRightAnchor(inputField, 70.0d);
		
		this.inputUnitBox = new ComboBox<String>();
		this.inputUnitBox.setMinWidth(70.0d);
//		this.inputUnitBox.getItems().add("kg");			// kilogram
//		this.inputUnitBox.getItems().add("M\u2609");	// solar mass
		
		AnchorPane.setLeftAnchor(inputUnitBox, inputField.getPrefWidth() + 10.0d);
		AnchorPane.setTopAnchor(inputUnitBox, 0.0d);
		AnchorPane.setBottomAnchor(inputUnitBox, 0.0d);
		AnchorPane.setRightAnchor(inputUnitBox, 0.0d);
		
		// Whether or not the number "0" is a valid input
		this.allowZero = allowZero;
		
		super.getChildren().add(inputField);
		super.getChildren().add(inputUnitBox);
	}
	
	public void setValue(double value)
	{
		// replace "." with decimal separator
		this.inputField.setText(Double.toString(value).replace('.', DECIMAL_SEPARATOR));
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
		@Override
		public void handle(ActionEvent e)
		{
			// Check if field is empty
			if(inputField.getText() == null || inputField.getText().length() == 0)
			{
				inputField.setText("1");
				return;
			}
			
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
	}
}
