package xml;

import java.util.LinkedList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.Edge;
import model.FullGraph;

public class XMLParser {
    private Integer count1 = 0;
    private Integer count2 = 0;

    public FullGraph mapParser(Document doc) {
	System.out.println("beginning of parsing");
	LinkedList<model.Node> nodes = new LinkedList<model.Node>();
	LinkedList<Edge> edges = new LinkedList<Edge>();
	try {
	    NodeList nList = doc.getElementsByTagName("noeud");
	    NodeList List = doc.getElementsByTagName("troncon");
	    for (int temp = 0; temp < nList.getLength(); temp++) {
		Node nNode = nList.item(temp);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		    Element eElement = (Element) nNode;
		    model.Node node = new model.Node(Long.parseLong(eElement.getAttribute("id")),
			    Float.parseFloat(eElement.getAttribute("latitude")),
			    Float.parseFloat(eElement.getAttribute("longitude")));
		    nodes.add(node);
		    /*
		     * System.out.println("id : " + eElement.getAttribute("id"));
		     * System.out.println("latitude: " + eElement.getAttribute("latitude"));
		     * System.out.println("longitude : " + eElement.getAttribute("longitude"));
		     * System.out.println("------------------------------------------");
		     */
		    count1 = count1 + 1;
		}
	    }
	    System.out.println("end of nodes");
	    for (int temp = 0; temp < List.getLength(); temp++) {
		Node node = List.item(temp);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
		    Element eElement = (Element) node;
		    model.Node dest = this.getNodeByIdFromList(nodes, 
			    Long.parseLong(eElement.getAttribute("destination")));
		    model.Node origin = this.getNodeByIdFromList(nodes, 
			    Long.parseLong(eElement.getAttribute("origine")));
		    Edge edge = new Edge(origin, dest, 
			    Float.parseFloat(eElement.getAttribute("longueur")),
				    eElement.getAttribute("nomRue"));
		    edges.add(edge);
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
	    System.out.println("end of edges");
	} catch (Exception e) {
	    e.printStackTrace();
	}
	Edge[] edgeArrayForCasting = new Edge[1];
	model.Node[] nodeArrayForCasting = new model.Node[1];
	
	System.out.println("end of parsing");
	FullGraph graph = new FullGraph(edges.toArray(edgeArrayForCasting), nodes.toArray(nodeArrayForCasting));
	return graph;
    }
    
    private model.Node getNodeByIdFromList(LinkedList<model.Node> list, Long nodeID){
	for (int i = 0; i<list.size(); i++) {
	    if (list.get(i).getIdNode() == nodeID) 
		return list.get(i);
	}
	return null;
    }

    public Integer get1() {
	return this.count1;
    }

    public Integer get2() {
	return this.count2;
    }
}
