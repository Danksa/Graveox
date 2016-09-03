package de.dspindler.graveox.simulation.tools;

import de.dspindler.graveox.ui.ValueField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

public class AddToolPanel extends ToolPanel
{
	private Accordion			tabs;
	
	// Physical properties tab attributes
	private TitledPane			propertyPane;
	private AnchorPane			propertyAnchor;
	private Label				propertyMassLabel;
	private ValueField			propertyMassField;
	
	public AddToolPanel()
	{
		super("Add Tool");
		
		this.initPropertiesTab();
		
		this.tabs = new Accordion(propertyPane);
		super.setContent(tabs);
	}
	
	private void initPropertiesTab()
	{
		// Property pane
		this.propertyPane = new TitledPane();
		this.propertyPane.setText("Physical Properties");
		this.propertyAnchor = new AnchorPane();
		
		// Property mass label
		this.propertyMassLabel = new Label("Mass");
		this.propertyMassLabel.setLayoutX(5.0d);
		this.propertyMassLabel.setLayoutY(13.0d);
		this.propertyAnchor.getChildren().add(propertyMassLabel);
		
		// Property mass field
		this.propertyMassField = new ValueField(false);
		this.propertyMassField.setLayoutX(50.0d);
		this.propertyMassField.setLayoutY(10.0d);
		this.propertyMassField.setPrefWidth(170.0d);
		this.propertyAnchor.getChildren().add(propertyMassField);
		
		// Event listener
		this.propertyMassField.addValueListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
			{
				System.out.println("Got Value: " + newValue.doubleValue());
			}
		});
		
		this.propertyPane.setContent(propertyAnchor);
	}
}
