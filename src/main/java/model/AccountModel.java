package model;


public class AccountModel {

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

	
	/*@
	  ensures
	 \result == (n > 0  &&  ((getAccount(id1).getPassword()  ==  s2)  
	 &&  ((getAccount(id1).getName()  ==  s1)  &&  (getAccount(id1)  !=  acc &&  ((getAccount(id2)  !=  acc ))))) );
	*/
	public /*@ pure @*/ boolean cdeposit(String s1, String s2, int id1, int id2, int n){
		if(getAccount(id1) != acc && getAccount(id2) != acc){
			if(getAccount(id1).getPassword()  ==  s2 && getAccount(id1).getName()  ==  s1 && n > 0 )
				return true;
			else{
				return false;
			}
		}else{
			return false;
		}
	}//end of cdeposit

	
	
	/*@ requires cdeposit(s1, s2, id1, id2, n);
	@ ensures 
	(\forall  int id;
	@(  (id  ==  id2) ==> 
	@ getAccount(id).getName()== \old(getAccount(id).getName()) &&
	@ getAccount(id).getPassword()== \old(getAccount(id).getPassword()) &&
	@ getAccount(id).getBalance()== \old(getAccount(id).getBalance() + n) &&
	@ getAccount(id).getId()== \old(getAccount(id).getId())));
	@ also 
	@ requires cdeposit(s1, s2, id1, id2, n);
	@ ensures 
	@  (\forall  int id;
	@( \old((id  ==  id1)) ==> 
	@ getAccount(id). cremove(n)==>
	@ getAccount(id).getName()== \old(getAccount(id).getName()) &&
	@ getAccount(id).getPassword()== \old(getAccount(id).getPassword()) &&
	@ getAccount(id).getBalance()== \old(getAccount(id).getBalance() - n) &&
	@ getAccount(id).getId()== \old(getAccount(id).getId())));
	@*/
	public void deposit(int id1, int id2, int n, String s1, String s2){
		if(cdeposit(s1, s2, id1, id2, n)){
			getAccount(id2).add(n);
			if(getAccount(id1). cremove(n)) getAccount(id1).remove(n);
		}
	}//end of deposit
	
	
	
	/*@ requires  caddAccount(id) ;
	 ensures 	(\forall  int id1;
	 (  (id1 >= 0  &&  (id1  ==  id) && (id1 < maxAccounts())) ==> 
	 getAccount(id1).getName()== s1 &&
	 getAccount(id1).getPassword()== s2 &&
	 getAccount(id1).getBalance()== 0 &&
	 getAccount(id1).getId()== id )) ;
	 */
	public void addAccount(int id, /* @ non_null @*/ String na,  /*@ non_null @*/ String s1, /*@ non_null @*/ String s2){
		Account ac = new Account("na");
		
		if( ac != null &&  accounts != null && id < accounts.length && accounts.length > 0 && id >= 0){
			ac.setId(id);
			ac.setName(s1);
			ac.setPassword(s2);
			if(id < accounts.length) accounts[id] = ac;
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
	
	
	
	
}//end of class
