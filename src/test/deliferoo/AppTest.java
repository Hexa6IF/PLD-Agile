/**
 * 
 */
package test.deliferoo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import main.deliferoo.App;

/**
 * @author sadsitha
 *
 */
public class AppTest {

    @Test
    public void testOnePlusOneShouldReturnTwo() {
	App newApp = new App();
	int sum = newApp.onePlusOne();
	assertEquals(2,sum);
    }

}
