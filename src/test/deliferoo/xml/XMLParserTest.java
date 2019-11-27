package xml;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import model.FullGraph;

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
	    
	} catch (Exception e) {
	    fail(e);
	}
    }

}
