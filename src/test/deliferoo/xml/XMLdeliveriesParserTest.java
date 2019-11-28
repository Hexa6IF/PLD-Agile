package xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.Deliveries;
import model.Delivery;
import model.Edge;
import model.FullGraph;
import xml.XMLdeliveriesParser.EnumXMLdeliveriesParser;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

class XMLdeliveriesParserTest {

    @Test
    void testsize1() throws SAXException, IOException, ParseException {

	// Creation d'un graphe
	model.Node n1 = new model.Node((long) 342873658, (float) 4.8742905, (float) 8.8742975);
	model.Node n2 = new model.Node((long) 25173820, (float) 5.4862686, (float) 6.5964585);
	model.Node n3 = new model.Node((long) 208769039, (float) 1.2548637, (float) 4.8934535);
	ArrayList<model.Node> listNodes = new ArrayList<model.Node>();
	listNodes.add(n1);
	listNodes.add(n2);
	listNodes.add(n3);
	ArrayList<model.Edge> listEdges = new ArrayList<model.Edge>();
	model.Edge[] arrayEdges = new model.Edge[listEdges.size()];
	arrayEdges = listEdges.toArray(arrayEdges);
	model.Node[] arrayNodes = new model.Node[listNodes.size()];
	arrayNodes = listNodes.toArray(arrayNodes);
	FullGraph graphe = new FullGraph(arrayEdges, arrayNodes);

	// Recuperation du document XML a charger
	File fXmlFile = new File("resources\\demandePetit1.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = null;
	try {
	    dBuilder = dbFactory.newDocumentBuilder();
	} catch (ParserConfigurationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Document doc = dBuilder.parse(fXmlFile);
	
	//appel de la fonction a tester
	Deliveries deliveries = XMLdeliveriesParser.parser(doc, graphe);

	//assertions
	SimpleDateFormat startTime = new SimpleDateFormat("H:m:s");
	Date date = startTime.parse("8:0:0");//L'heure de depart par defaut est 8h
	assertEquals(date, deliveries.getStartTime());
	assertEquals((long) 342873658, deliveries.getWarehouseAddress().getIdNode());
	assertEquals((long) 4.8742905, deliveries.getWarehouseAddress().getLatitude());
	assertEquals((long) 8.8742975, deliveries.getWarehouseAddress().getLongitude());
	assertEquals(2, deliveries.getDeliveries().length);
	assertEquals((long) 25173820, deliveries.getDeliveries()[0].getDeliveryNode().getIdNode());
	assertEquals((long) 5.4862686, deliveries.getDeliveries()[0].getDeliveryNode().getLatitude());
	assertEquals((long) 6.5964585, deliveries.getDeliveries()[0].getDeliveryNode().getLongitude());

    }

    @Test
    void testsize2() throws SAXException, IOException, ParseException {

	// Creation d'un graphe
	long entrepot = Long.parseLong("2835339774");
	model.Node n1 = new model.Node((long) entrepot, (float) 4.8742905, (float) 4.8742905);
	model.Node n2 = new model.Node((long) 208769457, (float) 5.4862686, (float) 6.5964585);
	model.Node n3 = new model.Node((long) 1679901320, (float) 1.2548637, (float) 4.8934535);
	model.Node n4 = new model.Node((long) 25336179, (float) 2.3102131, (float) 7.8542905);
	model.Node n5 = new model.Node((long) 208769120, (float) 2.3102131, (float) 7.8542905);
	ArrayList<model.Node> listNodes = new ArrayList<model.Node>();
	listNodes.add(n1);
	listNodes.add(n2);
	listNodes.add(n3);
	listNodes.add(n4);
	listNodes.add(n5);
	ArrayList<model.Edge> listEdges = new ArrayList<model.Edge>();
	model.Edge[] arrayEdges = new model.Edge[listEdges.size()];
	arrayEdges = listEdges.toArray(arrayEdges);
	model.Node[] arrayNodes = new model.Node[listNodes.size()];
	arrayNodes = listNodes.toArray(arrayNodes);
	FullGraph graphe = new FullGraph(arrayEdges, arrayNodes);

	// Recuperation du document XML a charger
	File fXmlFile = new File("resources\\demandePetit2.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = null;
	try {
	    dBuilder = dbFactory.newDocumentBuilder();
	} catch (ParserConfigurationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Document doc = dBuilder.parse(fXmlFile);
	Deliveries deliveries = XMLdeliveriesParser.parser(doc, graphe);

	//assertEquals(2, count);

    }

    @Test
    void testsize3() throws SAXException, IOException, ParseException {

	// Creation d'un graphe
	model.Node n1 = new model.Node((long) 1349383079, (float) 4.8742905, (float) 4.8742905);
	model.Node n2 = new model.Node((long) 191134392, (float) 5.4862686, (float) 6.5964585);
	model.Node n3 = new model.Node((long) 26121686, (float) 1.2548637, (float) 4.8934535);
	model.Node n4 = new model.Node((long) 26470086, (float) 2.3102131, (float) 7.8542905);
	model.Node n5 = new model.Node((long) 55444018, (float) 2.3102131, (float) 7.8542905);
	model.Node n6 = new model.Node((long) 505061101, (float) 2.3102131, (float) 7.8542905);
	model.Node n7 = new model.Node((long) 27362899, (float) 2.3102131, (float) 7.8542905);
	ArrayList<model.Node> listNodes = new ArrayList<model.Node>();
	listNodes.add(n1);
	listNodes.add(n2);
	listNodes.add(n3);
	listNodes.add(n4);
	listNodes.add(n5);
	listNodes.add(n6);
	listNodes.add(n7);
	ArrayList<model.Edge> listEdges = new ArrayList<model.Edge>();
	model.Edge[] arrayEdges = new model.Edge[listEdges.size()];
	arrayEdges = listEdges.toArray(arrayEdges);
	model.Node[] arrayNodes = new model.Node[listNodes.size()];
	arrayNodes = listNodes.toArray(arrayNodes);
	FullGraph graphe = new FullGraph(arrayEdges, arrayNodes);

	// Recuperation du document XML a charger
	File fXmlFile = new File("resources\\demandeMoyen3.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = null;
	try {
	    dBuilder = dbFactory.newDocumentBuilder();
	} catch (ParserConfigurationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Document doc = dBuilder.parse(fXmlFile);

	Deliveries deliveries = XMLdeliveriesParser.parser(doc, graphe);

	//assertEquals(2, count);

    }

    @Test
    void testsize5() throws SAXException, IOException, ParseException {

	// Creation d'un graphe
	long entrepot = Long.parseLong("4150019167");
	model.Node n1 = new model.Node((long) entrepot, (float) 4.8742905, (float) 4.8742905);
	model.Node n2 = new model.Node((long) 55444215, (float) 5.4862686, (float) 6.5964585);
	model.Node n3 = new model.Node((long) 21992645, (float) 1.2548637, (float) 4.8934535);
	model.Node n4 = new model.Node((long) 1036842078, (float) 2.3102131, (float) 7.8542905);
	model.Node n5 = new model.Node((long) 26155372, (float) 2.3102131, (float) 7.8542905);
	model.Node n6 = new model.Node((long) 21717915, (float) 2.3102131, (float) 7.8542905);
	model.Node n7 = new model.Node((long) 25610684, (float) 2.3102131, (float) 7.8542905);
	model.Node n8 = new model.Node((long) 208769083, (float) 2.3102131, (float) 7.8542905);
	model.Node n9 = new model.Node((long) 1400900990, (float) 2.3102131, (float) 7.8542905);
	model.Node n10 = new model.Node((long) 60755991, (float) 2.3102131, (float) 7.8542905);
	model.Node n11 = new model.Node((long) 26317393, (float) 2.3102131, (float) 7.8542905);
	ArrayList<model.Node> listNodes = new ArrayList<model.Node>();
	listNodes.add(n1);
	listNodes.add(n2);
	listNodes.add(n3);
	listNodes.add(n4);
	listNodes.add(n5);
	listNodes.add(n6);
	listNodes.add(n7);
	listNodes.add(n8);
	listNodes.add(n9);
	listNodes.add(n10);
	listNodes.add(n11);
	ArrayList<model.Edge> listEdges = new ArrayList<model.Edge>();
	model.Edge[] arrayEdges = new model.Edge[listEdges.size()];
	arrayEdges = listEdges.toArray(arrayEdges);
	model.Node[] arrayNodes = new model.Node[listNodes.size()];
	arrayNodes = listNodes.toArray(arrayNodes);
	FullGraph graphe = new FullGraph(arrayEdges, arrayNodes);

	// Recuperation du document XML a charger
	File fXmlFile = new File("resources\\demandeMoyen5.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = null;
	try {
	    dBuilder = dbFactory.newDocumentBuilder();
	} catch (ParserConfigurationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Document doc = dBuilder.parse(fXmlFile);
	Deliveries deliveries = XMLdeliveriesParser.parser(doc, graphe);

	//assertEquals(2, count);

    }

    @Test
    void testsize7() throws SAXException, IOException, ParseException {

	// Creation d'un graphe
		long uneAdr = Long.parseLong("2774590477");
		model.Node n1 = new model.Node((long) 25610888, (float) 4.8742905, (float) 4.8742905);
		model.Node n2 = new model.Node((long) 27259745, (float) 5.4862686, (float) 6.5964585);
		model.Node n3 = new model.Node((long) 1362781062, (float) 1.2548637, (float) 4.8934535);
		model.Node n4 = new model.Node((long) 25624140, (float) 2.3102131, (float) 7.8542905);
		model.Node n5 = new model.Node((long) 891129815, (float) 2.3102131, (float) 7.8542905);
		model.Node n6 = new model.Node((long) 194605322, (float) 2.3102131, (float) 7.8542905);
		model.Node n7 = new model.Node((long) 1382784520, (float) 2.3102131, (float) 7.8542905);
		model.Node n8 = new model.Node((long) uneAdr, (float) 2.3102131, (float) 7.8542905);
		model.Node n9 = new model.Node((long) 34401989, (float) 2.3102131, (float) 7.8542905);
		model.Node n10 = new model.Node((long) 26084604, (float) 2.3102131, (float) 7.8542905);
		model.Node n11 = new model.Node((long) 26149156, (float) 2.3102131, (float) 7.8542905);
		model.Node n12 = new model.Node((long) 26463669, (float) 2.3102131, (float) 7.8542905);
		model.Node n13 = new model.Node((long) 26155372, (float) 2.3102131, (float) 7.8542905);
		model.Node n14 = new model.Node((long) 21702421, (float) 2.3102131, (float) 7.8542905);
		model.Node n15 = new model.Node((long) 143403, (float) 2.3102131, (float) 7.8542905);
		ArrayList<model.Node> listNodes = new ArrayList<model.Node>();
		listNodes.add(n1);
		listNodes.add(n2);
		listNodes.add(n3);
		listNodes.add(n4);
		listNodes.add(n5);
		listNodes.add(n6);
		listNodes.add(n7);
		listNodes.add(n8);
		listNodes.add(n9);
		listNodes.add(n10);
		listNodes.add(n11);
		listNodes.add(n12);
		listNodes.add(n13);
		listNodes.add(n14);
		listNodes.add(n15);
	ArrayList<model.Edge> listEdges = new ArrayList<model.Edge>();
	model.Edge[] arrayEdges = new model.Edge[listEdges.size()];
	arrayEdges = listEdges.toArray(arrayEdges);
	model.Node[] arrayNodes = new model.Node[listNodes.size()];
	arrayNodes = listNodes.toArray(arrayNodes);
	FullGraph graphe = new FullGraph(arrayEdges, arrayNodes);

	// Recuperation du document XML a charger
	File fXmlFile = new File("resources\\demandeGrand7.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = null;
	try {
	    dBuilder = dbFactory.newDocumentBuilder();
	} catch (ParserConfigurationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Document doc = dBuilder.parse(fXmlFile);
	Deliveries deliveries = XMLdeliveriesParser.parser(doc, graphe);

	//assertEquals(2, count);

    }

    @Test
    void testsize9() throws SAXException, IOException, ParseException {

	// Creation d'un graphe
	long uneAdr = Long.parseLong("2774590477");
	model.Node n1 = new model.Node((long) 25327124, (float) 4.8742905, (float) 4.8742905);
	model.Node n2 = new model.Node((long) 239603465, (float) 5.4862686, (float) 6.5964585);
	model.Node n3 = new model.Node((long) 26464256, (float) 1.2548637, (float) 4.8934535);
	model.Node n4 = new model.Node((long) 1370403192, (float) 2.3102131, (float) 7.8542905);
	model.Node n5 = new model.Node((long) 25319255, (float) 2.3102131, (float) 7.8542905);
	model.Node n6 = new model.Node((long) 1368674802, (float) 2.3102131, (float) 7.8542905);
	model.Node n7 = new model.Node((long) 984553611, (float) 2.3102131, (float) 7.8542905);
	model.Node n8 = new model.Node((long) 26084216, (float) 2.3102131, (float) 7.8542905);
	model.Node n9 = new model.Node((long) 1678996781, (float) 2.3102131, (float) 7.8542905);
	model.Node n10 = new model.Node((long) 25310896, (float) 2.3102131, (float) 7.8542905);
	model.Node n11 = new model.Node((long) 1301805013, (float) 2.3102131, (float) 7.8542905);
	model.Node n12 = new model.Node((long) 25316399, (float) 2.3102131, (float) 7.8542905);
	model.Node n13 = new model.Node((long) 59654812, (float) 2.3102131, (float) 7.8542905);
	model.Node n14 = new model.Node((long) 25499154, (float) 2.3102131, (float) 7.8542905);
	model.Node n15 = new model.Node((long) 130144280, (float) 2.3102131, (float) 7.8542905);
	model.Node n16 = new model.Node((long) 25624158, (float) 2.3102131, (float) 7.8542905);
	model.Node n17 = new model.Node((long) 26035105, (float) 2.3102131, (float) 7.8542905);
	model.Node n18 = new model.Node((long) 843129906, (float) 2.3102131, (float) 7.8542905);
	model.Node n19 = new model.Node((long) 1362204817, (float) 2.3102131, (float) 7.8542905);
	ArrayList<model.Node> listNodes = new ArrayList<model.Node>();
	listNodes.add(n1);
	listNodes.add(n2);
	listNodes.add(n3);
	listNodes.add(n4);
	listNodes.add(n5);
	listNodes.add(n6);
	listNodes.add(n7);
	listNodes.add(n8);
	listNodes.add(n9);
	listNodes.add(n10);
	listNodes.add(n11);
	listNodes.add(n12);
	listNodes.add(n13);
	listNodes.add(n14);
	listNodes.add(n15);
	listNodes.add(n16);
	listNodes.add(n17);
	listNodes.add(n18);
	listNodes.add(n19);
	ArrayList<model.Edge> listEdges = new ArrayList<model.Edge>();
	model.Edge[] arrayEdges = new model.Edge[listEdges.size()];
	arrayEdges = listEdges.toArray(arrayEdges);
	model.Node[] arrayNodes = new model.Node[listNodes.size()];
	arrayNodes = listNodes.toArray(arrayNodes);
	FullGraph graphe = new FullGraph(arrayEdges, arrayNodes);

	// Recuperation du document XML a charger
	File fXmlFile = new File("resources\\demandeGrand9.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = null;
	try {
	    dBuilder = dbFactory.newDocumentBuilder();
	} catch (ParserConfigurationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Document doc = dBuilder.parse(fXmlFile);
	Deliveries deliveries = XMLdeliveriesParser.parser(doc, graphe);

	//assertEquals(2, count);

    }

    @Test
    void testsingleton() {
	EnumXMLdeliveriesParser enumDeliveries1 = EnumXMLdeliveriesParser.INSTANCE;
	EnumXMLdeliveriesParser enumDeliveries2 = EnumXMLdeliveriesParser.INSTANCE;
	assertEquals(enumDeliveries1, enumDeliveries2);
    }
}
