package btventures.ledger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import btventures.ledger.json.ParseService;
import btventures.ledger.tableview.CustomerCompleteDetails;

public class CustomerReportCriteria extends AppCompatActivity {
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
    private Activity mContext;
    public ProgressBar progressBar;
    private Toast mToast;
    private String actPerformed;
    ArrayList<Customer> customerfinal;
    Customer customerf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_report_criteria);
        mContext = this;
        accountButton = findViewById(R.id.search_account);
        nameButton = findViewById(R.id.search_name);
        addressButton = findViewById(R.id.search_address);
        mobileButton = findViewById(R.id.search_mobile);
        accountEdit = findViewById(R.id.input_account);
        nameEdit = findViewById(R.id.input_name);
        addressEdit = findViewById(R.id.input_address);
        phoneEdit = findViewById(R.id.input_mobile);
        progressBar = findViewById(R.id.progress);
        recieptEdit = findViewById(R.id.input_reciept);
        amountEdit = findViewById(R.id.input_amount);
        submitButton = findViewById(R.id.btn_submit);
        Bundle b= getIntent().getExtras();

        actPerformed = b.getString("CATEGORY");

        if(b.getString("account")!=null) {
            accountEdit.setText(b.getString("account"));
            nameEdit.setText(b.getString("name"));
            phoneEdit.setText(b.getString("phone"));
            addressEdit.setText(b.getString("address"));
            recieptEdit.setText(b.getString("receipt"));
            amountEdit.setText(b.getString("amount"));
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
                ArrayList<Customer> customers = fetchListByAccount();
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
                ArrayList<Customer> customers = fetchListByName();
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
                ArrayList<Customer> customers = fetchListByAddress();
                //handleResult(customers);
                //progressBar.setVisibility(View.GONE);

            }
        });
        mobileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(mContext);
                if(phoneEdit.getText().length()<3){
                    showAToast("Please enter atleast 3 charchaters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                ArrayList<Customer> customers = fetchListByPhone();
                //handleResult(customers);
                //progressBar.setVisibility(View.GONE);

            }
        });

        //showPopup();
    }

    private void generateReport(){

    }

    public boolean validate() {
        boolean valid = true;
        String recieptNo = recieptEdit.getText().toString();
        String amount =amountEdit.getText().toString();
        if (recieptNo.isEmpty()) {
            recieptEdit.setError("Enter Valid Address");
            valid = false;
        } else {
            recieptEdit.setError(null);
        }
        if (amount.isEmpty() || ((Double)Double.parseDouble(amount)).compareTo(new Double(0))==0) {
            amountEdit.setError("Enter Valid Amount");
            valid = false;
        } else {
            amountEdit.setError(null);
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

    public void handleResult(ArrayList<Customer> customers){
        if(customers == null || customers.size()==0){
            showAToast("No Record found");
            //TO-DO
        }else if(customers.size()==1){
            showAToast("Customer found");
            accountEdit.setText(customers.get(0).getAccount());
            nameEdit.setText(customers.get(0).getName());
            phoneEdit.setText(customers.get(0).getPhone());
            addressEdit.setText(customers.get(0).getAddress());
            customerf = customers.get(0);
            /*addressEdit.setFreezesText(true);
            addressEdit.setFocusable(false);
            */
        }else
            showPopup(customers);
        customerfinal = customers;

    }

    private void setupPopUpLayoutManager(View layout){
        rv=(GridView) layout.findViewById(R.id.rv);
        rv.setNumColumns(1);
    }

    private ArrayList<Customer> fetchListByName(){
        ArrayList<Customer> list= new ArrayList<Customer>();
        ArrayList<CustomerCompleteDetails> list1= new ArrayList<CustomerCompleteDetails>();
        ParseService newService = new ParseService(this);
        list1 = newService.getDatabyName(nameEdit.getText().toString());
        if(list1.size() !=0){
            for(int i=0; i < list1.size(); i++){
                list.add(new Customer(list1.get(i).getName(),list1.get(i).getAccount(),list1.get(i).getAddress(),list1.get(i).getPhone()));
            }
        }
        return list;
    }
    private ArrayList<Customer> fetchListByAccount(){
        ArrayList<Customer> list= new ArrayList<Customer>();
        ArrayList<CustomerCompleteDetails> list1= new ArrayList<CustomerCompleteDetails>();
        ParseService newService = new ParseService(this);
        list1 = newService.getDatabyAccount(accountEdit.getText().toString());
        Log.d("totalobj",String.valueOf(list1.size()));
        if(list1.size() !=0){
            for(int i=0; i < list1.size(); i++){
                list.add(new Customer(list1.get(i).getName(),list1.get(i).getAccount(),list1.get(i).getAddress(),list1.get(i).getPhone()));
            }
        }return list;
    }
    private ArrayList<Customer> fetchListByAddress(){
        ArrayList<Customer> list= new ArrayList<Customer>();
        ArrayList<CustomerCompleteDetails> list1= new ArrayList<CustomerCompleteDetails>();
        ParseService newService = new ParseService(this);
        list1 = newService.getDatabyAddress(addressEdit.getText().toString());
        if(list1.size() !=0){
            for(int i=0; i < list1.size(); i++){
                list.add(new Customer(list1.get(i).getName(),list1.get(i).getAccount(),list1.get(i).getAddress(),list1.get(i).getPhone()));
            }
        }return list;
    }
    private ArrayList<Customer> fetchListByPhone(){
        ArrayList<Customer> list= new ArrayList<Customer>();
        ArrayList<CustomerCompleteDetails> list1= new ArrayList<CustomerCompleteDetails>();
        ParseService newService = new ParseService(this);
        list1 = newService.getDatabyMobile(phoneEdit.getText().toString());
        if(list1.size() !=0){
            for(int i=0; i < list1.size(); i++){
                list.add(new Customer(list1.get(i).getName(),list1.get(i).getAccount(),list1.get(i).getAddress(),list1.get(i).getPhone()));
            }
        }return list;
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
                int j = 0;
                while(customerfinal.size() !=1 && customerfinal.size()>0){
                    if(customerfinal.get(j).getAccount() ==  list.get(position).getAccount()){
                        customerf = customerfinal.get(j);
                        break;
                    }
                    j++;
                }
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
            LayoutInflater inflater = (LayoutInflater) CustomerReportCriteria.this
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
