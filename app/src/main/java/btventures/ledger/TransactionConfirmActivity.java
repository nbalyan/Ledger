package btventures.ledger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseUser;

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
    private AppCompatButton submitButton;
    private AppCompatButton editButton;

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
        amountEdit = findViewById(R.id.input_amount);
        submitButton = findViewById(R.id.btn_submit);
        editButton = findViewById(R.id.btn_edit);

        Bundle b= getIntent().getExtras();
        accountEdit.setText(b.getString("account"));
        nameEdit.setText(b.getString("name"));
        phoneEdit.setText(b.getString("phone"));
        addressEdit.setText(b.getString("address"));
        recieptEdit.setText(ParseUser.getCurrentUser().getString("AgentCode")+timeStamp);
        amountEdit.setText(b.getString("amount"));

        final String accountType = b.getString("CATEGORY");
        disableField(accountEdit);
        disableField(nameEdit);
        disableField(phoneEdit);
        disableField(addressEdit);
        disableField(recieptEdit);
        disableField(amountEdit);

        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(mContext, TransactionEntry.class);
                intent1.putExtras(getIntent().getExtras());
                startActivity(intent1);
                finish();

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseService service = new ParseService(context);
                Customer newTransaction = new Customer();
                newTransaction.setAccount(accountEdit.getText().toString());
                newTransaction.setAccountType(accountType);
                newTransaction.setCifno(recieptEdit.getText().toString());
                newTransaction.setAgentCode(ParseUser.getCurrentUser().getUsername());
                newTransaction.setmAmount(amountEdit.getText().toString());
                newTransaction.setName(nameEdit.getText().toString());
                service.saveTransaction(newTransaction);
                //TODO
            }
        });


    }
}
