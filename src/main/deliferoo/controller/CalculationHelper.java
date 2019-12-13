/**
 * 
 */
package controller;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.BestPath;
import model.Cyclist;
import model.Delivery;
import model.SpecialNode;
import model.SpecialNodeType;

/**
 * @author sadsitha
 *
 */
public class CalculationHelper {

    /**
     * Update the passage times of special Nodes. To be called when order of passage
     * is modified
     * 
     * @param specialNodes
     * @param cyclist
     */
    public static void updatePassageTimesSpecialNodes(List<SpecialNode> specialNodes, Cyclist cyclist) {
	Integer listLength = specialNodes.size();
	Map<String, Map<String, BestPath>> bestPaths = cyclist.getBestPaths();
	if (listLength > 2) {
	    for (int i = 1; i < listLength; ++i) {
		SpecialNode specialNodePrevious = specialNodes.get(i - 1);
		String specialNodePreviousIndex = specialNodePrevious.getNode().getNodeId();
		SpecialNode specialNodeCurrent = specialNodes.get(i);
		String specialNodeCurrentIndex = specialNodeCurrent.getNode().getNodeId();
		Double timeSpent = bestPaths.get(specialNodePreviousIndex).get(specialNodeCurrentIndex).getDistance()
			/ cyclist.getSpeed().doubleValue();
		timeSpent += specialNodePrevious.getDuration();
		LocalTime previousPassageTime = specialNodePrevious.getPassageTime();
		LocalTime currentPassageTime = previousPassageTime.plusMinutes(timeSpent.longValue());
		specialNodeCurrent.setPassageTime(currentPassageTime);
	    }
	}
    }

    /**
     * Corrects the specialNode list's order taking into account the precedence
     * constraints. Inserts a delivery's pickup before its drop off if the drop off
     * has been moved up before it.
     * 
     * @param specialNodes
     */
    public static void updatePrecedences(List<SpecialNode> specialNodes) {
	Integer listLength = specialNodes.size();
	for (int i = 0; i < listLength - 1; ++i) {
	    SpecialNode specialNodeCurrent = specialNodes.get(i);
	    Delivery currentDelivery = specialNodeCurrent.getDelivery();
	    if (specialNodeCurrent.getSpecialNodeType() == SpecialNodeType.DROPOFF) {
		for (int j = i + 1; j < listLength; ++j) {
		    SpecialNode consequentNode = specialNodes.get(j);
		    SpecialNodeType specialNodeType = consequentNode.getSpecialNodeType();
		    Delivery consequentDelivery = consequentNode.getDelivery();
		    if (specialNodeType == SpecialNodeType.PICKUP && consequentDelivery == currentDelivery) {
			specialNodes.remove(j); // naive remove and insert
			specialNodes.add(i, consequentNode);
		    }
		}
	    }
	}
    }
}
