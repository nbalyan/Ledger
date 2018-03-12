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

public class AdminHomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button DayWise;
    private Button CustomerWise;
    private Button AgentWise;
    private Button PendingPayments;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_home);
        DayWise = findViewById(R.id.day_wise);
        CustomerWise = findViewById(R.id.customer_wise);
        AgentWise = findViewById(R.id.agent_wise);
        PendingPayments = findViewById(R.id.pending_payments);

        DayWise.setOnClickListener(this);
        CustomerWise.setOnClickListener(this);
        AgentWise.setOnClickListener(this);
        PendingPayments.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Bundle extras = new Bundle();
        if(view.getId()==R.id.day_wise){
            extras.putString("Category", "day_wise");
            Intent launchActivity = new Intent(getApplicationContext(), CommonFilterCritreria.class);
            launchActivity.putExtras(extras);
            startActivity(launchActivity);
        }else if(view.getId()==R.id.agent_wise){
            extras.putString("Category", "Agent");
            Intent launchActivity = new Intent(getApplicationContext(), AgentFilterCritreria.class);
            launchActivity.putExtras(extras);
            startActivity(launchActivity);
        }else if(view.getId()==R.id.customer_wise){//AgentFilterCritreria
            extras.putString("Category", "Transaction");
            Intent launchActivity = new Intent(getApplicationContext(), CustomerReportCriteria.class);
            launchActivity.putExtras(extras);
            startActivity(launchActivity);
        }else{
            extras.putString("Category","pending_report");
            Intent launchActivity = new Intent(getApplicationContext(), CommonFilterCritreria.class);
            launchActivity.putExtras(extras);
            startActivity(launchActivity);
        }

        /*Intent launchActivity = new Intent(getApplicationContext(), TableActivity.class);
        launchActivity.putExtras(extras);
        startActivity(launchActivity);*/
        /*if(extras.get("CATEGORY") == "ADDMODIFY"){
            Intent launchActivity = new Intent(getApplicationContext(), ModifyCustomer.class);
            launchActivity.putExtras(extras);
            startActivity(launchActivity);

        }else {
            Intent launchActivity = new Intent(getApplicationContext(), TransactionEntry.class);
            launchActivity.putExtras(extras);
            startActivity(launchActivity);
        }*/
    }
}
