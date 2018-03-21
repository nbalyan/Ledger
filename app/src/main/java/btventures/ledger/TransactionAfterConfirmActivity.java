package btventures.ledger;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import btventures.ledger.json.ParseService;

/**
 * Created by HP on 09-03-2018.
 */

public class TransactionAfterConfirmActivity extends AppCompatActivity {

    private Activity mContext;
    private TransactionAfterConfirmActivity context;
    private EditText accountEdit;
    private EditText nameEdit;
    private EditText phoneEdit;
    private EditText addressEdit;
    private EditText recieptEdit;
    private EditText amountEdit;
    private EditText remarksEdit;
    private TextView textView;
    private AppCompatButton printButton;
    private AppCompatButton smsButton;
    private AppCompatButton homeButton;


    private void disableField(EditText editText){
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        editText.setClickable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_confirm);
        String timeStamp = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        String recieptNo = ParseUser.getCurrentUser().getString("AgentCode")+timeStamp;

        mContext = this;
        context = this;
        accountEdit = findViewById(R.id.input_account);
        nameEdit = findViewById(R.id.input_name);
        addressEdit = findViewById(R.id.input_address);
        phoneEdit = findViewById(R.id.input_mobile);
        recieptEdit = findViewById(R.id.input_reciept);
        remarksEdit = findViewById(R.id.input_remarks);
        amountEdit = findViewById(R.id.input_amount);
        printButton = findViewById(R.id.btn_print);
        smsButton = findViewById(R.id.btn_sms);
        homeButton = findViewById(R.id.btn_home);
        textView = findViewById(R.id.text_view);

        Bundle b= getIntent().getExtras();
        textView.setText(new StringBuffer().append("Transaction has been saved successfully with reference no. ").append(recieptNo));
        accountEdit.setText(b.getString("account"));
        nameEdit.setText(b.getString("name"));
        phoneEdit.setText(b.getString("phone"));
        addressEdit.setText(b.getString("address"));
        String remarks =b.getString("remarks");
        if(remarks==null || remarks.trim().intern()=="".intern())
            remarks="-";
        remarksEdit.setText(remarks);
        recieptEdit.setText(recieptNo);
        amountEdit.setText(b.getString("amount"));

        final String accountType = b.getString("CATEGORY");
        disableField(accountEdit);
        disableField(nameEdit);
        disableField(phoneEdit);
        disableField(addressEdit);
        disableField(recieptEdit);
        disableField(amountEdit);
        disableField(remarksEdit);

        printButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "printing reciept", Toast.LENGTH_LONG).show();
                takeScreenshot();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //TODO
            }
        });

        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "SMS sent", Toast.LENGTH_LONG).show();

            }
        });


    }


    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/Test" + now + ".jpg";

            // create bitmap screen capture
            View v1 = findViewById(R.id.hidden_view);
            v1.setVisibility(View.VISIBLE);
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);
            v1.setVisibility(View.INVISIBLE);


            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        //Uri uri = Uri.fromFile(imageFile);
        Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".my.package.name.provider", imageFile);

        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(intent);
    }
}
