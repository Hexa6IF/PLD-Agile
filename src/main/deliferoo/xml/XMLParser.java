package xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Edge;
import model.FullGraph;
import model.Node;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XMLParser {

    private static XMLParser instance;

    private XMLInputFactory factory;

    private XMLParser() {
	factory = XMLInputFactory.newInstance();
    }

    public static XMLParser getInstance() { 
	if(instance == null) {
	    instance = new XMLParser();
	}
	return instance; 
    }

    public FullGraph parseMap(File mapFile) {
	FullGraph mapGraph = null;

	Map<String, Node> nodeMap = new HashMap<String, Node>();
	List<Edge> edges = new ArrayList<Edge>();

	Double minLat = Double.POSITIVE_INFINITY;
	Double maxLat = Double.NEGATIVE_INFINITY;

	Double minLong = Double.POSITIVE_INFINITY;
	Double maxLong = Double.NEGATIVE_INFINITY;

	try {
	    XMLStreamReader streamReader = factory.createXMLStreamReader(new FileReader(mapFile));
	    
	    while(streamReader.hasNext()) {
		streamReader.next();
		
		if(streamReader.getEventType() == XMLStreamReader.START_ELEMENT) {
		    if(streamReader.getLocalName().equalsIgnoreCase("noeud")) {
			String id = streamReader.getAttributeValue(null, "id");
			Double latitude = Double.parseDouble(streamReader.getAttributeValue(null, "latitude"));
			Double longitude = Double.parseDouble(streamReader.getAttributeValue(null, "longitude"));
			
			if (latitude < minLat) {
			    minLat = latitude;
			}

			if (latitude > maxLat) {
			    maxLat = latitude;
			}

			if (longitude < minLong) {
			    minLong = longitude;
			}

			if (longitude > maxLong) {
			    maxLong = longitude;
			}

			Node node = new Node(id, latitude, longitude);
			nodeMap.put(id, node);
		    }else if(streamReader.getLocalName().equalsIgnoreCase("troncon")) {
			String origin = streamReader.getAttributeValue(null, "origine");
			String destination = streamReader.getAttributeValue(null, "destination");
			Double longueur = Double.parseDouble(streamReader.getAttributeValue(null, "longueur"));
			String nomRue = streamReader.getAttributeValue(null, "nomRue");
			Node originNode = new Node(nodeMap.get(origin));
			Node destNode = new Node(nodeMap.get(destination));

			Edge edge = new Edge(originNode, destNode, longueur, nomRue);
			edges.add(edge);
		    }
		}
	    }
	    Edge[] edgeArrayForCasting = new Edge[1];
	    Node[] nodeArrayForCasting = new Node[1];
	    mapGraph = new FullGraph(edges.toArray(edgeArrayForCasting), nodeMap.values().toArray(nodeArrayForCasting), 
		    minLong, maxLong, minLat, maxLat);
	    
	} catch (XMLStreamException se) {
	    System.err.println(se);
	} catch (FileNotFoundException fe) {
	    System.err.println(fe);
	}
	
	return mapGraph;
    }
}