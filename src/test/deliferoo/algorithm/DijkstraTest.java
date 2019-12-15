package algorithm;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.BestPath;
import model.Delivery;
import model.FullMap;
import model.SpecialNode;
import xml.XMLParser;

class DijkstraTest {

	static FullMap map;
	static Map<String, Map<String, Double>> result;
	static List<Delivery> deliveries;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		map = XMLParser.getInstance().parseMap(new File("src/test/resources/FullMap.xml"));
		deliveries = XMLParser.getInstance().parseDeliveries(new File("src/test/resources/Deliveries.xml"), map);

		result = new HashMap<String, Map<String, Double>>();
		// bestPaths from 2
		Map<String, Double> bp2 = new HashMap<String, Double>();
		bp2.put("2", 0d);
		bp2.put("5", 3d);
		bp2.put("6", 5d);
		result.put("2", bp2);
		// bestPaths from 5
		Map<String, Double> bp5 = new HashMap<String, Double>();
		bp5.put("2", 3d);
		bp5.put("5", 0d);
		bp5.put("6", 8d);
		result.put("5", bp5);
		// bestPaths from 6
		Map<String, Double> bp6 = new HashMap<String, Double>();
		bp6.put("2", 5d);
		bp6.put("5", 8d);
		bp6.put("6", 0d);
		result.put("6", bp6);
	}

	private boolean compareBestPath(Map<String, Double> distances, Map<String, BestPath> bestpaths) {
		for (String distanceKey : distances.keySet()) {
			if (!(distances.get(distanceKey).equals(bestpaths.get(distanceKey).getDistance()))) {
				return false;
			}
		}
		return true;
	}

	private Boolean compareMaps(Map<String, Map<String, Double>> result, Map<String, Map<String, BestPath>> bestPaths) {
		Boolean valid = Boolean.TRUE;

		for (String key : result.keySet()) {
			if (!compareBestPath(result.get(key), bestPaths.get(key))) {
				valid = Boolean.FALSE;
				return valid;
			}
		}

		for (String key : bestPaths.keySet()) {
			for (String distanceKey : bestPaths.get(key).keySet()) {
				if (!(result.get(key).containsKey(distanceKey))) {
					valid = Boolean.FALSE;
					return valid;
				}
			}
		}

		return valid;
	}

	@Test
	void testCalculateAllShortestPaths() {

		Map<String, Map<String, BestPath>> bestPaths = Dijkstra.calculateAllShortestPaths(deliveries, map);

		assertTrue(compareMaps(result, bestPaths));

	}

	@Test
	void testCalculateShortestPaths() {
		Set<SpecialNode> specialNodes = new HashSet<SpecialNode>();

		for (Delivery delivery : deliveries) {
			specialNodes.add(delivery.getDeliveryNode());
			specialNodes.add(delivery.getPickupNode());
		}
		SpecialNode testedNode = deliveries.get(0).getDeliveryNode();
		Map<String, BestPath> bestpaths = Dijkstra.calculateShortestPaths(testedNode, specialNodes, map);
		Map<String, Double> distances = result.get(testedNode.getNode().getNodeId());
		assertTrue(compareBestPath(distances, bestpaths));
	}

}
