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

import advanced.project.DataModels.Customer;
import advanced.project.SQLiteDB.TouristDAO;
import edu.birzeit.lab.advanced.touristapp.R;

/**
 * Created by Bassam on 4/13/2015.
 */
public class CustomerSearchFilter extends ActionBarActivity {


    private static Customer cust = null;
    private Context cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_search_filter);
        cont = this;



        //object send using Serializable object
//        updateCust = (Customer) getIntent().getSerializableExtra("customer");

        final EditText custName = (EditText) findViewById(R.id.nameText);
        final EditText custId = (EditText) findViewById(R.id.idText);
        final EditText custPassport = (EditText) findViewById(R.id.passportText);
        final EditText address = (EditText) findViewById(R.id.addressText);


        Button search = (Button) findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cust = new Customer();
                boolean isEmpty=true;

                String name = custName.getText().toString();
                if (!name.equals("")) {
                    isEmpty=false;
                    cust.setName(name);
                }

                String IdS = custId.getText().toString();
                if (!IdS.equals("")) {
                    isEmpty=false;
                    cust.setId(Long.parseLong(IdS));
                }

                String passport = custPassport.getText().toString();
                if (!passport.equals("")) {
                    isEmpty=false;
                    cust.setPassportNum(Long.parseLong(passport));
                }

                String addressS = address.getText().toString();
                if (!name.equals("")) {
                    isEmpty=false;
                    cust.setAddress(addressS);
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
//                                    textToast("Customer has been added/updated successfully", cont);
                                    finish();
                                    //Start CustomerMain Activity
                                    Intent myIntent = new Intent(CustomerSearchFilter.this, CustomerActivity.class);
                                    myIntent.putExtra("customer", cust);
                                    CustomerSearchFilter.this.startActivity(myIntent);
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
        Intent myIntent = new Intent(CustomerSearchFilter.this, CustomerActivity.class);
        CustomerSearchFilter.this.startActivity(myIntent);
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

