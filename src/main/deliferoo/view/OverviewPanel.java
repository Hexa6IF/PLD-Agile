package view;

import java.time.LocalTime;
import java.util.List;
import java.time.Duration;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import model.SpecialNode;

/**
 * Class for components that gives an overview of the round
 *
 * @author teckwan
 * @author sadsitha
 */
public class OverviewPanel extends GridPane {

    private Label startTimeLabel;
    private Label endTimeLabel;
    private Label durationLabel;

    private TextField startTimeText;
    private TextField endTimeText;
    private TextField durationText;
    private TextField durationDifferenceText;

    private Duration duration;
    private Duration tempDuration;

    /**
     * Constructor
     * 
     * @param height
     * @param width
     */
    public OverviewPanel(Double height, Double width) {
	super();
	this.setAlignment(Pos.CENTER);
	this.setHgap(20);
	this.setVgap(5);
	this.setPadding(new Insets(30));
	this.setPrefSize(width, height);
	initializeComponent();
	placeGridComponents();
    }

    /**
     * Initializes the components.
     */
    private void initializeComponent() {
	this.startTimeLabel = new Label("START :");
	this.endTimeLabel = new Label("END :");
	this.durationLabel = new Label("DURATION :");

	this.startTimeText = new TextField("");
	this.endTimeText = new TextField("");
	this.durationText = new TextField("");
	this.durationDifferenceText = new TextField("");

	this.startTimeText.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
	this.endTimeText.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
	this.durationText.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
	this.durationDifferenceText.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));

	this.startTimeText.setEditable(false);
	this.endTimeText.setEditable(false);
	this.durationText.setEditable(false);
	this.durationDifferenceText.setEditable(false);

	this.setVisibilityDurationDifference(false);
    }

    /**
     * Places the components in a grid layout.
     */
    private void placeGridComponents() {
	this.add(this.startTimeLabel, 0, 0);
	this.add(this.endTimeLabel, 2, 0);
	this.add(this.durationLabel, 4, 0);

	this.add(this.startTimeText, 0, 1);
	this.add(this.endTimeText, 2, 1);
	this.add(this.durationText, 4, 1);
	this.add(this.durationDifferenceText, 5, 1);
    }

    public void updateOverview(List<SpecialNode> round) {
	Integer length = round.size();
	if (length > 0) {
	    SpecialNode startNode = round.get(0);
	    SpecialNode endNode = round.get(length - 1);
	    LocalTime startTime = startNode.getPassageTime();
	    LocalTime endTime = endNode.getPassageTime();
	    String startTimeDisplay = startTime.toString();
	    String endTimeDisplay = endTime.toString();
	    this.tempDuration = Duration.between(startTime, endTime);
	    String durationDisplay = this.durationToText(tempDuration);
	    this.startTimeText.setText(startTimeDisplay);
	    this.endTimeText.setText(endTimeDisplay);
	    this.durationText.setText(durationDisplay);
	    if (this.duration != null) {
		Duration durationDifference = this.tempDuration.minus(this.duration);
		String durationDifferenceDisplay = this.durationToText(durationDifference);
		if (durationDifference.isZero()) {
		    this.setVisibilityDurationDifference(false);
		} else if (durationDifference.isNegative()) {
		    durationDifferenceDisplay = "- " + durationDifferenceDisplay;
		    durationDifferenceText.setStyle("-fx-text-fill: green;");
		    this.setVisibilityDurationDifference(true);
		} else {
		    durationDifferenceDisplay = "+ " + durationDifferenceDisplay;
		    durationDifferenceText.setStyle("-fx-text-fill: red;");
		    this.setVisibilityDurationDifference(true);
		}
		this.durationDifferenceText.setText(durationDifferenceDisplay);
	    }
	}
    }

    /**
     * Confirms that the round loaded is final. Hides duration difference indicator.
     */
    public void confirmRound() {
	if (this.tempDuration != null) {
	    this.duration = this.tempDuration;
	}
	this.setVisibilityDurationDifference(false);
    }

    /**
     * Sets visibility of the visibility duration display components
     * 
     * @param visibility
     */
    private void setVisibilityDurationDifference(Boolean visibility) {
	this.durationDifferenceText.setVisible(visibility);
    }

    /**
     * Makes text representation of furation object
     * 
     * @param duration
     * @return Text representation of duration (HH:mm)
     */
    private String durationToText(Duration duration) {
	Long totalSeconds = duration.getSeconds();
	Long hours = totalSeconds / 3600;
	Long minutes = (totalSeconds % 3600) / 60;
	String text = String.format("%sh%sm", Math.abs(hours), Math.abs(minutes));
	return text;
    }
}
