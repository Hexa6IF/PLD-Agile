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
public class TableBoxView {

    private TableView<SpecialNodeView> table;

    /**
     * Constructor
     *
     * @param specialNodeViews List of Node text views
     */
    public TableBoxView(ObservableList<SpecialNodeView> specialNodeViews) {
	this.table = new TableView<SpecialNodeView>(specialNodeViews);
	this.table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	this.setTableColumns();
    }

    /**
     * @return the table
     */
    public TableView<SpecialNodeView> getTable() {
	return this.table;
    }

    /*
     * Sets the table columns
     * 
     */
    private void setTableColumns() {
	TableColumn<SpecialNodeView, String> indexColumn = new TableColumn<SpecialNodeView, String>("Delivery index");
	indexColumn.setCellValueFactory(new PropertyValueFactory<SpecialNodeView, String>("deliveryIndex"));
	indexColumn.prefWidthProperty().bind(this.table.widthProperty().divide(5));

	TableColumn<SpecialNodeView, String> typeColumn = new TableColumn<SpecialNodeView, String>("Type");
	typeColumn.setCellValueFactory(new PropertyValueFactory<SpecialNodeView, String>("type"));
	typeColumn.prefWidthProperty().bind(this.table.widthProperty().divide(5));

	TableColumn<SpecialNodeView, Number> durationColumn = new TableColumn<SpecialNodeView, Number>("Duration (min)");
	durationColumn.setCellValueFactory(new PropertyValueFactory<SpecialNodeView, Number>("duration"));
	durationColumn.prefWidthProperty().bind(this.table.widthProperty().divide(5));

	TableColumn<SpecialNodeView, String> timeColumn = new TableColumn<SpecialNodeView, String>("Time");
	timeColumn.setCellValueFactory(new PropertyValueFactory<SpecialNodeView, String>("time"));
	timeColumn.prefWidthProperty().bind(this.table.widthProperty().divide(5));

	TableColumn<SpecialNodeView, String> colorColumn = this.setColorColumn();
	colorColumn.prefWidthProperty().bind(this.table.widthProperty().divide(5));
	
	table.getColumns().setAll(indexColumn, typeColumn, durationColumn, timeColumn, colorColumn);
    }

    /*
     * Set color column - use color of NodeTextView to create custom css to set
     * background color of cells
     * 
     * @return colorColumn
     */
    private TableColumn<SpecialNodeView, String> setColorColumn() {
	TableColumn<SpecialNodeView, String> colorColumn = new TableColumn<SpecialNodeView, String>("Color code");
	colorColumn.setCellFactory(new Callback<TableColumn<SpecialNodeView, String>, TableCell<SpecialNodeView, String>>() {
	    @Override
	    public TableCell<SpecialNodeView, String> call(TableColumn<SpecialNodeView, String> param) {
		return new TableCell<SpecialNodeView, String>() {
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