package advanced.project.controllers;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import advanced.project.DataModels.Destination;
import advanced.project.DataModels.Flight;
import advanced.project.SQLiteDB.TouristDAO;
import edu.birzeit.lab.advanced.touristapp.R;

/**
 * Created by BassamJ on 4/8/2015.
 */
public class AddFlightActivity extends ActionBarActivity {

    private Context cont;
    private int dYear;
    private int dMonth;
    private int dDay;

    private int aYear;
    private int aMonth;
    private int aDay;

    private TextView dDateDisplay;
    private TextView aDateDisplay;

    static final int DATE_DIALOG_ID_A = 0;
    static final int DATE_DIALOG_ID_D = 1;

    private static Destination dest =null;
    private static Flight flight=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_flight_activity);
        cont = this;

        dDateDisplay = (TextView) findViewById(R.id.depDateText);
        aDateDisplay = (TextView) findViewById(R.id.arriDateText);

        dDateDisplay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID_D);
            }
        });

        aDateDisplay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID_A);
            }
        });

        // get the current date
        final Calendar c = Calendar.getInstance();
        dYear = c.get(Calendar.YEAR);
        dMonth = c.get(Calendar.MONTH);
        dDay = c.get(Calendar.DAY_OF_MONTH);

        aYear = c.get(Calendar.YEAR);
        aMonth = c.get(Calendar.MONTH);
        aDay = c.get(Calendar.DAY_OF_MONTH);
        // display the current date
        updateDisplay();

        final EditText companyName=(EditText)findViewById(R.id.companyText);
        final EditText cost=(EditText)findViewById(R.id.costText);
        final EditText depDate=(EditText)findViewById(R.id.depDateText);
        final EditText arriDate=(EditText)findViewById(R.id.arriDateText);
        final EditText destinationName=(EditText)findViewById(R.id.destinationNameText);
        Button addFlight = (Button)findViewById(R.id.addNewFlight);
        Button selectDest = (Button)findViewById(R.id.selectDestination);
        Button viewDestProfile = (Button)findViewById(R.id.viewDestProfile);

        selectDest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //save current selection of items into flight object
                flight=new Flight();
                flight.setCompanyName(companyName.getText().toString());
                flight.setCost(Integer.parseInt(cost.getText().toString()));
                flight.setArriDate(arriDate.getText().toString());
                flight.setDepDate(depDate.getText().toString());
                finish();
                Intent myIntent = new Intent(AddFlightActivity.this, SelectDestinationActivity.class);
                AddFlightActivity.this.startActivity(myIntent);
            }
        });

        viewDestProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(dest!=null) {
                    flight=new Flight();
                    //save current selection of items into flight object
                    flight.setCompanyName(companyName.getText().toString());
                    flight.setCost(Integer.parseInt(cost.getText().toString()));
                    flight.setArriDate(arriDate.getText().toString());
                    flight.setDepDate(depDate.getText().toString());
                    flight.setDestination(dest);
                    Intent myIntent = new Intent(AddFlightActivity.this, ViewDestinationProfile.class);
                    myIntent.putExtra("destination", dest);
                    myIntent.putExtra("viewNum", 1);
                    AddFlightActivity.this.startActivity(myIntent);
                    finish();
                }else{
                    new AlertDialog.Builder(cont).setTitle("Alert !").setMessage("No Destination is Selected !")
                            .setNeutralButton("Ok", null).show();
                }
            }
        });

        dest = (Destination)getIntent().getSerializableExtra("destination");
        if(dest != null ) {
            companyName.setText(flight.getCompanyName());
            cost.setText(flight.getCost()+"");
            depDate.setText(flight.getDepDate());
            arriDate.setText(flight.getArriDate());
            flight.setDestination(dest);
            destinationName.setText("Name : "+dest.getName());
        }

        if(flight!=null){
            companyName.setText(flight.getCompanyName());
            cost.setText(flight.getCost()+"");
            depDate.setText(flight.getDepDate());
            arriDate.setText(flight.getArriDate());
            destinationName.setText("Name : "+flight.getDestination().getName());
        }

        addFlight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name=companyName.getText().toString();
                if(name.equals("")){
                    new AlertDialog.Builder(cont).setTitle("Alert !").setMessage("Company name Field is empty")
                            .setNeutralButton("Ok", null).show();
                    return;
                }else{
                    flight.setCompanyName(name);
                }

                String costS= cost.getText().toString();
                if(costS.equals("")){
                    new AlertDialog.Builder(cont).setTitle("Alert !").setMessage("Cost Field is empty")
                            .setNeutralButton("Ok", null).show();
                    return;
                }else{
                    flight.setCost(Integer.parseInt(costS));
                }

                String depDateS=depDate.getText().toString();
                if(depDateS.equals("")){
                    new AlertDialog.Builder(cont).setTitle("Alert !").setMessage("Departure date is empty")
                            .setNeutralButton("Ok", null).show();
                    return;
                }else{
                    flight.setDepDate(depDateS);
                }

                String arriDateS=arriDate.getText().toString();
                if(arriDateS.equals("")){
                    new AlertDialog.Builder(cont).setTitle("Alert !").setMessage("Arrival date is empty")
                            .setNeutralButton("Ok", null).show();
                    return;
                }else{
                    flight.setArriDate(arriDateS);
                }

                //now check if destination to this flight is available or not
                // two variables can contain destination (destination variable or Flight)
                if(dest == null && flight.getDestination()== null){
                    new AlertDialog.Builder(cont).setTitle("Alert !").setMessage("No destination is selected ")
                            .setNeutralButton("Ok", null).show();
                    return;
                }else{
                    if(dest != null){
                        flight.setDestination(dest);
                        flight.setDestDbId(dest.getDbId());
                    }
                }
                showOKDialog("Confirmation Message" , "Are you sure that you want to add this Flight " , cont);

            }
        });


    }

    private void updateDisplay() {
        this.dDateDisplay.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(dMonth + 1).append("-")
                        .append(dDay).append("-")
                        .append(dYear).append(" "));

        this.aDateDisplay.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(aMonth + 1).append("-")
                        .append(aDay).append("-")
                        .append(aYear).append(" "));


    }

    private DatePickerDialog.OnDateSetListener dDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    dYear = year;
                    dMonth = monthOfYear;
                    dDay = dayOfMonth;
                    updateDisplay();
                }
            };
    private DatePickerDialog.OnDateSetListener aDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    aYear = year;
                    aMonth = monthOfYear;
                    aDay = dayOfMonth;
                    updateDisplay();
                }
            };
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID_D:
                return new DatePickerDialog(this,
                        dDateSetListener,
                        dYear, dMonth, dDay);
            case DATE_DIALOG_ID_A:
                return new DatePickerDialog(this,
                        aDateSetListener,
                        aYear, aMonth, aDay);
        }
        return null;
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
                            dbDAO.addFlight(flight);
                            textToast("Flight has been added successfully" , cont);
                            finish();
                            Intent myIntent=new Intent(AddFlightActivity.this, FlightActivity.class);
                            AddFlightActivity.this.startActivity(myIntent);
                            flight=null;
                            dest=null;
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

    private void textToast(String textToDisplay, Context con) {
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
        Intent myIntent = new Intent(AddFlightActivity.this, FlightActivity.class);
        AddFlightActivity.this.startActivity(myIntent);
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



