package btventures.ledger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton LICButton;
    private AppCompatButton FixDepButton;
    private AppCompatButton RecDepButton;
    private AppCompatButton BillingButton;
    //private Button CustomerModify;
    private AppCompatButton showTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        LICButton = findViewById(R.id.LICbutton);
        FixDepButton = findViewById(R.id.FIXDep);
        RecDepButton = findViewById(R.id.RecDep);
        //CustomerModify = findViewById(R.id.CustomerModify);
        BillingButton = findViewById(R.id.billing);
        //CustomerModify = findViewById(R.id.CustomerModify);
        showTransactions = findViewById(R.id.ShowRecentTransactions);

        if(ParseUser.getCurrentUser().getUsername().toString().intern() != "caprashantarya@gmail.com" && ParseUser.getCurrentUser().getUsername().toString().intern()!="aryapiush@gmail.com"){
            BillingButton.setVisibility(View.GONE);
        }

        LICButton.setOnClickListener(this);
        FixDepButton.setOnClickListener(this);
        RecDepButton.setOnClickListener(this);
        BillingButton.setOnClickListener(this);
        showTransactions.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menulogout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.logout){
            Log.i("Logging out",Integer.toString(item.getItemId()));
            ParseUser.logOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Bundle extras = new Bundle();
        if(view.getId()==R.id.FIXDep){
            extras.putString("CATEGORY", "FIX");
        }else if(view.getId()==R.id.RecDep){
            extras.putString("CATEGORY", "REC");
        }else if(view.getId()==R.id.LICbutton){
            extras.putString("CATEGORY", "LIC");
        }else if(view.getId()==R.id.ShowRecentTransactions){
            extras.putString("CATEGORY", "RTR");
        }else if(view.getId()==R.id.billing){
            extras.putString("CATEGORY", "BILL");
        }else{
            extras.putString("CATEGORY","ADDMODIFY");

        }
        if(extras.get("CATEGORY") == "ADDMODIFY"){
            Intent launchActivity = new Intent(getApplicationContext(), ModifyCustomer.class);
            launchActivity.putExtras(extras);
            startActivity(launchActivity);

        }else if(extras.get("CATEGORY") == "RTR"){
            Intent launchActivity = new Intent(getApplicationContext(), RecentTransactions.class);
            launchActivity.putExtras(extras);
            startActivity(launchActivity);

        }else {
            Intent launchActivity = new Intent(getApplicationContext(), TransactionEntry.class);
            launchActivity.putExtras(extras);
            startActivity(launchActivity);
        }
    }
}
