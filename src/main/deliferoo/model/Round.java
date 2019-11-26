package model;
/**
 * Model class which contains the path to follow
 * calculated according to the input deliveries
 * @author lung
 *
 */

public class Round {
    private BestPath[] resultPath;

    /**
     * @param resultPath
     */
    public Round(BestPath[] resultPath) {
	this.resultPath = resultPath;
    }

    /**
     * @return the resultPath
     */
    public BestPath[] getResultPath() {
        return this.resultPath;
    }

    /**
     * @param resultPath the resultPath to set
     */
    public void setResultPath(BestPath[] resultPath) {
        this.resultPath = resultPath;
    }
    
}
