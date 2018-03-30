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

    public TransactionAdditionalData(ParseObject objects){
        Set<String> keysArray = objects.keySet();
        Iterator iter = keysArray.iterator();
        while (iter.hasNext()) {
            String data = "Due";
            String columnData;
            if(objects.get(iter.toString()) != null){
                columnData = objects.get(iter.toString()).toString();
                if(columnData == "C"){
                    data = "Paid";
                }else if(columnData == "D"){
                    data = "Due";
                }else{
                    data = "Partial";
                }
            }else{
                data = "Pending";
            }
            transactionAdditionalData.put(iter.toString(),data);
            Log.d("Pending",data);
            iter.next();
        }

    }

}
