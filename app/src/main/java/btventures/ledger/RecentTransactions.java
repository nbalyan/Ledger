package btventures.ledger;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import btventures.ledger.json.ParseService;

public class RecentTransactions extends AppCompatActivity {
    private ListView recentTransactions;
    private HashMap<String,String> filters;
    ArrayList<Customer> transactionList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_transactions);
        recentTransactions = findViewById(R.id.recentTransactions);
        //transactionList.add(new Customer("Ner","2134123","sadas","123123"));
        recentTransactions.setAdapter(new TransactionAdapter(this,R.layout.transaction_item,transactionList));
        filters = new HashMap<>();
        filters.put("AgentCode", ParseUser.getCurrentUser().getUsername().toString());
        ParseService getDataService = new ParseService(this);
        getDataService.loadTransactionDataWithFilter(filters,new Date(-1), new Date(-1));
    }

    public void makeListView(ArrayList<Customer> list){
        if(list.size() == 0){
            Toast.makeText(this, "No Records Found", Toast.LENGTH_LONG).show();
        }
        /*TransactionAdapter listAdapter = new TransactionAdapter(this,list);
        recentTransactions.setAdapter(listAdapter);
        recentTransactions.deferNotifyDataSetChanged();*/
        this.transactionList = list;
        recentTransactions.setAdapter(new TransactionAdapter(this,R.layout.transaction_item,transactionList));
        recentTransactions.deferNotifyDataSetChanged();
        //recentTransactions.deferNotifyDataSetChanged();
    }


class TransactionAdapter extends ArrayAdapter<Customer>{
    Context context;
    List <Customer> transactionList;
    private LayoutInflater layoutInflater;

/*    public TransactionAdapter(Context context, ArrayList<Customer> transactionList){
        this.context = context;
        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.transactionList = transactionList;
    }*/

    public TransactionAdapter(@NonNull Context context, int resource, @NonNull List<Customer> objects) {
        super(context, resource, objects);
        this.context = context;
        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.transactionList = objects;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View vi = view;
        if (vi == null)
            vi = layoutInflater.inflate(R.layout.transaction_item, null);
        TextView text = (TextView) vi.findViewById(R.id.textView2);
        TextView amount = (TextView) vi.findViewById(R.id.textView4);
        TextView receipt = (TextView) vi.findViewById(R.id.textView5);
        TextView accountNo = (TextView) vi.findViewById(R.id.accountItem);
        text.setText("Name: "+transactionList.get(i).getName());
        amount.setText("Amount: " + transactionList.get(i).getmAmount());
        receipt.setText("Receipt No: " + transactionList.get(i).getCifno());
        accountNo.setText("Account No:" + transactionList.get(i).getAccount());

        final Customer customerf = transactionList.get(i);


        Button print = (Button) vi.findViewById(R.id.Print);
        Button delete = (Button) vi.findViewById(R.id.Delete);
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = new Bundle();
                extras.putString("account",customerf.getAccount());
                extras.putString("name",customerf.getName());
                extras.putString("address",customerf.getAddress());
                extras.putString("phone",customerf.getPhone());
                extras.putString("receipt",customerf.getCifno());
                extras.putString("amount",customerf.getmAmount());
                extras.putString("remarks",customerf.getRemarks());
                extras.putString("CATEGORY",customerf.getAccountType());
                extras.putString("CalledFrom", "recentTransactions");
                Intent intent1 = new Intent(getApplicationContext(), TransactionAfterConfirmActivity.class);
                intent1.putExtras(extras);
                startActivity(intent1);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "chal gya delete", Toast.LENGTH_SHORT).show();

            }
        });
        return vi;
    }
}
}


