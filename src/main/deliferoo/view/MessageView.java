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
public class MessageView {

    private String currentMessage;
    private BorderPane messagePane;

    /**
     * Constructor
     */
    public MessageView() {
	this.messagePane = new BorderPane();
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

    /**
     * @return the messagePane
     */
    public BorderPane getMessagePanel() {
        return messagePane;
    }
    
    private void updateMessage() {
	this.messagePane.getChildren().clear();
	this.messagePane.setCenter(new Label(this.currentMessage));
    }
    
    
    
}
