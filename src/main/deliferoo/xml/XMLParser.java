package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {
    private Integer count1 = 0;
    private Integer count2 = 0;

    public void mapParser(Document doc) {
	try {
	    //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	    NodeList nList = doc.getElementsByTagName("noeud");
	    NodeList List = doc.getElementsByTagName("troncon");
	    //System.out.println("----------------------------");
	    for (int temp = 0; temp < nList.getLength(); temp++) {
		Node nNode = nList.item(temp);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		    Element eElement = (Element) nNode;
		    /*
		     * System.out.println("id : " + eElement.getAttribute("id"));
		     * System.out.println("latitude: " + eElement.getAttribute("latitude"));
		     * System.out.println("longitude : " + eElement.getAttribute("longitude"));
		     * System.out.println("------------------------------------------");
		     */
		    count1 = count1 + 1;
		}
	    }
	    //System.out.println("count is " + count1);
	    for (int temp = 0; temp < nList.getLength(); temp++) {
		Node node = List.item(temp);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
		    Element eElement = (Element) node;
		    /*
		     * System.out.println("destination: " + eElement.getAttribute("destination"));
		     * System.out.println("longueur: " + eElement.getAttribute("longueur"));
		     * System.out.println("nomRue : " + eElement.getAttribute("nomRue"));
		     * System.out.println("origine : " + eElement.getAttribute("origine"));
		     * System.out.println("------------------------------------------");
		     */
		    count2 = count2 + 1;
		}
	    }
	    //System.out.println("count is " + count2);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public Integer get1() {
	return this.count1;
    }

    public Integer get2() {
	return this.count2;
    }
}
