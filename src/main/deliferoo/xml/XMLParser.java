package xml;

import java.util.LinkedList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.Edge;
import model.FullGraph;

public class XMLParser {
    private Integer nbNodes = 0;
    private Integer nbEdges = 0;

    public FullGraph mapParser(Document doc) {
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
		    nbNodes += 1;
		}
	    }
	    for (int temp = 0; temp < List.getLength(); temp++) {
		Node node = List.item(temp);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
		    Element eElement = (Element) node;
		    model.Node dest = new model.Node(this.getNodeByIdFromList(nodes, 
			    Long.parseLong(eElement.getAttribute("destination"))));
		    model.Node origin = new model.Node(this.getNodeByIdFromList(nodes, 
			    Long.parseLong(eElement.getAttribute("origine"))));
		    Edge edge = new Edge(origin, dest, 
			    Float.parseFloat(eElement.getAttribute("longueur")),
				    eElement.getAttribute("nomRue"));
		    edges.add(edge);
		    nbEdges += 1;
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	Edge[] edgeArrayForCasting = new Edge[1];
	model.Node[] nodeArrayForCasting = new model.Node[1];
	
	FullGraph graph = new FullGraph(edges.toArray(edgeArrayForCasting), nodes.toArray(nodeArrayForCasting));
	return graph;
    }
    
    private model.Node getNodeByIdFromList(LinkedList<model.Node> list, Long nodeID) throws Exception{
	for (int i = 0; i<list.size(); i++) {
	    if (list.get(i).getIdNode().equals(nodeID)) 
		return list.get(i);
	}
	throw new Exception("node not found");
    }

    public Integer getNbNodes() {
	return this.nbNodes;
    }

    public Integer getNbEdges() {
	return this.nbEdges;
    }
}
