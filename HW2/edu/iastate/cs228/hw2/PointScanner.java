package edu.iastate.cs228.hw2;

import java.io.File;


/**
 * 
 * @author Aditi Nachnani
 *
 */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * 
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	

	private String outputFileName; 
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		sortingAlgorithm=algo;
		points = new Point[pts.length];
		if(pts == null || pts.length == 0) {
			throw new IllegalArgumentException();
		}else {
			for (int i = 0; i<pts.length;i++) {
				points[i]=pts[i];
			}
		} 
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		sortingAlgorithm=algo;
		int size = 0;
		int index = 0;
		File file = new File(inputFileName);
		Scanner read = new Scanner(file);
		while(read.hasNextInt()) {
			read.nextInt();
			size++;
		}
		if (size%2!=0) {
			throw new InputMismatchException();
		}
		Scanner read1 = new Scanner(file);
		points = new Point[size/2];
		while(read1.hasNextInt()) {
			int x = read1.nextInt();
			int y = read1.nextInt();
			Point p1 = new Point(x,y);
			points[index]=p1;
			index++;
		}	
		read.close();
		read1.close();
	}

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param algo
	 * @return
	 */
	public void scan()
	{
		
		AbstractSorter aSorter; 
		if (sortingAlgorithm==Algorithm.SelectionSort) {
			aSorter = new SelectionSorter(points);
			outputFileName ="selectionSort.txt";
		}else if(sortingAlgorithm==Algorithm.InsertionSort) {
			aSorter = new InsertionSorter(points);
			outputFileName ="insertionSort.txt";
			
		}else if(sortingAlgorithm==Algorithm.MergeSort) {
			aSorter = new MergeSorter(points);
			outputFileName ="mergeSort.txt";
			
		}else{
			aSorter = new QuickSorter(points);
			outputFileName ="quickSort.txt";
		}
		aSorter.setComparator(0);
		long time1 = System.nanoTime(); 
		aSorter.sort();
		
		long time2 = System.nanoTime();
		Point medianX = aSorter.getMedian();
		
		aSorter.setComparator(1);
		long time3 = System.nanoTime();
		aSorter.sort();
		
		long time4 = System.nanoTime();
		Point medianY =  aSorter.getMedian();

		medianCoordinatePoint = new Point(medianX.getX(),medianY.getY());
		scanTime = (time2-time1)+(time4-time3);
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{ 
		String algo=sortingAlgorithm.toString()+" ".repeat(18-sortingAlgorithm.toString().length());
		String size = points.length+" ".repeat(6-Integer.toString(points.length).length());
		return algo + size + scanTime; 
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		return "MCP: (" + medianCoordinatePoint.getX()+ ", " + medianCoordinatePoint.getY()+")"; 
		// TODO
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
		try {
			 PrintWriter out = new PrintWriter(outputFileName);
			 out.println(this.toString());
			 out.close();
	    } catch (FileNotFoundException e) {
	    	System.out.println("Error: " + e.getMessage());
	    }
		
	}			
}
