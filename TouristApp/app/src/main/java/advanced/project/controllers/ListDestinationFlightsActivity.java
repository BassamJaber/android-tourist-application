package advanced.project.controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import advanced.project.DataModels.Destination;
import advanced.project.DataModels.Flight;
import advanced.project.SQLiteDB.TouristDAO;
import advanced.project.listview.LazyAdapter;
import edu.birzeit.lab.advanced.touristapp.R;

/**
 * Created by Bassam on 4/12/2015.
 */
public class ListDestinationFlightsActivity extends ActionBarActivity {

    static final String KEY_ID = "id";
    static final String KEY_TITLE = "title";
    static final String KEY_ARTIST = "artist";

    ListView list;
    LazyAdapter adapter;
    private Context cont;
    private LinkedList<Flight> flights;
    private static int destinationId;
    private static int custId;
    private static int viewMode=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);
        cont = this;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            destinationId = extras.getInt("destinationId");
            custId=extras.getInt("customerId");
            viewMode=extras.getInt("viewNum");
        }

        try {
            TouristDAO dbDAO = new TouristDAO(cont);
            flights = dbDAO.getDestinationFlightsList(destinationId);
        } catch (Exception e) {
            new AlertDialog.Builder(cont).setTitle("Error !").setMessage(e.getMessage().toString())
                    .setNeutralButton("Ok", null).show();
        }

        if (flights.size() == 0) {
            new AlertDialog.Builder(cont).setTitle("Error !").setMessage("Data base is Empty")
                    .setNeutralButton("Ok", null).show();
        }
        //data is obtained using hash map , get the data from db and add it to list
        ArrayList<HashMap<String, String>> destList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < flights.size(); i++) {
//            Log.i("MyActivity", "Destinations " + dest.get(i).toString());
            HashMap<String, String> map = new HashMap<String, String>();
            // adding each child node to HashMap key => value
            map.put(KEY_ID, flights.get(i).getDbId() + "");
            map.put(KEY_TITLE, flights.get(i).getCompanyName());
            map.put(KEY_ARTIST, flights.get(i).getCost() + "");
            // adding HashList to ArrayList
            destList.add(map);
        }

        list = (ListView) findViewById(R.id.list);
        // Getting adapter by passing xml data ArrayList
        adapter = new LazyAdapter(this, destList);
        list.setAdapter(adapter);

        Button addItem = (Button) findViewById(R.id.addNewItem);
        addItem.setVisibility(View.GONE);
        ImageView img= (ImageView) findViewById(R.id.image_view);
        img.setVisibility(View.GONE);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        list.setLayoutParams(lp);

        // Click event for single list row
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch(viewMode){
                    case 0:
                        break;
                    case 1:showDestinationDialog(flights.get(position));
                        break;
                    default:
                        break;
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent myIntent;
        switch(viewMode){
            case 0:myIntent = new Intent(ListDestinationFlightsActivity.this, DestinationActivity.class);
                ListDestinationFlightsActivity.this.startActivity(myIntent);
                break;
            case 1:myIntent = new Intent(ListDestinationFlightsActivity.this, SelectDestinationActivity.class);
                ListDestinationFlightsActivity.this.startActivity(myIntent);
                break;
            default:
                break;
        }
        //Start CustomerMain Activity
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    private void showDestinationDialog(final Flight flightObj) {

        final String[] items = new String[]{"Select Flight"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Flight options");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Intent myIntent;
                switch (item) {
                    case 0:
                        showOKDialog("Confirmation Message" , "Are you sure that you want to Book this flight " , cont,flightObj);
//                        myIntent = new Intent(SelectDestinationActivity.this, AddFlightActivity.class);
//                        myIntent.putExtra("destination", desObj);
//                        SelectDestinationActivity.this.startActivity(myIntent);
//                        textToast("Destination have been Selected successfully",cont);
//                        finish();
                        break;
                }
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showOKDialog(String title , String msg , Context con,final Flight flight)
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(con).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setButton("Yes", new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        TouristDAO dbDAO= new TouristDAO(cont);
                        try {
                            Exception e= dbDAO.bookFlight(custId,flight.getDbId());
                            if(e == null) {
                                textToast("Flight Has been booked successfully", cont);
                                finish();
                                //Start CustomerMain Activity
                                Intent myIntent = new Intent(ListDestinationFlightsActivity.this, CustomerActivity.class);
                                ListDestinationFlightsActivity.this.startActivity(myIntent);
//                                dest = null;
                            }else{
                                new AlertDialog.Builder(cont).setTitle("Error !").setMessage(e.getMessage())
                                        .setNeutralButton("Ok", null).show();
                            }

                        }catch(Exception e){
                            new AlertDialog.Builder(cont).setTitle("Error !").setMessage(e.getMessage())
                                    .setNeutralButton("Ok", null).show();
                        }
                    }
                });
        alertDialog.setButton2("No", new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
        alertDialog.show();
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