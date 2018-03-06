package advanced.project.controllers;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;

import advanced.project.DataModels.Destination;
import advanced.project.SQLiteDB.TouristDAO;
import edu.birzeit.lab.advanced.touristapp.R;

/**
 * Created by BassamJ on 4/1/2015.
 */
public class AddDestinationActivity extends  ActionBarActivity {

    private static final int CAMERA_PIC_REQUEST = 2500;
    private static final int RESULT_LOAD_IMAGE=100;
    private static Destination  dest=null;
    private Context cont;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.add_destination_activity);
            cont=this;

            Button cam = (Button)findViewById(R.id.cameraPhoto);
            cam.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
//                    showImageDialog();
//                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

                    String fileName = "temp.jpg";
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, fileName);
                    mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                    startActivityForResult(intent,CAMERA_PIC_REQUEST);
                }
            });

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                Double longitude = extras.getDouble("longitude");
                Double latitude = extras.getDouble("latitude");
                final EditText destLong=(EditText)findViewById(R.id.LongText);
                final EditText destLat=(EditText)findViewById(R.id.LatText);
                destLong.setText(longitude+"");
                destLat.setText(latitude+"");
            }

            Button openGmap = (Button)findViewById(R.id.openGmap);
            openGmap.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //save current object to use it when getting back from google maps
                    dest = new Destination();
                    final EditText destName=(EditText)findViewById(R.id.nameText);
                    final EditText destCountry=(EditText)findViewById(R.id.countryText);
                    final EditText destLong=(EditText)findViewById(R.id.LongText);
                    final EditText destLat=(EditText)findViewById(R.id.LatText);
                    final EditText imgPath=(EditText)findViewById(R.id.destPhotoPath);
                    dest.setName(destName.getText().toString());
                    dest.setCountry(destCountry.getText().toString());
                    dest.setPhotoPath(imgPath.getText().toString());
                    dest.setLatitude(Double.parseDouble(destLat.getText().toString()));
                    dest.setLongitude(Double.parseDouble(destLong.getText().toString()));
                    Intent myIntent = new Intent(AddDestinationActivity.this, GoogleCoordinatesActivity.class);
                    AddDestinationActivity.this.startActivity(myIntent);
                    finish();
                }
            });

            if(dest !=null){
                final EditText destName=(EditText)findViewById(R.id.nameText);
                final EditText destCountry=(EditText)findViewById(R.id.countryText);
                final EditText imgPath=(EditText)findViewById(R.id.destPhotoPath);
                destName.setText(dest.getName());
                destCountry.setText(dest.getCountry());
                imgPath.setText(dest.getPhotoPath());
                ImageView imageView = (ImageView) findViewById(R.id.image_view);
                try {
                    Bitmap resized = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(dest.getPhotoPath()), 900, 700, true);
                    imageView.setImageBitmap(resized);
                }catch(Exception e){
                    //show default image
//                    new AlertDialog.Builder(cont).setTitle("Alert !").setMessage("Image not found !")
//                            .setNeutralButton("Ok", null).show();
//                    String uri = "@drawable/default_no_photo.png";
//                    int imageResource = getResources().getIdentifier(uri, null, getPackageName());
//                    Drawable res = getResources().getDrawable(imageResource);
//                    imageView.setImageDrawable(res);
                }
                dest=null;
            }

            Button sel = (Button)findViewById(R.id.galleryPhoto);
            sel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
            });

            final EditText destName=(EditText)findViewById(R.id.nameText);
            final EditText destCountry=(EditText)findViewById(R.id.countryText);
            final EditText destLong=(EditText)findViewById(R.id.LongText);
            final EditText destLat=(EditText)findViewById(R.id.LatText);
            final EditText imgPath=(EditText)findViewById(R.id.destPhotoPath);

            Button add = (Button)findViewById(R.id.addDestination);
            add.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dest= new Destination();
                    String name=destName.getText().toString();
                    if(name.equals("")){
                        new AlertDialog.Builder(cont).setTitle("Alert !").setMessage("Name Field is empty")
                                .setNeutralButton("Ok", null).show();
                        return;
                    }else{
                        dest.setName(name);
                    }

                    String country=destCountry.getText().toString();
                    if(country.equals("")){
                        new AlertDialog.Builder(cont).setTitle("Alert !").setMessage("Country Field is empty")
                                .setNeutralButton("Ok", null).show();
                        return;
                    }else{
                        dest.setCountry(country);
                    }

                    String longS=destLong.getText().toString();
                    if(longS.equals("")){
                        new AlertDialog.Builder(cont).setTitle("Alert !").setMessage("Longitude Field is empty")
                                .setNeutralButton("Ok", null).show();
                        return;
                    }else{
                        dest.setLongitude(Double.parseDouble(longS));
                    }

                    String latitS=destLat.getText().toString();
                    if(name.equals("")){
                        new AlertDialog.Builder(cont).setTitle("Alert !").setMessage("Latitude Field is empty")
                                .setNeutralButton("Ok", null).show();
                        return;
                    }else{
                        dest.setLatitude(Double.parseDouble(latitS));
                    }

                    String path= imgPath.getText().toString();
                    if(path.equals("")){
                        new AlertDialog.Builder(cont).setTitle("Alert !").setMessage("Image path Field is empty")
                                .setNeutralButton("Ok", null).show();
                        return;
                    }else{
                        dest.setPhotoPath(path);
                    }

                    showOKDialog("Confirmation Message" , "Are you sure that you want to add this Destination " , cont);
//                    Log.i("MyActivity", "Destinations name " + dest.toString());
                }
            });


        }

    private void showOKDialog(String title , String msg , Context con)
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(con).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setButton("Yes", new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        TouristDAO dbDAO= new TouristDAO(cont);
                        try {
                            dbDAO.addDestination(dest);
                            textToast("Destination has been added successfully" , cont);
                            finish();
                            //Start CustomerMain Activity
                            Intent myIntent=new Intent(AddDestinationActivity.this, DestinationActivity.class);
                            AddDestinationActivity.this.startActivity(myIntent);
                            dest=null;
                        }catch(Exception e){
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

    public void textToast(String textToDisplay , Context con)
    {
        Context context = con;
        CharSequence text = textToDisplay;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.CENTER, 50, 50);
        toast.show();
    }
    Uri mCapturedImageURI;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {

            String[] projection = { MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(mCapturedImageURI, projection, null, null, null);
            int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String capturedImageFilePath = cursor.getString(column_index_data);
            cursor.close();
            ImageView imageView = (ImageView) findViewById(R.id.image_view);
            Bitmap resized = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(capturedImageFilePath), 900, 700, true);
            imageView.setImageBitmap(resized);
            EditText path= (EditText)findViewById(R.id.destPhotoPath);
            path.setText(capturedImageFilePath);
        }
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) findViewById(R.id.image_view);
            Bitmap resized = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(picturePath), 900, 700, true);
            imageView.setImageBitmap(resized);
            EditText path= (EditText)findViewById(R.id.destPhotoPath);
            path.setText(picturePath);
        }
    }
        @Override
        public void onBackPressed() {
            super.onBackPressed();
            finish();
            //Start CustomerMain Activity
            Intent myIntent=new Intent(AddDestinationActivity.this, DestinationActivity.class);
            AddDestinationActivity.this.startActivity(myIntent);
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
