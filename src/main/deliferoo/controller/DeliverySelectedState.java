package controller;

import model.Delivery;
import view.Window;

/**
 * 
 * @author sadsitha
 *
 */
public class DeliverySelectedState implements State {

    /**
     * State class constructor
     */
    public DeliverySelectedState() {
	
    }
    
    @Override
    public void selectDeliveryClick(Window window, Controller controller, Integer deliveryIndex) {
	for(Delivery delivery : controller.cyclist.getDeliveries()) {
	    if(delivery.getDeliveryIndex() == deliveryIndex) {
		window.updateSelectedDelivery(delivery);
		break;
	    }
	}
	controller.setCurrentState(controller.DELIVERY_SELECTED_STATE);
    }
}
