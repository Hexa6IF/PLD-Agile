package model;
/**
 * Abstract class for representing a graph
 * composed of Edges.
 * @author lung
 *
 */
public class FullGraph extends AbstractGraph {

    /**
     * @param edges
     */
    public FullGraph(Edge[] edges, Node[] nodes) {
	super(edges, nodes);
    }
    
    public void printFullGraph(){
	System.out.println("nodes :");
	for (int i = 0; i < nodes.length; i++) {
	    System.out.println(nodes[i].getIdNode() + " " +
		    Float.toString(nodes[i].getLatitude()) + " " +
		    Float.toString(nodes[i].getLongitude()));
	}
	System.out.println("\nedges :");
	for (int i = 0; i < edges.length; i++) {
	    Edge edge = (Edge)edges[i];
	    System.out.println(Float.toString(edge.getDistance()) + " " +
		    edge.getStreetname());
	}
    }

}
