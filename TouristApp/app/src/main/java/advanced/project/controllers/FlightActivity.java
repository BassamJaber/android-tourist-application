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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import advanced.project.DataModels.Destination;
import advanced.project.DataModels.Flight;
import advanced.project.SQLiteDB.TouristDAO;
import advanced.project.listview.LazyAdapter;
import edu.birzeit.lab.advanced.touristapp.MainActivity;
import edu.birzeit.lab.advanced.touristapp.R;

/**
 * Created by BassamJ on 4/8/2015.
 */
public class FlightActivity extends ActionBarActivity {

    static final String KEY_ID = "id";
    static final String KEY_TITLE = "title";
    static final String KEY_ARTIST = "artist";

    ListView list;
    LazyAdapter adapter;
    private Context cont;
    private LinkedList<Flight> flights;
    private Flight filterFlight=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);

        cont = this;
        filterFlight = (Flight) getIntent().getSerializableExtra("flight");
        try {
            TouristDAO dbDAO = new TouristDAO(cont);
            if(filterFlight!=null){
                flights = dbDAO.searchFlight(filterFlight);
            }else {
                flights = dbDAO.getFlightList();
            }
        } catch (Exception e) {
            new AlertDialog.Builder(cont).setTitle("Error !").setMessage(e.getMessage().toString())
                    .setNeutralButton("Ok", null).show();
        }

        if (flights == null || flights.size() == 0) {
            new AlertDialog.Builder(cont).setTitle("Error !").setMessage("Data base is Empty")
                    .setNeutralButton("Ok", null).show();
        }
        //data is obtained using hash map , get the data from db and add it to list
        final ArrayList<HashMap<String, String>> flightList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < flights.size(); i++) {
//            Log.i("MyActivity", "Destinations " + dest.get(i).toString());
            HashMap<String, String> map = new HashMap<String, String>();
            // adding each child node to HashMap key => value
            flights.get(i).getDbId();
            map.put(KEY_ID, flights.get(i).getDbId() + "");
            map.put(KEY_TITLE, flights.get(i).getCompanyName());
            map.put(KEY_ARTIST, "Cost : "+flights.get(i).getCost() + "");
            // adding HashList to ArrayList
            flightList.add(map);
        }


        list=(ListView)findViewById(R.id.list);

        Button addItem = (Button)findViewById(R.id.addNewItem);
        addItem.setText("Add new Flight");
        // Getting adapter by passing xml data ArrayList
        adapter=new LazyAdapter(this,flightList);
        list.setAdapter(adapter);

        // Click event for single list row
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                showFlightDialog(flights.get(position));

            }
        });

        ImageView img= (ImageView) findViewById(R.id.image_view);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(FlightActivity.this,FlightSearchFilter.class);
                FlightActivity.this.startActivity(myIntent);
                finish();
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(FlightActivity.this, AddFlightActivity.class);
                FlightActivity.this.startActivity(myIntent);
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
        Intent myIntent=new Intent(FlightActivity.this, MainActivity.class);
        FlightActivity.this.startActivity(myIntent);
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

    private void showFlightDialog(final Flight flight) {
        final String [] items = new String [] {"View Flight Information" ,"view Destination Profile", "Delete flight" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.select_dialog_item,items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final TouristDAO dbDAO = new TouristDAO(cont);

        builder.setTitle("Flight options");
        builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
            public void onClick( DialogInterface dialog, int item ) {
                Intent myIntent;
                switch(item){
                    case 0:
                        myIntent = new Intent(FlightActivity.this, ViewFlightProfile.class);
                        myIntent.putExtra("viewNum", 0);
                        myIntent.putExtra("flight", flight);
                        FlightActivity.this.startActivity(myIntent);
                        finish();
                        break;
                    case 1:
                        Destination dest = dbDAO.getSingleDestination(flight.getDestDbId());
                        if (dest != null) {
                             myIntent = new Intent(FlightActivity.this, ViewDestinationProfile.class);
                            myIntent.putExtra("destination", dest);
                            myIntent.putExtra("viewNum", 2);
                            FlightActivity.this.startActivity(myIntent);
                            finish();
                        } else {
                            new AlertDialog.Builder(cont).setTitle("Error !").setMessage("Can't View Destination")
                                    .setNeutralButton("Ok", null).show();
                        }
                        break;
                    default:

                        Exception e = dbDAO.deleteFlight(flight.getDbId());
                        if (e == null) {
                            textToast("flight has been deleted successfully", cont);
                            //refresh Activity after delete
                            finish();
                            startActivity(getIntent());
                        } else {
                            new AlertDialog.Builder(cont).setTitle("Error !").setMessage(e.getMessage().toString())
                                    .setNeutralButton("Ok", null).show();
                        }
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
