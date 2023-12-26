package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;


/**
 * Implementation of the list interface based on linked nodes
 * that store multiple items per node.  Rules for adding and removing
 * elements ensure that each node (except possibly the last one)
 * is at least half full.
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E>
{
  /**
   * Default number of elements that may be stored in each node.
   */
  private static final int DEFAULT_NODESIZE = 4;
  
  /**
   * Number of elements that can be stored in each node.
   */
  private final int nodeSize;
  
  /**
   * Dummy node for head.  It should be private but set to public here only  
   * for grading purpose.  In practice, you should always make the head of a 
   * linked list a private instance variable.  
   */
  public Node head;
  
  /**
   * Dummy node for tail.
   */
  private Node tail;
  
  /**
   * Number of elements in the list.
   */
  private int size;
  
  /**
   * Constructs an empty list with the default node size.
   */
  public StoutList()
  {
    this(DEFAULT_NODESIZE);
  }

  /**
   * Constructs an empty list with the given node size.
   * @param nodeSize number of elements that may be stored in each node, must be 
   *   an even number
   */
  public StoutList(int nodeSize)
  {
    if (nodeSize <= 0 || nodeSize % 2 != 0) throw new IllegalArgumentException();
    
    // dummy nodes
    head = new Node();
    tail = new Node();
    head.next = tail;
    tail.previous = head;
    this.nodeSize = nodeSize;
  }
  
  /**
   * Constructor for grading only.  Fully implemented. 
   * @param head
   * @param tail
   * @param nodeSize
   * @param size
   */
  public StoutList(Node head, Node tail, int nodeSize, int size)
  {
	  this.head = head; 
	  this.tail = tail; 
	  this.nodeSize = nodeSize; 
	  this.size = size; 
  }

  @Override
  public int size()
  {
    // TODO Auto-generated method stub
    return size;
  }
  
  @Override
  public boolean add(E item)
  {
	  
	  if(item==null) throw new NullPointerException();
	  Node toAdd=new Node();
	  //if list is empty, create a new node and add item to it
	  //if a predecesor is full, create a new node and add it in there
	  //if the predeccor is not full, add the elment at the end 
	  //Node toAdd = new Node();
	  if(size==0) {
		  head.next = toAdd;
		  toAdd.previous=head;
		  toAdd.next=tail;
		    tail.previous = toAdd;
		    
		  toAdd.addItem(item);
	  }else if(tail.previous.count<nodeSize) {
		  tail.previous.addItem(item);
	  }else if(tail.previous.count==nodeSize) {
		  toAdd.addItem(item);
		  //Node current = tail.previous;
		  toAdd.next=tail;
		  toAdd.previous=tail.previous;
		  tail.previous.next=toAdd;
		  tail.previous=toAdd;
	  }
		size++;
    return true;
  }

  @Override
  public void add(int pos, E item)
  {
	  if (pos < 0 || pos > size) {
			throw new IndexOutOfBoundsException();
	  }
	  Node newNode;
	  NodeInfo n=find(pos);
	  //case 1
	  if(size==0) {
		  add(item);
	  }

	 
	   if(n.offset==0) {
		  if(n.node.previous!=null&&n.node.previous!=head) {
			  if(n.node.previous.count<nodeSize) {
				  n.node.previous.addItem(pos, item);
				  //put e in node.previous
				  
			  }
		  }else if(n.node==tail&&n.node.previous.count==nodeSize) {
			  newNode= new Node();
			  newNode.addItem(0, item);
			  n.node.next=newNode.next;
			  n.node=newNode.previous;
			  newNode=n.node.next.previous;
			  newNode=n.node.next;
			  
			  //need to link newNode
			  
		  }
	  }//case2
	   else if(n.node.count<nodeSize) {
			  n.node.addItem(pos, item);
		  }
		  
	  else { //split operation

		  //create a successor node n' nand store M/2 elements from n
		  Node succNode= new Node();
		  Node old = n.node.next;
			//System.out.println(n.toStringInternal());
		  
		  for (int i = 0; i< (nodeSize/2);i++) {
			  succNode.addItem(n.node.data[(nodeSize/2)]);
			n.node.removeItem(nodeSize/2);
			  
		  }
		  n.node.next=succNode;
		  succNode.previous=n.node;
		  succNode.next=old;
		  old.previous=succNode;
		  
		  if(n.offset<=(nodeSize/2)) {
			  n.node.addItem(pos, item);
		  }else if(n.offset>(nodeSize/2)) {
			  succNode.addItem((n.offset-nodeSize/2), item);
			  
		  }
	  }
		 
	  //n.node
	  //n.offset
	  //case 1: if off==0
	  		// if n has a predecesspr which fewer and it is not full, put item in that predecesoor
	  		// if n is the tail node(tail.previous) and n's predecessor is full, create a new node and put x at offset 0
	  //case 2: if there is space in n, put item in node n at offset off and shift array elements
	  //case 3: split opwrayion
	  		//move  M/2 elements of node n to a new successor node n'
	  			//if off<= m/2--->Add X in node n at offset off
	  			//if off> m/2, put X in node n'(successor) at offset (off-m/2)
	  		size++;	
  }

  @Override
  public E remove(int pos)
  {
	  if (pos < 0 || pos > size) {
		throw new IndexOutOfBoundsException();
	  }
	  NodeInfo n=find(pos);

	  E returnNode = n.node.data[n.offset];
	  if(n.node.next==tail&&n.node.count==1) {
		  //unlink
		  n.node.previous.next=n.node.next;
		  n.node.next.previous=n.node.previous;
		  n.node=null;
	  }else if((n.node.next==tail&&n.node.count>=2)||n.node.count>nodeSize/2) {
		  n.node.removeItem(n.offset);
	  }else if(n.node.count>=nodeSize/2) {
		  Node succ = n.node.next;
		  if(succ.count>nodeSize/2) {
			  n.node.removeItem(0);
			  n.node.addItem(succ.data[0]);
			 succ.removeItem(0);
		  }else{
			  //succ.removeItem(0);
			  n.node.removeItem(n.offset);
			  for (int i = 0; i<succ.count;i++) {
				  n.node.addItem(succ.data[i]);
			  }
			  
			  //unlink
			  n.node.next = succ.next;
				succ.next.previous = n.node;
				succ = null;
		  }
	  }
	  size--;
    // TODO Auto-generated method stub
    return returnNode;
  }

  /**
   * Sort all elements in the stout list in the NON-DECREASING order. You may do the following. 
   * Traverse the list and copy its elements into an array, deleting every visited node along 
   * the way.  Then, sort the array by calling the insertionSort() method.  (Note that sorting 
   * efficiency is not a concern for this project.)  Finally, copy all elements from the array 
   * back to the stout list, creating new nodes for storage. After sorting, all nodes but 
   * (possibly) the last one must be full of elements.  
   *  
   * Comparator<E> must have been implemented for calling insertionSort().    
   */
  public void sort()
  {
	 
	  E[] arr = (E[]) new Comparable[size];
	  
	  int j = 0;
		Node node = head.next;
		while (node != tail) {
			for (int i = 0; i < node.count; i++) {
				arr[j] = node.data[i];
				j++;
			}
			node = node.next;
		}
		head.next = tail;
		tail.previous = head;
		
		insertionSort(arr, new comp());
		
		size=0;
		for (int i = 0; i < arr.length; i++) {
			add(arr[i]);
		}
 }
  
  /**
   * Sort all elements in the stout list in the NON-INCREASING order. Call the bubbleSort()
   * method.  After sorting, all but (possibly) the last nodes must be filled with elements.  
   *  
   * Comparable<? super E> must be implemented for calling bubbleSort(). 
   */
  public void sortReverse() 
  {
 E[] arr = (E[]) new Comparable[size];
	  
	  int j = 0;
		Node node = head.next;
		while (node != tail) {
			for (int i = 0; i < node.count; i++) {
				arr[j] = node.data[i];
				j++;
			}
			node = node.next;
		}
		head.next = tail;
		tail.previous = head;
bubbleSort(arr);
		
		size=0;
		for (int i = 0; i < arr.length; i++) {
			add(arr[i]);
		}
		
  }
  
  @Override
  public Iterator<E> iterator()
  {
    // TODO Auto-generated method stub
    return new StoutListIterator();
  }

  @Override
  public ListIterator<E> listIterator()
  {
    // TODO Auto-generated method stub
	  return new StoutListIterator();
  }

  @Override
  public ListIterator<E> listIterator(int index)
  {
    // TODO Auto-generated method stub
	  return new StoutListIterator(index);
  }
  
  /**
   * Returns a string representation of this list showing
   * the internal structure of the nodes.
   */
  public String toStringInternal()
  {
    return toStringInternal(null);
  }

  /**
   * Returns a string representation of this list showing the internal
   * structure of the nodes and the position of the iterator.
   *
   * @param iter
   *            an iterator for this list
   */
  public String toStringInternal(ListIterator<E> iter) 
  {
      int count = 0;
      int position = -1;
      if (iter != null) {
          position = iter.nextIndex();
      }

      StringBuilder sb = new StringBuilder();
      sb.append('[');
      Node current = head.next;
      while (current != tail) {
          sb.append('(');
          E data = current.data[0];
          if (data == null) {
              sb.append("-");
          } else {
              if (position == count) {
                  sb.append("| ");
                  position = -1;
              }
              sb.append(data.toString());
              ++count;
          }

          for (int i = 1; i < nodeSize; ++i) {
             sb.append(", ");
              data = current.data[i];
              if (data == null) {
                  sb.append("-");
              } else {
                  if (position == count) {
                      sb.append("| ");
                      position = -1;
                  }
                  sb.append(data.toString());
                  ++count;

                  // iterator at end
                  if (position == size && count == size) {
                      sb.append(" |");
                      position = -1;
                  }
             }
          }
          sb.append(')');
          current = current.next;
          if (current != tail)
              sb.append(", ");
      }
      sb.append("]");
      return sb.toString();
  }


  /**
   * Node type for this list.  Each node holds a maximum
   * of nodeSize elements in an array.  Empty slots
   * are null.
   */
  private class Node
  {
    /**
     * Array of actual data elements.
     */
    // Unchecked warning unavoidable.
    public E[] data = (E[]) new Comparable[nodeSize];
    
    /**
     * Link to next node.
     */
    public Node next;
    
    /**
     * Link to previous node;
     */
    public Node previous;
    
    /**
     * Index of the next available offset in this node, also 
     * equal to the number of elements in this node.
     */
    public int count;
    

    /**
     * Adds an item to this node at the first available offset.
     * Precondition: counct < nodeSize
     * @param item element to be added
     */
    void addItem(E item)
    {
      if (count >= nodeSize)
      {
        return;
      }
      data[count++] = item;
      //useful for debugging
      //      System.out.println("Added " + item.toString() + " at index " + count + " to node "  + Arrays.toString(data));
    }
  
    /**
     * Adds an item to this node at the indicated offset, shifting
     * elements to the right as necessary.
     * 
     * Precondition: count < nodeSize
     * @param offset array index at which to put the new element
     * @param item element to be added
     */
    void addItem(int offset, E item)
    {
      if (count >= nodeSize)
      {
    	  return;
      }
      for (int i = count - 1; i >= offset; --i)
      {
        data[i + 1] = data[i];
      }
      ++count;
      data[offset] = item;
      //useful for debugging 
//      System.out.println("Added " + item.toString() + " at index " + offset + " to node: "  + Arrays.toString(data));
    }

    /**
     * Deletes an element from this node at the indicated offset, 
     * shifting elements left as necessary.
     * Precondition: 0 <= offset < count
     * @param offset
     */
    void removeItem(int offset)
    {
      E item = data[offset];
      for (int i = offset + 1; i < nodeSize; ++i)
      {
        data[i - 1] = data[i];
      }
      data[count - 1] = null;
      --count;
    }    
  }
  
  /*
   * class to keep track of node and its offset
   */
  private class NodeInfo { 
	  public Node node; 
	  public int offset; 
      public NodeInfo(Node node, int offset) 
      { 
          this.node = node; 
          this.offset = offset; 
      } 
  }
      /*
       * helper method that finds which node is getting refered to and at what offset 
       */
     private NodeInfo find(int pos){
    	  int off=0;
    	  // find node
    	 // nodeSize;
    	  Node current = head.next;
    	  while(current!=tail) {
    		  if(current.count<pos+1) {
    			  pos = pos - current.count;
    			  current = current.next;
    		  }else if(current.count>=pos+1) {
    			  off=pos;
    			  break;
    		  }
    	  }
    	  NodeInfo n = new NodeInfo(current,off);
    	  return n;
    	  //if pos =2
      } 
  
  private class StoutListIterator implements ListIterator<E>
  {
	  private int pos;
	  private NodeInfo cursor;
	  private int direction;
	  public E[] StoutListData;
	  
	  /*
	   * 
	   */
	  private static final int BEHIND = -1;//when next is called
	   private static final int AHEAD = 1;//when previous is called
	   private static final int NONE = 0;
	    
	   /*
	    * Used to track whether or not to remove or set
	    */
	   int action;
	// constants you possibly use ...   
	  
	// instance variables ... 
	  
    /**
     * Default constructor 
     */
    public StoutListIterator()
    {
    	this(0); 
    }

    /**
     * Constructor finds node at a given position.
     * @param pos
     */
    public StoutListIterator(int pos)
    {
    	action=-1;
    	StoutListData=(E[]) new Comparable[size];
    	int j = 0;
		Node node = head.next;
		while (node != tail) {
			for (int i = 0; i < node.count; i++) {
				StoutListData[j] = node.data[i];
				j++;
			}
			node = node.next;
		}
    	 if (pos < 0 || pos > size) throw new IndexOutOfBoundsException();
         direction =NONE; 
    	this.pos = pos;
    }

    @Override
    public boolean hasNext()
    {
    	//has next if number of items in the list is less than the capactiy of the list 
		return size>pos;
    	// TODO 
    }

    @Override
    public E next()
    {
    	if (!hasNext()) throw new NoSuchElementException();
    	direction= BEHIND; 
    	action=direction;
		return StoutListData[pos++];
    	// TODO 
    }

    @Override
    public void remove()
    {//next
    	if(direction==action) {
			StoutList.this.remove(pos - 1);
			StoutListData=(E[]) new Comparable[size];
	    	int j = 0;
			Node node = head.next;
			while (node != tail) {
				for (int i = 0; i < node.count; i++) {
					StoutListData[j] = node.data[i];
					j++;
				}
				node = node.next;
			}
			action=-1;
			if(pos<=0) {
				pos=0;
			}else {
			pos--;
			}
			//prev
		}else if(direction==action) {
			StoutList.this.remove(pos);
			StoutListData=(E[]) new Comparable[size];
	    	int j = 0;
			Node node = head.next;
			while (node != tail) {
				for (int i = 0; i < node.count; i++) {
					StoutListData[j] = node.data[i];
					j++;
				}
				node = node.next;
			}
			action=-1;
			if(pos<=0) {
				pos=0;
			}else {
			pos--;
			}
		}
		else {
		throw new IllegalStateException();
	}
    }

	@Override
	public boolean hasPrevious() {
		// TODO Auto-generated method stub
		return pos>0;
	}

	@Override
	public E previous() {
		if (!hasPrevious()) throw new NoSuchElementException();
    	direction= AHEAD; 
    	action=direction;
		// TODO Auto-generated method stub
		 return StoutListData[pos--];
	}

	@Override
	public int nextIndex() {
		return pos;
	}

	@Override
	public int previousIndex() {
		// TODO Auto-generated method stub
		return pos-1;
	}

	@Override
	public void set(E e) {
		//if direction ==ahead, change what is in front of the curor
		//if direction ==behind, change what is behind it 
		
		NodeInfo N; 
		
			if(direction==BEHIND&&action==BEHIND) {
				N = find(pos-1);
				StoutListData[pos-1]=e;
			}else if(direction==AHEAD&&action==AHEAD) {
				N = find(pos+1);
				StoutListData[pos+1]=e;
			}
			else {
			throw new IllegalStateException();
		}
			N.node.data[N.offset]=e;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(E e) { 
		
		if (e == null)
			throw new NullPointerException();

		StoutList.this.add(pos, e);
		pos++;
		StoutListData=(E[]) new Comparable[size];
    	int j = 0;
		Node node = head.next;
		while (node != tail) {
			for (int i = 0; i < node.count; i++) {
				StoutListData[j] = node.data[i];
				j++;
			}
			node = node.next;
		}
		action = -1;
		
	}
    
    // Other methods you may want to add or override that could possibly facilitate 
    // other operations, for instance, addition, access to the previous element, etc.
    // 
    // ...
    // 
  }
  

  /**
   * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING order. 
   * @param arr   array storing elements from the list 
   * @param comp  comparator used in sorting 
   */
  private void insertionSort(E[] arr, Comparator<? super E> comp)
  {
	  E temp;
		int j;
		for (int i = 0; i<arr.length;i++) {
			temp = arr[i];
			j =i-1;
			while(j>-1 && arr[j].compareTo(temp)>0) {
				arr[j+1]=arr[j];
				--j;
			}
			arr[j+1]=temp;
		}
	  // TODO
  }
  
  /**
   * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a 
   * description of bubble sort please refer to Section 6.1 in the project description. 
   * You must use the compareTo() method from an implementation of the Comparable 
   * interface by the class E or ? super E. 
   * @param arr  array holding elements from the list
   */
  private void bubbleSort(E[] arr)
  {
		int i; 
		int j;
		for (i = 0; i < arr.length - 1; i++) {
			for (j = 0; j < arr.length - i - 1; j++) {
				if (arr[j].compareTo(arr[j + 1]) < 0) {
					E temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
	  // TODO
  }
  

	class comp<E extends Comparable<E>> implements Comparator<E> {
		@Override
		public int compare(E item0, E item1) {
			// TODO Auto-generated method stub
			return item0.compareTo(item1);
		}

	}

}

