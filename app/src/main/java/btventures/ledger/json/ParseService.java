package btventures.ledger.json;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import btventures.ledger.Customer;
import btventures.ledger.MainFragment;
import btventures.ledger.ModifyCustomer;
import btventures.ledger.TransactionEntry;
import btventures.ledger.tableview.CustomerCompleteDetails;

/**
 * Created by Neeraj on 09-03-2018.
 */

public class ParseService {
    private MainFragment mainFragment;
    private ModifyCustomer modifyCustomer;
    private TransactionEntry transactionEntry;
    public ParseService(TransactionEntry transactionEntry){this.transactionEntry = transactionEntry;};
    public ParseService(ModifyCustomer modifyCustomer){this.modifyCustomer = modifyCustomer;};
    public ParseService(MainFragment mainFragment){this.mainFragment = mainFragment;}
    public ParseService(){};

    public void loadCustomerData(){
        mainFragment.showProgressDialog();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("LLL");
        query.setLimit(2000);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    List<UserInfo> userInfoList = new ArrayList<>();
                    Log.d("score", "Retrieved " + objects.size() + " scores");
                    for (int i = 0; i < objects.size(); i++){
                        UserInfo userInfo = createUserInfoFromParseObject(objects.get(i));
                        userInfoList.add(userInfo);
                    }
                    mainFragment.populatedTableView(userInfoList);
                    mainFragment.hideProgressDialog();
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                    mainFragment.hideProgressDialog();
                }
            }
        });
    }

    public void saveTransaction(Customer data){
        ParseObject newTransaction = new ParseObject("TransactionData");
        newTransaction.put("CIFNO", data.getCifno().toString());
        newTransaction.put("AgentCode",data.getAgentCode());
        newTransaction.put("Amount",data.getmAmount());
        newTransaction.put("CustomerAccountNo",data.getAccount());
        newTransaction.put("AccountType", data.getAccountType());
        newTransaction.saveInBackground();

    }

    public ArrayList<CustomerCompleteDetails> getDatabyName(String Name){
        final ArrayList<CustomerCompleteDetails> customerCompleteDetailsList = new ArrayList<>();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("CustomerData");
        query.whereContains("Name", Name);
        query.setLimit(10);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null) {
                    for (int i = 0; i < objects.size(); i++) {
                        CustomerCompleteDetails userInfo = createCustomerInfoFromParseObject(objects.get(i));
                        customerCompleteDetailsList.add(userInfo);
                    }

                    if(modifyCustomer != null){
                        modifyCustomer.handleResult(customerCompleteDetailsList);
                        modifyCustomer.progressBar.setVisibility(View.INVISIBLE);
                    }
                    if(transactionEntry!= null){
                        ArrayList<Customer> list= new ArrayList<Customer>();
                        ArrayList<CustomerCompleteDetails> list1 = customerCompleteDetailsList;
                        for(int j=0; j < list1.size(); j++){
                            list.add(new Customer(list1.get(j).getName(),list1.get(j).getAccount(),list1.get(j).getAddress(),list1.get(j).getPhone()));
                        }
                        transactionEntry.handleResult(list);
                        transactionEntry.progressBar.setVisibility(View.INVISIBLE);
                    }
                }else{
                    Log.d("error", "Retrieved " + e.getMessage().toString() + " scores");
                }
            }
        });

        return customerCompleteDetailsList;
    }

    public ArrayList<CustomerCompleteDetails> getDatabyAccount(String Account){
        final ArrayList<CustomerCompleteDetails> customerCompleteDetailsList = new ArrayList<>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("CustomerData");
        query.whereContains("AccountNo", Account);
        query.setLimit(10);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                Log.d("data returnnkb","nkb" + String.valueOf(objects.size()));
                if(e==null) {
                    for (int i = 0; i < objects.size(); i++) {
                        Log.d("data returnnkb","nkb");
                        CustomerCompleteDetails userInfo = createCustomerInfoFromParseObject(objects.get(i));
                        customerCompleteDetailsList.add(userInfo);

                    }
                    if(modifyCustomer != null){modifyCustomer.handleResult(customerCompleteDetailsList);
                    Log.d("hello", "hello");
                        modifyCustomer.progressBar.setVisibility(View.INVISIBLE);}
                    if(transactionEntry!= null){
                        ArrayList<Customer> list= new ArrayList<Customer>();
                        ArrayList<CustomerCompleteDetails> list1 = customerCompleteDetailsList;
                        for(int j=0; j < list1.size(); j++){
                            list.add(new Customer(list1.get(j).getName(),list1.get(j).getAccount(),list1.get(j).getAddress(),list1.get(j).getPhone()));
                        }
                        transactionEntry.handleResult(list);
                        transactionEntry.progressBar.setVisibility(View.INVISIBLE);
                    }
                }else{
                    Log.d("error", "Retrieved " + e.getMessage().toString() + " scores");
                }
            }
        });


        Log.d("data returnnkb",String.valueOf(customerCompleteDetailsList.size()));
        return customerCompleteDetailsList;
    }

    public ArrayList<CustomerCompleteDetails> getDatabyMobile(String Mobile){
        final ArrayList<CustomerCompleteDetails> customerCompleteDetailsList = new ArrayList<>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("CustomerData");
        query.whereContains("Mobile", Mobile);
        query.setLimit(10);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null) {
                    for (int i = 0; i < objects.size(); i++) {
                        CustomerCompleteDetails userInfo = createCustomerInfoFromParseObject(objects.get(i));
                        customerCompleteDetailsList.add(userInfo);

                    }
                    if(modifyCustomer != null){modifyCustomer.handleResult(customerCompleteDetailsList);
                        modifyCustomer.progressBar.setVisibility(View.INVISIBLE);}
                    if(transactionEntry!= null){
                        ArrayList<Customer> list= new ArrayList<Customer>();
                        ArrayList<CustomerCompleteDetails> list1 = customerCompleteDetailsList;
                        for(int j=0; j < list1.size(); j++){
                            list.add(new Customer(list1.get(j).getName(),list1.get(j).getAccount(),list1.get(j).getAddress(),list1.get(j).getPhone()));
                        }
                        transactionEntry.handleResult(list);
                        transactionEntry.progressBar.setVisibility(View.INVISIBLE);
                    }
                }else{
                    Log.d("error", "Retrieved " + e.getMessage().toString() + " scores");
                }
            }
        });
        return customerCompleteDetailsList;
    }

    public ArrayList<CustomerCompleteDetails> getDatabyAddress(String Address){
        final ArrayList<CustomerCompleteDetails> customerCompleteDetailsList = new ArrayList<>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("CustomerData");
        query.whereContains("Address", Address);
        query.setLimit(10);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null) {
                    for (int i = 0; i < objects.size(); i++) {
                        CustomerCompleteDetails userInfo = createCustomerInfoFromParseObject(objects.get(i));
                        customerCompleteDetailsList.add(userInfo);
                    }
                    if(modifyCustomer != null){modifyCustomer.handleResult(customerCompleteDetailsList);
                        modifyCustomer.progressBar.setVisibility(View.INVISIBLE);}
                    if(transactionEntry!= null){
                        ArrayList<Customer> list= new ArrayList<Customer>();
                        ArrayList<CustomerCompleteDetails> list1 = customerCompleteDetailsList;
                        for(int j=0; j < list1.size(); j++){
                            list.add(new Customer(list1.get(j).getName(),list1.get(j).getAccount(),list1.get(j).getAddress(),list1.get(j).getPhone()));
                        }
                        transactionEntry.handleResult(list);
                        transactionEntry.progressBar.setVisibility(View.INVISIBLE);
                    }
                }else{
                    Log.d("error", "Retrieved " + e.getMessage().toString() + " scores");
                }
            }
        });
        return customerCompleteDetailsList;}

    public void addCustomerData(final CustomerCompleteDetails newData){
        final ParseObject newCustomer = new ParseObject("CustomerData");
        CustomerCompleteDetails data = new CustomerCompleteDetails();
        Log.d("beforefetch",newData.getAccount());
        if(getDatabyAccount(newData.getAccount().toString()).size() !=0) {
            data = getDatabyAccount(newData.getAccount().toString()).get(0);
        }
        if(data.getAccount()!=null)
        Log.d("data",data.getAccount().toString());
        if(data.getAccount() != null) {
            newCustomer.put("Name", newData.getName());
            newCustomer.put("AccountNo", newData.getAccount());
            newCustomer.put("Mobile", newData.getPhone());
            newCustomer.put("AadharNo", newData.getAadhar());
            newCustomer.put("PanNo", newData.getPan_no());
            newCustomer.put("Nomination", newData.getNomination());
            newCustomer.put("Amount", newData.getAmount());
            newCustomer.put("Address", newData.getAddress());
            newCustomer.put("JointAccountHolder", newData.getJointAccountName());
            Log.d("about to save","there");
            newCustomer.saveInBackground();
        }
        else{

            Log.d("niggaupdate","there");
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("CustomerData");
            final ParseObject abc = new ParseObject("CustomerData");
            query.whereEqualTo("AccountNo", newData.getAccount());
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if(object == null){
                        object = abc;
                    }
                    object.put("Name", newData.getName());
                    object.put("AccountNo", newData.getAccount());
                    object.put("Mobile", newData.getPhone());
                    object.put("AadharNo", newData.getAadhar());
                    object.put("PanNo", newData.getPan_no());
                    object.put("Nomination", newData.getNomination());
                    object.put("Amount", newData.getAmount());
                    object.put("Address", newData.getAddress());
                    object.put("JointAccountHolder", newData.getJointAccountName());
                    object.saveInBackground();
                }
            });
        }
    }

    public void loadAgentData(){

    }
    
    private CustomerCompleteDetails createCustomerInfoFromParseObject(ParseObject object){
        CustomerCompleteDetails newCustomer = new CustomerCompleteDetails();
        newCustomer.setAccount(object.getString("AccountNo"));
        newCustomer.setName(object.getString("Name"));
        newCustomer.setPhone(object.getString("Mobile"));
        newCustomer.setAadhar(object.getString("AadharNo"));
        newCustomer.setPan_no(object.getString("PanNo"));
        newCustomer.setNomination(object.getString("Nomination"));
        newCustomer.setAmount(object.getString("Amount"));
        newCustomer.setAddress(object.getString("Address"));
        newCustomer.setJointAccountName(object.getString("JointAccountHolder"));
        newCustomer.setObjectID(object.getString("objectId"));
        return newCustomer;
    }

    private UserInfo createUserInfoFromParseObject(ParseObject object){
        UserInfo cellModel = new UserInfo();
        Log.d("Accountno", object.describeContents() + "account no");
        cellModel.setmAccountNo(object.getString("AccountNo"));
        cellModel.setName(object.getString("AccountHolderName"));
        cellModel.setmAmount(String.valueOf(object.getString("Amount")));
        cellModel.setmOpeningDate(object.getString("OPENINGDATE"));
        cellModel.setAddress(object.getString("ADDRESS"));
        cellModel.setmAgentCode(object.getString("CODE"));

        cellModel.setmCifNo(object.getString("CIFNO"));
        cellModel.setMobile(object.getString("PhoneNumber"));
        cellModel.setmAadharCardNo(object.getString("AADHARCARDNO"));
        cellModel.setmPanNo(object.getString("PANNO"));
        cellModel.setmScecondCIF(object.getString("SECONDCIF"));
        cellModel.setmNomination(object.getString("NOMINATION"));

        return cellModel;
    }
}
