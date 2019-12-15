package view;

import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Delivery;
import model.SpecialNode;

/**
 * Class for displaying details of a delivery
 * 
 * @author teckwan
 */
public class DeliveryDetailView extends GridPane {

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

	HBox headerContainer;

	/**
	 * Constructor
	 * 
	 * @param height
	 * @param width
	 */
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

	public void setDurationEdit(boolean pickup, boolean dropoff) {
		this.pickupDurationField.setEditable(pickup);
		this.dropoffDurationField.setEditable(dropoff);
	}

	public Double getPickupDuration() {
		return Double.parseDouble(this.pickupDurationField.getText());
	}

	public Double getDropoffDuration() {
		return Double.parseDouble(this.dropoffDurationField.getText());
	}

	/**
	 * Method to update each field with the details of a delivery
	 * 
	 * @param delivery
	 * @param deliveryColorMap
	 */
	public void updateDeliveryDetail(Delivery delivery, Map<Integer, Color> deliveryColorMap) {
		Integer deliveryIndex = delivery.getDeliveryIndex();
		SpecialNode pickup = delivery.getPickupNode();
		SpecialNode dropoff = delivery.getDeliveryNode();

		this.deliveryTitle.setText("Delivery #" + deliveryIndex);
		this.color.setValue(deliveryColorMap.get(deliveryIndex));

		if (pickup != null) {
			if (pickup.getPassageTime() != null) {
				this.startTimeField.setText(pickup.getPassageTime().toString());
			}
			this.pickupPointField.setText(pickup.getNode().getNodeId());
			this.pickupDurationField.setText(pickup.getDuration().toString());
		} else {
			this.startTimeField.setText("");
			this.pickupPointField.setText("");
			this.pickupDurationField.setText("");
		}
		if (dropoff != null) {
			if (dropoff.getPassageTime() != null) {
				this.endTimeField.setText(dropoff.getPassageTime().toString());
			}
			this.dropoffPointField.setText(dropoff.getNode().getNodeId());
			this.dropoffDurationField.setText(dropoff.getDuration().toString());
		} else {
			this.endTimeField.setText("");
			this.dropoffPointField.setText("");
			this.dropoffDurationField.setText("");
		}
	}

	private void initializeComponents() {
		this.deliveryTitle = new Text("Delivery ");
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
	}
}
