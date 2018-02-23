package btventures.ledger;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;

/**
 * Created by Neeraj on 24-09-2017.
 */

public class StartLedger extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("5dcf36893dd9f40f05dfb21e1ef5220c437a4876")
                .server("http://52.66.142.22:80/parse/")
                .build()
        );



        //ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}
