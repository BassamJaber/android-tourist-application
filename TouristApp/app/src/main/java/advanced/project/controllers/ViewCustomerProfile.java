package advanced.project.controllers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import advanced.project.DataModels.Customer;
import advanced.project.DataModels.Destination;
import edu.birzeit.lab.advanced.touristapp.R;

/**
 * Created by BassamJ on 4/12/2015.
 */
public class ViewCustomerProfile extends ActionBarActivity {

    private int viewMode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_profile);
//
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            viewMode = extras.getInt("viewNum");
//        }

        //object send using Serializable object
        Customer cust = (Customer) getIntent().getSerializableExtra("customer");

        TextView name = (TextView) findViewById(R.id.nameText);
        TextView id = (TextView) findViewById(R.id.idText);
        TextView passport = (TextView) findViewById(R.id.passportText);
        TextView address = (TextView) findViewById(R.id.addressText);
        ImageView image = (ImageView) findViewById(R.id.image_view);
        TextView path = (TextView) findViewById(R.id.custPhotoPath);

        name.setText(cust.getName());
        id.setText(cust.getId()+"");
        passport.setText(cust.getPassportNum() + "");
        address.setText(cust.getAddress());
        path.setText(cust.getPhotoPath());
        try {
            Bitmap resized = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(cust.getPhotoPath()), 1000, 800, true);
            image.setImageBitmap(resized);
        } catch (Exception e) {
            //display default image.
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
        Intent myIntent;
        myIntent = new Intent(ViewCustomerProfile.this, CustomerActivity.class);
        ViewCustomerProfile.this.startActivity(myIntent);
        //Start CustomerMain Activity
//        switch (viewMode) {
//            case 0:
//                myIntent = new Intent(ViewCustomerProfile.this, CustomerActivity.class);
//                ViewCustomerProfile.this.startActivity(myIntent);
//                break;
//            case 1:
//                myIntent = new Intent(ViewDestinationProfile.this, AddFlightActivity.class);
//                ViewDestinationProfile.this.startActivity(myIntent);
//                break;
//            case 2:
//                myIntent = new Intent(ViewDestinationProfile.this, FlightActivity.class);
//                ViewDestinationProfile.this.startActivity(myIntent);
//                break;
//        }

    }

}


