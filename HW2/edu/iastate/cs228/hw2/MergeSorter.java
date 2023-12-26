package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;

/**
 *  
 * @author Aditi Nachnani
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		super.algorithm="mergesort"; 
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		
		 mergeSortRec(this.points);
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
		int n = pts.length;
		
		if (n<=1) {
			return;
		}
		int m = n/2;
		Point[] left=new Point[m];
		Point[] right=new Point[n-m];
		for (int i = 0; i<m;i++) {
			left[i]=pts[i];
		}
		int num=0;
		for (int i = m; i<n;i++) {
			right[num]=pts[i];
			num++;
		}
		
		mergeSortRec(left);
		mergeSortRec(right);
		
		Point[] p3 = new Point[pts.length];
		p3=merge(left,right);
		
		for (int i = 0; i < p3.length; i++) {
			pts[i] = p3[i];
		}
	}

	/**
	 * Merges the two Point arrays that are passed into the method
	 * 
	 * @param B An Point array that needs to be merged
	 * @param C A second Point array that needs to be merged
	 * 
	 * @return Returns a merged Point array  
	 */
	private Point[] merge(Point[] B, Point[] C) {
		int p = B.length;
		int q = C.length;
		Point[] D = new Point[p+q];
		int i = 0;
		int j = 0;
		int k = 0;
		while ((i<p) && (j<q)) {
			if (B[i].compareTo(C[j])<=0) {
				D[k]=B[i];
				i++;
				k++;
			}else {
				D[k]=C[j];
				j++;
				k++;
			}
		}
		if(i>=p){
			for(int u = j; u<q;u++) {
				D[k]=C[u];
				k++;
			}
		}else {
			for(int o = i; o<p;o++){
				D[k]=B[o];
				k++;
			}
			
		}
		return D;
	}
	
	// Other private methods if needed ...

}
