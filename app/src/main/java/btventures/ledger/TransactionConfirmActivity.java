package btventures.ledger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseUser;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import btventures.ledger.json.ParseService;

/**
 * Created by HP on 09-03-2018.
 */

public class TransactionConfirmActivity extends AppCompatActivity {

    private Activity mContext;
    private TransactionConfirmActivity context;
    private EditText accountEdit;
    private EditText nameEdit;
    private EditText phoneEdit;
    private EditText addressEdit;
    private EditText recieptEdit;
    private EditText amountEdit;
    private EditText remarksEdit;
    private AppCompatButton submitButton;
    private AppCompatButton editButton;
    private String actPerformed;
    private String reciept;
    private String duePayString;
    private String lastPayString;
    private EditText dateEdit;

    private void disableField(EditText editText){
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        editText.setClickable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        String timeStamp = new SimpleDateFormat("yyMMddHHmmss").format(new Date());

        mContext = this;
        context = this;
        accountEdit = findViewById(R.id.input_account);
        nameEdit = findViewById(R.id.input_name);
        addressEdit = findViewById(R.id.input_address);
        phoneEdit = findViewById(R.id.input_mobile);
        recieptEdit = findViewById(R.id.input_reciept);
        dateEdit = findViewById(R.id.input_date);
        remarksEdit = findViewById(R.id.input_remarks);
        amountEdit = findViewById(R.id.input_amount);
        submitButton = findViewById(R.id.btn_submit);
        editButton = findViewById(R.id.btn_edit);

        Bundle b= getIntent().getExtras();
        actPerformed = b.getString("CATEGORY");
        if(actPerformed.intern()=="LIC".intern()){
            accountEdit.setVisibility(View.GONE);
            accountEdit = findViewById(R.id.input_account_lic);
        }else{
            findViewById(R.id.input_account_lic).setVisibility(View.GONE);
        }

        accountEdit.setText(b.getString("account"));
        nameEdit.setText(b.getString("name"));
        phoneEdit.setText(b.getString("phone"));
        addressEdit.setText(b.getString("address"));
        remarksEdit.setText(b.getString("remarks"));
        String dateEditStr= b.getString("dateEdit");
        if(dateEditStr==null || dateEditStr.trim().intern()=="".intern())
            dateEditStr = DateToString(new Date(),null);
        dateEdit.setText(dateEditStr);
        reciept = ParseUser.getCurrentUser().getString("AgentCode")+timeStamp;
        recieptEdit.setText(reciept);
        amountEdit.setText(b.getString("amount"));
        duePayString = b.getString("pending");
        lastPayString = b.getString("last_pay");

        final String accountType = b.getString("CATEGORY");

        TextView textView = findViewById(R.id.add_text);
        if(duePayString!=null && duePayString.intern()!="".intern())
            textView.setText(duePayString);

        if(lastPayString!=null && lastPayString.intern()!="".intern()){
            textView = findViewById(R.id.add_text1);
            textView.setText(lastPayString);
        }

        disableField(accountEdit);
        disableField(nameEdit);
        disableField(phoneEdit);
        disableField(addressEdit);
        disableField(recieptEdit);
        disableField(amountEdit);
        disableField(remarksEdit);
        disableField(dateEdit);

        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(mContext, TransactionEntry.class);
                intent1.putExtras(getIntent().getExtras());
                startActivity(intent1);
                finish();

            }
        });

        final String finalDateEditStr = dateEditStr;
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseService service = new ParseService(context);
                Customer newTransaction = new Customer();
                newTransaction.setAccount(accountEdit.getText().toString());
                newTransaction.setAccountType(accountType);
                newTransaction.setAddress(addressEdit.getText().toString());
                newTransaction.setPhone(phoneEdit.getText().toString());
                newTransaction.setCifno(recieptEdit.getText().toString());
                newTransaction.setRemarks(remarksEdit.getText().toString());
                newTransaction.setAgentCode(ParseUser.getCurrentUser().getUsername());
                newTransaction.setmAmount(amountEdit.getText().toString());
                newTransaction.setName(nameEdit.getText().toString());
                newTransaction.setDate(finalDateEditStr);
                /*Date tranDate = null;
                if(finalDateEditStr.intern()==DateToString(new Date(),null)){

                }*/
                //tranDate = StringToDate(dateEdit.getText().toString());

                service.saveTransaction(newTransaction);

                if(getIntent().getExtras().getStringArrayList("list_Dates")!=null && finalDateEditStr.intern()==DateToString(new Date(),null).intern())
                    service.saveTransactionAdditionalInfo(getIntent().getExtras().getStringArrayList("list_Dates"),getIntent().getExtras().getStringArrayList("list_amounts"),accountEdit.getText().toString());
                //TODO
            }
        });


    }

    private String DateToString(Date date,String format){
        format="dd/MM/yy";
        Format formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }





    public void handleCallBack() {
        Intent transactionConfirmed = new Intent(this, TransactionAfterConfirmActivity.class);
        Bundle bundle = getIntent().getExtras();
        bundle.putString("receipt",reciept);
        transactionConfirmed.putExtras(bundle);
        startActivity(transactionConfirmed);
        finish();
    }
}
