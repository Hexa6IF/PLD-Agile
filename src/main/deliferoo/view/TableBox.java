package view;

import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;

/**
 * Class for displaying table of special nodes (pickup and delivery nodes)
 *
 * @author sadsitha
 */
public class TableBox {

    private TableView<NodeTextView> table;

    /**
     * Constructor
     *
     * @param nodeTextViews List of Node text views
     */
    public TableBox(ObservableList<NodeTextView> nodeTextViews) {
	this.table = new TableView<NodeTextView>(nodeTextViews);
	this.setTableColumns();
    }

    /**
     * @return the table
     */
    public TableView<NodeTextView> getTable() {
	return this.table;
    }

    /*
     * Sets the table columns
     * 
     */
    private void setTableColumns() {
	TableColumn<NodeTextView, String> indexColumn = new TableColumn<NodeTextView, String>("Delivery index");
	indexColumn.setCellValueFactory(new PropertyValueFactory<NodeTextView, String>("deliveryIndex"));
	indexColumn.prefWidthProperty().bind(this.table.widthProperty().divide(5));

	TableColumn<NodeTextView, String> typeColumn = new TableColumn<NodeTextView, String>("Type");
	typeColumn.setCellValueFactory(new PropertyValueFactory<NodeTextView, String>("type"));
	typeColumn.prefWidthProperty().bind(this.table.widthProperty().divide(5));

	TableColumn<NodeTextView, Number> durationColumn = new TableColumn<NodeTextView, Number>("Duration (min)");
	durationColumn.setCellValueFactory(new PropertyValueFactory<NodeTextView, Number>("duration"));
	durationColumn.prefWidthProperty().bind(this.table.widthProperty().divide(5));

	TableColumn<NodeTextView, String> timeColumn = new TableColumn<NodeTextView, String>("Time");
	timeColumn.setCellValueFactory(new PropertyValueFactory<NodeTextView, String>("time"));
	timeColumn.prefWidthProperty().bind(this.table.widthProperty().divide(5));

	TableColumn<NodeTextView, String> colorColumn = this.setColorColumn();
	colorColumn.prefWidthProperty().bind(this.table.widthProperty().divide(5));

	table.getColumns().setAll(indexColumn, typeColumn, durationColumn, timeColumn, colorColumn);
    }

    /*
     * Set color column - use color of NodeTextView to create custom css to set
     * background color of cells
     * 
     * @return colorColumn
     */
    private TableColumn<NodeTextView, String> setColorColumn() {
	TableColumn<NodeTextView, String> colorColumn = new TableColumn<NodeTextView, String>("Color code");
	colorColumn.setCellFactory(new Callback<TableColumn<NodeTextView, String>, TableCell<NodeTextView, String>>() {
	    @Override
	    public TableCell<NodeTextView, String> call(TableColumn<NodeTextView, String> param) {
		return new TableCell<NodeTextView, String>() {
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
