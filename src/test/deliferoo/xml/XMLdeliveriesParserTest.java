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
	
	File fXmlFile = new File("C:\\Users\\greni\\Downloads\\fichiersXML2019\\fichiersXML2019\\demandePetit1.xml");
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

    	void testsize2() throws SAXException, IOException {
	
	File fXmlFile = new File("C:\\Users\\greni\\Downloads\\fichiersXML2019\\fichiersXML2019\\demandePetit2.xml");
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

}
