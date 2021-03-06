package btventures.ledger;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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
    private ScrollView linearLayout;
    String calledFrom;
    private String actPerformed;
    private TextView textViewHeader;
    private String duePayString;
    private String lastPayString;



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
        //String recieptNo = ParseUser.getCurrentUser().getString("AgentCode")+timeStamp;

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
        textViewHeader= findViewById(R.id.reciept_header);

        linearLayout = findViewById(R.id.hidden_view);

        Bundle b= getIntent().getExtras();
        actPerformed = b.getString("CATEGORY");
        if(actPerformed.intern()=="LIC".intern()){
            accountEdit.setVisibility(View.GONE);
            accountEdit = findViewById(R.id.input_account_lic);
        }else{
            findViewById(R.id.input_account_lic).setVisibility(View.GONE);
        }

        textView.setText(new StringBuffer().append("Transaction has been saved successfully with reference no. ").append(b.getString("receipt")));
        accountEdit.setText(b.getString("account"));
        nameEdit.setText(b.getString("name"));
        phoneEdit.setText(b.getString("phone"));
        addressEdit.setText(b.getString("address"));
        String remarks =b.getString("remarks");
        if(remarks==null || remarks.trim().intern()=="".intern())
            remarks="-";
        remarksEdit.setText(remarks);
        recieptEdit.setText(b.getString("receipt"));
        amountEdit.setText(b.getString("amount"));
        duePayString = b.getString("pending");
        lastPayString= b.getString("last_pay");





        TextView textView1 = findViewById(R.id.add_text);
        if(duePayString!=null && duePayString.trim().intern()!="".intern()) {
            textView1.setText(duePayString);
            Log.d("duePayString",duePayString);
        }

        if(lastPayString!=null && lastPayString.intern()!="".intern()){
            textView1 = findViewById(R.id.add_text1);
            textView1.setText(lastPayString);
        }

        calledFrom = b.getString("CalledFrom", "");

        if(calledFrom.intern() == "recentTransactions".intern()){

            textView.setText(new StringBuffer().append("Please select the option you want to perform on the selected transaction : ").append(b.getString("receipt")));
        }
        /*linearLayout.post(new Runnable() {
            @Override
            public void run() {

                if(calledFrom.intern() == "recentTransactions".intern()){
                    Toast.makeText(mContext, "trying to print", Toast.LENGTH_SHORT).show();
                    takeScreenshot();
                }
            }
        });*/

        final String accountType = b.getString("CATEGORY");


        if(actPerformed.intern()=="BILL".intern()){
            if(remarks.intern()=="GST".intern()){
                textViewHeader.setText("SCA Business Solutions Private Limited\n43 Ward No.11\nJhajjar");
            }else{
                textViewHeader.setText("Prashant Arya and Associates\nShop No. 109\nOpposite Post Office, Near Khadi Ashram\nJhajjar");
            }

        }else
            textViewHeader.setText("Arya Investment Centre\nShop No. 108\nOpposite Post Office, Near Khadi Ashram\nJhajjar");


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
            public void onClick(View view) {/*
                        try {
                            // Construct data
                            String apiKey = "apikey=" + URLEncoder.encode("oTjNx5xJhGQ-bCq31vCK36pT3XtWOBnoLXYUdwnPxN");
                            String message = "&message=" + "Your payment of Rs " + amountEdit.getText().toString() + " has been received. Reference number is " + recieptEdit.getText();
                            //String sender = "&sender=" + "ARYAIC";
                            String numbers = "&numbers=" + "91"+ phoneEdit.getText();

                            Log.d("Sendingsms","sending");

                            // Send data
                            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
                            String data = apiKey + numbers + message;// + sender;
                            conn.setDoOutput(true);
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                            conn.getOutputStream().write(data.getBytes("UTF-8"));
                            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            final StringBuffer stringBuffer = new StringBuffer();
                            String line;
                            while ((line = rd.readLine()) != null) {
                                stringBuffer.append(line);
                            }
                            rd.close();


                            Toast.makeText(view.getContext(), "SMS sent", Toast.LENGTH_LONG).show();

                            //return stringBuffer.toString();
                        } catch (Exception e) {
                            //System.out.println("Error SMS "+e);
                            Log.d("fatgyi",e.toString());

                            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            //return "Error "+e;
                        }*/
                new SendSms().execute();

                //Toast.makeText(view.getContext(), "SMS sent", Toast.LENGTH_LONG).show();

            }
        });
        //modifyData();


    }

    class SendSms extends AsyncTask<Void, Void, Void> {

        private Exception exception;
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                // Construct data
                String apiKey = "apikey=" + URLEncoder.encode("oTjNx5xJhGQ-bCq31vCK36pT3XtWOBnoLXYUdwnPxN");
                String message = "&message=" + "Your payment of Rs " + amountEdit.getText().toString() + " has been received by ARYAIC. Reference number is " + recieptEdit.getText();
                //String sender = "&sender=" + "ARYAIC";
                String numbers = "&numbers=" + "91"+ phoneEdit.getText();

                Log.d("Sendingsms","sending");

                // Send data
                HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
                String data = apiKey + numbers + message;// + sender;
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                conn.getOutputStream().write(data.getBytes("UTF-8"));
                final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                final StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = rd.readLine()) != null) {
                    stringBuffer.append(line);
                }
                rd.close();
                return null;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    public static Bitmap loadBitmapFromView(View v)
    {


        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.findViewById(R.id.hidden_view1).getHeight(), Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
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
            //Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            Bitmap bitmap =loadBitmapFromView(v1);
            v1.setDrawingCacheEnabled(false);
            v1.setVisibility(View.INVISIBLE);


            /*File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);*/
            doPhotoPrint(bitmap);


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

    /*private void doPhotoPrint() {
        PrintHelper photoPrinter = new PrintHelper(this);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.droids);
        photoPrinter.printBitmap("droids.jpg - test print", bitmap);
    }*/

    private void doPhotoPrint(Bitmap bitmap) {
        PrintHelper photoPrinter = new PrintHelper(this);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        photoPrinter.printBitmap("receipt", bitmap);
    }
}
