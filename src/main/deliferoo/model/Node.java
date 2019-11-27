package model;
import java.lang.Math;
/**
 * Model class.
 * A node corresponds to a place within a city,
 * identified by its ID and coordinates.
 * A node can be associated to a delivery.
 * 
 * @author louis
 */

public class Node {
    //private final Float EARTH_RADIUS =  Float.valueOf((float)6371.0);
    //private final Float PI_DEGREES = Float.valueOf((float)180.0);
    private Long idNode;
    private Float latitude;
    private Float longitude;
    private Delivery delivery;
    
    /**
     * @param idNode
     * @param latitude
     * @param longitude
     */
    public Node(Long idNode, Float latitude, Float longitude) {
	this.idNode = idNode;
	this.latitude = latitude;
	this.longitude = longitude;
    }
    
    
    /**
     * Calculate the distance between 2 Nodes in kilometers
     * 
     * @param otherNode
     * @return distance
     */
    /*
     * public Float calculateDistance(Node otherNode) { Float lat1 =
     * Float.valueOf(this.latitude * (float)Math.PI / PI_DEGREES); Float lat2 =
     * Float.valueOf(otherNode.getLatitude() * (float)Math.PI / PI_DEGREES); Float
     * long1 = Float.valueOf(this.longitude * (float)Math.PI / PI_DEGREES); Float
     * long2 = Float.valueOf(otherNode.getLongitude() * (float)Math.PI /
     * PI_DEGREES);
     * 
     * Float dLat = lat2 - lat1; Float dLong = long2 - long1; Float calcul1 =
     * (float)(Math.sin(dLat / 2.0) * Math.sin(dLat / 2.0) + Math.sin(dLong / 2.0) *
     * Math.sin(dLong / 2.0) * Math.cos(lat1) * Math.cos(lat2)); Float calcul2 =
     * (float)(2.0 * Math.atan2((double)Math.sqrt(calcul1), (double)Math.sqrt(1.0 -
     * calcul1))); Float distance = EARTH_RADIUS * calcul2; return distance; }
     */

    /**
     * @return the idNode
     */
    public Long getIdNode() {
        return this.idNode;
    }

    /**
     * @param idNode the idNode to set
     */
    public void setIdNode(Long idNode) {
        this.idNode = idNode;
    }

    /**
     * @return the latitude
     */
    public Float getLatitude() {
        return this.latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public Float getLongitude() {
        return this.longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the delivery
     */
    public Delivery getDelivery() {
        return this.delivery;
    }

    /**
     * @param delivery the delivery to set
     */
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
    
    
}
