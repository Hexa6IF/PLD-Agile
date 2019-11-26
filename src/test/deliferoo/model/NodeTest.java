package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class NodeTest {

    @Test
    public void testCalculateDistance() {
	Node node1 = new Node((long)25175791, (float)45.75406, (float)4.857418);
	Node node2 = new Node((long)2129259178, (float)45.750404, (float)4.8744674);
	Float distance = node1.calculateDistance(node2);
	assertEquals(distance, (float)1.39);
    }

}
