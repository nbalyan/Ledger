package btventures.ledger;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("granted storage", "granted");
                    return;

                    /*if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {

                        //Log.d("granted storage", "here");
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                2);
                    }*/


                } else {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                1);
                    }

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
            }
            case 2:{
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(ParseUser.getCurrentUser() != null){ //Uncomment
            /*Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(homeActivity);
            finish();*/
                        int role = ParseUser.getCurrentUser().getInt("role");// 0 for admin, 1 for agent
                        if(role == 0){
                            Intent adminActivity = new Intent(getApplicationContext(), AdminHomeActivity.class);
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

                } else {

                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                2);
                    }

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;

            }
            case 3:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    if(ParseUser.getCurrentUser() != null){ //Uncomment
            /*Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(homeActivity);
            finish();*/
                        int role = ParseUser.getCurrentUser().getInt("role");// 0 for admin, 1 for agent
                        if(role == 0){
                            Intent adminActivity = new Intent(getApplicationContext(), AdminHomeActivity.class);
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
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    2);
        }*/

        int PERMISSION_ALL = 3;
        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.SEND_SMS};

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }else {
            if (ParseUser.getCurrentUser() != null) { //Uncomment
                //Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
                //startActivity(homeActivity);
                //finish();
                int role = ParseUser.getCurrentUser().getInt("role");// 0 for admin, 1 for agent
                if (role == 0) {
                    Intent adminActivity = new Intent(getApplicationContext(), AdminHomeActivity.class);
                    startActivity(adminActivity);
                } else if (role == 1) {
                    Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(homeActivity);
                }
                finish();


            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
