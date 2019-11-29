package algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import model.BestPath;

public abstract class TemplateTSP implements TSP {
	
    	private ArrayList<BestPath> bestPathSolution;
	private Integer[] bestSolution;
	private int bestSolutionCost = 0;
	private Boolean timeLimitReached;
	private Map<String, Map<String, BestPath>> graph;
	
	public Boolean getTimeLimitReached(){
		return timeLimitReached;
	}
	
	public void searchSolution(int timeLimit, int nbNodes, int[][] cost, int[] duration){
		timeLimitReached = false;
		bestSolutionCost = Integer.MAX_VALUE;
		bestSolution = new Integer[nbNodes];
		ArrayList<Integer> undiscovered = new ArrayList<Integer>();
		for (int i=1; i<nbNodes; i++) undiscovered.add(i);
		ArrayList<Integer> discovered = new ArrayList<Integer>(nbNodes);
		discovered.add(0); // le premier sommet visite est 0
		branchAndBound(0, undiscovered, discovered, 0, cost, duration, System.currentTimeMillis(), timeLimit);
	}
	
	public void searchSolution(int timeLimit, Map<String, Map<String, BestPath>> graph) {
	    this.graph = graph;
	    timeLimitReached = false;
	    bestSolutionCost = Integer.MAX_VALUE;
	    int nbNodes = graph.size();
	    int[][] cost = this.createCostFromGraph();
	    int [] duration = this.createDurationFromGraph();
	    bestSolution = new Integer[nbNodes];
	    ArrayList<Integer> undiscovered = new ArrayList<Integer>();
	    for (int i=1; i<nbNodes; i++) undiscovered.add(i);
	    ArrayList<Integer> discovered = new ArrayList<Integer>(nbNodes);
	    discovered.add(0); // le premier sommet visite est 0
	    branchAndBound(0, undiscovered, discovered, 0, cost, duration, System.currentTimeMillis(), timeLimit);
	}
	
	private int[][] createCostFromGraph(){
	    int nbNodes = this.graph.size();
	    int[][] cost = new int[nbNodes][nbNodes];//to do : convert to double
	    List<String> keysNodeOrigin = new ArrayList<String>(this.graph.keySet());
	    for (int i=0; i<nbNodes; i++) {
		String nodeOrigin = keysNodeOrigin.get(i);
		List<String> keysNodeDest = new ArrayList<String>(this.graph.get(nodeOrigin).keySet());
		for (int j=0; j<nbNodes; j++) {
		    String nodeDest = keysNodeDest.get(j);
		    int distanceFromOrigintoDest = this.graph.get(nodeOrigin).get(nodeDest).getDistance().intValue();
		    cost[i][j]= distanceFromOrigintoDest;
		}
	    }
	    return cost;
	}
	
	private int[] createDurationFromGraph() {
	    int nbNodes = this.graph.size();
	    int [] duration = new int[nbNodes];
	    String[] nodeList = this.graph.keySet().toArray(new String[1]);
	    Map<String, BestPath> firstEntry = this.graph.get(nodeList[0]);
	    for (int i=0; i<nbNodes; i++) {
		duration[i]= firstEntry.get(nodeList[i]).getStart().getDuration().intValue();
	    }
	    return duration;
	}
	
	public Integer getBestSolution(int i){
		if ((bestSolution == null) || (i<0) || (i>=bestSolution.length))
			return null;
		return bestSolution[i];
	}
	
	public int getBestSolutionCost(){
		return bestSolutionCost;
	}
	
    
      public List<BestPath> getBestPathSolution() {
	  if (this.bestSolution == null)
	      return null;
	  else {
	      List<String> keysNode = new ArrayList<String>(this.graph.keySet());
	      for (int i=0; i<this.bestSolution.length-1; i++) {
		  String nodeOriginID = keysNode.get(bestSolution[i]);
		  String nodeDestID = keysNode.get(bestSolution[i+1]);
		  this.bestPathSolution = new ArrayList<BestPath>();
		  this.bestPathSolution.add(this.graph.get(nodeOriginID).get(nodeDestID));
	      }
	  } 
	  return this.bestPathSolution; }
     
	
	/**
	 * Methode devant etre redefinie par les sous-classes de TemplateTSP
	 * @param currentNode
	 * @param undiscovered : tableau des sommets restant a visiter
	 * @param cost : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
	 * @param duration : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
	 * @return une borne inferieure du cout des permutations commencant par sommetCourant, 
	 * contenant chaque sommet de nonVus exactement une fois et terminant par le sommet 0
	 */
	protected abstract int bound(Integer currentNode, ArrayList<Integer> undiscovered, int[][] cost, int[] duration);
	
	/**
	 * Methode devant etre redefinie par les sous-classes de TemplateTSP
	 * @param currentNode
	 * @param undiscovered : tableau des sommets restant a visiter
	 * @param cost : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
	 * @param duration : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
	 * @return un iterateur permettant d'iterer sur tous les sommets de nonVus
	 */
	protected abstract Iterator<Integer> iterator(Integer currentNode, ArrayList<Integer> undiscovered, int[][] cost, int[] duration);
	
	/**
	 * Methode definissant le patron (template) d'une resolution par separation et evaluation (branch and bound) du TSP
	 * @param currentNode le dernier sommet visite
	 * @param undiscovered la liste des sommets qui n'ont pas encore ete visites
	 * @param discovered la liste des sommets visites (y compris sommetCrt)
	 * @param discoveredCost la somme des couts des arcs du chemin passant par tous les sommets de vus + la somme des duree des sommets de vus
	 * @param cost : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
	 * @param duration : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
	 * @param startTime : moment ou la resolution a commence
	 * @param timeLimit : limite de temps pour la resolution
	 */	
	 void branchAndBound(int currentNode, ArrayList<Integer> undiscovered, ArrayList<Integer> discovered, int discoveredCost, int[][] cost, int[] duration, long startTime, int timeLimit){
		 if (System.currentTimeMillis() - startTime > timeLimit){
			 timeLimitReached = true;
			 return;
		 }
	    if (undiscovered.size() == 0){ // tous les sommets ont ete visites
	    	discoveredCost += cost[currentNode][0];
	    	if (discoveredCost < bestSolutionCost){ // on a trouve une solution meilleure que bestSolution
	    	    	//discovered.toArray(bestSolution);
	    	    	this.bestSolution = discovered.toArray(bestSolution);
	    		bestSolutionCost = discoveredCost;
	    	}
	    } else if (discoveredCost + bound(currentNode, undiscovered, cost, duration) < bestSolutionCost){
	        Iterator<Integer> it = iterator(currentNode, undiscovered, cost, duration);
	        while (it.hasNext()){
	        	Integer prochainSommet = it.next();
	        	discovered.add(prochainSommet);
	        	undiscovered.remove(prochainSommet);
	        	branchAndBound(prochainSommet, undiscovered, discovered, discoveredCost + cost[currentNode][prochainSommet] + duration[prochainSommet], cost, duration, startTime, timeLimit);
	        	discovered.remove(prochainSommet);
	        	undiscovered.add(prochainSommet);
	        }	    
	    }
	}
}

