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
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		map = XMLParser.getInstance().parseMap(new File("src/test/resources/BestPathMap.xml"));
		deliveries= XMLParser.getInstance().parseDeliveries(new File("src/test/resources/Deliveries.xml"),map);

		result = new HashMap<String,Map<String,Double>>();
		//bestPaths from 2
		Map<String,Double> bp2 = new HashMap<String,Double>();
		bp2.put("2", 0d);
		bp2.put("5", 3d);
		bp2.put("6", 5d);
		result.put("2", bp2);
		//bestPaths from 5
		Map<String,Double> bp5 = new HashMap<String,Double>();
		bp5.put("2", 3d);
		bp5.put("5", 0d);
		bp5.put("6", 8d);
		result.put("5", bp5);
		//bestPaths from 6
		Map<String,Double> bp6 = new HashMap<String,Double>();
		bp6.put("2", 5d);
		bp6.put("5", 8d);
		bp6.put("6", 0d);
		result.put("6", bp6);
	}

	
    @Test
    public void testSearchSolution() {
	TSP tsp = new TSPHeuristic();
	//TSP tsp = new TSPSimple();
	final Integer timeLimit = 100000;
	
	XMLParser parser = XMLParser.getInstance();
	//FullMap map = parser.parseMap(new File("src/main/resources/petitPlan.xml"));
	FullMap map = parser.parseMap(new File("src/main/resources/grandPlan.xml"));
	//List<Delivery> deliveries = parser.parseDeliveries(new File("src/main/resources/demandePetit2.xml"), map);
	List<Delivery> deliveries = parser.parseDeliveries(new File("src/main/resources/demandeGrand9.xml"), map);
	Map<String, Map<String, BestPath>> graph = new HashMap<String, Map<String, BestPath>>();	
	
	graph = Dijkstra.calculateAllShortestPaths(deliveries, map);
	long startTime = System.currentTimeMillis();
	tsp.searchSolution(timeLimit, graph, deliveries);
	long finishTime = System.currentTimeMillis() - startTime;
	List<BestPath> solution = tsp.getBestPathSolution();
	Integer totalCost = tsp.getBestSolutionCost();
    }
    
    @Test
    public void testTimerGetBestPath() {
	
    }
    
    @Test
    public void testGetBestPath() {
	
    }
}

