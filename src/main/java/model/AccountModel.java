package model;



public class AccountModel {

	
	//THIS IS VERY IMPORTANT NOT ONLY THE TYPE OF THE ARRAY MUST BE SPECIFIED
	// BUT ALSO THE TYPE OF THE ELEMENTS OF THE ARRAY IN ORDER TO USE ESC/JAVA!!!!!
	/// I.E. IT DOES NOT UNDERSTAND THAT 
	
	private /*@ spec_public non_null@*/  Account[] accounts ;
	/*@  public invariant accounts != null  && 0 <= accounts.length 
	            && (\typeof(accounts) == \type(Account[]) ) 
	 			&&	\elemtype(\type(Account[])) == \type(Account);
	 */
	
	
	public final /*@ non_null @*/ Account acc ;

	public AccountModel(){
		accounts = new Account[0];
		acc = new Account("");
	}
	

	/*@ modifies \nothing;  
	    @   requires accounts != null && id < accounts.length && id >= 0 
	    		&& accounts[id] != null && accounts.length > 0;
	 	@ ensures \result == accounts[id] && \result != null;
	 	@ also
	 	@ requires  accounts != null && ( id >= accounts.length || id < 0 
	 	|| accounts[id] == null || accounts.length <= 0);
	 	@ ensures \result == acc && \result != null;
	 */
	public /*@ pure non_null@*/ Account getAccount(int id){
		if(accounts.length <= 0){
			return acc;
		}else{
			if(accounts != null && id < accounts.length && id >=0 
					&& accounts[id] != null && accounts.length > 0)
				return accounts[id];
			else return acc;
		}
	}//end of getAccount
	
	/*@ ensures \result == accounts.length;
	 */
	public /*@ pure @*/ int maxAccounts(){return accounts.length;}

	
	/*@ensures
	@ \result == (n >= 0  &&  (n < maxAccounts()));
	@*/
	public /*@ pure @*/ boolean caddAccount(int n){
		return n >= 0 && n < maxAccounts() ;
	}
	
	
	/*@ensures
	 \result == ((maxAccounts()  ==  0)  &&  n > 0);
	*/
	public /*@ pure @*/ boolean csetMaxSize(int n){
		return accounts.length == 0 && n > 0 ; 
	}

	
	/*@ensures
	@ \result == (n > 0  &&  ((getAccount(id1).getPassword().equals(s2) ) 
					&&  ((getAccount(id1).getName().equals(s1))  &&  (!((getAccount(id1)  ==  acc))  
					&&  (!((getAccount(id2)  ==  acc))  &&  (n <= getAccount(id1).getBalance()) ) ))) );
	*/
	public /*@ pure @*/ boolean cdeposit(String s1, String s2, int id1, int id2, int n){
		if(getAccount(id1) != acc && getAccount(id2) != acc){
			if(getAccount(id1).getPassword().equals(s2) && getAccount(id1).getName().equals(s1) && n > 0
					&& n <= getAccount(id1).getBalance())
				return true;
			else{
				return false;
			}
		}else{
			return false;
		}
	}//end of cdeposit

	

		
	/*@ requires accounts != null  && accounts.length  >0 && size >= accounts.length && size > 0;
	 		ensures accounts != null ;
	 		also
	 		requires accounts != null  && accounts.length  >0 && size >= accounts.length && size > 0;
	 		ensures accounts.length == size;
	 		also
	 		requires accounts != null  && accounts.length  >0 && size >= accounts.length && size > 0;
	 		ensures (\forall int j; j >=0 && j < \old(accounts.length); accounts[j] == \old(accounts[j]) );
	 */
	public  void resizeArray( int size){
		
		if(accounts != null  && accounts.length  >0 && size > accounts.length && size > 0){
			
			
			Account[] temp = new Account[size];
			
			/*@ loop_invariant accounts!= null && temp != null && (accounts.length < temp.length) ==> 
	        (\forall int j; (j >= 0 && j < i  )
	         ==> ( (j < accounts.length) ==> temp[j] == accounts[j] )
	          && (!(j < accounts.length) ==>  temp[j] == null) ) ;
	       decreasing temp.length-i;
			  */
			for(int i = 0; i < temp.length; i++){
				 
				if(i < accounts.length && i >=0 ) temp[i] = accounts[i];
				 else{ 
					 if(i >=0)  temp[i] = null;
				 }
			 }//end of for loop
			
			accounts =temp;
		}
	}//end of appendElement

	
	/*@ requires ac != null &&  pos >= 0 && pos < accounts.length;
	 		ensures getAccount(pos) == ac;
	 */
	public void setAccount(/*@ non_null@*/ Account ac, int pos){
		if(ac != null && pos >=0 && pos < accounts.length){
			accounts[pos] = ac;
		}
	}
	

		
	/*@  requires   b >= 0 && pos >= 0 && pos >= maxAccounts() ;
	 	ensures  maxAccounts() == pos +1 && getAccount(pos).getBalance() == b ;
	 	also 
	 	requires   b >= 0 && pos >= 0 && pos >= maxAccounts() ;
	 	ensures   getAccount(pos).getName() == s1;
	 	also 
	 	requires   b >= 0 && pos >= 0 && pos >= maxAccounts() ;
	 	ensures   getAccount(pos).getPassword() == s2;
	 	also 
	 	requires   b >= 0 && pos >= 0 && pos >= maxAccounts() ;
	 	ensures   getAccount(pos).getId() == pos;
	 */
	public void addAccount(int b, int pos, /*@ non_null @*/ String s1, /*@ non_null @*/ String s2){
		if( b >= 0 && accounts.length > 0 && pos >= maxAccounts() && pos >= 0){
			resizeArray(pos+1);
			Account account = new Account("newAc");
			account.setBalance(b);
			if(s1 != null) account.setName(s1);
			if(s2 != null) account.setPassword(s2);
			 account.setId(pos);
			
			if(account != null && pos >= 0 && pos < accounts.length){ 
				setAccount(account,pos);
			}
		}
	}//end of addAccount
	
	
	/*@
	 requires csetMaxSize(n);  
	 ensures (\forall  int id; 
		getAccount(id).getName() ==\old(getAccount(id).getName())  && 
   	getAccount(id).getPassword() ==\old(getAccount(id).getPassword())  && 
		getAccount(id).getBalance() ==\old(getAccount(id).getBalance())  && 
		getAccount(id).getId() ==\old(getAccount(id).getId()) );
	 also 
	 requires csetMaxSize(n);
	 ensures 
	( maxAccounts() == n);
	*/
	public void setMaxSize(int n){
		if(accounts.length == 0 && n > 0){
			accounts = new Account[n];
			/*@ loop_invariant accounts!= null ==> 
			         (\forall int j; (j >= 0 && j < i )
			          ==> accounts[j] == null);
			        decreasing accounts.length-i;
			 */
			 for(int i = 0; i < accounts.length; i++){
				if(i >=0)accounts[i] = null;
			}
		}
	}//end of setMaxSize
	
	
	
	/*@ requires cdeposit(s1, s2, id1, id2, n);
		ensures 
		(\forall  int id;
			( \old((id  ==  id2)) ==> 
			n > 0 ==>
		 	getAccount(id).getName()== \old(getAccount(id).getName()) &&
		 	getAccount(id).getPassword()== \old(getAccount(id).getPassword()) &&
		 	getAccount(id).getBalance()== \old(getAccount(id). getBalance()  + n) &&
		 	getAccount(id).getId()== \old(getAccount(id).getId())));
	 	also 
	 	requires cdeposit(s1, s2, id1, id2, n);
	 	ensures 
	  	(\forall  int id;
			( \old((id  ==  id1)) ==> 
			(getAccount(id1).cremove(n) )==>
		 	getAccount(id).getName()== \old(getAccount(id).getName()) &&
		 	getAccount(id).getPassword()== \old(getAccount(id).getPassword()) &&
		 	getAccount(id).getBalance()== \old(getAccount(id).getBalance() - n) &&
		 	getAccount(id).getId()== \old(getAccount(id).getId())));
	@*/
	public void deposit(int id1, int id2, int n, String s1, String s2){
		
		//System.out.println(id1 +" " + id2+" " + n +" "+s1 + " "+s2 );
		//System.out.println("condition " + cdeposit(s1,s2,id1,id2,n));
		
		if(cdeposit(s1,s2,id1,id2,n)){
			getAccount(id1).remove(n);
			getAccount(id2).add(n);
		}
		
	}//end of deposit

	
	
}//end of class
