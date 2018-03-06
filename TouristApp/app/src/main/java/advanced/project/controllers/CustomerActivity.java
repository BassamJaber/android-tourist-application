package advanced.project.controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import advanced.project.DataModels.Customer;
import advanced.project.DataModels.Destination;
import advanced.project.DataModels.Flight;
import advanced.project.SQLiteDB.TouristDAO;
import advanced.project.listview.LazyAdapter;
import edu.birzeit.lab.advanced.touristapp.MainActivity;
import edu.birzeit.lab.advanced.touristapp.R;

/**
 * Created by BassamJ on 3/24/2015.
 */
public class CustomerActivity extends ActionBarActivity {

    static final String KEY_ID = "id";
    static final String KEY_TITLE = "title";
    static final String KEY_ARTIST = "artist";
    static final String KEY_THUMB_URL = "thumb_url";

    ListView list;
    LazyAdapter adapter;
    private Context cont;
    private LinkedList<Customer> customers ;
    private Customer filterCust=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);
        cont = this;
        filterCust = (Customer) getIntent().getSerializableExtra("customer");
        try {
            TouristDAO dbDAO = new TouristDAO(cont);
            if(filterCust!=null){
                customers = dbDAO.searchCustomer(filterCust);
            }else {
                customers = dbDAO.getCustomerList();
            }

        } catch (Exception e) {
            new AlertDialog.Builder(cont).setTitle("Error !").setMessage(e.getMessage().toString())
                    .setNeutralButton("Ok", null).show();
        }

        if (customers==null || customers.size() == 0) {
            new AlertDialog.Builder(cont).setTitle("Error !").setMessage("Data base is Empty")
                    .setNeutralButton("Ok", null).show();
            customers=new <Customer>LinkedList();
        }
        //data is obtained using hash map , get the data from db and add it to list
        ArrayList<HashMap<String, String>> custList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < customers.size(); i++) {
            Log.i("MyActivity", "Customers " + customers.get(i).toString());
            HashMap<String, String> map = new HashMap<String, String>();
            // adding each child node to HashMap key => value
            map.put(KEY_ID, customers.get(i).getDbId() + "");
            map.put(KEY_TITLE, customers.get(i).getName());
            map.put(KEY_ARTIST, customers.get(i).getAddress());
            map.put(KEY_THUMB_URL, customers.get(i).getPhotoPath());
            // adding HashList to ArrayList
            custList.add(map);
        }

        list=(ListView)findViewById(R.id.list);

        Button addItem = (Button)findViewById(R.id.addNewItem);
        addItem.setText("Add new Customer");
        // Getting adapter by passing xml data ArrayList
        adapter=new LazyAdapter(this, custList);
        list.setAdapter(adapter);


        // Click event for single list row
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                showCustomerDialog(customers.get(position));
            }
        });

        ImageView img= (ImageView) findViewById(R.id.image_view);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(CustomerActivity.this,CustomerSearchFilter.class);
                CustomerActivity.this.startActivity(myIntent);
                finish();
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(CustomerActivity.this, AddCustomerActivity.class);
                CustomerActivity.this.startActivity(myIntent);
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        //Start CustomerMain Activity
        Intent myIntent=new Intent(CustomerActivity.this, MainActivity.class);
        CustomerActivity.this.startActivity(myIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showCustomerDialog(final Customer cust) {
        final String [] items = new String [] {"View profile", "Update" , "Delete" , "Book Flight"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.select_dialog_item,items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Customer options");
        builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
            public void onClick( DialogInterface dialog, int item ) {
                Intent myIntent;
             switch(item){
                 case 0:
                     myIntent = new Intent(CustomerActivity.this, ViewCustomerProfile.class);
                     myIntent.putExtra("customer", cust);
                     CustomerActivity.this.startActivity(myIntent);
                     finish();
                     break;
                 case 1:
                     myIntent = new Intent(CustomerActivity.this, AddCustomerActivity.class);
                     myIntent.putExtra("viewNum",1);
                     myIntent.putExtra("customer", cust);
                     CustomerActivity.this.startActivity(myIntent);
                     finish();
                     break;
                 case 2:
                     TouristDAO dbDAO = new TouristDAO(cont);
                     Exception e = dbDAO.deleteCustomer(cust.getDbId());
                     if (e == null) {
                         textToast("customer has been deleted successfully", cont);
                         //refresh Activity after delete
                         finish();
                         startActivity(getIntent());
                     } else {
                         new AlertDialog.Builder(cont).setTitle("Error !").setMessage(e.getMessage().toString())
                                 .setNeutralButton("Ok", null).show();
                     }
                     break;
                 default:
                     myIntent = new Intent(CustomerActivity.this, SelectDestinationActivity.class);
                     myIntent.putExtra("viewNum",1);
                     myIntent.putExtra("customerId", cust.getDbId());
                     CustomerActivity.this.startActivity(myIntent);
                     finish();
                     break;
             }
            }
        } );

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void textToast(String textToDisplay, Context con) {
        Context context = con;
        CharSequence text = textToDisplay;

        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.CENTER, 50, 50);
        toast.show();
    }

}
