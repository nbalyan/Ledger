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

public class ModifyCustomer extends AppCompatActivity {
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
    private EditText dateEdit;
    private EditText amountEdit;

    private EditText codeEdit;
    private EditText CIFedit;
    private EditText aadharEdit;
    private EditText PANEdit;
    private EditText jointNameEdit;
    private EditText aadharJointEdit;
    private EditText jointCIFedit;
    private EditText nominationEdit;


    private Activity mContext;
    public ProgressBar progressBar;
    private Toast mToast;
    private String actPerformed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifycustomer);
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
        dateEdit = findViewById(R.id.input_date);
        amountEdit = findViewById(R.id.input_amount);
        submitButton = findViewById(R.id.btn_submit);
        aadharEdit = findViewById(R.id.input_aadhar);
        PANEdit = findViewById(R.id.input_pan);
        jointNameEdit = findViewById(R.id.input_jointAccountHolder);
        nominationEdit = findViewById(R.id.input_nomination);

        Bundle b= getIntent().getExtras();

        actPerformed = b.getString("CATEGORY");

        if(b.getString("account")!=null) {
            accountEdit.setText(b.getString("account"));
            nameEdit.setText(b.getString("name"));
            phoneEdit.setText(b.getString("phone"));
            addressEdit.setText(b.getString("address"));
            dateEdit.setText(b.getString("receipt"));
            amountEdit.setText(b.getString("amount"));
        }


       /* submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<CustomerCompleteDetails> customers = fetchListByAccount();
                if(customers==null || customers.size()!=1){
                    //showAToast("Please enter a valid customer account");
                    accountEdit.setError("Enter a Valid Account");
                    return;
                }else{
                    accountEdit.setError(null);

                }

                if(validate()){
                    Bundle extras = new Bundle();
                    extras.putString("account",customers.get(0).getAccount());
                    extras.putString("name",customers.get(0).getName());
                    extras.putString("address",customers.get(0).getAddress());
                    extras.putString("phone",customers.get(0).getPhone());
                    extras.putString("openingDate",dateEdit.getText().toString());
                    extras.putString("amount",amountEdit.getText().toString());
                    extras.putString("CATEGORY",actPerformed);
                    Intent intent1 = new Intent(mContext, TransactionConfirmActivity.class);
                    intent1.putExtras(extras);
                    startActivity(intent1);
                    finish();
                }
            }
        });*/


        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(mContext);
                if(accountEdit.getText().length()<3){
                    showAToast("Please enter atleast 3 charchaters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                ArrayList<CustomerCompleteDetails> customers = fetchListByAccount();
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
                ArrayList<CustomerCompleteDetails> customers = fetchListByName();
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
                ArrayList<CustomerCompleteDetails> customers = fetchListByAddress();
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
                ArrayList<CustomerCompleteDetails> customers = fetchListByPhone();
                //handleResult(customers);
               // progressBar.setVisibility(View.GONE);

            }
        });

        //showPopup();
    }

    public boolean validate() {
        boolean valid = true;
        String accountNo = accountEdit.getText().toString();
        String name = nameEdit.getText().toString();
        String address = addressEdit.getText().toString();
        String phone = phoneEdit.getText().toString();
        String amount =amountEdit.getText().toString();
        if (name.isEmpty()) {
            nameEdit.setError("Enter Valid Receipt No.");
            valid = false;
        } else {
            nameEdit.setError(null);
        }
        if (phone.isEmpty() || phone.length()!=10) {
            phoneEdit.setError("Enter Valid Phone No.");
            valid = false;
        } else {
            phoneEdit.setError(null);
        }
        if (address.isEmpty()) {
            addressEdit.setError("Enter Valid Address");
            valid = false;
        } else {
            addressEdit.setError(null);
        }
        if (amount.isEmpty() || ((Double)Double.parseDouble(amount)).compareTo(new Double(0))==0) {
            amountEdit.setError("Enter Valid Amount");
            valid = false;
        } else {
            amountEdit.setError(null);
        }

        if (accountNo.isEmpty() || ((Double)Double.parseDouble(accountNo)).compareTo(new Double(0))==0) {
            accountEdit.setError("Enter Valid Account No.");
            valid = false;
        } else {
            accountEdit.setError(null);
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

    public void submitData(View view){
        /*
        *
        accountEdit = findViewById(R.id.input_account);
        nameEdit = findViewById(R.id.input_name);
        addressEdit = findViewById(R.id.input_address);
        phoneEdit = findViewById(R.id.input_mobile);
        progressBar = findViewById(R.id.progress);
        dateEdit = findViewById(R.id.input_date);
        amountEdit = findViewById(R.id.input_amount);
        submitButton = findViewById(R.id.btn_submit);
        codeEdit = findViewById(R.id.input_code);
        CIFedit = findViewById(R.id.input_cif);
        aadharEdit = findViewById(R.id.input_aadhar);
        PANEdit = findViewById(R.id.input_pan);
        jointNameEdit = findViewById(R.id.input_jointAccountHolder);
        aadharJointEdit = findViewById(R.id.input_aadharJoint);
        jointCIFedit = findViewById(R.id.input_secondaryCIF);
        nominationEdit = findViewById(R.id.input_nomination);
        * */

        CustomerCompleteDetails newData = new CustomerCompleteDetails();
        newData.setAccount(accountEdit.getText().toString());
        newData.setName(nameEdit.getText().toString());
        newData.setPhone(phoneEdit.getText().toString());
        newData.setOpeningDate(dateEdit.getText().toString());
        newData.setAddress(addressEdit.getText().toString());
        newData.setAmount(amountEdit.getText().toString());
        newData.setAadhar(aadharEdit.getText().toString());
        newData.setPan_no(PANEdit.getText().toString());
        newData.setJointAccountName(jointNameEdit.getText().toString());
        newData.setNomination(nominationEdit.getText().toString());
        ParseService newService = new ParseService(this);
        Log.d("herein click", newData.getAccount());
        newService.addCustomerData(newData);



    }

    public void handleResult(ArrayList<CustomerCompleteDetails> customers){
        if(customers == null || customers.size()==0){
            showAToast("No Record found");
            //TO-DO
        }else if(customers.size()==1){
            showAToast("Customer found");
            accountEdit.setText(customers.get(0).getAccount());
            nameEdit.setText(customers.get(0).getName());
            phoneEdit.setText(customers.get(0).getPhone());
            addressEdit.setText(customers.get(0).getAddress());
            dateEdit.setText(customers.get(0).getOpeningDate());
            amountEdit.setText(customers.get(0).getAmount());

//            codeEdit.setText(customers.get(0).getCode());
   //         CIFedit.setText(customers.get(0).getCif());
            aadharEdit.setText(customers.get(0).getAadhar());
            PANEdit.setText(customers.get(0).getPan_no());
            jointNameEdit.setText(customers.get(0).getJointAccountName());
      //      jointCIFedit.setText(customers.get(0).getJointAccountCIF());
       //     aadharJointEdit.setText(customers.get(0).getJointAccountAadharNo());
            nominationEdit.setText(customers.get(0).getNomination());

            /*addressEdit.setFreezesText(true);
            addressEdit.setFocusable(false);
            */
        }else {
            ArrayList<Customer> list= new ArrayList<Customer>();

            ArrayList<CustomerCompleteDetails> list1 = customers;
            for(int j=0; j < list1.size(); j++) {
                list.add(new Customer(list1.get(j).getName(), list1.get(j).getAccount(), list1.get(j).getAddress(), list1.get(j).getPhone()));
            }
            showPopup(list);
        }
    }

    private void setupPopUpLayoutManager(View layout){
        rv=(GridView) layout.findViewById(R.id.rv);
        rv.setNumColumns(1);
    }

    private ArrayList<CustomerCompleteDetails> fetchListByName(){
        ArrayList<CustomerCompleteDetails> list= new ArrayList<CustomerCompleteDetails>();
        ParseService newService = new ParseService(this);
        list = newService.getDatabyName(nameEdit.getText().toString());
        return list;
    }
    private ArrayList<CustomerCompleteDetails> fetchListByAccount(){
        ArrayList<CustomerCompleteDetails> list= new ArrayList<CustomerCompleteDetails>();
        ParseService newService = new ParseService(this);
        list = newService.getDatabyAccount(accountEdit.getText().toString());
        return list;
    }
    private ArrayList<CustomerCompleteDetails> fetchListByAddress(){
        ArrayList<CustomerCompleteDetails> list= new ArrayList<CustomerCompleteDetails>();
        ParseService newService = new ParseService(this);
        list = newService.getDatabyAddress(addressEdit.getText().toString());
        return list;
    }
    private ArrayList<CustomerCompleteDetails> fetchListByPhone(){
        ArrayList<CustomerCompleteDetails> list= new ArrayList<CustomerCompleteDetails>();
        ParseService newService = new ParseService(this);
        list = newService.getDatabyMobile(phoneEdit.getText().toString());
        return list;
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
            LayoutInflater inflater = (LayoutInflater) ModifyCustomer.this
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

