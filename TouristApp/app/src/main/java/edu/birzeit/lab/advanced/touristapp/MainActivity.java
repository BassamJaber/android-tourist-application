package edu.birzeit.lab.advanced.touristapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import advanced.project.SQLiteDB.TouristDAO;
import advanced.project.controllers.BackupActivity;
import advanced.project.controllers.CustomerActivity;
import advanced.project.controllers.DestinationActivity;
import advanced.project.controllers.FlightActivity;
import advanced.project.listview.CustomizedListView;


public class MainActivity extends ActionBarActivity {

    Context cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cont=this;
        Button viewDestBtn = (Button) findViewById(R.id.viewDestinations);
        viewDestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //Start CustomerMain Activity
                Intent myIntent = new Intent(MainActivity.this, DestinationActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        Button viewFlightBtn = (Button) findViewById(R.id.viewFlights);
        viewFlightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //Start CustomerMain Activity
                Intent myIntent = new Intent(MainActivity.this, FlightActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        Button viewCustomerBtn = (Button) findViewById(R.id.viewCustomers);
        viewCustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //Start CustomerMain Activity
                Intent myIntent = new Intent(MainActivity.this, CustomerActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        //create Database or initialize it
        TouristDAO DB = new TouristDAO(this);
        Exception e= DB.createDB();
        if ( e!= null) {
            new AlertDialog.Builder(this).setTitle("Error !").setMessage(e.getMessage().toString())
                    .setNeutralButton("Ok", null).show();
        }


        Button backup = (Button) findViewById(R.id.backupData);
        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //Start CustomerMain Activity
                Intent myIntent = new Intent(MainActivity.this, BackupActivity.class);
                MainActivity.this.startActivity(myIntent);
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
