package advanced.project.controllers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import advanced.project.DataModels.Destination;
import advanced.project.DataModels.Flight;
import edu.birzeit.lab.advanced.touristapp.R;

/**
 * Created by BassamJ on 4/12/2015.
 */
public class ViewFlightProfile extends ActionBarActivity {

    private int viewMode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_profile_layout);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            viewMode = extras.getInt("viewNum");
        }

        //object send using Serializable object
        Flight flight = (Flight) getIntent().getSerializableExtra("flight");

        TextView company = (TextView) findViewById(R.id.companyText);
        TextView cost = (TextView) findViewById(R.id.costText);
        TextView depDate = (TextView) findViewById(R.id.depDateText);
        TextView arriDate = (TextView) findViewById(R.id.arriDateText);

        company.setText(flight.getCompanyName());
        cost.setText(flight.getCost()+"");
        depDate.setText(flight.getDepDate());
        arriDate.setText(flight.getArriDate());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
        Intent myIntent;
        //Start CustomerMain Activity
        switch (viewMode) {
            case 0:
                myIntent = new Intent(ViewFlightProfile.this, FlightActivity.class);
                ViewFlightProfile.this.startActivity(myIntent);
                break;
//            case 1:
//                myIntent = new Intent(ViewDestinationProfile.this, AddFlightActivity.class);
//                ViewDestinationProfile.this.startActivity(myIntent);
//                break;
//            case 2:
//                myIntent = new Intent(ViewDestinationProfile.this, FlightActivity.class);
//                ViewDestinationProfile.this.startActivity(myIntent);
//                break;
        }

    }
}
