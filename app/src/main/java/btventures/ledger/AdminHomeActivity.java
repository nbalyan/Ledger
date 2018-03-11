package btventures.ledger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AdminHomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button DayWise;
    private Button CustomerWise;
    private Button AgentWise;
    private Button PendingPayments;

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
            extras.putString("Category", "Customer");
        }else if(view.getId()==R.id.agent_wise){
            extras.putString("Category", "Agent");
            Intent launchActivity = new Intent(getApplicationContext(), TableActivity.class);
            launchActivity.putExtras(extras);
            startActivity(launchActivity);
        }else if(view.getId()==R.id.customer_wise){//AgentFilterCritreria
            extras.putString("Category", "Transaction");
            Intent launchActivity = new Intent(getApplicationContext(), AgentFilterCritreria.class);
            launchActivity.putExtras(extras);
            startActivity(launchActivity);
        }else{
            extras.putString("Category","ADDMODIFY");

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
