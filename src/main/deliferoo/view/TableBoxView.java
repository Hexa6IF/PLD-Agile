package view;

import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.Pair;
import model.BestPath;
import model.Delivery;
import model.Round;
import model.SpecialNode;

/**
 * Class for displaying table of special nodes (pickup and delivery nodes)
 *
 * @author sadsitha
 */
public class TableBoxView extends TableView<SpecialNodeTextView> {

    /**
     * Constructor
     *
     * @param height preferred height of node
     * @param width  preferred width of node
     */
    public TableBoxView(Double height, Double width) {
	super();
	this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	this.setPrefSize(width, height);
	this.setPlaceholder(new Label("No deliveries loaded"));
	
	this.setTableColumns();
	this.addRowListeners();	
    }

    /**
     * Updates the order of the table box view according to calculated round
     * 
     * @param round
     * @param deliveryColourMap
     */
    public void updateSpecialNodes(Round round, Map<Delivery, Color> deliveryColourMap) {
	this.getItems().clear();
	Integer counter = 0;
	ObservableList<SpecialNodeTextView> specialNodeTextViews = this.getItems();
	for (BestPath bestPath : round.getResultPath()) {
	    if (counter++ == 0) {
		SpecialNode startSpecialNode = bestPath.getStart();
		Color startDeliveryColor = deliveryColourMap.get(startSpecialNode.getDelivery());
		SpecialNodeTextView departureNode = new SpecialNodeTextView(startSpecialNode, startDeliveryColor);
		specialNodeTextViews.add(departureNode);

	    }
	    SpecialNode endSpecialNode = bestPath.getEnd();
	    Color endDeliveryColor = deliveryColourMap.get(endSpecialNode.getDelivery());
	    SpecialNodeTextView arrivalNode = new SpecialNodeTextView(endSpecialNode, endDeliveryColor);
	    specialNodeTextViews.add(arrivalNode);
	}
    }

    /**
     * Adds a SpecialNodeTextView to the table
     * 
     * @param specialNode
     * @param color
     */
    public void addSpecialNode(SpecialNode specialNode, Color color) {
	ObservableList<SpecialNodeTextView> specialNodeTextViews = this.getItems();
	SpecialNodeTextView specialNodeTextView = new SpecialNodeTextView(specialNode, color);
	specialNodeTextViews.add(specialNodeTextView);
    }

    /**
     * Sets the table columns
     * 
     */
    private void setTableColumns() {
	TableColumn<SpecialNodeTextView, String> indexColumn = new TableColumn<SpecialNodeTextView, String>("Delivery index");
	indexColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryIndex"));

	TableColumn<SpecialNodeTextView, String> typeColumn = new TableColumn<SpecialNodeTextView, String>("Type");
	typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
	
	TableColumn<SpecialNodeTextView, Number> durationColumn = new TableColumn<SpecialNodeTextView, Number>("Duration (min)");
	durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

	TableColumn<SpecialNodeTextView, String> timeColumn = new TableColumn<SpecialNodeTextView, String>("Time");
	timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
	
	TableColumn<SpecialNodeTextView, String> colorColumn = this.setColorColumn();
	
	indexColumn.prefWidthProperty().bind(this.widthProperty().divide(5));
	typeColumn.prefWidthProperty().bind(this.widthProperty().divide(5));
	durationColumn.prefWidthProperty().bind(this.widthProperty().divide(5));
	timeColumn.prefWidthProperty().bind(this.widthProperty().divide(5));
	colorColumn.prefWidthProperty().bind(this.widthProperty().divide(5));
	
	this.getColumns().add(indexColumn);
	this.getColumns().add(typeColumn);
	this.getColumns().add(durationColumn);
	this.getColumns().add(timeColumn);
	this.getColumns().add(colorColumn);
    }
    
    private void addRowListeners() {
	this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	
	this.getSelectionModel().selectedItemProperty().addListener((observer, oldSelection, newSelection) -> {
	    if (newSelection != null) {
	        this.getSelectionModel().clearSelection();
	    }
	    
	    Pair<Integer, Integer> indices = getPairSpecialNodes(newSelection.getDeliveryIndex());
	    
	    this.getSelectionModel().selectIndices(indices.getKey(), indices.getValue());
	});
    }
    
    private Pair<Integer, Integer> getPairSpecialNodes(Number deliveryIndex) {
	Integer index = null;
	List<SpecialNodeTextView> sn = this.getItems();
	
	for(int i = 0; i< sn.size(); i++) {
	    if(sn.get(i).getDeliveryIndex() == deliveryIndex) {
		if(index == null) {
		    index = i;
		} else {
		    return new Pair<Integer, Integer>(index, i);
		}
	    }
	}
	return null;
    }

    /**
     * Set color column - use color of SpecialNodeTextView to create custom css to set
     * background color of cells
     * 
     * @return colorColumn
     */
    private TableColumn<SpecialNodeTextView, String> setColorColumn() {
	TableColumn<SpecialNodeTextView, String> colorColumn = new TableColumn<SpecialNodeTextView, String>(
		"Color code");
	colorColumn.setCellFactory(
		new Callback<TableColumn<SpecialNodeTextView, String>, TableCell<SpecialNodeTextView, String>>() {
		    @Override
		    public TableCell<SpecialNodeTextView, String> call(TableColumn<SpecialNodeTextView, String> param) {
			return new TableCell<SpecialNodeTextView, String>() {
			    @Override
			    protected void updateItem(String item, boolean empty) {
				if (!empty) {
				    /* get index of item in collection */
				    int currentIndex = indexProperty().getValue() < 0 ? 0 : indexProperty().getValue();
				    /* get color of item */
				    Color color = param.getTableView().getItems().get(currentIndex).getColor();
				    /* set background color of cell */
				    Double r = color.getRed() * 255;
				    Double g = color.getGreen() * 255;
				    Double b = color.getBlue() * 255;
				    setStyle("-fx-background-color: rgb(" + r + "," + g + ", " + b + ");");
				}
			    }
			};
		    }

		});
	return colorColumn;
    }
}