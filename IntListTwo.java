/* 
* This is a two-way list class
*
* @author - Yoaz Shmider
* @version - 09.06.19	
*/

import java.util.Scanner;

public class IntListTwo {
	
    private IntNodeTwo _head, _tail;
    
    //Default constructor
    public IntListTwo(){    
		_head = null;
        _tail = null;
	}    
    
    //Constructor with provided vars
    public IntListTwo(IntNodeTwo h, IntNodeTwo t){    
		_head = h;
		_tail = t; 
	}
	
	
	/* 
	* Method add the provided num into the list to correct location based on value
	* @param num - integar value to be added to the list
	* Time Complexity - O(n)
	* Storage complexity - O(1)
	*/
	public void addNumber(int num){
		IntNodeTwo p = _head;
		
		//the list is empty, set _head value to provided num, _prev/_next stays null
		if(p == null){
			_head = new IntNodeTwo(num);
			_tail = _head;
			return;
		}
		
		//list isn't empty
		while(p != null && p.getNum() < num){
			p = p.getNext();				
		}
		
		
		//p points to null end of list, adding a unit to end of list and connect
		//num is heighest value among existent list units
		if(p == null){
			p = new IntNodeTwo(num);
			p.setPrev(_tail);
			_tail.setNext(p);
			_tail = p;
			return;
		}
		
		//adding unit to start of list
		else if(p.getPrev() == null){
			p = new IntNodeTwo(num,_head,null);
			_head.setPrev(p);
			_head = p;
			return;		
		}
		
		//adding unit to middle of list
		//p points to unit with value > num
		IntNodeTwo q = new IntNodeTwo(num,p,p.getPrev());
		p.getPrev().setNext(q);
		p.setPrev(q);	
	}
	
	
	/*
	* This method accept a num and remove the unit with this num value from the list if exist
	* @param num an int value to be removed from list if exist	
	* Time Complexity - O(n)
	* Storage complexity - O(1)
	*/
	public void removeNumber(int num){
		IntNodeTwo p;
		
		//if list is empty, return
		if(_head == null)
			return;
		
		//list isnt empty
		for(p=_head;p!=null;p=p.getNext()){
			if(p.getNum() == num){
				//unit is at the begining of list
				if(p.getPrev() == null){
					 //if list is only 1 unit
					if(p.getNext() == null)
						_head = _tail = null;
					else{
						_head = _head.getNext();
						_head.setPrev(null);
						}
					return;
				}
				
				//unit is at the end of list
				else if(p.getNext() == null){
					_tail = _tail.getPrev();
					_tail.setNext(null);
					return;
				}
				
				//else unit is in the middle of list
				p.getPrev().setNext(p.getNext());
				p.getNext().setPrev(p.getPrev());
				return;
			}
		}
	}
	
	
	/*
	* Method reads from input int numbers and create a list based on relevant value order
	* if -9999 value read from input than stop 
	* Time Complexity - O(n)
	* Storage complexity - O(n)
	*/
	public void readToList(){
		IntNodeTwo p = _head;
		Scanner input = new Scanner(System.in);
		int value = input.nextInt();
		
		while(value != -9999){
			this.addNumber(value);
			value = input.nextInt();
		}
		
		//else value is -9999, return
		return;
	}
	
	
	/*
	* Prints out the list unit values in the order they are stored in the list 
	* @Return str a string represent list unit value by order
	* Time Complexity - O(n)
	* Storage complexity - O(1)
	*/
	public String toString(){
		IntNodeTwo p = _head;
		String str = "{";
		while(p != null){
			str += p.getNum();
			if(p.getNext() == null){
				str += "}";
				return str; 
			}
			str += ", ";
			p = p.getNext();
		} 
		str += "}";
		return str;
	}
	
	
	/*
	* @Returns an int number represent the number of units in the list
	* Time Complexity - O(n)
	* Storage complexity - O(1)
	*/
	public int length(){
		IntNodeTwo p = _head;
		int counter = 0;
		while(p != null){
			counter ++;
			p = p.getNext();
			}
		return counter;
	}
	
	
	/*
	* @Returns an int number represent the sum of all lists value units
	* Time Complexity - O(n)
	* Storage complexity - O(1)
	*/
	public int sum(){
	IntNodeTwo p = _head;
	int sum = 0;
	while(p != null){
		sum += p.getNum();
		p = p.getNext();
	}
	return sum;
	}
	
	
	/*
	* Computes rather a sequenced sub-list with sum values of units equal even exist
	* @Returns an int number represent the largest sequence sub-list with units sum value
	* that preduce even sum number, if none, return 0
	* Time Complexity - O(n)
	* Storage complexity - O(1)
	*/
	public int maxLength(){
		IntNodeTwo l;
        int sum = this.sum();
        int counter = this.length();
        int itCounter = 0;

        if (sum % 2 == 0) // if the whole list is even return it's length
            return counter;
        else {
            l = _head;               // Initial to the first unit
            IntNodeTwo r = _tail;   // Initial to the last unit

            while (l != r) {      // Will run until they meet in the middle
                itCounter++;
                if (l.getNum() % 2 != 0 || r.getNum() % 2 != 0)   // Check whether the left or righ cell is odd number
                    return counter - itCounter;                  // If it's odd number then, Odd - Odd = even, then return total unit count - iteration count          			
                else {                                          // If none is odd number then move towards the center
                    r=r.getPrev();
                    l=l.getNext();
                }
            }
        }
        return 0;
    }
	
	
	/*
	* Computes rather a sequence sub-list with average of units value equal to provided num exist
	* @Returns true if an exist sub list that the average of its units value equals 
	* to the provided num, else false
	* Time Complexity - O(n)
	* Storage complexity - O(1)
	*/
	public boolean isAverage(double num){
		IntNodeTwo l;
		int counter = this.length();
		int sum = this.sum();
		
		//check if list is empty
		if(counter == 0)
			return false;
			
		double average = (double)sum/counter;
		
		//if full list units values average == num, return true
		if(average == num)
				return true;
		
		l = _head;               //initial left pointer to _head
		IntNodeTwo r = _tail;   // initial right pointer to _tail
		
		//run over list
		for(l=_head; l!=null; l=l.getNext()){
			//if average < num, reduce smallest unit value from sum to higher average
			if(average < num){
				sum -= l.getNum();
			}
			//if average > num, reduce biggest unit value from sum to lower average
			else if(average > num){
				sum -= r.getNum();
				r=r.getPrev();
			}
			//else average == num, return true
			else{
				return true;
			}	
			counter--;
			average = (double)sum/counter;
		}
		
		//no such sub-list, return false
		return false;
	}
	
}