package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Class to group all controls and message view
 * 
 * @author teckwan
 */
public class ControlPanel extends GridPane{

    private TextArea messageBox;

    private Button modifyButton;
    private Button addButton;
    private Button removeButton;
    private Button undoButton;
    private Button redoButton;
    private Button cancelButton;

    /**
     * Constructor 
     * 
     * @param height
     * @param width
     */
    public ControlPanel(Double height, Double width) {
	super();

	this.setAlignment(Pos.CENTER);
	this.setHgap(20);
	this.setVgap(20);
	this.setPadding(new Insets(20));
	this.setPrefSize(width, height);

	initializeComponent(width, height);
	placeGridComponents();
    }

    /**
     * Method to change currently shown message
     * 
     * @param msg message to show
     */
    public void setCurrentMessage(String msg) {
	this.messageBox.setText(msg);
    }

    public void disableButtons(boolean modify, boolean add, boolean remove, boolean undo, boolean redo, boolean cancel) {
	this.modifyButton.setDisable(modify);
	this.addButton.setDisable(add);
	this.removeButton.setDisable(remove);
	this.undoButton.setDisable(undo);
	this.redoButton.setDisable(redo);
	this.cancelButton.setDisable(cancel);
    }

    private void initializeComponent(Double width, Double height) {
	this.messageBox = new TextArea();

	this.modifyButton = new Button("Modify");	
	this.addButton = new Button("Add");
	this.removeButton = new Button("Remove");
	this.undoButton = new Button("Undo");
	this.redoButton = new Button("Redo");
	this.cancelButton = new Button("Cancel");

	this.messageBox.setEditable(false);
	this.messageBox.setPrefSize(width, height / 2);
	this.messageBox.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 15));

	this.modifyButton.setPrefSize(width / 6, height / 4);
	this.addButton.setPrefSize(width / 6, height / 4);
	this.removeButton.setPrefSize(width / 6, height / 4);
	this.undoButton.setPrefSize(width / 6, height / 4);
	this.redoButton.setPrefSize(width / 6, height / 4);
	this.cancelButton.setPrefSize(width / 6, height / 4);
    }

    private void placeGridComponents() {
	this.add(this.messageBox, 0, 0, 6, 1);

	this.add(this.modifyButton, 0, 1);
	this.add(this.addButton, 1, 1);
	this.add(this.removeButton, 2, 1);
	this.add(this.undoButton, 3, 1);
	this.add(this.redoButton, 4, 1);
	this.add(this.cancelButton, 5, 1);
    }
}
