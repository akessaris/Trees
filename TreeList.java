package edu.nyu.ds.ank352;

import java.util.ArrayList;

/**
 * This class is used to store all the Tree objects
 * @author alexanderkessaris
 * @version 0.1
 */
public class TreeList extends ArrayList<Tree>{

	/**
	 * Default constructor that creates an empty ArrayList of Tree objects
	 */
	public TreeList() 
	{
		ArrayList<Tree> tree_list = new ArrayList<Tree>();
	}
	
	/**
	 * Getter - returns the total number of Tree objects stored in this list
	 * @return int
	 */
	public int getTotalNumberOfTrees() {
		return this.size();
	}
	
	/**
	 * Getter - returns  the number of Tree objects in the list whose species matches the speciesName specified by the parameter
	 * @param speciesName
	 * @return int - Tree objects with matching species names
	 */
	public int getCountByTreeSpecies (String speciesName) {
		int trees = 0;
		for (int i = 0; i<this.size();i++) {
			if (this.get(i).toString().equalsIgnoreCase(speciesName.toLowerCase())) {
				trees+=1;
			}
		}
		return trees;
	}
	
	/**
	 * returns the number of Tree objects in the list that are located in the borough specified by the parameter
	 * @param boroName
	 * @return int - trees located in the specified borough
	 */
	public int getCountByBorough (String boroName) {
		int trees = 0;
		for (int i = 0; i<this.size();i++) {
			if (this.get(i).getBoroname().equalsIgnoreCase(boroName)) {
				trees+=1;
			}
		}
		return trees;
	}
	
	/**
	 * Getter - returns the number of Tree objects in the list whose species matches the speciesName specified by the 
	 * first parameter and are located in the borough specified by the second parameter
	 * @param speciesName, boroName
	 * @return int 
	 */
	public int getCountByTreeSpeciesBorough (String speciesName, String boroName) {
		int trees = 0;
		for (int i = 0; i<this.size();i++) {
			if (this.get(i).getBoroname().equalsIgnoreCase(boroName.toLowerCase()) &&
					this.get(i).getSPC().toLowerCase().contains(speciesName.toLowerCase()))
			{
				trees+=1;
			}
		}
		return trees;
	}
	
	/**
	 * Getter - returns an ArrayList<String> object containing a list of all the actual tree species that match a given parameter string speciesName
	 * @param speciesName
	 * @return ArrayList<String> - contains list of tree species
	 */
    public ArrayList<String> getMatchingSpecies(String speciesName)
    {
    	ArrayList<String> list = new ArrayList<String>();
    	for (int i = 0; i<this.size(); i++){
			if (this.get(i).getSPC().toLowerCase().contains(speciesName.toLowerCase()) && !list.contains(this.get(i))){
					list.add(this.get(i).toString());		
			}
		}
		return list;
    }
	
    /**
     * Override the toString method to return a list of Tree objects in String form
     * @return String
     */
    @Override
    public String toString(){
    	String s = "";
    	for (int i=0; i<this.size();i++) {
    		s+=(this.get(i).toString()+ ",");
    	}
    	return "[" + s + "]";
    }	
}
