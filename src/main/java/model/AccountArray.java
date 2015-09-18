package model;

public class AccountArray {

	private /*@ spec_public @*/  Account[] elements ;
	private /*@ spec_public @*/ int size;
	private /*@ spec_public @*/ int first;
	private /*@ spec_public @*/ int last;
	
	
	//THIS IS VERY IMPORTANT NOT ONLY THE TYPE OF THE ARRAY MUST BE SPECIFIED
	// BUT ALSO THE TYPE OF THE ELEMENTS OF THE ARRAY IN ORDER TO USE ESC/JAVA!!!!!
	/// I.E. IT DOES NOT UNDERSTAND THAT 
	
	//@ public invariant elements != null;
	//@ public invariant \typeof(elements) == \type(Account[]) && \elemtype(\type(Account[])) == \type(Account);
	//@ public invariant 0 <= size && size <= elements.length;


	/*@ requires maxSize > 0;
	 	ensures first == -1 && last == -1 && size == 0 &&
	 	elements != null && size < elements.length;
	 */
	public AccountArray(int maxSize){
		this.elements = new Account[maxSize];
		this.first = -1;
		this.last = -1;
		this.size = 0;
	}

	/* requires pos < elements.length && pos > 0 && el instanceof Account && el != null;
	 */
	public void setElementAt(int pos, /*@ non_null @*/ Account el){
		if(elements!=null && elements instanceof Account[] && 
				pos < elements.length && el != null && pos > 0 && el instanceof Account && el != null){
			elements[pos] = (Account) el;
		}
	}//end of setElementAt


	/*@ requires pos < elements.length && pos <= last && pos > 0;
	 	ensures \result == elements[pos];
	 */
	public Account getElementAt(int pos){
		if(pos < elements.length && pos <= last && pos > 0){
			return elements[pos];
		}else{
			return null;
		}
	}






}
