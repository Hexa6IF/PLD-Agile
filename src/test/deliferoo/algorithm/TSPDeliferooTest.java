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
	TSPSimple tsp = new TSPSimple();
	final Integer timeLimit = 100000;
	
	XMLParser parser = XMLParser.getInstance();
	FullMap map = parser.parseMap(new File("src/main/resources/moyenPlan.xml"));
	List<Delivery> deliveries = parser.parseDeliveries(new File("src/main/resources/demandeMoyen5.xml"), map);
	Map<String, Map<String, BestPath>> graph = new HashMap<String, Map<String, BestPath>>();	
	
	graph = Dijkstra.calculateAllShortestPaths(deliveries, map);
	LocalTime a = LocalTime.of(8, 59);
	LocalTime b = a.plusMinutes(5);
	tsp.searchSolution(timeLimit, graph, deliveries);
	List<BestPath> solution = tsp.getBestPathSolution();
	Integer totalCost = tsp.getBestSolutionCost();
	System.out.println("a");
    }

}
