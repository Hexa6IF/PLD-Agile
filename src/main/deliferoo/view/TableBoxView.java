package view;

import javafx.scene.control.Label;

import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DataFormat;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.SpecialNode;

/**
 * Class for displaying table of special nodes (pickup and delivery nodes)
 *
 * @author sadsitha
 */
public class TableBoxView extends TableView<SpecialNodeTextView> {

    public static final DataFormat SNTV_INDEX_DATA = new DataFormat("SpecialNodeTextView");
    private boolean drag;

    /**
     * Constructor
     *
     * @param height preferred height of node
     * @param width  preferred width of node
     */
    public TableBoxView(Double height, Double width) {
	super();

	this.drag = true;

	this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	this.setPrefSize(width, height);
	this.setPlaceholder(new Label("No deliveries loaded"));

	this.setColumns();
    }
    
    public void setDrag(boolean drag) {
	this.drag = drag;
    }
    
    public boolean getDrag() {
	return this.drag;
    }

    /**
     * Updates the contents of the TableBox to correspond with the list of special nodes (order and number)
     * 
     * @param specialNodes
     * @param deliveryColourMap
     */
    public void updateTableBox(List<SpecialNode> specialNodes, Map<Integer, Color> deliveryColorMap) {
	ObservableList<SpecialNodeTextView> specialNodeTextViews = this.getItems();
	specialNodeTextViews.clear();
	for (SpecialNode node : specialNodes) {
	    Integer deliveryIndex = node.getDelivery().getDeliveryIndex();
	    Color color = deliveryColorMap.get(deliveryIndex);
	    specialNodeTextViews.add(new SpecialNodeTextView(node, color));
	}
    }

    /**
     * Sets the table columns
     * 
     */
    private void setColumns() {
	TableColumn<SpecialNodeTextView, String> indexColumn = new TableColumn<SpecialNodeTextView, String>("Delivery index");
	indexColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryIndex"));

	TableColumn<SpecialNodeTextView, String> typeColumn = new TableColumn<SpecialNodeTextView, String>("Type");
	typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

	TableColumn<SpecialNodeTextView, Number> durationColumn = new TableColumn<SpecialNodeTextView, Number>("Duration (min)");
	durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

	TableColumn<SpecialNodeTextView, String> timeColumn = new TableColumn<SpecialNodeTextView, String>("Time");
	timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

	TableColumn<SpecialNodeTextView, Shape> colorColumn = new TableColumn<SpecialNodeTextView, Shape>("Color");
	colorColumn.setCellFactory(column -> {
	    TableCell<SpecialNodeTextView, Shape> tc = new TableCell<SpecialNodeTextView, Shape>() {
		@Override
		protected void updateItem(Shape item, boolean empty) {
		    if (empty) {
			setGraphic(null);
		    } else {
			Color color = column.getTableView().getItems().get(indexProperty().getValue()).getColor();
			setGraphic(new Rectangle(15, 15, color));
		    }
		}
	    };
	    tc.setAlignment(Pos.CENTER);
	    return tc;
	});

	indexColumn.setSortable(false);
	typeColumn.setSortable(false);
	durationColumn.setSortable(false);
	timeColumn.setSortable(false);
	colorColumn.setSortable(false);

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
}