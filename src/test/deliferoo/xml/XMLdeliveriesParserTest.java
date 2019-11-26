package xml;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

class XMLdeliveriesParserTest {

    @Test
    void testsize1() throws SAXException, IOException {
	
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
	Integer count = XMLdeliveriesParser.parser(doc);
	
	assertEquals(1, count);
	
    }

    @Test
    void testsize2() throws SAXException, IOException {
	
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
	Integer count = XMLdeliveriesParser.parser(doc);
	
	assertEquals(2, count);
	
    }
    
    @Test
    void testsize3() throws SAXException, IOException {
	
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
	Integer count = XMLdeliveriesParser.parser(doc);
	
	assertEquals(3, count);
	
    }
    
    @Test
    void testsize5() throws SAXException, IOException {
	
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
	Integer count = XMLdeliveriesParser.parser(doc);
	
	assertEquals(5, count);
	
    }
    
    @Test
    void testsize7() throws SAXException, IOException {
	
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
	Integer count = XMLdeliveriesParser.parser(doc);
	
	assertEquals(7, count);
	
    }
    
    @Test
    void testsize9() throws SAXException, IOException {
	
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
	Integer count = XMLdeliveriesParser.parser(doc);
	
	assertEquals(9, count);
	
    }

}
