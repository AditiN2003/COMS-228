package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/**
 *  
 * @author Aditi Nachnani
 *
 */
public class MsgTree {
	
	public char payloadChar;
	public MsgTree left;
	public MsgTree right;
	
	/*Stack to create the message tree */
	public Stack<MsgTree> s;
	
	/*Can use a static char idx to the tree string for recursive 
	solution, but it is not strictly necessary*/
	private static int staticCharIdx = 0;
	
	//Constructor building the tree from a string
	public MsgTree(String encodingString){
		if(encodingString.length()==0) {
			return; 
		}
		s = new Stack<>();
		for(int i = encodingString.length()-1;i>=0;i--) {
			char c = encodingString.charAt(i);
			MsgTree node = new MsgTree(c);
			if(c!='^') {
				s.push(node);
			}else {
				node.left=s.pop();
				if(s.size()!=0) {
					node.right = s.pop();
				}
				s.push(node);
			}
		}
	}
	
	//Constructor for a single node with null children
	public MsgTree(char payloadChar){
		this.payloadChar=payloadChar;
		left=null;
		right=null;
	} 
	//method to print characters and their binary codes
	public static void printCodes(MsgTree root, String code){
		if(root==null) {
			return;
		} 
		if(root.left==null && root.right==null) {
			System.out.println("   " + root.payloadChar+ "       "+code);
		}else {
		printCodes(root.left,code+"0");
		printCodes(root.right,code+"1");
		}
	}

	public void decode(MsgTree codes, String msg) {
		MsgTree tree = codes; 
		
		
		for(int i = 0; i< msg.length();i++) {
			if(msg.charAt(i)=='0') {
				codes = codes.left;
			}else if(codes.right!=null) {
				codes = codes.right;
			}
			if(codes.left==null && codes.right==null) {
				System.out.print(codes.payloadChar);
				codes=tree;
			}
		}
	}
		
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scnr = new Scanner(System.in);
		System.out.println("Please enter filename to decode:  ");
		String inputFileName = scnr.next();
		String encodingMsg = "";
		String binaryMsg="";
		int count=0;

		try {
			File file = new File(inputFileName);
			
			Scanner read = new Scanner(file);
			Scanner read2 = new Scanner(file);
			
			while(read.hasNextLine()) {
				count++;
				read.nextLine();
			}
			
			if(count>2) {
				encodingMsg=read2.nextLine()+"\n"+read2.nextLine();
			}else if(count==2) {
				encodingMsg=read2.nextLine();
			}
			
			while(read2.hasNextLine()) {
				binaryMsg=binaryMsg+read2.nextLine();
			}
			MsgTree tree = new MsgTree(encodingMsg);
			
			System.out.println("character code\n-------------------------");
			MsgTree.printCodes(tree.s.pop(), "");
			MsgTree msgTree = new MsgTree(encodingMsg);
			System.out.println("MESSAGE:");
			msgTree.decode(msgTree.s.pop(), binaryMsg);
			read.close();
			read2.close();
		}catch(FileNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
