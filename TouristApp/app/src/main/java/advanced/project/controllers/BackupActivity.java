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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

import advanced.project.DataModels.Booking;
import advanced.project.DataModels.Customer;
import advanced.project.DataModels.Destination;
import advanced.project.DataModels.Flight;
import advanced.project.SQLiteDB.TouristDAO;
import advanced.project.services.ConnectionManager;
import edu.birzeit.lab.advanced.touristapp.MainActivity;
import edu.birzeit.lab.advanced.touristapp.R;

/**
 * Created by Bassam on 4/13/2015.
 */
public class BackupActivity extends ActionBarActivity implements ConnectionManager.ConnectionManagerCallback {

    Context cont;
    ConnectionManager connectionManager;
    public String baseAPIAddress = "http://";
    public String serverIPAdress;
    public String getDestinationService = ":8080/FirstApp/getAllDestinations";
    public String getFlightService = ":8080/FirstApp/getAllFlights";
    public String getCustomerService = ":8080/FirstApp/getAllCustomers";
    public String getBookingService = ":8080/FirstApp/getAllBooking";
    public String postDestinationService = ":8080/FirstApp/addDestinationList";
    public String postFlightService = ":8080/FirstApp/addFlightList";
    public String postCustomerService = ":8080/FirstApp/addCustomerList";
    public String postBookingService = ":8080/FirstApp/addBookingList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rest_backup_view);

        cont = this;

        connectionManager = new ConnectionManager();
        connectionManager.registerCallback(this);

        final EditText ipAddress = (EditText) findViewById(R.id.ipAddress);

        Button clear = (Button) findViewById(R.id.clearData);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this function clear Database content
                showOKDialog("Confirmation Message", "Are you sure that you want to clear Database ", cont);
            }
        });

        Button backup = (Button) findViewById(R.id.backupServer);
        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverIPAdress = ipAddress.getText().toString();
                //create Database or initialize it
                TouristDAO DB = new TouristDAO(cont);
                LinkedList<Destination> destination = DB.getDestinationList();
                ArrayList<Destination> destinationList = new ArrayList<Destination>();
                for (Destination dest : destination) {
                    destinationList.add(dest);
                }

                connectionManager.perfromHTTPPostRequestOnUrlWithParamsDestination(
                        baseAPIAddress + serverIPAdress + postDestinationService,
                        destinationList
                );

                LinkedList<Flight> flights= DB.getFlightList();
                ArrayList<Flight>flightList= new ArrayList<Flight>();
                for(Flight flight: flights){
                    flightList.add(flight);
                    Log.i("Debug", "Flights " + flight.toString());
                }

                connectionManager.perfromHTTPPostRequestOnUrlWithParamsFlight(
                        baseAPIAddress+serverIPAdress+postFlightService,
                        flightList
                );

                LinkedList<Customer> customers = DB.getCustomerList();
                ArrayList<Customer>customerList= new ArrayList<Customer>();
                for(Customer customer:customers){
                    customerList.add(customer);
                }

                connectionManager.perfromHTTPPostRequestOnUrlWithParamsCustomer(
                        baseAPIAddress+serverIPAdress+postCustomerService,
                        customerList
                );

                LinkedList<Booking> bookings =DB.getBookingList();
                ArrayList<Booking> bookingList= new ArrayList<Booking>();
                for(Booking booking:bookings){
                    bookingList.add(booking);
                }

                connectionManager.perfromHTTPPostRequestOnUrlWithParamsBooking(
                        baseAPIAddress+serverIPAdress+postBookingService,
                        bookingList
                );

                new AlertDialog.Builder(cont).setTitle("Operation Finished").setMessage("Database content uploaded to server correctly")
                        .setNeutralButton("Ok", null).show();
            }
        });

        Button load = (Button) findViewById(R.id.loadBackup);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverIPAdress = ipAddress.getText().toString();
                connectionManager.perfromHTTPGetRequestDestination(
                        baseAPIAddress+serverIPAdress+getDestinationService,
                        null
                );

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
                                Exception e = null;
                                dbDAO.clearDatabaseData();
                                if (e == null) {
                                    textToast("Database cleared successfully", cont);
                                } else {
                                    new AlertDialog.Builder(cont).setTitle("Error !").setMessage(e.toString())
                                            .setNeutralButton("Ok", null).show();
                                }
                            }
                        }
        );
        alertDialog.setButton2("No", new
                        DialogInterface.OnClickListener()

                        {
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        }

        );
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        //Start CustomerMain Activity
        Intent myIntent = new Intent(BackupActivity.this, MainActivity.class);
        BackupActivity.this.startActivity(myIntent);
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

    @Override
    public void getRequestSucceededWithArrayOfCustomers(ArrayList<Customer> list) {
        //a list of Destination is obtained from server , Add it to database
        TouristDAO dbDAO = new TouristDAO(cont);
        Exception e;
        for(Customer customer: list){
            e=dbDAO.loadCustomer(customer);
            if( e != null){
                Log.i("BackUpActivity", "Customer Failure "+e.getMessage().toString() );
            }
        }
        new AlertDialog.Builder(cont).setTitle("Operation Finished").setMessage("Customer Table Loaded from server Correctly !")
                .setNeutralButton("Ok", null).show();
        connectionManager.perfromHTTPGetRequestBooking(
                baseAPIAddress+serverIPAdress+getBookingService,
                null
        );
//        new AlertDialog.Builder(cont).setTitle("Operation Finished").setMessage("Database content requested from server correctly")
//                .setNeutralButton("Ok", null).show();
    }

    @Override
    public void getRequestSucceededWithArrayOfDestination(ArrayList<Destination> list) {
         //a list of Destination is obtained from server , Add it to database
        TouristDAO dbDAO = new TouristDAO(cont);
        Exception e;
        for(Destination destination: list){
            e=dbDAO.loadDestination(destination);
            if( e != null){
                Log.i("BackUpActivity", "Destination Failure "+e.getMessage().toString() );
            }
        }
        new AlertDialog.Builder(cont).setTitle("Operation Finished").setMessage("Destination Table Loaded from server Correctly !")
                .setNeutralButton("Ok", null).show();

        connectionManager.perfromHTTPGetRequestFlight(
                baseAPIAddress+serverIPAdress+getFlightService,
                null
        );
    }

    @Override
    public void getRequestSucceededWithArrayOfFlight(ArrayList<Flight> list) {
        //a list of Destination is obtained from server , Add it to database
        TouristDAO dbDAO = new TouristDAO(cont);
        Exception e;
        for(Flight flight: list){
            e=dbDAO.loadFlight(flight);
            if( e != null){
                Log.i("BackUpActivity", "Flight Failure "+e.getMessage().toString() );
            }
        }
        new AlertDialog.Builder(cont).setTitle("Operation Finished").setMessage("Flight Table Loaded from server Correctly !")
                .setNeutralButton("Ok", null).show();

        connectionManager.perfromHTTPGetRequestCustomers(
                baseAPIAddress+serverIPAdress+getCustomerService,
                null
        );
    }

    @Override
    public void getRequestSucceededWithArrayOfBooking(ArrayList<Booking> list) {
        //a list of Destination is obtained from server , Add it to database
        TouristDAO dbDAO = new TouristDAO(cont);
        Exception e;
        for(Booking booking: list){
            e=dbDAO.loadBookFlight(booking);
            if( e != null){
                Log.i("BackUpActivity", "Booking Failure "+e.getMessage().toString() );
            }
        }
        new AlertDialog.Builder(cont).setTitle("Operation Finished").setMessage("Booking Table Loaded from server Correctly !")
                .setNeutralButton("Ok", null).show();
    }


    @Override
    public void postRequestSucceededCustomer() {
        Log.i("BackupActivity", "success , posting customer list " );
    }

    @Override
    public void postRequestSucceededFlight() {
        Log.i("BackupActivity", "success , posting flight list  " );
    }

    @Override
    public void postRequestSucceededDestination() {
        Log.i("BackupActivity", "success , posting destination list " );
    }

    @Override
    public void postRequestSucceededBooking() {
        Log.i("BackupActivity", "success , posting booking list " );
    }
}
