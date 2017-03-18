package edu.nyu.ds.ank352;

/**
 * This class stores information about a particular tree that grows in New York City
 * @author alexanderkessaris
 * @version 0.1
 * 
 */
public class Tree implements Comparable<Tree> {
	
	//non-negative int
	private int tree_id;
	
	//non-negative int
	private int tree_dbh;
	
	//Valid values: "Alive", "Dead", "Stump", or empty string or null"
	private String status;
	
	//valid values: ”Good”, ”Fair”, ”Poor”, or empty string or null
	private String health;
	
	//(or the name) as a, possibly empty, string, cannot be null
	private String spc_common;
	
	//positive five digit integer (This means that any number from 0 to 99999 is acceptable. The values that are shorter
	//should be treated as if they had leading zeroes, i.e., 8608 represents zipcode 08608, 98 represents zip code 00098, etc.)
	private int zipcode;
	
	//valid values: ”Manhattan”, ”Bronx”, ”Brooklyn”, ”Queens”, ”Staten Island”
	private String boroname;
	
	//double values
	private double x_sp;
	private double y_sp;
	
	/**
	 * Getters in order to retrieve private variables
	 * @return all private variables
	 */
	public int getID() {return tree_id;}
	public int getDBH() {return tree_dbh;}
	public String getStatus() {return status;}
	public String getHealth() {return health;}
	public String getSPC() {return spc_common;}
	public String getZip() {
		String s = String.format("%05d",zipcode);
		return s;
		}
	public String getBoroname() {return boroname;}
	public double getX_sp() {return x_sp;}
	public double getY_sp() {return y_sp;}
	
	/**
	 * Constructor for Tree class
	 * @param id
	 * @param diam
	 * @param status
	 * @param health
	 * @param spc
	 * @param zip
	 * @param boro
	 * @param x
	 * @param y
	 * 
	 * @throws IllegalArgumentException - if Tree object is constructed with invalid properties
	 */
	public Tree ( int id, int diam, String status, String health, String spc,
	           int zip, String boro, double x, double y ) throws IllegalArgumentException
	{
		/**
		 * Make sure values match with expected parameters
		 */
		if (id>=0 && diam>=0 && !spc.equals(null) && (99999>=zip && zip>=0) && 
				(status.equalsIgnoreCase("Alive") || status.equalsIgnoreCase("Dead") ||status.equals("Stump")) ||status.equals(null) &&
				(health.equalsIgnoreCase("Good") || health.equalsIgnoreCase("Fair") ||health.equals("Poor") ||health.equals(null)))
		{
			this.tree_id=id;
			this.tree_dbh=diam;
			this.status=status;
			this.health=health;
			this.spc_common=spc;
			this.zipcode=zip;
			this.boroname=boro;
			this.x_sp=x;
			this.y_sp=y;
		}
		else {
			throw new IllegalArgumentException("Invalid property of tree detected.");
		}
	}
	/**
	 * Override equals method to test if two Tree objects are equal
	 * @param Object tree
	 * @return boolean - whether objects are the same (true) or different (false)
	 * @throws IllegalArgumentException - if the two Tree objects have the same IDs but different species names
	 */
	@Override
	public boolean equals(Object tree) throws IllegalArgumentException {
		if (this.getID()==((Tree) tree).getID() && this.getSPC().equals(((Tree) tree).getSPC())) {
			return true;
		}
		else if (this.getID()==((Tree) tree).getID() && !this.getSPC().equals(((Tree) tree).getSPC())) {
			throw new IllegalArgumentException("Error detected: the IDs of tree should be unique and not shared among different trees.");
		}
		else {
			return false;
		}	
	}
	
	/**
	 * Override the compareTo method to compare two Tree objects
	 * @param Tree object
	 * @return int (whether greater than (1), less than (-1), or equal to (0))
	 */
	@Override
	public int compareTo(Tree tree) {
		/**
		 * Compare by species name first
		 */
		char[] tree1 = this.getSPC().toCharArray();
		char[] tree2 = tree.getSPC().toCharArray();
		char[] larger_tree = (tree1.length >= tree2.length) ? tree1 : tree2;
		
		for (int i=0; i<larger_tree.length;i++) {
			if (tree1[i]>tree2[i]) {
				return 1;
			}
			if (tree1[i]<tree2[i]) {
				return -1;
			}
		}
		/**
		 * If trees have same species name, compare by ID
		 */
		if (tree.getSPC().equals(this.getSPC())) {
			if (tree.getID()>this.getID()) {
				return -1;
			}
			else if (tree.getID()<this.getID()) {
				return 1;
			}
			else {
				return 0;
			}
		}
		return 0;
	}
	
	/**
	 * Override toString method and return a String of the tree's species name and ID
	 * @return String
	 */
	@Override
	public String toString()
	{
		return ("Species: " + this.getSPC() + " ID: " + this.getID());
	}
}
