package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class BestPathGraphTest {

    @Test
    public void testGetNodeByID() {
	Node[] nodes = new Node[2];
	nodes[0] = new Node((long)25175791, (float)45.75406, (float)4.857418);
	nodes[1] = new Node((long)25175792, (float)45.75406, (float)4.857418);
	BestPathGraph graph = new BestPathGraph(null, nodes);
	assertEquals(nodes[1], graph.getNodeByID((long)25175792));
    }

}
