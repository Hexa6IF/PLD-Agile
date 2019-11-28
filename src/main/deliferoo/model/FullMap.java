/**
 * 
 */
package model;

import java.util.List;
import java.util.Map;

/**
 * @author sadsitha
 *
 */
public class FullMap {

    private Double minLong;
    private Double maxLong;
    private Double minLat;
    private Double maxLat;
    private Map<Long, Node> nodeMap;
    private List<Edge> edgeList;

    /**
     * @param minLong
     * @param maxLong
     * @param minLat
     * @param maxLat
     * @param nodeMap
     * @param edgeList
     */
    public FullMap(Double minLong, Double maxLong, Double minLat, Double maxLat, Map<Long, Node> nodeMap,
	    List<Edge> edgeList) {
	this.minLong = minLong;
	this.maxLong = maxLong;
	this.minLat = minLat;
	this.maxLat = maxLat;
	this.nodeMap = nodeMap;
	this.edgeList = edgeList;
    }

    /**
     * @return the minLong
     */
    public Double getMinLong() {
        return minLong;
    }

    /**
     * @param minLong the minLong to set
     */
    public void setMinLong(Double minLong) {
        this.minLong = minLong;
    }

    /**
     * @return the maxLong
     */
    public Double getMaxLong() {
        return maxLong;
    }

    /**
     * @param maxLong the maxLong to set
     */
    public void setMaxLong(Double maxLong) {
        this.maxLong = maxLong;
    }

    /**
     * @return the minLat
     */
    public Double getMinLat() {
        return minLat;
    }

    /**
     * @param minLat the minLat to set
     */
    public void setMinLat(Double minLat) {
        this.minLat = minLat;
    }

    /**
     * @return the maxLat
     */
    public Double getMaxLat() {
        return maxLat;
    }

    /**
     * @param maxLat the maxLat to set
     */
    public void setMaxLat(Double maxLat) {
        this.maxLat = maxLat;
    }

    /**
     * @return the nodeMap
     */
    public Map<Long, Node> getNodeMap() {
        return nodeMap;
    }

    /**
     * @param nodeMap the nodeMap to set
     */
    public void setNodeMap(Map<Long, Node> nodeMap) {
        this.nodeMap = nodeMap;
    }

    /**
     * @return the edgeList
     */
    public List<Edge> getEdgeList() {
        return edgeList;
    }

    /**
     * @param edgeList the edgeList to set
     */
    public void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }
    
    

}
