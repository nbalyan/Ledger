package btventures.ledger.json;

import android.util.Log;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Neeraj on 30-03-2018.
 */

public class TransactionAdditionalData {
    public HashMap<String, String> getTransactionAdditionalData() {
        return transactionAdditionalData;
    }

    public void setTransactionAdditionalData(HashMap<String, String> transactionAdditionalData) {
        this.transactionAdditionalData = transactionAdditionalData;
    }

    HashMap<String,String> transactionAdditionalData = new HashMap<>();

    public TransactionAdditionalData(ParseObject objects, ArrayList<String> keysArray){
       // ArrayList<String> keysArray = new ArrayList<>(objects.keySet());
        transactionAdditionalData.put("AccountNo",objects.get("AccountNo").toString());
        for(String iter: keysArray) {
            String data = "Due";
            String columnData;
            //if(objects.get(iter) != null)
            if(objects.containsKey(iter)){
                columnData = objects.get(iter).toString();
                if(columnData.intern() == "C".intern()){
                    data = "Paid";
                }else if(columnData.intern() == "D".intern()){
                    data = "Due";
                }else{
                    data = columnData;
                }
            }else{
                data = "Pending";
            }
            transactionAdditionalData.put(iter,data);
            Log.d("Pending",iter + data);
        }

    }

}
