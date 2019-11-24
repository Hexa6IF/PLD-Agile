/**
 * 
 */
package test.deliferoo;

import static org.junit.Assert.*;

import org.junit.Test;

import main.deliferoo.App;

/**
 * Test class for App
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
