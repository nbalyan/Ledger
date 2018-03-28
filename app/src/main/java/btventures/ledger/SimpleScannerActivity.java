package btventures.ledger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.ArrayList;

import btventures.ledger.json.ParseService;
import btventures.ledger.tableview.CustomerCompleteDetails;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by tewat on 21-03-2018.
 */

public class SimpleScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private String actPerformed;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        Bundle b= getIntent().getExtras();
        actPerformed = b.getString("CATEGORY");
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
        //mScannerView.
    }

    @Override
    public void handleResult(Result rawResult) {
        //Log.v("CAM", rawResult.getText());
        fetchListByAccount(rawResult.getText());
        //mScannerView.resumeCameraPreview(this);
    }
    private Toast mToast;
    public void showAToast (String message){

        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void handleParseResult(ArrayList<Customer> customers){
        if(customers == null || customers.size()==0){
            showAToast("No Record found");
            mScannerView.resumeCameraPreview(this);
            //TO-DO
        }else if(customers.size()==1){

            Bundle extras = new Bundle();
            extras.putString("account",customers.get(0).getAccount());
            extras.putString("name",customers.get(0).getName());
            extras.putString("address",customers.get(0).getAddress());
            extras.putString("phone",customers.get(0).getPhone());
            /*extras.putString("receipt",recieptEdit.getText().toString());
            extras.putString("amount",amountEdit.getText().toString());
            extras.putString("remarks",remarks.getText().toString());*/
            extras.putString("CATEGORY",actPerformed);
            Intent intent1 = new Intent(this, TransactionEntry.class);
            intent1.putExtras(extras);
            startActivity(intent1);
            finish();
        }else {
            showAToast("More than one record found");
            mScannerView.resumeCameraPreview(this);
        }
    }

    private void fetchListByAccount(String account){
        //ArrayList<Customer> list= new ArrayList<Customer>();
        //ArrayList<CustomerCompleteDetails> list1= new ArrayList<CustomerCompleteDetails>();
        ParseService newService = new ParseService(this);
        newService.getDatabyAccount(account,actPerformed);
       // Log.d("totalobj",String.valueOf(list1.size()));
        /*if(list1.size() !=0){
            for(int i=0; i < list1.size(); i++){
                list.add(new Customer(list1.get(i).getName(),list1.get(i).getAccount(),list1.get(i).getAddress(),list1.get(i).getPhone()));
            }
        }//return list;*/
    }
}
