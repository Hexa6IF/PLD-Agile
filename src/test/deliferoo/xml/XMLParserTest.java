package xml;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

public class XMLParserTest {

    @Test
    public void test() {

	try {
	    File fXmlFile = new File("resources\\petitPlan.xml");
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(fXmlFile);
	    XMLParser.parserxml(doc);
	    assertEquals(616, XMLParser.get1());
	    assertEquals(308, XMLParser.get2());
	} catch (Exception e) {
	}
    }

}
