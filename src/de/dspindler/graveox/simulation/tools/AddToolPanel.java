package de.dspindler.graveox.simulation.tools;

import javafx.scene.control.Accordion;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

public class AddToolPanel extends ToolPanel
{
	private Accordion			tabs;
	
	// Physical properties tab attributes
	private TitledPane			propertyPane;
	private AnchorPane			propertyAnchor;
	private Label				propertyMassLabel;
	private TextField			propertyMassField;
	private ComboBox<String>			propertyMassBox;
	
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
		this.propertyMassField = new TextField("Value");
		this.propertyMassField.setLayoutX(50.0d);
		this.propertyMassField.setLayoutY(10.0d);
		this.propertyMassField.setPrefWidth(170.0d);
		this.propertyAnchor.getChildren().add(propertyMassField);
		
		// Property mass unit box
		this.propertyMassBox = new ComboBox<String>();
		// Some test units, without function
		this.propertyMassBox.getItems().add("g");
		this.propertyMassBox.getItems().add("kg");
		this.propertyMassBox.getItems().add("M\u2609");
		this.propertyMassBox.setLayoutX(220.0d);
		this.propertyMassBox.setLayoutY(10.0d);
		this.propertyAnchor.getChildren().add(propertyMassBox);
		
		this.propertyPane.setContent(propertyAnchor);
	}
}
