/**
 * 
 */
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Test class for App
 * 
 * @author sadsitha
 *
 */
public class AppTest {

    @Test
    public void testOnePlusOneShouldReturnTwo() {
	App newApp = new App();
	int sum = newApp.onePlusOne();
	assertEquals(2, sum);
    }

}
