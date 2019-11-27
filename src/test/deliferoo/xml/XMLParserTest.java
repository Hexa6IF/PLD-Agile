package xml;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import model.Edge;
import model.FullGraph;
import model.Node;

public class XMLParserTest {

    @Test
    public void testMapParser() {
	XMLParser parser = new XMLParser();
	try {
	    File fXmlFile = new File("resources/petitPlan.xml");
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(fXmlFile);
	    parser.mapParser(doc);
	    assertEquals(308, parser.get1());
	    assertEquals(616, parser.get2());
	} catch (Exception e) {
	    fail(e);
	}
    }
    
    @Test
    public void testFullGraph() {
	XMLParser parser = new XMLParser();
	try {
	    File fXmlFile = new File("resources/petitPlan.xml");
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(fXmlFile);
	    FullGraph graph = parser.mapParser(doc);
	    Node[] nodes = graph.getNodes();
	    System.out.println("before edgesdfsva");
	    //List<Edge> edges = Arrays.asList(graph.getEdges());
	    System.out.println(Long.toString(graph.getEdges().length));
	    System.out.println(Float.toString(graph.getEdges()[2].getDistance()));
	    Node node = new Node(graph.getEdges()[2].getNodeDest());
	    System.out.println(Long.toString(node.getIdNode()));
	    System.out.println(Long.toString(graph.getEdges()[2].getNodeOrigin().getIdNode()));
	    System.out.println("after assert");
	    assertEquals((long)2129259176, nodes[3].getIdNode());
	    assertEquals((float)45.75171, nodes[3].getLatitude());
	    assertEquals((float)4.8718166, nodes[3].getLongitude());
	    //assertEquals((long)2129259180, edges[2].getNodeDest().getIdNode());
	    /*assertEquals((long)2129259178, edges[2].getNodeOrigin().getIdNode());
	    assertEquals((float)25.26484, edges[2].getDistance());
	    assertEquals("Avenue Lacassagne", edges[3].getStreetname());*/
	    
	} catch (Exception e) {
	    fail(e);
	}
    }

}
