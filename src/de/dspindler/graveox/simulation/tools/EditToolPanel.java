package de.dspindler.graveox.simulation.tools;

import java.text.DecimalFormat;

import de.dspindler.graveox.simulation.physics.RigidBody;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
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
	
	public EditToolPanel()
	{
		super("Edit Tool");
		
		this.valueFormat = new DecimalFormat("0.000E0");
		
		initInfoTab();
		initTrailTab();
		
		this.tabs = new Accordion(infoPane, trailPane);
		super.setContent(tabs);
		
		this.updateValues(null);
	}
	
	private void initInfoTab()
	{
		this.infoPane = new TitledPane();
		this.infoPane.setText("Body Information");
		
		this.infoItems = new ListItem[4];
		this.infoItems[0] = new ListItem("Mass", "");
		this.infoItems[1] = new ListItem("Inertia", "");
		this.infoItems[2] = new ListItem("Position", "");
		this.infoItems[3] = new ListItem("Velocity", "");
		
		this.infoList = new ListView<ListItem>();
		this.infoList.getItems().addAll(infoItems);
		this.infoList.setSelectionModel(new DisabledSelectionModel<ListItem>());
		
		this.infoPane.setContent(infoList);
	}
	
	private void initTrailTab()
	{
		this.trailPane = new TitledPane();
		this.trailPane.setText("Trail");
		this.trailAnchor = new AnchorPane();
		
		this.trailEnableBox = new CheckBox("Show Trail");
		this.trailEnableBox.setLayoutX(5.0d);
		this.trailEnableBox.setLayoutY(5.0d);
		
		this.trailAnchor.getChildren().add(trailEnableBox);
		
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
		
		// Trail items
		this.trailEnableBox.setSelected(b.isTrailShown());
	}
	
	public CheckBox getTrailEnableBox()
	{
		return trailEnableBox;
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
		
		public String getName()
		{
			return name;
		}
		
		public void setValue(String value)
		{
			this.value = value;
			this.valueLabel.setText(value);
		}
		
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
