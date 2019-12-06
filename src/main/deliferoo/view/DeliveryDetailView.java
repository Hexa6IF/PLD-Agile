package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class DeliveryDetailView extends GridPane{
    
    Text deliveryTitle;
    
    ColorPicker color;
    
    Text pickupText;
    Text dropoffText;
    
    Label startTimeLabel;
    Label endTimeLabel;
    Label pickupPointLabel;
    Label dropoffPointLabel;
    Label pickupDurationLabel;
    Label dropoffDurationLabel;
    
    TextField startTimeField;
    TextField endTimeField;
    TextField pickupPointField;
    TextField dropoffPointField;
    TextField pickupDurationField;
    TextField dropoffDurationField;
    
    Button modifyButton;
    
    HBox buttonContainer;
    HBox headerContainer;
    
    public DeliveryDetailView(Double height, Double width) {
	super();
	this.setAlignment(Pos.CENTER);
	this.setHgap(10);
	this.setVgap(10);
	this.setPadding(new Insets(10));
	this.setPrefSize(width, height);
	
	initializeComponents();
	placeGridComponents();
    }
    
    private void initializeComponents() {
	this.deliveryTitle = new Text("Delivery #");
	this.deliveryTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
	this.color = new ColorPicker();
	this.color.setDisable(true);
	this.color.setOpacity(1);
	this.color.setStyle("-fx-color-label-visible: false;");
	this.headerContainer = new HBox(10);
	this.headerContainer.getChildren().addAll(this.deliveryTitle, this.color);
	
	this.pickupText = new Text("Pickup - ");
	this.pickupText.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
	this.startTimeLabel = new Label("Time :");
	this.pickupPointLabel = new Label("Point :");
	this.pickupDurationLabel = new Label("Duration :");
	
	this.dropoffText = new Text("Dropoff - ");
	this.dropoffText.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
	this.endTimeLabel = new Label("Time :");
	this.dropoffPointLabel = new Label("Point :");
	this.dropoffDurationLabel = new Label("Duration :");
	
	this.startTimeField = new TextField();
	this.endTimeField = new TextField();
	this.pickupPointField = new TextField();
	this.dropoffPointField = new TextField();
	this.pickupDurationField = new TextField();
	this.dropoffDurationField = new TextField();
	
	this.startTimeField.setEditable(false);
	this.endTimeField.setEditable(false);
	this.pickupPointField.setEditable(false);
	this.dropoffPointField.setEditable(false);
	this.pickupDurationField.setEditable(false);
	this.dropoffDurationField.setEditable(false);
	
	this.modifyButton = new Button("Modify");
	this.buttonContainer = new HBox(10);
	this.buttonContainer.setAlignment(Pos.BOTTOM_RIGHT);
	this.buttonContainer.getChildren().add(this.modifyButton);
    }
    
    private void placeGridComponents() {
	this.add(this.headerContainer, 0, 0, 3, 1);
	
	this.add(this.pickupText, 0, 2);
	this.add(this.startTimeLabel, 1, 1);
	this.add(this.startTimeField, 2, 1);
	this.add(this.pickupPointLabel, 1, 2);
	this.add(this.pickupPointField, 2, 2);
	this.add(this.pickupDurationLabel, 1, 3);
	this.add(this.pickupDurationField, 2, 3);
	
	this.add(this.dropoffText, 3, 2);
	this.add(this.endTimeLabel, 4, 1);
	this.add(this.endTimeField, 5, 1);
	this.add(this.dropoffPointLabel, 4, 2);
	this.add(this.dropoffPointField, 5, 2);
	this.add(this.dropoffDurationLabel, 4, 3);
	this.add(this.dropoffDurationField, 5, 3);
	
	this.add(this.buttonContainer, 5, 0);
    }
}
