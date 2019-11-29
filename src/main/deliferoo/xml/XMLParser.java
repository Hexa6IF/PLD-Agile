package xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Delivery;
import model.Edge;
import model.Node;
import model.SpecialNode;
import model.SpecialNodeType;
import model.FullMap;

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
	if (instance == null) {
	    instance = new XMLParser();
	}
	return instance;
    }

    public FullMap parseMap(File mapFile) {
	FullMap mapGraph = null;

	Map<String, Node> nodeMap = new HashMap<String, Node>();
	List<Edge> edges = new ArrayList<Edge>();

	Double minLat = Double.POSITIVE_INFINITY;
	Double maxLat = Double.NEGATIVE_INFINITY;

	Double minLong = Double.POSITIVE_INFINITY;
	Double maxLong = Double.NEGATIVE_INFINITY;

	try {
	    XMLStreamReader streamReader = factory.createXMLStreamReader(new FileReader(mapFile));

	    while (streamReader.hasNext()) {
		streamReader.next();

		if (streamReader.getEventType() == XMLStreamReader.START_ELEMENT) {
		    if (streamReader.getLocalName().equalsIgnoreCase("noeud")) {
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
		    } else if (streamReader.getLocalName().equalsIgnoreCase("troncon")) {
			String origin = streamReader.getAttributeValue(null, "origine");
			String destination = streamReader.getAttributeValue(null, "destination");

			Double longueur = Double.parseDouble(streamReader.getAttributeValue(null, "longueur"));
			String nomRue = streamReader.getAttributeValue(null, "nomRue");

			Node originNode = nodeMap.get(origin);
			Node destNode = nodeMap.get(destination);

			Edge edge = new Edge(originNode, destNode, longueur, nomRue);
			edges.add(edge);
		    }
		}
	    }

	    mapGraph = new FullMap(minLong, maxLong, minLat, maxLat, nodeMap, edges);

	} catch (XMLStreamException se) {
	    System.err.println(se);
	} catch (FileNotFoundException fe) {
	    System.err.println(fe);
	}

	return mapGraph;
    }

    public List<Delivery> parseDeliveries(File deliveriesFile, FullMap map) {

	ArrayList<Delivery> listDeliveries = new ArrayList<Delivery>();
	Node warehouseAddress = null;
	DateTimeFormatter startTimeFormatter = DateTimeFormatter.ofPattern("H:m:s");
	LocalTime startTime = null;
	Map<String, Node> nodeMap = map.getNodeMap();
	
	try {
	    XMLStreamReader streamReader = factory.createXMLStreamReader(new FileReader(deliveriesFile));

	    while (streamReader.hasNext()) {
		streamReader.next();

		if (streamReader.getEventType() == XMLStreamReader.START_ELEMENT) {
		    
		    if (streamReader.getLocalName().equalsIgnoreCase("entrepot")) {
			
			String heureDepart = streamReader.getAttributeValue(null, "heureDepart");
			
			startTime = LocalTime.parse(heureDepart, startTimeFormatter);

			warehouseAddress = (Node) nodeMap.get(streamReader.getAttributeValue(null, "adresse"));
			
			SpecialNode wareHouseSrt = new SpecialNode(warehouseAddress, SpecialNodeType.START, 0.0,
				startTime);
			SpecialNode wareHouseFin = new SpecialNode(warehouseAddress, SpecialNodeType.FINISH, 0.0, null);
			Delivery wareHouseDel = new Delivery(wareHouseSrt, wareHouseFin);
			listDeliveries.add(wareHouseDel);

		    } else if (streamReader.getLocalName().equalsIgnoreCase("livraison")) {
			
			Integer dureeLivr = Integer.parseInt(streamReader.getAttributeValue(null, "dureeLivraison"));
			Double delivDuration = dureeLivr / 60.0;
			
			Integer dureeEnlev = Integer.parseInt(streamReader.getAttributeValue(null, "dureeEnlevement"));
			Double pickDuration = dureeEnlev / 60.0;
			
			Node delivAdr = nodeMap.get(streamReader.getAttributeValue(null, "adresseLivraison"));
			
			Node pickAdr = nodeMap.get(streamReader.getAttributeValue(null, "adresseEnelvement"));

			SpecialNode startNode = new SpecialNode(pickAdr, SpecialNodeType.PICKUP, pickDuration, null);
			SpecialNode endNode = new SpecialNode(delivAdr, SpecialNodeType.DROPOFF, delivDuration, null);
			Delivery delivery = new Delivery(endNode, startNode);
			listDeliveries.add(delivery);

		    }
		}
	    }

	} catch (XMLStreamException se) {
	    System.err.println(se);
	} catch (FileNotFoundException fe) {
	    System.err.println(fe);
	}

	return listDeliveries;
    }

}