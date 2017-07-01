package de.dspindler.graveox.simulation.tools;

import de.dspindler.graveox.ui.ValueField;
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
	private Label				propertyDensityLabel;
	private ValueField			propertyDensityField;
	
	public AddToolPanel()
	{
		super("Add Tool");
		
		this.initPropertiesTab();
		
		this.tabs = new Accordion(propertyPane);
		this.tabs.setExpandedPane(propertyPane);
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
<<<<<<< HEAD
		this.propertyMassField = new ValueField(false, ValueField.UnitType.MASS);
=======
		this.propertyMassField = new ValueField(false);
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
		this.propertyMassField.setLayoutX(50.0d);
		this.propertyMassField.setLayoutY(10.0d);
		this.propertyMassField.setPrefWidth(170.0d);
		this.propertyAnchor.getChildren().add(propertyMassField);
		
		// Property density label
		this.propertyDensityLabel = new Label("Density");
		this.propertyDensityLabel.setLayoutX(5.0d);
		this.propertyDensityLabel.setLayoutY(43.0d);
		this.propertyAnchor.getChildren().add(propertyDensityLabel);
		
		// Property density field
<<<<<<< HEAD
		this.propertyDensityField = new ValueField(false, ValueField.UnitType.DENSITY);
=======
		this.propertyDensityField = new ValueField(false);
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
		this.propertyDensityField.setLayoutX(50.0d);
		this.propertyDensityField.setLayoutY(40.0d);
		this.propertyDensityField.setPrefWidth(170.0d);
		this.propertyAnchor.getChildren().add(propertyDensityField);
		
		this.propertyPane.setContent(propertyAnchor);
	}
	
	public void updateValues(double mass, double density)
	{
		this.propertyMassField.setValue(mass);
		this.propertyDensityField.setValue(density);
	}
	
	public ValueField getMassPropertyField()
	{
		return propertyMassField;
	}
	
	public ValueField getDensityPropertyField()
	{
		return propertyDensityField;
	}
}
