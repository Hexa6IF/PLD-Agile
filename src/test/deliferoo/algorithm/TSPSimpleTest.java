package algorithm;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.Delivery;
import model.FullMap;
import xml.XMLParser;

class TSPSimpleTest {

	static FullMap map;
	
	static Map<String,Map<String,Double>> result;
	static List<Delivery> deliveries;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		map = XMLParser.getInstance().parseMap(new File("src/test/resources/FullMap.xml"));
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
	void testGetTimeLimitReached() {
		fail("Not yet implemented");
	}

	@Test
	void testSearchSolutionIntIntIntArrayArrayIntArray() {
		fail("Not yet implemented");
	}

	@Test
	void testSearchSolutionIntMapOfStringMapOfStringBestPath() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBestSolution() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBestSolutionCost() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBestPathSolution() {
		fail("Not yet implemented");
	}

}
