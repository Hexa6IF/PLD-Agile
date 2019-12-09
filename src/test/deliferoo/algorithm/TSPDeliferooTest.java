package algorithm;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import model.BestPath;
import model.Delivery;
import model.FullMap;
import xml.XMLParser;

public class TSPDeliferooTest {

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
	System.out.println("a");
    }

}
