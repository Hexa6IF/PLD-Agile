package model;

import java.util.List;

/**
 * Model class which contains the path to follow
 * calculated according to the input deliveries
 * @author lung
 *
 */

public class Round {
    private List<BestPath> resultPath;

    /**
     * @param resultPath
     */
    public Round(List<BestPath> resultPath) {
	this.resultPath = resultPath;
    }

    /**
     * @return the resultPath
     */
    public List<BestPath> getResultPath() {
        return this.resultPath;
    }

    /**
     * @param resultPath the resultPath to set
     */
    public void setResultPat(List<BestPath> resultPath) {
        this.resultPath = resultPath;
    }    
}
