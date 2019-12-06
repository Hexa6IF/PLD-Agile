package algorithm;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.BestPath;
import model.Delivery;
import model.FullMap;
import xml.XMLParser;

class DijkstraTest {
	
	@BeforeEach
	void setUp() throws Exception {
		
	}
	
	Boolean compareMaps(Map<String,Map<String,Double>> result,Map<String,Map<String,BestPath>> bestPaths) {
		Boolean valid = Boolean.TRUE;
		
		for(String key:result.keySet()) {
			for(String distanceKey:result.get(key).keySet()) {
				System.out.println(result.get(key).get(distanceKey).equals(bestPaths.get(key).get(distanceKey).getDistance()));
				if(!(result.get(key).get(distanceKey).equals(bestPaths.get(key).get(distanceKey).getDistance()))) {
					valid = Boolean.FALSE;
					return valid;
				}
			}
		}
		
		for(String key:bestPaths.keySet()) {
			for(String distanceKey:bestPaths.get(key).keySet()) {
				if(!(result.get(key).containsKey(distanceKey))) {
					valid = Boolean.FALSE;
					return valid;
				}
			}
		}
		
		
		return valid;
	}
	
	@Test
	void testCalculateAllShortestPaths() {
		
		FullMap map = XMLParser.getInstance().parseMap(new File("src/test/resources/FullMap.xml"));

		List<Delivery> deliveries= XMLParser.getInstance().parseDeliveries(new File("src/test/resources/Deliveries.xml"),map);
		Map<String,Map<String,BestPath>> bestPaths = Dijkstra.calculateAllShortestPaths(deliveries, map);
		Map<String,Map<String,Double>> result = new HashMap<String,Map<String,Double>>();
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
		assertTrue(compareMaps(result, bestPaths));
		
	}
	
	@Test
	void testTimeCalculateAllShortestPaths() {
		final int[][] edges = new int[1000][1000];
		for(int i=0;i<1000;i++) {
			Arrays.fill(edges[i],1);
		}
		fail("Not yet implemented");
	}

	@Test
	void testCalculateShortestPaths() {
		fail("Not yet implemented");
	}

}
