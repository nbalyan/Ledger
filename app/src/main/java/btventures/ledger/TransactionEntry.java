package btventures.ledger;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import btventures.ledger.json.ParseService;
import btventures.ledger.tableview.CustomerCompleteDetails;

public class TransactionEntry extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private PopupWindow pw;
    private GridView rv;
    private ImageButton accountButton;
    private ImageButton nameButton;
    private ImageButton addressButton;
    private AppCompatButton submitButton;
    private ImageButton mobileButton;
    private ImageButton closePopup;
    private EditText accountEdit;
    private EditText nameEdit;
    private EditText phoneEdit;
    private EditText addressEdit;
    private EditText recieptEdit;
    private EditText amountEdit;
    private EditText remarks;
    private Activity mContext;
    public ProgressBar progressBar;
    private Toast mToast;
    private String actPerformed;
    private ArrayList<String> allAccounts;
    private ArrayList<String> allAccountsCheckList;
    private int monthlyAmount;
    //private String cifNo;
    //ArrayList<Customer> customerfinal;
    Customer customerf;
    private EditText dateEdit;
    Calendar myCalendar;


    private void initiliazeDate(){
        myCalendar = Calendar.getInstance();
        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                hideSoftKeyboard(mContext);
                DatePickerDialog mDatePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        myCalendar.set(Calendar.MILLISECOND, 0);
                        myCalendar.set(Calendar.SECOND, 0);
                        myCalendar.set(Calendar.MINUTE, 0);
                        myCalendar.set(Calendar.HOUR, 0);
                        updateLabelFrom();
                    }

                }, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                //if(new Date().getTime()!=0)
                mDatePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                mDatePickerDialog.show();

                /*new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabelFrom();
                    }

                }, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();*/
            }
        });


    }

    private void updateLabelFrom() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateEdit.setText(sdf.format(myCalendar.getTime()));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        mContext = this;
        accountButton = findViewById(R.id.search_account);
        nameButton = findViewById(R.id.search_name);
        addressButton = findViewById(R.id.search_address);
        mobileButton = findViewById(R.id.search_mobile);
        accountEdit = findViewById(R.id.input_account);
        nameEdit = findViewById(R.id.input_name);
        addressEdit = findViewById(R.id.input_address);
        dateEdit = findViewById(R.id.input_date);
        phoneEdit = findViewById(R.id.input_mobile);
        progressBar = findViewById(R.id.progress);
        recieptEdit = findViewById(R.id.input_reciept);
        amountEdit = findViewById(R.id.input_amount);
        submitButton = findViewById(R.id.btn_submit);
        remarks = findViewById(R.id.input_remarks);
        initiliazeDate();
        Bundle b= getIntent().getExtras();

        actPerformed = b.getString("CATEGORY");
        if(actPerformed.intern()=="LIC".intern()){
            accountEdit.setVisibility(View.GONE);
            accountEdit = findViewById(R.id.input_account_lic);
        }else{
            findViewById(R.id.input_account_lic).setVisibility(View.GONE);
        }

        if(b.getString("account")!=null) {
            accountEdit.setText(b.getString("account"));
            nameEdit.setText(b.getString("name"));
            phoneEdit.setText(b.getString("phone"));
            addressEdit.setText(b.getString("address"));
            recieptEdit.setText(b.getString("receipt"));
            amountEdit.setText(b.getString("amount"));
            monthlyAmount = Integer.parseInt(b.getString("Mamount"));
            remarks.setText(b.getString("remarks"));
            //cifNo = b.getString("cif");
            customerf = new Customer(b.getString("name"), b.getString("account"), b.getString("address"), b.getString("phone"),b.getString("cif"));
            fetchPendingPayments(b.getString("account"));
            progressBar.setVisibility(View.VISIBLE);
        }


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ArrayList<Customer> customers = fetchListByAccount();
                if(customerf==null ){
                    //showAToast("Please enter a valid customer account");
                    accountEdit.setError("Enter a Valid Account");
                    return;
                }else{
                    accountEdit.setError(null);

                }
                if (customerf == null){

                }
                if(validate()){
                    Bundle extras = new Bundle();
                    //extras.putStringArrayList("all_accounts",allAccounts);

                    if(actPerformed.intern()=="REC".intern()) {
                        try {
                            CompleteTransactionInfo();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        extras.putStringArrayList("list_Dates", listStringDates);
                        extras.putStringArrayList("list_amounts", listAmount);
                        extras.putString("pending", duePayString);
                        extras.putString("last_pay", lastPayString);
                    }

                    //extras.putStringArrayList("all_accounts_check_list",allAccountsCheckList);
                    extras.putString("account",customerf.getAccount());
                    extras.putString("name",customerf.getName());
                    extras.putString("dueDateString",duePayString);
                    extras.putString("address",customerf.getAddress());
                    extras.putString("phone",customerf.getPhone());
                    extras.putString("dateEdit",dateEdit.getText().toString());
                    extras.putString("cif",customerf.getCifno());
                    extras.putString("Mamount", String.valueOf(monthlyAmount));
                    extras.putString("receipt",recieptEdit.getText().toString());
                    extras.putString("amount",amountEdit.getText().toString());
                    extras.putString("remarks",remarks.getText().toString());
                    extras.putString("CATEGORY",actPerformed);

                    Intent intent1 = new Intent(mContext, TransactionConfirmActivity.class);
                    intent1.putExtras(extras);
                    startActivity(intent1);
                    finish();
                }
            }
        });


        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(mContext);
                if(accountEdit.getText().length()<3){
                    showAToast("Please enter atleast 3 charchaters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                fetchListByAccount();
                //handleResult(customers);
                //progressBar.setVisibility(View.GONE);

            }
        });
        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(mContext);
                if(nameEdit.getText().length()<3){
                    showAToast("Please enter atleast 3 charchaters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                fetchListByName();
                //handleResult(customers);
                //progressBar.setVisibility(View.GONE);

            }
        });
        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(mContext);
                if(addressEdit.getText().length()<3){
                    showAToast("Please enter atleast 3 charchaters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                fetchListByAddress();
                //handleResult(customers);
                //progressBar.setVisibility(View.GONE);

            }
        });
        mobileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(mContext);
                if(phoneEdit.getText().length()<3){
                    showAToast("Please enter atleast 3 characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                fetchListByPhone();
                //handleResult(customers);
                //progressBar.setVisibility(View.GONE);

            }
        });
        modifyData();
        //showPopup();
    }

    private Date getDateAtIndex(int i){
        SimpleDateFormat formatter = new SimpleDateFormat("MMMyy");
        if(listDates.size()<=i){
            try {
                Date date = formatter.parse(formatter.format(listDates.get(listDates.size()-1)));
                Log.d("checking addition", String.valueOf(date));
                date.setMonth(date.getMonth()+1);
                Log.d("checking addition", String.valueOf(date));
                listDates.add(date);
                listAmount.add("D");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return listDates.get(listDates.size()-1);
        }
        return listDates.get(i);

    }

    private String getMonthAtIndex(int i){
        SimpleDateFormat formatter = new SimpleDateFormat("MMMyy");
        if(listDates.size()<=i){
            try {
                //listDates.add(formatter.parse(formatter.format(listDates.get(listDates.size()-1))));
                Date date = formatter.parse(formatter.format(listDates.get(listDates.size()-1)));
                Log.d("checking addition", String.valueOf(date));
                date.setMonth(date.getMonth()+1);
                Log.d("checking addition", String.valueOf(date));
                listDates.add(date);
                listAmount.add("D");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return formatter.format(listDates.get(listDates.size()-1));
        }
        return formatter.format(listDates.get(i));

    }

    private ArrayList<String> listAmount;
    private ArrayList<String> listStringDates;
    String duePayString;
    String lastPayString;
    private void CompleteTransactionInfo() throws ParseException {
        int inputAmount = Integer.parseInt(String.valueOf(amountEdit.getText()));

        SimpleDateFormat formatter = new SimpleDateFormat("MMMyy");
        Date currentdate=null;

        currentdate = formatter.parse(formatter.format(new Date()));

        String currentMonth = DateToString(currentdate,"MMMyy");

        listAmount = new ArrayList<String>();
        for(Date date:listDates){
            String value=mapDates.get(DateToString(date,"MMMyy"));
            value = value==null||value.trim().intern()=="".intern()?"D":value;
            listAmount.add(value);
        }
        String monthtoAdd = null;

        int index=0;
        while(inputAmount>0){
                if(mapDates.get(currentMonth)!=null && mapDates.get(currentMonth).intern()!="C".intern()){
                    monthtoAdd = currentMonth;
                }else {
                    Log.d("ankur","here1"+mapDates.get(currentMonth));
                    //Log.d("ankur","here1"+getDateAtIndex(index));
                    Log.d("ankur","here1"+getMonthAtIndex(index));
                    if (getMonthAtIndex(index).equals(currentMonth) && mapDates.get(currentMonth).intern() == "C".intern()) {
                        monthtoAdd=getMonthAtIndex(index+1);
                        index++;
                    } else {
                        monthtoAdd=getMonthAtIndex(index);
                    }
                    index++;
                }
                String value = mapDates.get(monthtoAdd);
                value = value==null||value.trim().intern()=="".intern()?"D":value;
                String valueSub = value.substring(0,1);
                String toPut = null;
                if(valueSub.intern()=="P".intern()){
                    int amount = Integer.parseInt(value.substring(2));
                    if(monthlyAmount-amount>inputAmount){
                        int amountToPut=inputAmount+amount;
                        toPut="P:"+(amountToPut);
                        inputAmount=0;
                    }else{
                        toPut="C";
                        inputAmount=inputAmount-(monthlyAmount-amount);

                    }
                }else{
                    if(monthlyAmount>inputAmount){
                        toPut="P:"+(inputAmount);
                        inputAmount=0;
                    }else{
                        toPut="C";
                        inputAmount=inputAmount-monthlyAmount;
                    }

                }
                mapDates.put(monthtoAdd,toPut);
                listStringDates = new ArrayList<String>();
                for(Date date:listDates){
                    listStringDates.add(DateToString(date,"MMMyy"));
                }
                listAmount.remove(listStringDates.indexOf(monthtoAdd));
                listAmount.add(listStringDates.indexOf(monthtoAdd),toPut);

        }
        StringBuffer stringBuffer= new StringBuffer();
        boolean addRequired = true;
        for(int i=0;i<listAmount.size();i++){
            String amount=listAmount.get(i);
            String amountSub = amount.substring(0,1);
            if(amount.intern()=="D".intern()){
                stringBuffer.append(listStringDates.get(i));
            }
            if(amountSub.intern()=="D".intern()||amountSub.intern()=="P".intern())
                addRequired = false;
        }
        if(addRequired){
            Date addDate = formatter.parse(formatter.format(listDates.get(listDates.size()-1)));
            addDate.setMonth(addDate.getMonth()+1);
            listAmount.add("D");
            listStringDates.add(formatter.format(addDate));

        }
        StringBuffer newBuffer = new StringBuffer();
        newBuffer.append("Payment pending for ");
        //Date currentDate  = formatter.parse(formatter.format(new Date()));;
        int currentYear = Integer.parseInt(DateToString(currentdate,"yyyy"));
        boolean tobeAppended = false;
        //for(Date date:listDates){
        for(int i=0;i<listDates.size();i++){
            Date date = listDates.get(i);
            if(currentdate.after(date) && listAmount.get(i).intern()!="C".intern()){
                tobeAppended= true;
                if(currentYear<Integer.parseInt(DateToString(date,"yyyy")))
                    newBuffer.append(DateToString(date,"MMM yy")).append(", ");
                else
                    newBuffer.append(DateToString(date,"MMM")).append(", ");

            }
        }
        duePayString=tobeAppended?newBuffer.substring(0,newBuffer.length()-2).toString():"";
        Log.d("datess", String.valueOf(listAmount));
        Log.d("datesstring", String.valueOf(listStringDates));

        //if inputAmount
    }

    private void modifyData(){

        if(actPerformed.intern()=="BILL".intern()){
            findViewById(R.id.others_view).setVisibility(View.GONE);
            initializeDropDown();
        }else{
            findViewById(R.id.bill_view).setVisibility(View.GONE);
        }

    }

    private void initializeDropDown(){
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_text,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private static final String[]paths = {"GST", "Income Tax"};

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                remarks.setText("GST");
                break;
            case 1:
                remarks.setText("Income Tax");
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public boolean validate() {
        boolean valid = true;
        String recieptNo = recieptEdit.getText().toString();
        String amount =amountEdit.getText().toString();
//        if (recieptNo.isEmpty()) {
//            recieptEdit.setError("Enter Valid Address");
//            valid = false;
//        } else {
//            recieptEdit.setError(null);
//        }
        if (amount.isEmpty() || ((Double)Double.parseDouble(amount)).compareTo(new Double(0))==0) {
            amountEdit.setError("Enter Valid Amount");
            valid = false;
        } else {
            amountEdit.setError(null);
        }
        if(StringToDate(dateEdit.getText().toString(),null).after(new Date())){
            dateEdit.setError("Date exceeds current Date");
            valid = false;
        }else{
            dateEdit.setError(null);
        }

        return valid;
    }

    public void showAToast (String message){
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    //public void call
    static int i;

    public void addLayout(){
        LinearLayout ll = (LinearLayout) findViewById(R.id.relatedChannels);
        ll.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //layoutParams.setMargins(25, 20, 25, 10);
        EditText view = new EditText(this);
        view.setText(++i+" view");
        ll.addView(view, layoutParams);
    }

    private void createDynamicLayout(LinearLayout ll){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for(int i=0;i<allAccounts.size();i++){
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(allAccounts.get(i));
            //checkBox.setChecked();
            ll.addView(checkBox, layoutParams);
        }

    }

    private void setAdditionalAccounts(ArrayList<Customer> customers){
        for(int i=0;i<customers.size();i++){
            allAccounts.add(customers.get(i).getAccount());
            if(i==0){

            }
        }

    }

    public void handleAllAccountsResult(ArrayList<Customer> customers){
        LinearLayout ll = (LinearLayout) findViewById(R.id.relatedChannels);
        clearLayout(ll);
        if(customers!=null && customers.size()>0){
            //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    public void handleResult(ArrayList<Customer> customers){
        if(customers == null || customers.size()==0){
            showAToast("No Record found");
            progressBar.setVisibility(View.GONE);
            //TO-DO
        }else if(customers.size()==1){
            showAToast("Customer found");
            accountEdit.setText(customers.get(0).getAccount());
            nameEdit.setText(customers.get(0).getName());
            phoneEdit.setText(customers.get(0).getPhone());
            addressEdit.setText(customers.get(0).getAddress());
            amountEdit.setText(customers.get(0).getmAmount());
            if(customers.get(0).getmAmount()!=null && customers.get(0).getmAmount().trim().intern()!="".intern())
                monthlyAmount = Integer.parseInt(customers.get(0).getmAmount());
            customerf = customers.get(0);
            fetchPendingPayments(customers.get(0).getAccount());
            /*addressEdit.setFreezesText(true);
            addressEdit.setFocusable(false);
            */
        }else {
            showPopup(customers);
            progressBar.setVisibility(View.GONE);
        }

        //customerfinal = customers;

    }

    private void clearLayout(LinearLayout l1){
        if(l1.getChildCount()>1)
            l1.removeAllViews();
    }

    private void setupPopUpLayoutManager(View layout){
        rv=(GridView) layout.findViewById(R.id.rv);
        rv.setNumColumns(1);
    }

    private void fetchAccountsByCIF(String cif){
        ParseService newService = new ParseService(this);
        newService.getDataByCIF(cif);
    }

    private void fetchListByName(){
        /*ArrayList<Customer> list= new ArrayList<Customer>();
        ArrayList<CustomerCompleteDetails> list1= new ArrayList<CustomerCompleteDetails>();*/
        ParseService newService = new ParseService(this);
        newService.getDatabyName(nameEdit.getText().toString(),actPerformed);
        /*if(list1.size() !=0){
            for(int i=0; i < list1.size(); i++){
                list.add(new Customer(list1.get(i).getName(),list1.get(i).getAccount(),list1.get(i).getAddress(),list1.get(i).getPhone()));
            }
        }
        return list;*/
    }

    private void fetchPendingPayments(String account){
        if(actPerformed.intern()!="REC".intern()){
            progressBar.setVisibility(View.GONE);
            return;

        }
        if(account==null)
            account= accountEdit.getText().toString();
        this.account=account;
        ParseService newService = new ParseService(this);
        newService.getPendingPayments(accountEdit.getText().toString());
    }

    private void fetchListByAccount(){
        /*ArrayList<Customer> list= new ArrayList<Customer>();
        ArrayList<CustomerCompleteDetails> list1= new ArrayList<CustomerCompleteDetails>();*/
        ParseService newService = new ParseService(this);
        newService.getDatabyAccount(accountEdit.getText().toString(),actPerformed);
        /*Log.d("totalobj",String.valueOf(list1.size()));
        if(list1.size() !=0){
            for(int i=0; i < list1.size(); i++){
                list.add(new Customer(list1.get(i).getName(),list1.get(i).getAccount(),list1.get(i).getAddress(),list1.get(i).getPhone()));
            }
        }return list;*/
    }
    private void fetchListByAddress(){
        /*ArrayList<Customer> list= new ArrayList<Customer>();
        ArrayList<CustomerCompleteDetails> list1= new ArrayList<CustomerCompleteDetails>();*/
        ParseService newService = new ParseService(this);
        newService.getDatabyAddress(addressEdit.getText().toString(),actPerformed);
        /*if(list1.size() !=0){
            for(int i=0; i < list1.size(); i++){
                list.add(new Customer(list1.get(i).getName(),list1.get(i).getAccount(),list1.get(i).getAddress(),list1.get(i).getPhone()));
            }
        }return list;*/

    }
    private void fetchListByPhone(){
        /*ArrayList<Customer> list= new ArrayList<Customer>();
        ArrayList<CustomerCompleteDetails> list1= new ArrayList<CustomerCompleteDetails>();*/
        ParseService newService = new ParseService(this);
        newService.getDatabyMobile(phoneEdit.getText().toString(),actPerformed);
        /*if(list1.size() !=0){
            for(int i=0; i < list1.size(); i++){
                list.add(new Customer(list1.get(i).getName(),list1.get(i).getAccount(),list1.get(i).getAddress(),list1.get(i).getPhone()));
            }
        }return list;*/
    }

    private void initializeAdapter(Context mContext,ArrayList<Customer> customers){
       // DatabaseHandler db = new DatabaseHandler(this);
      //  ArrayList<HashMap<String,String>> list= gridSetup();
        final ArrayList<Customer> list= customers;
        CustomerAdapter adapter = new CustomerAdapter(mContext, R.layout.popup_items, list);
        rv.setAdapter(adapter);
        rv.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //((TextView)findViewById(R.id.hello)).setText("YIPEEEEE");
                accountEdit.setText(list.get(position).getAccount());
                nameEdit.setText(list.get(position).getName());
                phoneEdit.setText(list.get(position).getPhone());
                addressEdit.setText(list.get(position).getAddress());
                amountEdit.setText(list.get(position).getmAmount());
                if(list.get(position).getmAmount()!=null && list.get(position).getmAmount().trim().intern()!="".intern())
                    monthlyAmount = Integer.parseInt(list.get(position).getmAmount());
                customerf = new Customer(list.get(position).getName(),list.get(position).getAccount(),list.get(position).getAddress(),list.get(position).getPhone(),list.get(position).getCifno());
                progressBar.setVisibility(View.VISIBLE);
                fetchPendingPayments(list.get(position).getAccount());

                /*int j = 0;
                //addLayout();
                while(customerfinal.size() !=1 && customerfinal.size()>0){
                    if(customerfinal.get(j).getAccount() ==  list.get(position).getAccount()){
                        customerf = customerfinal.get(j);
                        break;
                    }
                    j++;
                }*/
                pw.dismiss();


                /*Log.d(TAG, "onItemClick: navigating to fragment#: " + position);
                setViewPager(position);*/
                /*Bundle extras = new Bundle();
                //TODO
                extras.putString("CATEGORY", rl.get(position).get("CATEGORY"));
                Intent intent1 = new Intent(mContext, CategoriesFragment.class);
                intent1.putExtras(extras);
                startActivity(intent1);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();*/
            }
        });
    }

    private String DateToString(Date date,String format){
        Format formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    private Date StringToDate(String date,String format){
        Log.d("ankur test",date);
        format="dd/MM/yy";
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    ArrayList<Date> listDates;
    HashMap<String,String> mapDates;
    HashMap map;
    String account;

    public void handlePendingResultsModified(HashMap map) throws ParseException {
        this.map = map;
        ParseService newService = new ParseService(this);
        newService.getLastTransaction(account,actPerformed);
    }

    public void handlePendingPaymentsResults(HashMap returnMap) throws ParseException {
        if(returnMap.containsKey("Amount")){
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
            //cutomerAdditinalData.put(formatter.format(new Date()),"D");
            try {
                lastPayString = "Last payment was made on " + formatter.format(returnMap.get("CreateDate"));
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        if(lastPayString!=null && lastPayString.intern()!="".intern()){
            TextView textView = findViewById(R.id.add_text1);
            textView.setText(lastPayString);
        }
        handlePendingPaymentsResults();
    }

    public void handlePendingPaymentsResults() throws ParseException {
        listDates = (ArrayList<Date>) map.get("list");
        mapDates = (HashMap<String, String>) map.get("data");
        //Log.d("ankur1", String.valueOf(listDates));
        //Log.d("ankur1", String.valueOf(mapDates));
        boolean tobeAppended=false;
        SimpleDateFormat formatter = new SimpleDateFormat("MMMyy");
        Date currentDate  = formatter.parse(formatter.format(new Date()));;
        int currentYear = Integer.parseInt(DateToString(currentDate,"yyyy"));
        //String currentMonth = DateToString(new Date());
        StringBuffer newBuffer = new StringBuffer();
        newBuffer.append("Payment pending for ");
        for(Date date:listDates){
                if(currentDate.after(date)){
                    tobeAppended= true;
                    if(currentYear<Integer.parseInt(DateToString(date,"yyyy")))
                        newBuffer.append(DateToString(date,"MMM yy")).append(", ");
                    else
                        newBuffer.append(DateToString(date,"MMM")).append(", ");

                }
        }
        duePayString = tobeAppended?newBuffer.substring(0,newBuffer.length()-2).toString():"";
        //if(tobeAppended)
        TextView textView = findViewById(R.id.add_text);
        textView.setText(duePayString);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_entry,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.scan){
            //Log.i("Logging out",Integer.toString(item.getItemId()));
            //ParseUser.logOut();
            Bundle extras = new Bundle();
            extras.putString("CATEGORY",actPerformed);
            Intent intent = new Intent(getApplicationContext(), SimpleScannerActivity.class);
            intent.putExtras(extras);
            startActivity(intent);
            finish();
        }else{
            Bundle extras = new Bundle();
            extras.putString("CATEGORY",actPerformed);
            Intent intent = new Intent(getApplicationContext(), ModifyCustomer.class);
            intent.putExtras(extras);
            startActivity(intent);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(pw!=null && pw.isShowing()){
            pw.dismiss();
            pw=null;
        }else {
            finish();
        }

    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    private void showPopup(ArrayList<Customer> list) {
        try {
            LayoutInflater inflater = (LayoutInflater) TransactionEntry.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup,
                    (ViewGroup) findViewById(R.id.popup_1));
            layout.setAnimation(AnimationUtils.loadAnimation(this, R.anim.popupanim));
            pw = new PopupWindow(layout);
            pw.setWidth(FrameLayout.LayoutParams.WRAP_CONTENT);
            pw.setHeight(FrameLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                pw.setElevation(100);
            }
            pw.setOutsideTouchable(true);
            pw.setFocusable(true);
            pw.showAtLocation(layout, Gravity.TOP, 0, 0);
            setupPopUpLayoutManager(layout);
            initializeAdapter(layout.getContext(),list);
            //progressBar.setVisibility(View.GONE);
            closePopup = (ImageButton) layout.findViewById(R.id.ib_close);
            closePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pw.dismiss();
                }
            });
            /*No = (Button) layout.findViewById(R.id.b3);
            No.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    pw.dismiss();
                }
            });
            Close = (Button) layout.findViewById(R.id.b2);
            Close.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    pw.dismiss();
                    finish();
                }
            });
            Rateus = (Button) layout.findViewById(R.id.b2);
            Rateus.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    pw.dismiss();
                    finish();
                }
            });*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
