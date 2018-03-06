package advanced.project.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.birzeit.lab.advanced.touristapp.R;

/**
 * Created by BassamJ on 4/10/2015.
 */
public class GoogleCoordinatesActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_map_layout);

        final GoogleMap googleMap;
        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(point).snippet(""));
                Intent myIntent = new Intent(GoogleCoordinatesActivity.this, AddDestinationActivity.class);
                myIntent.putExtra("longitude", point.longitude);
                myIntent.putExtra("latitude", point.latitude);
                GoogleCoordinatesActivity.this.startActivity(myIntent);
                finish();
            }
        });


        final LatLng TutorialsPoint = new LatLng(31.89807, 35.20680);
        Marker TP = googleMap.addMarker(new MarkerOptions().position(TutorialsPoint).title("I am Here !"));
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        //Start CustomerMain Activity
        Intent myIntent = new Intent(GoogleCoordinatesActivity.this, DestinationActivity.class);
        GoogleCoordinatesActivity.this.startActivity(myIntent);
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