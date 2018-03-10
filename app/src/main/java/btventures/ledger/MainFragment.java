package btventures.ledger;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evrencoskun.tableview.TableView;

import btventures.ledger.json.ParseService;
import btventures.ledger.json.UserInfo;
import btventures.ledger.json.WebServiceHandler;
import btventures.ledger.tableview.MyTableAdapter;
import btventures.ledger.tableview.MyTableViewListener;
import btventures.ledger.tableview.model.CellModel;
import btventures.ledger.tableview.model.ColumnHeaderModel;
import btventures.ledger.tableview.model.RowHeaderModel;

import java.util.ArrayList;
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


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        parseService.loadCustomerData();
       // mWebServiceHandler.loadUserInfoList();

        return view;
    }

    public void populatedTableView(List<UserInfo> userInfoList) {
        // create Models
        mColumnHeaderList = createColumnHeaderModelList();
        mCellList = loadCellModelList(userInfoList);
        mRowHeaderList = createRowHeaderList();

        // Set all items to the TableView
        mTableAdapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);
    }


    private List<ColumnHeaderModel> createColumnHeaderModelList() {
        List<ColumnHeaderModel> list = new ArrayList<>();

        // Create Column Headers
        list.add(new ColumnHeaderModel("AccountNo."));
        list.add(new ColumnHeaderModel("Name"));
        list.add(new ColumnHeaderModel("Amount"));
        list.add(new ColumnHeaderModel("OpeningDate"));
        list.add(new ColumnHeaderModel("Address"));
        list.add(new ColumnHeaderModel("Agent Code"));
        list.add(new ColumnHeaderModel("CifNo"));
        list.add(new ColumnHeaderModel("PhoneNumber"));
        list.add(new ColumnHeaderModel("AadharNo"));
        list.add(new ColumnHeaderModel("PanNo"));
        list.add(new ColumnHeaderModel("SecondCif"));
        list.add(new ColumnHeaderModel("Nomination"));

        return list;
    }

    private List<List<CellModel>> loadCellModelList(List<UserInfo> userInfoList) {
        List<List<CellModel>> lists = new ArrayList<>();

        // Creating cell model list from UserInfo list for Cell Items
        // In this example, UserInfo list is populated from web service

        for (int i = 0; i < userInfoList.size(); i++) {
            UserInfo userInfo = userInfoList.get(i);

            List<CellModel> list = new ArrayList<>();

            // The order should be same with column header list;
            list.add(new CellModel("1-" + i, userInfo.getmAccountNo()));       // "Account No"
            list.add(new CellModel("2-" + i, userInfo.getName()));     // "Name"
            list.add(new CellModel("3-" + i, userInfo.getmAmount())); // "Amount"
            list.add(new CellModel("4-" + i, userInfo.getmOpeningDate()));    // "Opening Date"
            list.add(new CellModel("5-" + i, userInfo.getAddress())); // "Address"
            list.add(new CellModel("6-" + i, userInfo.getmAgentCode()));   // "Agent Code"
            list.add(new CellModel("7-" + i, userInfo.getmCifNo()));      // "Cif No"
            list.add(new CellModel("8-" + i, userInfo.getMobile()));      // "phone"
            list.add(new CellModel("9-" + i, userInfo.getmAadharCardNo()));   // "aadhar"
            list.add(new CellModel("10-" + i, userInfo.getmPanNo()));// "pan"
            list.add(new CellModel("11-" + i, userInfo.getmScecondCIF()));// "secondcif"
            list.add(new CellModel("12-" + i, userInfo.getmNomination()));  // "nomination"

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
