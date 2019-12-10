package algorithm;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.BestPath;
import model.Delivery;
import model.FullMap;
import xml.XMLParser;

public class TSPHeuristicTest {

	static FullMap map;
	static Map<String,Map<String,Double>> result;
	static List<Delivery> deliveries;
	static Map<String,Map<String,BestPath>> bestPaths;
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		map = XMLParser.getInstance().parseMap(new File("src/test/resources/TSPMap.xml"));
		deliveries= XMLParser.getInstance().parseDeliveries(new File("src/test/resources/TSPDeliveries.xml"),map);
		bestPaths = Dijkstra.calculateAllShortestPaths(deliveries, map);
		
	}

	/* Test Louis
	 * 
    @Test
    public void testSearchSolution() {
	TSP tsp = new TSPHeuristic();
	//TSP tsp = new TSPSimple();
	final Integer timeLimit = 1000000;
	XMLParser parser = XMLParser.getInstance();
	//FullMap map = parser.parseMap(new File("src/main/resources/petitPlan.xml"));
	FullMap map = parser.parseMap(new File("src/main/resources/grandPlan.xml"));
	//List<Delivery> deliveries = parser.parseDeliveries(new File("src/main/resources/demandePetit2.xml"), map);
	List<Delivery> deliveries = parser.parseDeliveries(new File("src/main/resources/demandeGrand9.xml"), map);
	Map<String, Map<String, BestPath>> graph = new HashMap<String, Map<String, BestPath>>();	
	
	graph = Dijkstra.calculateAllShortestPaths(deliveries, map);
	tsp.searchSolution(timeLimit, graph, deliveries);
	List<BestPath> solution = tsp.getBestPathSolution();
	Integer totalCost = tsp.getBestSolutionCost();
    }
    */
    @Test
    public void testTimerGetBestPath() {

    	final long timeLimit = Long.MAX_VALUE;
    	long startTime = System.currentTimeMillis();
    	for(int i=0;i<10;i++) {
    		TSP tsp = new TSPHeuristic();
    		tsp.searchSolution(Integer.MAX_VALUE, bestPaths, deliveries);
    	}
    	long duration = System.currentTimeMillis() - startTime;
    	assertTrue(duration<timeLimit);
    }
    
    @Test
    public void testGetBestPath() {

		TSP tsp = new TSPHeuristic();
		tsp.searchSolution(Integer.MAX_VALUE, bestPaths, deliveries);
    }
}

