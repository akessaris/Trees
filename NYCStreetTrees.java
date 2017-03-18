package edu.nyu.ds.ank352;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This project allows users to visualize the popularity of trees in NYC. It accepts command line arguments.
 * @author alexanderkessaris
 * @version 0.1
 *    
 * How to use command line (this is for my personal use on my computer and doesn't reflect usage on other computers):
cd Documents/workspace/Kessaris,Alexander-Project1/bin
java edu.nyu.ds.ank352.NYCStreetTrees ~/Downloads/trees.csv
java edu.nyu.ds.ank352.NYCStreetTrees ~/Desktop/trees_sample.csv
java edu.nyu.ds.ank352.NYCStreetTrees ~/Desktop/trees_sample_copy.csv
java edu.nyu.ds.ank352.NYCStreetTrees ~/Desktop/tree-test3.csv
java edu.nyu.ds.ank352.NYCStreetTrees ~/Desktop/test5.csv

 */
public class NYCStreetTrees {

	/**
	 * Create variables for all column values
	 */
	private static final int COLUMN_TREE_ID = 0;
	private static final int COLUMN_TREE_DBH = 3;
	private static final int COLUMN_STATUS = 6;
	private static final int COLUMN_HEALTH = 7;
	private static final int COLUMN_SPC_COMMON = 9;
	private static final int COLUMN_ZIPCODE = 25;
	private static final int COLUMN_BORONAME = 29;
	private static final int COLUMN_X_SP = 39;
	private static final int COLUMN_Y_SP = 40;
	
	/**
	 * Main Method
	 * @param args - receives filename as an argument from command line
	 * @throws IOException - if file is invalid
	 * @throws IllegalArgumentException - if properties of Tree objects are invalid
	 * @throws IndexOutOfBoundsException - if no file is passed to main method
	 */
	public static void main (String [] args) throws IOException, IllegalArgumentException, IndexOutOfBoundsException{

		/**
		 * Attempt to run the program
		 */
		try {
			/**
			 * Accept a csv file as a parameter and read its contents as a file object
			 */
			File file = new File (args[0]);
			
			/**
			 * Make sure file is readable
			 */
			if (file.canRead()) {
				/**
				 * Create TreeList object and scanner to read file
				 */
				TreeList tree_list = new TreeList();
				Scanner scanner = new Scanner (file);
				
				/**
				 * Read in column names in first line to skip over it
				 */
				if (scanner.hasNextLine()) {
					String column_names = scanner.nextLine();
				}
				
				/**
				 * Read in rest of content and pass to splitCSVLine method to create Tree objects for TreeList array
				 * Catch and skip over Tree objects with invalid properties
				 */
				while (scanner.hasNextLine()) {
					ArrayList<String> temp = splitCSVLine(scanner.nextLine());		
					
					/**
					if (temp.size()!=0) {
						for (int j=0; j<temp.size(); j++) {
							System.out.println(temp.get(j));
							//System.out.println(temp.get(index));
						}
					}
					*/
					
					/**
					 * Test if line has 41 values, if not then don't create a Tree object and add to tree_list
					 */
					if (temp.size()==41) {
						try {
							tree_list.add(new Tree(Integer.parseInt(temp.get(COLUMN_TREE_ID)), Integer.parseInt(temp.get(COLUMN_TREE_DBH)),
									temp.get(COLUMN_STATUS),temp.get(COLUMN_HEALTH),temp.get(COLUMN_SPC_COMMON),
									Integer.parseInt(temp.get(COLUMN_ZIPCODE)),temp.get(COLUMN_BORONAME),
									Double.parseDouble(temp.get(COLUMN_X_SP)), Double.parseDouble(temp.get(COLUMN_Y_SP))));
							
							/**
							 * Remove duplicate Tree objects by checking if they are equal
							 */
							for (int i = 0; i < tree_list.size()-1; i++) {
								if (tree_list.get(i).equals(tree_list.get(tree_list.size()-1))) {
									tree_list.remove(tree_list.size()-1);
									i=tree_list.size();
								}
							}
						}
						catch (IllegalArgumentException e) {			
						}
					}
				}	
				
				
				
				Scanner input = new Scanner(System.in);
				boolean search = true;
				while (search) 	{
					/**
					 * Prompt user to enter a tree species to search for
					 */
					System.out.println("Enter the tree species to learn more about it ('quit' to stop)");
					String tree = input.nextLine().toLowerCase();					
					
					/**
					 * If the user enters a value that isn't "quit," the program will continue
					 */
					if (tree.equals("quit")) {
						System.out.println("Thank you for using my program!");
						break;
					}
					
					/**
					 * Place all matching trees species in a new TreeList
					 */
					TreeList matches = new TreeList();
					for (int i = 0; i<tree_list.size(); i++) {
						if (tree_list.get(i).getSPC().contains(tree)){
							matches.add(tree_list.get(i));
						}
					}
					/**
					 * If matches is empty(no matches were found) output a message and start a new loop
					 */
					if (matches.isEmpty()) {
						System.out.println("There were no records of " + tree + " on NYC streets.");
						continue;
					}
					
					/**
					 * Test matches against an ArrayList of outputted species to prevent printing duplicate species
					 */
					ArrayList<String> outputted_species = new ArrayList<>();
					System.out.println("\nAll matching species: ");
					/**
					 * Output matching species
					 */
					for (int i = 0; i<matches.size(); i++) {	
						String holder = matches.get(i).getSPC().toLowerCase();
						if (!outputted_species.contains(holder)) {
							System.out.println(matches.get(i).getSPC());
						}
						outputted_species.add(matches.get(i).getSPC().toLowerCase());
					}
					/**
					 * Test matches against an ArrayList of outputted borough names to prevent printing duplicate boroughs
					 */
					ArrayList<String> outputted_boro = new ArrayList<>();
					System.out.println("\nPopularity in the city: ");
					/**
					 * Output the following:
					 * Borough
					 * Total number of the tree different types of trees(variable) in that borough,
					 * The number in parenthesis is the total number of trees in that borough, 
					 * The last column contains the percentage calculated as the total number of trees(variable) divided by the total number of trees times 100.
					 */
					for (int i = 0; i<matches.size(); i++) {
						String holder = matches.get(i).getBoroname().toLowerCase();
						if (!outputted_boro.contains(holder)) {
							System.out.printf("%-15s : %,10d(%d) %10.2f%%",
									matches.get(i).getBoroname(),
									matches.getCountByTreeSpeciesBorough(matches.get(i).getSPC(),matches.get(i).getBoroname()),
									tree_list.getCountByBorough(matches.get(i).getBoroname()),
									(1.0*matches.getCountByTreeSpeciesBorough(matches.get(i).getSPC(),matches.get(i).getBoroname()) / tree_list.getTotalNumberOfTrees() * 100.0)								
									);
							System.out.println();
						}
						outputted_boro.add(matches.get(i).getBoroname().toLowerCase());
					}
				}
				/**
				 * Close scanners
				 */
				input.close();
				scanner.close();
				}
			/**
			 * If file can't be opened, print an error message
			 */
			else {
				System.err.println("Error: the file " + args[0] + " could not be opened.");
			}
			}
		/**
		 * Catch an IOException and print error message if the file can't be opened
		 */
		catch (IOException e) {;
			System.err.println("Error: the file " + args[0] + " could not be opened.");
		}
		/**
		 * Catch IllegalArgument exception
		 */
		catch(IllegalArgumentException e) {
			System.err.println(e.getMessage());
			
		}
		/**
		 * Catch IndexOutOfBoundsException if no file name is passed as argument
		 */
		catch (IndexOutOfBoundsException e) {
			System.err.println("Usage Error: the program expects a file name as an argument.");
		}
		/**
		 * Catch general exception
		 */
		catch (Exception e) {
			System.err.println("Error: An unexpected error occurred.");
		}
	}
	
	/**
	 * Splits the given line of a CSV file according to commas and double quotes
	 * (double quotes are used to surround  multi-word entries that may contain commas). 
	 * 
	 * @param textLine  line of text to be parsed
	 * @return an ArrayList object containing all individual entries/tokens
	 *         found on the line.
	 */
	public static ArrayList<String> splitCSVLine(String textLine) {
		ArrayList<String> entries = new ArrayList<String>();
		int lineLength = textLine.length();
		StringBuffer nextWord = new StringBuffer();
		char nextChar;
		boolean insideQuotes = false;
		boolean insideEntry= false;
		
		//iterate over all characters in the textLine
		for (int i = 0; i < lineLength; i++) {
			nextChar = textLine.charAt(i);
			
			//handle smart quotes as well as regular quotes 
			if (nextChar == '"' || nextChar == '\u201C' || nextChar =='\u201D') { 
				//change insideQuotes flag when nextChar is a quote
				if (insideQuotes) {
					insideQuotes = false;
					insideEntry = false; 
				}
				else {
					insideQuotes = true; 
					insideEntry = true; 
				}
			}
			else if (Character.isWhitespace(nextChar)) {
				if  ( insideQuotes || insideEntry ) {
					// add it to the current entry
					nextWord.append( nextChar );
				}
				else  { // skip all spaces between entries 
					continue;
				}
			}
			else if ( nextChar == ',') {
				if (insideQuotes) //comma inside an entry 
					nextWord.append(nextChar);
				else { //end of entry found 
					insideEntry = false; 
					entries.add(nextWord.toString());
					nextWord = new StringBuffer();
				}
			}
			else {
				//add all other characters to the nextWord 
				nextWord.append(nextChar);
				insideEntry = true; 
			}

		}
		// add the last word (assuming not empty)
		// trim the white space before adding to the list
		if (!nextWord.toString().equals("")) {
			entries.add(nextWord.toString().trim());
		}

		return entries;
	}
}
