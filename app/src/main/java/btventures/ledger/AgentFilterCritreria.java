package btventures.ledger;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import btventures.ledger.json.AgentInfo;
import btventures.ledger.json.ParseService;
import btventures.ledger.tableview.CustomerCompleteDetails;

/**
 * Created by HP on 11-03-2018.
 */

public class AgentFilterCritreria extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Activity mContext;
    EditText fromdate;
    EditText todate;
    Calendar myCalendar;
    private EditText agentName;
    private Toast mToast;
    private EditText agentMail;
    private PopupWindow pw;
    private GridView rv;
    private ImageButton closePopup;
    private ImageButton nameButton;
    private ImageButton mailButton;
    private AppCompatButton submitButton;
    private AppCompatButton generateButton;
    public ProgressBar progressBar;
    private DatePickerDialog toDateDialog;
    private ScrollView scrollView;
    ArrayList<AgentInfo> agentfinal;
    AgentInfo agentf;
    private Date startDateFilter;
    private Date endDateFilter;
    private String actPerformed;

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    private static final String[]paths = {"Recurring Deposit", "Savings", "LIC", "Billing"};

    private void initializeDropDown(){
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_text,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                actPerformed="REC";
                //clearLayout();
                break;
            case 1:
                actPerformed="FIX";
                //clearLayout();
                break;
            case 2:
                actPerformed="LIC";
                //clearLayout();
                break;
            case 3:
                actPerformed="BILL";
                //clearLayout();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_filter);
        startDateFilter = new Date(-1);
        endDateFilter = new Date(-1);
        mContext = this;
        fromdate = findViewById(R.id.input_from_date);
        todate = findViewById(R.id.input_to_date);
        agentName = findViewById(R.id.input_name);
        agentMail = findViewById(R.id.input_mail);
        nameButton = findViewById(R.id.search_name);
        mailButton= findViewById(R.id.search_mail);
        progressBar = findViewById(R.id.progress);
        submitButton = findViewById(R.id.btn_submit);
        generateButton = findViewById(R.id.btn_generateCum2);
        scrollView = findViewById(R.id.scrollView2);
        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(mContext);
                if(agentName.getText().length()<3){
                    showAToast("Please enter atleast 3 charchaters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
               fetchListByName();
            }
        });
        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(mContext);
                if(agentMail.getText().length()<3){
                    showAToast("Please enter atleast 3 charchaters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                fetchListByMail();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ArrayList<Customer> customers = fetchListByAccount();
                if(agentf==null ){
                    //showAToast("Please enter a valid customer account");
                    agentName.setError("Enter a Valid Agent");
                    return;
                }else{
                    agentName.setError(null);

                }
                if (agentf == null){

                }
                if(validate()){
                    /*Bundle extras = new Bundle();
                    extras.putString("account",customerf.getAccount());
                    extras.putString("name",customerf.getName());
                    extras.putString("address",customerf.getAddress());
                    extras.putString("phone",customerf.getPhone());
                    extras.putString("receipt",recieptEdit.getText().toString());
                    extras.putString("amount",amountEdit.getText().toString());
                    extras.putString("CATEGORY",actPerformed);
                    Intent intent1 = new Intent(mContext, TransactionConfirmActivity.class);
                    intent1.putExtras(extras);
                    startActivity(intent1);*/
                    generateReport();
                    finish();
                }else{

                    Toast.makeText(mContext, "Please select agent to view the report.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ArrayList<Customer> customers = fetchListByAccount();
                if(agentf==null ){
                    //showAToast("Please enter a valid customer account");
                    agentName.setError("Enter a Valid Agent");
                    return;
                }else{
                    agentName.setError(null);

                }
                if (agentf == null){

                }
                if(validate()){
                    /*Bundle extras = new Bundle();
                    extras.putString("account",customerf.getAccount());
                    extras.putString("name",customerf.getName());
                    extras.putString("address",customerf.getAddress());
                    extras.putString("phone",customerf.getPhone());
                    extras.putString("receipt",recieptEdit.getText().toString());
                    extras.putString("amount",amountEdit.getText().toString());
                    extras.putString("CATEGORY",actPerformed);
                    Intent intent1 = new Intent(mContext, TransactionConfirmActivity.class);
                    intent1.putExtras(extras);
                    startActivity(intent1);*/
                    generateCumulativeReport();
                    finish();
                }else{

                    Toast.makeText(mContext, "Please select agent to view the report.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        initiliazeDate();
        initializeDropDown();

    }

    public boolean validate() {
        if(agentf == null){
            return false;
        }else
        {
            return true;
        }
    }

    private void generateCumulativeReport(){
        HashMap<String,String> filters = new HashMap<>();
        if(agentf !=null){
            if(agentf.getAgentName() != null){
                filters.put("agentEmail", agentf.getEmail());
                //filters.put("agentEmail", agentf.getEmail());
            }
            if(startDateFilter.getTime() != -1){

                filters.put("fromDate", startDateFilter.toString());
            }
            if(endDateFilter.getTime() != -1){

                filters.put("toDate", endDateFilter.toString());
            }
//            if(agentf.getEmail() != null){
//                filters.put("email",agentf.getEmail());
//            }
            if(filters.size() !=0){
                //ParseService serviceData = new ParseService(this);
                //serviceData.loadTransactionDataWithFilter(filters);
                /*Bundle b = new Bundle();
                b.putString("Category","TransactionAgentWise");
                    MainFragment mainf = new MainFragment(filters,startDateFilter,endDateFilter);
                    mainf.setArguments(b);
                    getSupportFragmentManager().beginTransaction().add(R.id.activity_containerAgent, mainf
                            , MainFragment.class.getSimpleName()).commit();*/

                Intent tableActivity = new Intent(this,TableActivity.class);
                filters.put("AccountType",actPerformed);
                tableActivity.putExtra("FiltersMap",filters);
                tableActivity.putExtra("startDate", startDateFilter.getTime());
                tableActivity.putExtra("endDate",endDateFilter.getTime());
                tableActivity.putExtra("Category","TransactionDayWise");
                startActivity(tableActivity);
            }
            else {
                Toast.makeText(mContext, "Invalid Agent Data.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void generateReport(){
        HashMap<String,String> filters = new HashMap<>();
        if(agentf !=null){
            if(agentf.getAgentName() != null){
                filters.put("AgentCode", agentf.getEmail());
                //filters.put("agentEmail", agentf.getEmail());
            }
//            if(agentf.getEmail() != null){
//                filters.put("email",agentf.getEmail());
//            }
            if(filters.size() !=0){
                //ParseService serviceData = new ParseService(this);
                //serviceData.loadTransactionDataWithFilter(filters);
                /*Bundle b = new Bundle();
                b.putString("Category","TransactionAgentWise");
                    MainFragment mainf = new MainFragment(filters,startDateFilter,endDateFilter);
                    mainf.setArguments(b);
                    getSupportFragmentManager().beginTransaction().add(R.id.activity_containerAgent, mainf
                            , MainFragment.class.getSimpleName()).commit();*/

                Intent tableActivity = new Intent(this,TableActivity.class);
                filters.put("AccountType",actPerformed);
                tableActivity.putExtra("FiltersMap",filters);
                tableActivity.putExtra("startDate", startDateFilter.getTime());
                tableActivity.putExtra("endDate",endDateFilter.getTime());
                tableActivity.putExtra("Category","TransactionAgentWise");
                startActivity(tableActivity);
            }
            else {
                Toast.makeText(mContext, "Invalid Agent Data.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //TO-DO
    private ArrayList<AgentInfo> fetchListByName(){
        ArrayList<AgentInfo> list1= new ArrayList<AgentInfo>();
        ParseService newService = new ParseService(this);
        newService.getAgentDatabyName(agentName.getText().toString());
        return list1;
    }

    private ArrayList<AgentInfo> fetchListByMail(){
        ArrayList<AgentInfo> list1= new ArrayList<AgentInfo>();
        ParseService newService = new ParseService(this);
        newService.getAgentDatabyMail(agentMail.getText().toString());
        return list1;
    }


    private long fromTime=0;
    private void updateLabelFrom() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        fromdate.setText(sdf.format(myCalendar.getTime()));
        fromTime = myCalendar.getTimeInMillis();
        /*if(toDateDialog!=null)
            toDateDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());*/

    }

    private void updateLabelTo() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        todate.setText(sdf.format(myCalendar.getTime()));
    }

    private void initiliazeDate(){

        myCalendar = Calendar.getInstance();
        fromdate.setOnClickListener(new View.OnClickListener() {
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
                        startDateFilter = myCalendar.getTime();
                        updateLabelFrom();
                    }

                }, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
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
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(mContext);
                // TODO Auto-generated method stub
                toDateDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        myCalendar.set(Calendar.MILLISECOND, 59);
                        myCalendar.set(Calendar.SECOND, 59);
                        myCalendar.set(Calendar.MINUTE, 59);
                        myCalendar.set(Calendar.HOUR_OF_DAY, 23);
                        endDateFilter = myCalendar.getTime();
                        updateLabelTo();
                    }

                }, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                if(fromTime!=0)
                    toDateDialog.getDatePicker().setMinDate(fromTime);
                toDateDialog.show();
            }
        });

    }

    public void showAToast (String message){
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void handleResult(ArrayList<AgentInfo> customers){
        if(customers == null || customers.size()==0){
            showAToast("No Record found");
            //TO-DO
        }else if(customers.size()==1){
            showAToast("Agent found");
            agentName.setText(customers.get(0).getAgentName());
            agentMail.setText(customers.get(0).getEmail());
            agentf = customers.get(0);
            /*addressEdit.setFreezesText(true);
            addressEdit.setFocusable(false);
            */
        }else
            showPopup(customers);
            agentfinal = customers;

    }

    private void setupPopUpLayoutManager(View layout){
        rv=(GridView) layout.findViewById(R.id.rv);
        rv.setNumColumns(1);
    }

    private void initializeAdapter(Context mContext,ArrayList<AgentInfo> customers){
        // DatabaseHandler db = new DatabaseHandler(this);
        //  ArrayList<HashMap<String,String>> list= gridSetup();
        final ArrayList<AgentInfo> list= customers;
        AgentAdapter adapter = new AgentAdapter(mContext, R.layout.popup_agent_items, list);
        rv.setAdapter(adapter);
        rv.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //((TextView)findViewById(R.id.hello)).setText("YIPEEEEE");
                agentName.setText(list.get(position).getAgentName());
                agentMail.setText(list.get(position).getEmail());

                int j = 0;
                while(agentfinal.size() !=1 && agentfinal.size()>0){
                    if(agentfinal.get(j).getEmail() ==  list.get(position).getEmail()){
                        agentf = agentfinal.get(j);
                        break;
                    }
                    j++;
                }
                pw.dismiss();
            }
        });
    }

    private void showPopup(ArrayList<AgentInfo> list) {
        try {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_agent,
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
