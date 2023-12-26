package edu.iastate.cs228.hw2;

/**
 *  
 * @author Aditi Nachnani
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;

import java.util.Scanner;


import java.util.Random; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{		
	int round = 1; 
	PointScanner p; 
	Scanner scnr = new Scanner(System.in);
	System.out.println("Performances of Four Sorting Algorithms in Point Scanning\n");
	System.out.println("keys:  1 (random integers)  2 (file input)  3 (exit) ");
	System.out.print("Trial "+ round+": ");
	int inputNum = scnr.nextInt();
	PointScanner[] scanners = new PointScanner[4]; 
	while(inputNum!=3) {
		if (inputNum==2) {
			System.out.println("Points from a file");
			System.out.print("File name: ");
			String fileName = scnr.next();
			try {
				scanners[0]= new PointScanner(fileName, Algorithm.SelectionSort);
				scanners[1]= new PointScanner(fileName, Algorithm.InsertionSort);
				scanners[2]= new PointScanner(fileName, Algorithm.MergeSort);
				scanners[3]= new PointScanner(fileName, Algorithm.QuickSort);
			}catch(FileNotFoundException e) {
				System.out.println("Error: " + e.getMessage());
			}
			
		}else if(inputNum==1) {
			System.out.print("Enter number of random points: ");
			int randPoints = scnr.nextInt();
			Point[] pNew = generateRandomPoints(randPoints,new Random());
			scanners[0]= new PointScanner(pNew, Algorithm.SelectionSort);
			scanners[1]= new PointScanner(pNew, Algorithm.InsertionSort);
			scanners[2]= new PointScanner(pNew, Algorithm.MergeSort);
			scanners[3]= new PointScanner(pNew, Algorithm.QuickSort);
			
		}
		for (int i = 0; i<scanners.length;i++) {
			scanners[i].scan();
			
			scanners[i].writeMCPToFile();
		   
		}
		
		System.out.println("\nalgorithm size  time (ns) ");
		System.out.println("-".repeat(34));
		for (int i = 0; i<scanners.length;i++) {
			System.out.println(scanners[i].stats());
		}
		System.out.println("-".repeat(34));
		round++;
		System.out.print("Trial "+ round+": ");
		inputNum = scnr.nextInt();
	}
		
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] × [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		Point[] randP = new Point[numPts];
		if (numPts<1) {
			throw new IllegalArgumentException();
		}
		Random generator = new Random(); 
		for (int i = 0; i<numPts;i++) {
			int numX = generator.nextInt(101)-50;
			int numY = generator.nextInt(101)-50;
			randP[i]=new Point(numX,numY);
		}
		return randP; 
	}
	
}
