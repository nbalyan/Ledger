package btventures.ledger.json;

import java.util.ArrayList;

/**
 * Created by Neeraj on 30-03-2018.
 */

public class TransactionAdditionalDataKeys {
    public ArrayList<String> getKeysArray() {
        return keysArray;
    }

    public void setKeysArray(ArrayList<String> keysArray) {
        this.keysArray = keysArray;
    }

    public void addKey(String key){
        this.keysArray.add(key);
    }

    ArrayList<String> keysArray= new ArrayList<>();


}
