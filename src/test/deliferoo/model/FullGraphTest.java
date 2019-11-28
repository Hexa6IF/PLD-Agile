package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class FullGraphTest {

    @Test
    public void testGetNodeByID() {
	Node[] nodes = new Node[2];
	nodes[0] = new Node((long)25175791, 45.75406, 4.857418);
	nodes[1] = new Node((long)25175792, 45.75406, 4.857418);
	FullGraph graph = new FullGraph(null, nodes);
	assertEquals(nodes[1], graph.getNodeByID((long)25175792));
    }

}
