package advanced.project.controllers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;

import advanced.project.DataModels.Destination;
import edu.birzeit.lab.advanced.touristapp.R;

/**
 * Created by BassamJon 4/11/2015.
 */
public class ViewDestinationProfile extends ActionBarActivity {

    private int viewMode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.destination_profile_view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            viewMode = extras.getInt("viewNum");
        }

        //object send using Serializable object
        Destination dest = (Destination) getIntent().getSerializableExtra("destination");

        TextView name = (TextView) findViewById(R.id.nameText);
        TextView country = (TextView) findViewById(R.id.countryText);
        TextView longitude = (TextView) findViewById(R.id.LongText);
        TextView latitude = (TextView) findViewById(R.id.LatText);
        ImageView image = (ImageView) findViewById(R.id.image_view);
        name.setText(dest.getName());
        country.setText(dest.getCountry());
        longitude.setText(dest.getLongitude() + "");
        latitude.setText(dest.getLatitude() + "");
        try {
            Bitmap resized = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(dest.getPhotoPath()), 1000, 800, true);
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
        //Start CustomerMain Activity
        switch (viewMode) {
            case 0:
                myIntent = new Intent(ViewDestinationProfile.this, DestinationActivity.class);
                ViewDestinationProfile.this.startActivity(myIntent);
                break;
            case 1:
                myIntent = new Intent(ViewDestinationProfile.this, AddFlightActivity.class);
                ViewDestinationProfile.this.startActivity(myIntent);
                break;
            case 2:
                myIntent = new Intent(ViewDestinationProfile.this, FlightActivity.class);
                ViewDestinationProfile.this.startActivity(myIntent);
                break;
        }

    }

}


