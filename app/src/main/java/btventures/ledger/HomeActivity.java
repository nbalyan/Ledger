package btventures.ledger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button LICButton;
    private Button FixDepButton;
    private Button RecDepButton;
    private Button CustomerModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        LICButton = findViewById(R.id.LICbutton);
        FixDepButton = findViewById(R.id.FIXDep);
        RecDepButton = findViewById(R.id.RecDep);
        CustomerModify = findViewById(R.id.CustomerModify);

        LICButton.setOnClickListener(this);
        FixDepButton.setOnClickListener(this);
        RecDepButton.setOnClickListener(this);
        CustomerModify.setOnClickListener(this);

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
        }else{
            extras.putString("CATEGORY","ADDMODIFY");

        }
        if(extras.get("CATEGORY") == "ADDMODIFY"){
            Intent launchActivity = new Intent(getApplicationContext(), ModifyCustomer.class);
            launchActivity.putExtras(extras);
            startActivity(launchActivity);

        }else {
            Intent launchActivity = new Intent(getApplicationContext(), TransactionEntry.class);
            launchActivity.putExtras(extras);
            startActivity(launchActivity);
        }
    }
}
