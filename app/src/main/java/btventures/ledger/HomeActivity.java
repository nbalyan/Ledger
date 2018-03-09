package btventures.ledger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
    public void onClick(View view) {
        Bundle extras = new Bundle();
        if(view.getId()==R.id.FIXDep){
            extras.putString("CATEGORY", "FIX");
        }else if(view.getId()==R.id.RecDep){
            extras.putString("CATEGORY", "REC");
        }else if(view.getId()==R.id.LICbutton){
            extras.putString("CATEGORY", "LIC");
        }else{
            Intent launchActivity = new Intent(getApplicationContext(), TransactionEntry.class);
            launchActivity.putExtras(extras);
            startActivity(launchActivity);

        }
        Intent launchActivity = new Intent(getApplicationContext(), TransactionEntry.class);
        launchActivity.putExtras(extras);
        startActivity(launchActivity);

    }
}
