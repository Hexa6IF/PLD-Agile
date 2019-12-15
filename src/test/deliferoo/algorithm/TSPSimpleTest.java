package algorithm;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.BestPath;
import model.Delivery;
import model.FullMap;
import model.SpecialNode;
import xml.XMLParser;

class TSPSimpleTest {

    @Test
    public void testTimerGetBestPath() {

    	FullMap map = XMLParser.getInstance().parseMap(new File("src/test/resources/grandPlan.xml"));
    	List<Delivery> deliveries= XMLParser.getInstance().parseDeliveries(new File("src/test/resources/demandeGrand9.xml"),map);
    	Map<String,Map<String,BestPath>> bestPaths= Dijkstra.calculateAllShortestPaths(deliveries, map);

    	final long timeLimit = 40000;
    	long startTime = System.currentTimeMillis();
    	for(int i=0;i<3;i++) {
    		TSP tsp = new TSPSimple(250);
    		tsp.searchSolution(10000, bestPaths, deliveries);
    	}
    	long duration = System.currentTimeMillis() - startTime;
    	assertTrue(duration<timeLimit);
    }
    
    @Test
    public void testGetBestPath() {

    	FullMap map = XMLParser.getInstance().parseMap(new File("src/test/resources/TSPMap.xml"));
    	List<Delivery> deliveries= XMLParser.getInstance().parseDeliveries(new File("src/test/resources/TSPDeliveries.xml"),map);
    	Map<String,Map<String,BestPath>> bestPaths= Dijkstra.calculateAllShortestPaths(deliveries, map);

		TSP tsp = new TSPSimple(250);
		tsp.searchSolution(Integer.MAX_VALUE, bestPaths, deliveries);
		List<SpecialNode> tspResult = tsp.getTransformedSolution();
		String resultIdOrder = "";
		for(SpecialNode node : tspResult) {
			resultIdOrder += node.getNode().getNodeId();
		}
		assertEquals(resultIdOrder, "423514");
    }
}
