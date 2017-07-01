package de.dspindler.graveox.simulation.tools;

import java.text.DecimalFormat;

import de.dspindler.graveox.simulation.physics.Physics;
import de.dspindler.graveox.simulation.physics.RigidBody;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class EditToolPanel extends ToolPanel
{
	private Accordion			tabs;
	
	// Info tab attributes
	private TitledPane			infoPane;
	private ListView<ListItem>	infoList;
	private ListItem[]			infoItems;
	
	private DecimalFormat		valueFormat;
	
	// Trail tab attributes
	private TitledPane			trailPane;
	private AnchorPane			trailAnchor;
	private CheckBox			trailEnableBox;
	private Label				trailColorLabel;
	private ColorPicker			trailColorPicker;
	private Label				trailLengthLabel;
	private Slider				trailLengthSlider;
	private CheckBox			trailLengthBox;
	
	
	public EditToolPanel()
	{
		super("Edit Tool");
		
		this.valueFormat = new DecimalFormat("0.000E0");
		
		initInfoTab();
		initTrailTab();
		
		this.tabs = new Accordion(infoPane, trailPane);
		this.tabs.setExpandedPane(infoPane);
		super.setContent(tabs);
		
		this.updateValues(null);
	}
	
	private void initInfoTab()
	{
		// Info pane
		this.infoPane = new TitledPane();
		this.infoPane.setText("Body Information");
		
		// Info items
		this.infoItems = new ListItem[5];
		this.infoItems[0] = new ListItem("Mass", "");
		this.infoItems[1] = new ListItem("Inertia", "");
		this.infoItems[2] = new ListItem("Position", "");
		this.infoItems[3] = new ListItem("Velocity", "");
		this.infoItems[4] = new ListItem("Speed", "");
		
		// Initialize info list and add above items
		this.infoList = new ListView<ListItem>();
		this.infoList.getItems().addAll(infoItems);
		
		// Disable selection of list
		this.infoList.setSelectionModel(new DisabledSelectionModel<ListItem>());
		
		this.infoPane.setContent(infoList);
	}
	
	private void initTrailTab()
	{
		// Trail panes
		this.trailPane = new TitledPane();
		this.trailPane.setText("Trail");
		this.trailAnchor = new AnchorPane();
		
		// Trail enable check box
		this.trailEnableBox = new CheckBox("Show Trail");
		this.trailEnableBox.setLayoutX(5.0d);
		this.trailEnableBox.setLayoutY(5.0d);
		this.trailAnchor.getChildren().add(trailEnableBox);
		
		// Trail enable check box listener
		this.trailEnableBox.selectedProperty().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal)
			{
				// if trail disabled, disable all trail menus, except this one and enable them, if trail is enabled
				
				trailColorPicker.setDisable(!newVal);
				trailLengthSlider.setDisable(!newVal);
				trailLengthBox.setDisable(!newVal);
			}
		});
		
		// Trail color label
		this.trailColorLabel = new Label("Trail color:");
		this.trailColorLabel.setLayoutX(5.0d);
		this.trailColorLabel.setLayoutY(35.0d);
		this.trailAnchor.getChildren().add(trailColorLabel);
		
		// Trail color picker
		this.trailColorPicker = new ColorPicker();
		this.trailColorPicker.setLayoutX(5.0d);
		this.trailColorPicker.setLayoutY(55.0d);
		this.trailAnchor.getChildren().add(trailColorPicker);
		
		// Trail length label
		this.trailLengthLabel = new Label("Trail length");
		this.trailLengthLabel.setLayoutX(85.0d);
		this.trailLengthLabel.setLayoutY(100.0d);
		this.trailAnchor.getChildren().add(trailLengthLabel);
		
		// Trail length slider
		this.trailLengthSlider = new Slider();
		this.trailLengthSlider.setMin(10);
		this.trailLengthSlider.setMax(1000);
		this.trailLengthSlider.setMajorTickUnit(500);
		this.trailLengthSlider.setShowTickMarks(true);
		this.trailLengthSlider.setShowTickLabels(true);
		this.trailLengthSlider.setLayoutX(10.0d);
		this.trailLengthSlider.setLayoutY(120.0d);
		this.trailLengthSlider.setPrefWidth(210.0d);
		this.trailAnchor.getChildren().add(trailLengthSlider);
		
		// Trail length box
		this.trailLengthBox = new CheckBox("Infinite");
		this.trailLengthBox.setLayoutX(230.0d);
		this.trailLengthBox.setLayoutY(120.0d);
		this.trailAnchor.getChildren().add(trailLengthBox);
		
		this.trailPane.setContent(trailAnchor);
	}
	
	public void updateValues(RigidBody b)
	{
		if(b == null)
		{
			for(ListItem i : infoItems)
			{
				i.setValue("");
			}
			
			this.tabs.setDisable(true);
			
			return;
		}
		this.tabs.setDisable(false);
		
		// Info items
		this.infoItems[0].setValue(valueFormat.format(b.getMass()).replace("E", "x10^"));
		this.infoItems[1].setValue(valueFormat.format(b.getInertia()).replace("E", "x10^"));
		this.infoItems[2].setValue(b.getPosition().toString());
		this.infoItems[3].setValue(b.getVelocity().toString());
		this.infoItems[4].setValue(String.format("%.4f \t %.4f c", b.getVelocity().getMagnitude(), b.getVelocity().getMagnitude() / Physics.LIGHT_SPEED));
		
		// Disable or enable all trail menus accordingly
		this.trailColorPicker.setDisable(!trailEnableBox.isSelected());
		this.trailLengthSlider.setDisable(!trailEnableBox.isSelected());
		this.trailLengthBox.setDisable(!trailEnableBox.isSelected());
		
		// Trail items
		this.trailEnableBox.setSelected(b.isTrailShown());
		
		if(b.hasTrail() && !trailLengthBox.isDisabled())
		{
			this.trailColorPicker.setValue(b.getTrail().getColor());
			
			this.trailLengthBox.setSelected(b.getTrail().getLength() == -1);
			if(b.getTrail().getLength() == -1)
			{
//				this.trailLengthSlider.setValue(10);
				this.trailLengthSlider.setDisable(true);
			}
			else
			{
				this.trailLengthSlider.setValue(b.getTrail().getLength());
				this.trailLengthSlider.setDisable(false);
			}
		}
	}
	
	public CheckBox getTrailEnableBox()
	{
		return trailEnableBox;
	}
	
	public ColorPicker getTrailColorPicker()
	{
		return trailColorPicker;
	}
	
	public Slider getTrailLengthSlider()
	{
		return trailLengthSlider;
	}
	
	public CheckBox getTrailLengthBox()
	{
		return trailLengthBox;
	}
	
	private class ListItem extends HBox
	{
		private String			name;
		private String			value;
		
		private Label			nameLabel;
		private Label			valueLabel;
		
		public ListItem(String name, String value)
		{
			this.name = name;
			this.value = value;
			
			this.nameLabel = new Label(name);
			this.nameLabel.setAlignment(Pos.CENTER_LEFT);
			this.nameLabel.setPrefWidth(100.0d);
			
			this.valueLabel = new Label(value);
			this.valueLabel.setAlignment(Pos.CENTER_RIGHT);
			
			super.getChildren().add(nameLabel);
			super.getChildren().add(valueLabel);
		}
		
		@SuppressWarnings("unused")
		public String getName()
		{
			return name;
		}
		
		public void setValue(String value)
		{
			this.value = value;
			this.valueLabel.setText(value);
		}
		
		@SuppressWarnings("unused")
		public String getValue()
		{
			return value;
		}
	}
	
	private class DisabledSelectionModel<T> extends MultipleSelectionModel<T>
	{
		public DisabledSelectionModel()
		{
			super.setSelectedIndex(-1);
			super.setSelectedItem(null);
		}
		
		@Override public ObservableList<Integer> getSelectedIndices(){return FXCollections.<Integer>emptyObservableList();}
		@Override public ObservableList<T> getSelectedItems(){return FXCollections.<T>emptyObservableList();}
		@Override public void selectAll(){}
		@Override public void selectFirst(){}
		@Override public void selectIndices(int arg0, int... arg1){}
		@Override public void selectLast(){}
		@Override public void clearAndSelect(int arg0){}
		@Override public void clearSelection(){}
		@Override public void clearSelection(int arg0){}
		@Override public boolean isEmpty(){return false;}
		@Override public boolean isSelected(int arg0){return false;}
		@Override public void select(int arg0){}
		@Override public void select(T arg0){}
		@Override public void selectNext(){}
		@Override public void selectPrevious(){}
	}
}
