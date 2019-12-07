package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Class for components that gives an overview of the round
 *
 * @author teckwan
 */
public class OverviewPanel extends GridPane{
    
    private Label startTimeLabel;
    private Label endTimeLabel;
    private Label durationLabel;
    
    private TextField startTimeText;
    private TextField endTimeText;
    private TextField durationText;
    
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
    
    public void updateOverview() {
	
    }
    
    private void initializeComponent() {
	this.startTimeLabel = new Label("START :");
	this.endTimeLabel = new Label("END :");
	this.durationLabel = new Label("TIME :");
	
	this.startTimeText = new TextField("8:00");
	this.endTimeText = new TextField("9:00");
	this.durationText = new TextField("1h");
	
	this.startTimeText.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
	this.endTimeText.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
	this.durationText.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
	
	this.startTimeText.setEditable(false);
	this.endTimeText.setEditable(false);
	this.durationText.setEditable(false);
    }
    
    private void placeGridComponents() {
	this.add(this.startTimeLabel, 0, 0);
	this.add(this.endTimeLabel, 1, 0);
	this.add(this.durationLabel, 2, 0);
	
	this.add(this.startTimeText, 0, 1);
	this.add(this.endTimeText, 1, 1);
	this.add(this.durationText, 2, 1);
    }
}
