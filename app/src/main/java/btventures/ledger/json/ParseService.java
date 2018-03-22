package btventures.ledger.json;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import btventures.ledger.AgentFilterCritreria;
import btventures.ledger.Customer;
import btventures.ledger.CustomerReportCriteria;
import btventures.ledger.HomeActivity;
import btventures.ledger.MainFragment;
import btventures.ledger.ModifyCustomer;
import btventures.ledger.SimpleScannerActivity;
import btventures.ledger.TransactionAfterConfirmActivity;
import btventures.ledger.TransactionConfirmActivity;
import btventures.ledger.TransactionEntry;
import btventures.ledger.tableview.CustomerCompleteDetails;

/**
 * Created by Neeraj on 09-03-2018.
 */

public class ParseService {
    private MainFragment mainFragment;
    private ModifyCustomer modifyCustomer;
    private TransactionEntry transactionEntry;
    private TransactionConfirmActivity transactionConfirmActivity;
    private AgentFilterCritreria agentFilterCritreria;
    private CustomerReportCriteria customerReportCriteria;
    private SimpleScannerActivity simpleScannerActivity;
    public ParseService(TransactionEntry transactionEntry){this.transactionEntry = transactionEntry;};
    public ParseService(ModifyCustomer modifyCustomer){this.modifyCustomer = modifyCustomer;};
    public ParseService(MainFragment mainFragment){this.mainFragment = mainFragment;}
    public ParseService(TransactionConfirmActivity transactionConfirmActivity){this.transactionConfirmActivity = transactionConfirmActivity;}
    public ParseService(AgentFilterCritreria agentFilterCritreria){this.agentFilterCritreria = agentFilterCritreria;}
    public ParseService(CustomerReportCriteria customerReportCriteria){this.customerReportCriteria = customerReportCriteria;}
    public ParseService(SimpleScannerActivity simpleScannerActivity){this.simpleScannerActivity = simpleScannerActivity;}

    //public ParseService(TransactionAfterConfirmActivity transactionAfterConfirmActivity){this.transactionAfterConfirmActivity = transactionAfterConfirmActivity;}

    public ParseService(){};

    public void loadCustomerData(){
        mainFragment.showProgressDialog();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("CustomerData");
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
                    mainFragment.populatedTableViewUser(userInfoList);
                    mainFragment.hideProgressDialog();
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                    mainFragment.hideProgressDialog();
                }
            }
        });
    }

    public void loadAgentData(){
        mainFragment.showProgressDialog();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("AgentData");
        query.setLimit(50);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    List<AgentInfo> agentInfoList = new ArrayList<>();
                    Log.d("score", "Retrieved " + objects.size() + " scores");
                    for (int i = 0; i < objects.size(); i++){
                        AgentInfo userInfo = createAgentInfoFromParseObject(objects.get(i));
                        agentInfoList.add(userInfo);
                    }
                    mainFragment.populatedTableViewAgent(agentInfoList);
                    mainFragment.hideProgressDialog();
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                    mainFragment.hideProgressDialog();
                }
            }
        });
    }

    public void loadTransactionData(){
        mainFragment.showProgressDialog();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("TransactionData");
        query.setLimit(100);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    List<Customer> transactionList = new ArrayList<>();
                    Log.d("score", "Retrieved " + objects.size() + " scores");
                    for (int i = 0; i < objects.size(); i++){
                        Customer userInfo = createTransactionInfoFromParseObject(objects.get(i));
                        transactionList.add(userInfo);
                    }
                    mainFragment.populatedTableViewTransaction(transactionList);
                    mainFragment.hideProgressDialog();
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                    mainFragment.hideProgressDialog();
                }
            }
        });
    }

    public void loadTransactionDataWithFilter(HashMap<String,String> filters, Date startDate, Date endDate){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("TransactionData");
        query.setLimit(100);
        Log.d("startDate", String.valueOf(startDate.getTime()));
        Log.d("startDate", String.valueOf(endDate.getTime()));
//        mealPlan.whereGreaterThanOrEqualTo("createdAt", today);
//        mealPlan.whereLessThan("createdAt", tomorrow);
        if(startDate.getTime() != -1) {
            query.whereGreaterThanOrEqualTo("createdAt", startDate);
        }
        if(endDate.getTime() != -1) {
            query.whereLessThan("createdAt", endDate);
        }
        Log.d("BKNMainFragment","In Parse");
        for(String key: filters.keySet()){
            query.whereMatches(key,filters.get(key),"i");
            Log.d("DDDFilterAdded",key + " " + filters.get(key));
        }
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                Log.d("BKNMainFragment","Done");
                if(e==null){
                    List<Customer> transactionList = new ArrayList<>();
                    Log.d("score", "Retrieved " + objects.size() + " scores");
                    for (int i = 0; i < objects.size(); i++){
                        Customer userInfo = createTransactionInfoFromParseObject(objects.get(i));
                        transactionList.add(userInfo);
                    }

                    Log.d("BKNMainFragment","Here");
                    mainFragment.populatedTableViewTransaction(transactionList);
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
        newTransaction.put("CustomerName", data.getName());
        newTransaction.put("Remarks",data.getRemarks());
        newTransaction.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    transactionsaveCallback();
                }else{
                    failCallback();
                }
            }
        });

    }

    public void failCallback(){
        if(transactionConfirmActivity != null){
            Toast.makeText(transactionConfirmActivity, "Transaction could not be saved", Toast.LENGTH_LONG).show();

        }else if(transactionEntry != null){
            Toast.makeText(transactionEntry, "Could not find entered Account No", Toast.LENGTH_LONG).show();
        }else if(modifyCustomer != null){
            Toast.makeText(modifyCustomer, "Could not save/update the user. Please try again.", Toast.LENGTH_LONG).show();
        }
    }

    public static void test(){
        Map<String,String> params = new HashMap<>();
        params.put("refer", "refer");
        ParseCloud.callFunctionInBackground("testingCloud",params,new FunctionCallback<String>() {
            public void done(String results, ParseException e) {
                if (results != null) {
                    //Toast.makeText(restaurantActivity, results, Toast.LENGTH_LONG).show();
                    Log.d("testingCloud",results);
                } else {
                    Log.d("testingCloud","fail");
                    //Toast.makeText(restaurantActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                if(e!=null) e.printStackTrace();
            }
        });


    }

    public void getAgentDatabyName(String name){

        final ArrayList<AgentInfo> agentInfos = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("AgentData");
        query.whereMatches("Name",name,"i");
        query.setLimit(50);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    ArrayList<AgentInfo> agentInfoList = new ArrayList<>();
                    Log.d("score", "Retrieved " + objects.size() + " scores");
                    for (int i = 0; i < objects.size(); i++){
                        AgentInfo userInfo = createAgentInfoFromParseObject(objects.get(i));
                        agentInfoList.add(userInfo);
                    }
                    if(agentFilterCritreria != null) {
                        agentFilterCritreria.handleResult(agentInfoList);
                        agentFilterCritreria.progressBar.setVisibility(View.INVISIBLE);
                    }
                    //mainFragment.populatedTableViewAgent(agentInfoList);
                    //mainFragment.hideProgressDialog();
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                    agentFilterCritreria.progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(agentFilterCritreria,"Error performing operation. Please try again. Issue: " + e.getMessage() , Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void getAgentDatabyMail(String name){

        final ArrayList<AgentInfo> agentInfos = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("AgentData");
        query.whereMatches("email",name,"i");
        query.setLimit(50);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    ArrayList<AgentInfo> agentInfoList = new ArrayList<>();
                    Log.d("score", "Retrieved " + objects.size() + " scores");
                    for (int i = 0; i < objects.size(); i++){
                        AgentInfo userInfo = createAgentInfoFromParseObject(objects.get(i));
                        agentInfoList.add(userInfo);
                    }
                    if(agentFilterCritreria != null) {
                        agentFilterCritreria.handleResult(agentInfoList);
                        agentFilterCritreria.progressBar.setVisibility(View.INVISIBLE);
                    }
                    //mainFragment.populatedTableViewAgent(agentInfoList);
                    //mainFragment.hideProgressDialog();
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                    agentFilterCritreria.progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(agentFilterCritreria,"Error performing operation. Please try again. Issue: " + e.getMessage() , Toast.LENGTH_SHORT).show();
                    //mainFragment.hideProgressDialog();
                }
            }
        });
    }

    public ArrayList<CustomerCompleteDetails> getDatabyName(String Name){
        final ArrayList<CustomerCompleteDetails> customerCompleteDetailsList = new ArrayList<>();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("CustomerData");
        query.whereMatches("Name", Name,"i");
        //query.setLimit(10);
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
                    if(customerReportCriteria!=null){
                        ArrayList<Customer> list= new ArrayList<Customer>();
                        ArrayList<CustomerCompleteDetails> list1 = customerCompleteDetailsList;
                        for(int j=0; j < list1.size(); j++){
                            list.add(new Customer(list1.get(j).getName(),list1.get(j).getAccount(),list1.get(j).getAddress(),list1.get(j).getPhone()));
                        }
                        customerReportCriteria.handleResult(list);
                        customerReportCriteria.progressBar.setVisibility(View.INVISIBLE);

                    }
                }else{
                    Log.d("error", "Retrieved " + e.getMessage().toString() + " scores");
                    if(modifyCustomer!= null){
                        modifyCustomer.progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(modifyCustomer, "Error performing operation. Please try again. Issue: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    if(transactionEntry!=null){
                        transactionEntry.progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(transactionEntry, "Error performing operation. Please try again. Issue: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return customerCompleteDetailsList;
    }

    public ArrayList<CustomerCompleteDetails> getDatabyAccount(String Account, final boolean callback){
        final ArrayList<CustomerCompleteDetails> customerCompleteDetailsList = new ArrayList<>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("CustomerData");
        query.whereMatches("AccountNo", Account,"i");
        //query.setLimit(10);
        //ParseQuery.or()
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
//                Log.d("data returnnkb","nkb" + String.valueOf(objects.size()));
                if(e==null) {
                    for (int i = 0; i < objects.size(); i++) {
                        Log.d("data returnnkb","nkb");
                        CustomerCompleteDetails userInfo = createCustomerInfoFromParseObject(objects.get(i));
                        customerCompleteDetailsList.add(userInfo);

                    }
                    if(modifyCustomer != null) {
                        if (callback){
                            modifyCustomer.handleResult(customerCompleteDetailsList);
                            modifyCustomer.progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                    if(transactionEntry!= null){
                        ArrayList<Customer> list= new ArrayList<Customer>();
                        ArrayList<CustomerCompleteDetails> list1 = customerCompleteDetailsList;
                        for(int j=0; j < list1.size(); j++){
                            list.add(new Customer(list1.get(j).getName(),list1.get(j).getAccount(),list1.get(j).getAddress(),list1.get(j).getPhone()));
                        }
                        if(callback) {
                            transactionEntry.handleResult(list);
                            transactionEntry.progressBar.setVisibility(View.INVISIBLE);
                        }
                    }

                    if(customerReportCriteria!=null){
                        ArrayList<Customer> list= new ArrayList<Customer>();
                        ArrayList<CustomerCompleteDetails> list1 = customerCompleteDetailsList;
                        for(int j=0; j < list1.size(); j++){
                            list.add(new Customer(list1.get(j).getName(),list1.get(j).getAccount(),list1.get(j).getAddress(),list1.get(j).getPhone()));
                        }
                        if(callback) {
                            customerReportCriteria.handleResult(list);
                            customerReportCriteria.progressBar.setVisibility(View.INVISIBLE);
                        }

                    }
                    if(simpleScannerActivity!=null){
                        ArrayList<Customer> list= new ArrayList<Customer>();
                        ArrayList<CustomerCompleteDetails> list1 = customerCompleteDetailsList;
                        for(int j=0; j < list1.size(); j++){
                            list.add(new Customer(list1.get(j).getName(),list1.get(j).getAccount(),list1.get(j).getAddress(),list1.get(j).getPhone()));
                        }
                        if(callback) {
                            simpleScannerActivity.handleParseResult(list);

                        }

                    }
                }else{
                    Log.d("error", "Retrieved " + e.getMessage().toString() + " scores");
                    if(modifyCustomer!= null){
                        modifyCustomer.progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(modifyCustomer, "Error performing operation. Please try again. Issue: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    if(transactionEntry!=null){
                        transactionEntry.progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(transactionEntry, "Error performing operation. Please try again. Issue: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    if(simpleScannerActivity!=null){
                        Toast.makeText(transactionEntry, "Error performing operation. Please try again. Issue: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        Log.d("data returnnkb",String.valueOf(customerCompleteDetailsList.size()));
        return customerCompleteDetailsList;
    }

    public ArrayList<CustomerCompleteDetails> getDatabyAccount(String Account){
        return getDatabyAccount(Account,true);
    }

    public ArrayList<CustomerCompleteDetails> getDatabyMobile(String Mobile){
        final ArrayList<CustomerCompleteDetails> customerCompleteDetailsList = new ArrayList<>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("CustomerData");
        query.whereMatches("Mobile", Mobile,"i");
        //query.setLimit(10);
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
                    if(customerReportCriteria!=null){
                        ArrayList<Customer> list= new ArrayList<Customer>();
                        ArrayList<CustomerCompleteDetails> list1 = customerCompleteDetailsList;
                        for(int j=0; j < list1.size(); j++){
                            list.add(new Customer(list1.get(j).getName(),list1.get(j).getAccount(),list1.get(j).getAddress(),list1.get(j).getPhone()));
                        }
                        customerReportCriteria.handleResult(list);
                        customerReportCriteria.progressBar.setVisibility(View.INVISIBLE);

                    }
                }else{
                    Log.d("error", "Retrieved " + e.getMessage().toString() + " scores");
                    if(modifyCustomer!= null){
                        modifyCustomer.progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(modifyCustomer, "Error performing operation. Please try again. Issue: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    if(transactionEntry!=null){
                        transactionEntry.progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(transactionEntry, "Error performing operation. Please try again. Issue: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return customerCompleteDetailsList;
    }

    public ArrayList<CustomerCompleteDetails> getDatabyAddress(String Address){
        final ArrayList<CustomerCompleteDetails> customerCompleteDetailsList = new ArrayList<>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("CustomerData");
        query.whereMatches("Address", Address,"i");
        //query.setLimit(10);
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
                    if(customerReportCriteria!=null){
                        ArrayList<Customer> list= new ArrayList<Customer>();
                        ArrayList<CustomerCompleteDetails> list1 = customerCompleteDetailsList;
                        for(int j=0; j < list1.size(); j++){
                            list.add(new Customer(list1.get(j).getName(),list1.get(j).getAccount(),list1.get(j).getAddress(),list1.get(j).getPhone()));
                        }
                        customerReportCriteria.handleResult(list);
                        customerReportCriteria.progressBar.setVisibility(View.INVISIBLE);

                    }
                }else{
                    Log.d("error", "Retrieved " + e.getMessage().toString() + " scores");
                    if(modifyCustomer!= null){
                        modifyCustomer.progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(modifyCustomer, "Error performing operation. Please try again. Issue: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    if(transactionEntry!=null){
                        transactionEntry.progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(transactionEntry, "Error performing operation. Please try again. Issue: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return customerCompleteDetailsList;}

    public void addCustomerData(final CustomerCompleteDetails newData){
        final ParseObject newCustomer = new ParseObject("CustomerData");
        CustomerCompleteDetails data = new CustomerCompleteDetails();
        Log.d("beforefetch",newData.getAccount());
        if(getDatabyAccount(newData.getAccount().toString(), false).size() !=0) {
            data = getDatabyAccount(newData.getAccount().toString(), false).get(0);
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
            newCustomer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null){
                        accountsaveCallback();
                    }else{
                        failCallback();
                    }
                }
            });
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
                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null){
                                accountupdateCallback();

                            }else{
                                failCallback();
                            }
                        }
                    });
                }
            });
        }
    }

    public void transactionsaveCallback(){
        transactionConfirmActivity.handleCallBack();
        //Toast.makeText(transactionConfirmActivity, "Transaction Saved", Toast.LENGTH_SHORT).show();

    }

    public void accountsaveCallback(){
        Toast.makeText(modifyCustomer, "New Customer Created", Toast.LENGTH_SHORT).show();
        //Intent homeActivity = new Intent(modifyCustomer, HomeActivity.class);
        //modifyCustomer.startActivity(homeActivity);
        modifyCustomer.finish();
    }
    public void accountupdateCallback(){
        Toast.makeText(modifyCustomer, "Customer details updated", Toast.LENGTH_SHORT).show();
        Intent homeActivity = new Intent(modifyCustomer, HomeActivity.class);
        modifyCustomer.startActivity(homeActivity);
        modifyCustomer.finish();
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
        if(object.getString("CreatedAt")!=null && object.getString("CreatedAt").intern()!="//".intern())
            newCustomer.setOpeningDate(object.getString("CreatedAt"));
        else
            newCustomer.setOpeningDate("");

        return newCustomer;
    }

    private AgentInfo createAgentInfoFromParseObject(ParseObject object){

        AgentInfo cellModel = new AgentInfo();
        cellModel.setAgentName(object.getString("Name"));
        cellModel.setEmail(object.getString("email"));
        cellModel.setCode(object.getString("Code"));
        cellModel.setCode(object.getString("ContactNo"));
        cellModel.setAgentCode(object.getString("AgentCode"));
        return cellModel;
    }

    private Customer createTransactionInfoFromParseObject(ParseObject object){

        Customer cellModel = new Customer();
        cellModel.setAccount(object.getString("CustomerAccountNo"));
        cellModel.setName(object.getString("CustomerName"));
        cellModel.setmAmount(object.getString("Amount"));
        cellModel.setAgentCode(object.getString("AgentCode"));
        cellModel.setAccountType(object.getString("AccountType"));
        cellModel.setCifno(object.getString("CIFNO"));
        //cellModel.setCreatedAt(new Date((object.getDate("createdAt")).getTime()));

        return cellModel;
    }

    private UserInfo createUserInfoFromParseObject(ParseObject object){
        UserInfo cellModel = new UserInfo();
        cellModel.setmAccountNo(object.getString("AccountNo"));
        cellModel.setName(object.getString("Name"));
        cellModel.setmAmount(String.valueOf(object.getString("Amount")));
        cellModel.setmOpeningDate((object.getString("CreatedAt")));
        cellModel.setAddress(object.getString("Address"));
        cellModel.setMobile(object.getString("Mobile"));
        cellModel.setmAadharCardNo(object.getString("AadharNo"));
        cellModel.setmPanNo(object.getString("PanNo"));
        cellModel.setmNomination(object.getString("Nomination"));
        cellModel.setmJointAccountHolder(object.getString("JointAccountHolder"));

        return cellModel;
    }
}
