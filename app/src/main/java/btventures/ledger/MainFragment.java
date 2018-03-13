package btventures.ledger;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evrencoskun.tableview.TableView;

import btventures.ledger.json.AgentInfo;
import btventures.ledger.json.ParseService;
import btventures.ledger.json.UserInfo;
import btventures.ledger.json.WebServiceHandler;
import btventures.ledger.tableview.MyTableAdapter;
import btventures.ledger.tableview.MyTableViewListener;
import btventures.ledger.tableview.model.CellModel;
import btventures.ledger.tableview.model.ColumnHeaderModel;
import btventures.ledger.tableview.model.RowHeaderModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainFragment extends Fragment {

    private static final String LOG_TAG = MainFragment.class.getSimpleName();

    private TableView mTableView;
    private MyTableAdapter mTableAdapter;

    private ProgressDialog mProgressDialog;
    //private WebServiceHandler mWebServiceHandler;

    private ParseService mParseService;

    // For TableView
    private List<List<CellModel>> mCellList;
    private List<ColumnHeaderModel> mColumnHeaderList;
    private List<RowHeaderModel> mRowHeaderList;
    private String category;
    private HashMap<String,String> filters;
    private Date startDate;
    private Date endDate;


    /*public MainFragment(HashMap<String,String> filters, Date startDate, Date endDate) {
        // Required empty public constructor
        this.filters = filters;
        this.startDate = startDate;
        this.endDate = endDate;
        Log.d("BKNMainFragment","initialized");
    }*/

    public MainFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        category = b.getString("Category");
        filters = (HashMap<String,String>)b.getSerializable("FiltersMap");
        startDate = new Date();
        startDate.setTime(b.getLong("startDate",-1));
        endDate = new Date();
        endDate.setTime(b.getLong("endDate",-1));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mTableView = (TableView) view.findViewById(R.id.my_TableView);

        // Create TableView Adapter
        mTableAdapter = new MyTableAdapter(getContext());
        mTableView.setAdapter(mTableAdapter);

        // Create listener
        mTableView.setTableViewListener(new MyTableViewListener(mTableView));

        // UserInfo data will be getting from a web server.
       // mWebServiceHandler = new WebServiceHandler(this);
        mParseService = new ParseService(this);
        ParseService parseService = new ParseService(this);
        //Log.d("Cattty", category);
        if(category.intern()=="Customer".intern()) {
            parseService.loadCustomerData();
        }else if(category.intern() == "Agent".intern()){
            parseService.loadAgentData();
        }else if (category.intern() == "Transaction".intern()){
            parseService.loadTransactionData();
        }else if(category.intern() == "TransactionAgentWise".intern()){

            Log.d("BKNMainFragment","Calling Parse");
            parseService.loadTransactionDataWithFilter(filters,startDate,endDate);
        }
       // mWebServiceHandler.loadUserInfoList();

        return view;
    }

    public void populatedTableViewUser(List<UserInfo> userInfoList) {
        // create Models
        mColumnHeaderList = createColumnHeaderModelListUser();
        mCellList = loadCellModelListUser(userInfoList);
        mRowHeaderList = createRowHeaderList();

        // Set all items to the TableView
        mTableAdapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);
    }

    public void populatedTableViewAgent(List<AgentInfo> userInfoList) {
        // create Models
        mColumnHeaderList = createColumnHeaderModelListAgent();
        mCellList = loadCellModelListAgent(userInfoList);
        mRowHeaderList = createRowHeaderList();

        // Set all items to the TableView
        mTableAdapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);
    }

    public void populatedTableViewTransaction(List<Customer> userInfoList) {
        // create Models
        mColumnHeaderList = createColumnHeaderModelListTransaction();
        mCellList = loadCellModelListTransaction(userInfoList);
        mRowHeaderList = createRowHeaderList();

        // Set all items to the TableView
        mTableAdapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);
    }



    private List<ColumnHeaderModel> createColumnHeaderModelListUser() {
        List<ColumnHeaderModel> list = new ArrayList<>();

        // Create Column Headers
        list.add(new ColumnHeaderModel("Name"));
        list.add(new ColumnHeaderModel("AccountNo"));
        list.add(new ColumnHeaderModel("Mobile"));
        list.add(new ColumnHeaderModel("Amount"));
        list.add(new ColumnHeaderModel("AadharNo"));
        list.add(new ColumnHeaderModel("Address"));
        list.add(new ColumnHeaderModel("JointAccountHolder"));
        list.add(new ColumnHeaderModel("PanNo"));

        return list;
    }


    private List<ColumnHeaderModel> createColumnHeaderModelListAgent() {
        List<ColumnHeaderModel> list = new ArrayList<>();

        // Create Column Headers
        list.add(new ColumnHeaderModel("Name"));
        list.add(new ColumnHeaderModel("Email"));
        list.add(new ColumnHeaderModel("Code"));
        list.add(new ColumnHeaderModel("ContactNo"));

        return list;
    }

    private List<ColumnHeaderModel> createColumnHeaderModelListTransaction() {
        List<ColumnHeaderModel> list = new ArrayList<>();

        // Create Column Headers
        list.add(new ColumnHeaderModel("CustomerAccountNo"));
        list.add(new ColumnHeaderModel("CustomerName"));
        list.add(new ColumnHeaderModel("Amount"));
        list.add(new ColumnHeaderModel("AgentEmail"));
        list.add(new ColumnHeaderModel("CIFNo"));
        list.add(new ColumnHeaderModel("AccountType"));

        return list;
    }

    private List<ColumnHeaderModel> createColumnHeaderModelListDayWiseAgent() {
        List<ColumnHeaderModel> list = new ArrayList<>();

        // Create Column Headers
        list.add(new ColumnHeaderModel("Date"));
        list.add(new ColumnHeaderModel("AmountCollected"));
        list.add(new ColumnHeaderModel("AgentName"));
        //list.add(new ColumnHeaderModel("ContactNo"));

        return list;
    }

    private List<ColumnHeaderModel> createColumnHeaderModelListDayWiseCustomer() {
        List<ColumnHeaderModel> list = new ArrayList<>();

        // Create Column Headers
        list.add(new ColumnHeaderModel("Date"));
        list.add(new ColumnHeaderModel("AmountDeposited"));
        list.add(new ColumnHeaderModel("CustomerName"));
        list.add(new ColumnHeaderModel("AccountType"));

        return list;
    }

    private List<List<CellModel>> loadCellModelListUser(List<UserInfo> userInfoList) {
        List<List<CellModel>> lists = new ArrayList<>();

        // Creating cell model list from UserInfo list for Cell Items
        // In this example, UserInfo list is populated from web service

        for (int i = 0; i < userInfoList.size(); i++) {
            UserInfo userInfo = userInfoList.get(i);

            List<CellModel> list = new ArrayList<>();



            // The order should be same with column header list;
            list.add(new CellModel("1-" + i, userInfo.getName()));       //
            list.add(new CellModel("2-" + i, userInfo.getmAccountNo()));     //
            list.add(new CellModel("3-" + i, userInfo.getMobile())); //
            list.add(new CellModel("4-" + i, userInfo.getmAmount()));    //
            list.add(new CellModel("5-" + i, userInfo.getmAadharCardNo())); //
            list.add(new CellModel("6-" + i, userInfo.getAddress()));   //
            list.add(new CellModel("7-" + i, userInfo.getmJointAccountHolder()));      //
            list.add(new CellModel("8-" + i, userInfo.getmPanNo()));      //

            // Add
            lists.add(list);
        }

        return lists;
    }

    private List<List<CellModel>> loadCellModelListAgent(List<AgentInfo> userInfoList) {
        List<List<CellModel>> lists = new ArrayList<>();

        // Creating cell model list from UserInfo list for Cell Items
        // In this example, UserInfo list is populated from web service

        for (int i = 0; i < userInfoList.size(); i++) {
            AgentInfo userInfo = userInfoList.get(i);

            List<CellModel> list = new ArrayList<>();


            // The order should be same with column header list;
            list.add(new CellModel("1-" + i, userInfo.getAgentName()));       //
            list.add(new CellModel("2-" + i, userInfo.getEmail()));     //
            list.add(new CellModel("3-" + i, userInfo.getCode())); //
            list.add(new CellModel("4-" + i, userInfo.getPhone()));    //

            // Add
            lists.add(list);
        }

        return lists;
    }

    private List<List<CellModel>> loadCellModelListTransaction(List<Customer> userInfoList) {
        List<List<CellModel>> lists = new ArrayList<>();

        // Creating cell model list from UserInfo list for Cell Items
        // In this example, UserInfo list is populated from web service

        for (int i = 0; i < userInfoList.size(); i++) {
            Customer userInfo = userInfoList.get(i);

            List<CellModel> list = new ArrayList<>();

            /*
            *
        list.add(new ColumnHeaderModel("CustomerAccountNo"));
        list.add(new ColumnHeaderModel("CustomerName"));
        list.add(new ColumnHeaderModel("Amount"));
        list.add(new ColumnHeaderModel("AgentEmail"));
        list.add(new ColumnHeaderModel("CIFNo"));
        list.add(new ColumnHeaderModel("AccountType"));*/

            // The order should be same with column header list;
            list.add(new CellModel("1-" + i, userInfo.getAccount()));       //
            list.add(new CellModel("2-" + i, userInfo.getName()));     //
            list.add(new CellModel("3-" + i, userInfo.getmAmount())); //
            list.add(new CellModel("4-" + i, userInfo.getAgentCode()));    //
            list.add(new CellModel("5-" + i, userInfo.getCifno())); //
            list.add(new CellModel("6-" + i, userInfo.getAccountType()));   //

            // Add
            lists.add(list);
        }

        return lists;
    }

    private List<RowHeaderModel> createRowHeaderList() {
        List<RowHeaderModel> list = new ArrayList<>();
        for (int i = 0; i < mCellList.size(); i++) {
            // In this example, Row headers just shows the index of the TableView List.
            list.add(new RowHeaderModel(String.valueOf(i + 1)));
        }
        return list;
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage("Get data, please wait...");
            mProgressDialog.setCancelable(false);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {

        if ((mProgressDialog != null) && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
        mProgressDialog = null;
    }
}
