/**
 * 
 */
package view;


/**
 * Class to represent the text view of a delivery
 * 
 * @author sadsitha
 *
 */
public class DeliveryTextView {
    
    private Float startLongitude;
    private Float startLatitude;
    private Float endLongitude;
    private Float endLatitude;
    private String pickUpTime;
    private String dropOffTime;
    private Float pickUpDuration;
    private Float dropOffDuration;
    
    /**
     * @param startLongitude
     * @param startLatitude
     * @param endLongitude
     * @param endLatitude
     * @param pickUpTime
     * @param dropOffTime
     * @param pickUpDuration
     * @param dropOffDuration
     */
    public DeliveryTextView(Float startLongitude, Float startLatitude, Float endLongitude, Float endLatitude,
	    String pickUpTime, String dropOffTime, Float pickUpDuration, Float dropOffDuration) {
	this.startLongitude = startLongitude;
	this.startLatitude = startLatitude;
	this.endLongitude = endLongitude;
	this.endLatitude = endLatitude;
	this.pickUpTime = pickUpTime;
	this.dropOffTime = dropOffTime;
	this.pickUpDuration = pickUpDuration;
	this.dropOffDuration = dropOffDuration;
    }
    
    

}
