package btventures.ledger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ParseUser.getCurrentUser() != null){ //Uncomment
            /*Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(homeActivity);
            finish();*/
            int role = ParseUser.getCurrentUser().getInt("role");// 0 for admin, 1 for agent
            if(role == 0){
                Intent adminActivity = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(adminActivity);
            }
            else if(role == 1){
                Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeActivity);
            }
            finish();

        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
