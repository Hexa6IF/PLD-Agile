/**
 * 
 */
package view;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Class to display messages for user
 * 
 * @author sadsitha
 *
 */
public class MessageView extends BorderPane{

    private String currentMessage;

    /**
     * Constructor
     */
    public MessageView(Double height, Double width) {
	super();
	this.setPrefSize(width, height);
	
	this.currentMessage = "Application loaded successfully, please load a map";
	this.updateMessage();
    }

    /**
     * @return the currentMessage
     */
    public String getCurrentMessage() {
        return currentMessage;
    }

    /**
     * @param currentMessage the currentMessage to set
     */
    public void setCurrentMessage(String currentMessage) {
        this.currentMessage = currentMessage;
        this.updateMessage();
    }
    
    private void updateMessage() {
	this.getChildren().clear();
	this.setCenter(new Label(this.currentMessage));
    }    
}
