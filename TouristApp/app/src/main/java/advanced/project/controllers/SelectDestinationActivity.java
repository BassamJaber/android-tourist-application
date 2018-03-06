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

import advanced.project.DataModels.Customer;
import advanced.project.DataModels.Destination;
import advanced.project.SQLiteDB.TouristDAO;
import advanced.project.listview.LazyAdapter;
import edu.birzeit.lab.advanced.touristapp.MainActivity;
import edu.birzeit.lab.advanced.touristapp.R;

/**
 * Created by BassamJ on 4/12/2015.
 */
public class SelectDestinationActivity extends ActionBarActivity{

    static final String KEY_SONG = "song"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_TITLE = "title";
    static final String KEY_ARTIST = "artist";
    static final String KEY_THUMB_URL = "thumb_url";

    ListView list;
    LazyAdapter adapter;
    private Context cont;
    private LinkedList<Destination> dest;
    private static int viewMode=0;
    private static int custId=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);
        cont = this;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            viewMode = extras.getInt("viewNum");
            custId=extras.getInt("customerId");
        }

        try {
            TouristDAO dbDAO = new TouristDAO(cont);
            dest = dbDAO.getDestinationList();
        } catch (Exception e) {
            new AlertDialog.Builder(cont).setTitle("Error !").setMessage(e.getMessage().toString())
                    .setNeutralButton("Ok", null).show();
        }

        if (dest.size() == 0) {
            new AlertDialog.Builder(cont).setTitle("Error !").setMessage("Data base is Empty")
                    .setNeutralButton("Ok", null).show();
        }
        //data is obtained using hash map , get the data from db and add it to list
        ArrayList<HashMap<String, String>> destList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < dest.size(); i++) {
//            Log.i("MyActivity", "Destinations " + dest.get(i).toString());
            HashMap<String, String> map = new HashMap<String, String>();
            // adding each child node to HashMap key => value
            dest.get(i).getDbId();
            map.put(KEY_ID, dest.get(i).getDbId() + "");
            map.put(KEY_TITLE, dest.get(i).getName());
            map.put(KEY_ARTIST, dest.get(i).getCountry());
            map.put(KEY_THUMB_URL, dest.get(i).getPhotoPath());
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
                showDestinationDialog(dest.get(position));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        //Start CustomerMain Activity
        Intent myIntent;
        switch(viewMode){
            case 0:
                 myIntent = new Intent(SelectDestinationActivity.this, AddFlightActivity.class);
                SelectDestinationActivity.this.startActivity(myIntent);
                break;
            default:
                myIntent = new Intent(SelectDestinationActivity.this, CustomerActivity.class);
                SelectDestinationActivity.this.startActivity(myIntent);
                break;
        }

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

    private void showDestinationDialog(final Destination desObj) {

        final String[] items = new String[]{"Select Destination"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Destination options");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Intent myIntent;
                switch (item) {
                    case 0:
                        switch(viewMode){
                            case 0:
                                myIntent = new Intent(SelectDestinationActivity.this, AddFlightActivity.class);
                                myIntent.putExtra("destination", desObj);
                                SelectDestinationActivity.this.startActivity(myIntent);
                                textToast("Destination have been Selected successfully",cont);
                                finish();
                                break;
                            case 1:
                                myIntent = new Intent(SelectDestinationActivity.this, ListDestinationFlightsActivity.class);
                                myIntent.putExtra("customerId", custId);
                                myIntent.putExtra("destinationId",desObj.getDbId());
                                myIntent.putExtra("viewNum",1);
                                SelectDestinationActivity.this.startActivity(myIntent);
                                textToast("Destination have been Selected successfully ",cont);
                                finish();
                                break;
                            default:
                        }

                        break;
                }
            }
        });

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
