package advanced.project.controllers;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import advanced.project.DataModels.Destination;
import advanced.project.SQLiteDB.TouristDAO;
import edu.birzeit.lab.advanced.touristapp.R;

/**
 * Created by Bassam on 4/13/2015.
 */
public class DestinationSearchFilter extends ActionBarActivity {

    private static Destination dest = null;
    private Context cont;
    private static String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.destination_search_filter);
        cont = this;

        //when returning from google maps activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
//            Double longitude = extras.getDouble("longitude");
//            Double latitude = extras.getDouble("latitude");
//            final EditText destLong = (EditText) findViewById(R.id.LongText);
//            final EditText destLat = (EditText) findViewById(R.id.LatText);
//            destLong.setText(longitude + "");
//            destLat.setText(latitude + "");
        }

        final EditText destName = (EditText) findViewById(R.id.nameText);
        final EditText destCountry = (EditText) findViewById(R.id.countryText);

        Button search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dest = new Destination();
                boolean isEmpty=true;
                String name = destName.getText().toString();
                if (!name.equals("")) {
                    isEmpty=false;
                    dest.setName(name);
                }

                String country = destCountry.getText().toString();
                if (!country.equals("")) {
                    isEmpty=false;
                    dest.setCountry(country);
                }

                if(isEmpty){
                    new AlertDialog.Builder(cont).setTitle("Error !").setMessage("No Field is Selected , Please Fill Some info")
                            .setNeutralButton("Ok", null).show();
                }else{
                    showOKDialog("Confirmation Message", "Did you fill all required information ? ", cont);
                }
            }
        });


    }

    private void showOKDialog(String title, String msg, Context con) {
        final AlertDialog alertDialog = new AlertDialog.Builder(con).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setButton("Yes", new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        TouristDAO dbDAO = new TouristDAO(cont);
                        try {
//                            dbDAO.addDestination(dest);
//                            textToast("Destination has been added successfully", cont);
                            //we will send query back to destination in  order to request search filter result
                            finish();
                            //Start CustomerMain Activity
                            Intent myIntent = new Intent(DestinationSearchFilter.this, DestinationActivity.class);
                            myIntent.putExtra("destination", dest);
                            DestinationSearchFilter.this.startActivity(myIntent);
                            dest = null;
                        } catch (Exception e) {
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

    Uri mCapturedImageURI;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        //Start CustomerMain Activity
        Intent myIntent = new Intent(DestinationSearchFilter.this, DestinationActivity.class);
        DestinationSearchFilter.this.startActivity(myIntent);
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
}