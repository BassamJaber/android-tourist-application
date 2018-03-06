package advanced.project.controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import advanced.project.DataModels.MyLocation;
import edu.birzeit.lab.advanced.touristapp.MainActivity;
import edu.birzeit.lab.advanced.touristapp.R;

/**
 * Created by BassamJ on 4/1/2015.
 */
public class GoogleMapActivity  extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_map_layout);

        final GoogleMap googleMap;
        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
//        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//
//            @Override
//            public void onMapClick(LatLng point) {
//                googleMap.clear();
//                googleMap.addMarker(new MarkerOptions().position(point).snippet(""));
//                Intent myIntent=new Intent(GoogleMapActivity.this, AddDestinationActivity.class);
//                myIntent.putExtra("longitude",point.longitude);
//                myIntent.putExtra("latitude",point.latitude );
//                GoogleMapActivity.this.startActivity(myIntent);
//
//                finish();
//            }
//        });

//        MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
//            private Location location;
//            @Override
//            public void gotLocation(Location location){
////                final LatLng TutorialsPoint = new LatLng(location.getLatitude(),location);
//            }
//        };
//        MyLocation myLocation = new MyLocation();
//        myLocation.getLocation(this, locationResult);

//my home coordinates
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Double longitude = extras.getDouble("longitude");
            Double latitude = extras.getDouble("latitude");
//            Log.i("MyActivity", "Destinations " +longitude+" "+latitude);
            final LatLng TutorialsPoint = new LatLng(latitude,longitude);
            Marker TP = googleMap.addMarker(new MarkerOptions().position(TutorialsPoint).title("I am Here !"));
            googleMap.getUiSettings().setZoomGesturesEnabled(true);
        }else{
            final LatLng TutorialsPoint = new LatLng(31.89807,35.20680);
            Marker TP = googleMap.addMarker(new MarkerOptions().position(TutorialsPoint).title("I am Here !"));
            googleMap.getUiSettings().setZoomGesturesEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        //Start CustomerMain Activity
        Intent myIntent=new Intent(GoogleMapActivity.this, DestinationActivity.class);
        GoogleMapActivity.this.startActivity(myIntent);
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
