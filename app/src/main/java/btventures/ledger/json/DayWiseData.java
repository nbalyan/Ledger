package btventures.ledger.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;

import btventures.ledger.Customer;

/**
 * Created by Neeraj on 13-03-2018.
 */

public class DayWiseData {
    private HashMap<Date,Float> daywiseTotal; //for both customer and agents
    private String name;
    private String accountType;
    private String contactNo;
    public DayWiseData(){

    }
    public DayWiseData(ArrayList<Customer> transactionlist, String name, int agentOrCustomerWise){
        this.daywiseTotal = new HashMap<>();
            for(int i =0; i < transactionlist.size();i++){
                Date key = new Date(transactionlist.get(i).getCreatedAt().getTime());
                if(daywiseTotal.containsKey(key)){

                    daywiseTotal.put(key,Float.valueOf(transactionlist.get(i).getmAmount())+daywiseTotal.get(key));
                }else {
                    daywiseTotal.put(key, Float.valueOf(transactionlist.get(i).getmAmount()));
                }
            }

            if(agentOrCustomerWise == 1){
                this.name = name;
            }else{
                this.name = transactionlist.get(0).getName();
            }
    }
}
