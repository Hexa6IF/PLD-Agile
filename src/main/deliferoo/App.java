import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import view.Window;

/**
 * Main Application class for Del'IFeroo
 * 
 * @author sadsitha
 */
public class App extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
	new Controller();
    }

    /**
     * Main method
     * 
     * @param args
     */
    public static void main(String[] args) {
	launch(args);
    }
}
