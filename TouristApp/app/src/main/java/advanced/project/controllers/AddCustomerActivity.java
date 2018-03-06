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
import advanced.project.DataModels.Destination;
import advanced.project.SQLiteDB.TouristDAO;
import edu.birzeit.lab.advanced.touristapp.R;

/**
 * Created by BassamJ on 4/8/2015.
 */
public class AddCustomerActivity extends ActionBarActivity {

    private static final int CAMERA_PIC_REQUEST = 2500;
    private static final int RESULT_LOAD_IMAGE = 100;
    private static Customer cust = null;
    private static Customer updateCust=null;
    private Context cont;
    private static int viewMode=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer_activity);
        cont = this;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            viewMode = extras.getInt("viewNum");
        }

        //object send using Serializable object
        updateCust = (Customer) getIntent().getSerializableExtra("customer");

        final EditText custName = (EditText) findViewById(R.id.nameText);
        final EditText custId = (EditText) findViewById(R.id.idText);
        final EditText custPassport = (EditText) findViewById(R.id.passportText);
        final EditText address = (EditText) findViewById(R.id.addressText);
        final EditText imgPath = (EditText) findViewById(R.id.custPhotoPath);
        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        if(updateCust!=null){
            custName.setText(updateCust.getName());
            custId.setText(updateCust.getId()+"");
            custPassport.setText(updateCust.getPassportNum()+"");
            address.setText(updateCust.getAddress());
            imgPath.setText(updateCust.getPhotoPath());

            try {
                Bitmap resized = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(updateCust.getPhotoPath()), 900, 700, true);
                imageView.setImageBitmap(resized);
            }catch(Exception e){
                //show default image
            }
        }

        Button cam = (Button) findViewById(R.id.cameraPhoto);
        cam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String fileName = "temp.jpg";
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, fileName);
                mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                startActivityForResult(intent, CAMERA_PIC_REQUEST);
            }
        });


        Button sel = (Button) findViewById(R.id.galleryPhoto);
        sel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });



        Button add = (Button) findViewById(R.id.addCustomer);
        add.setText("Add new Customer");
        if(viewMode == 1){
            add.setText("Update current customer");
        }

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cust = new Customer();
                String name = custName.getText().toString();
                if (name.equals("")) {
                    new AlertDialog.Builder(cont).setTitle("Alert !").setMessage("Name Field is empty")
                            .setNeutralButton("Ok", null).show();
                    return;
                } else {
                    cust.setName(name);
                }

                String IdS = custId.getText().toString();
                if (IdS.equals("")) {
                    new AlertDialog.Builder(cont).setTitle("Alert !").setMessage("Customer ID Field is empty")
                            .setNeutralButton("Ok", null).show();
                    return;
                } else {
                    cust.setId(Long.parseLong(IdS));
                }

                String passport = custPassport.getText().toString();
                if (passport.equals("")) {
                    new AlertDialog.Builder(cont).setTitle("Alert !").setMessage("Passport Field is empty")
                            .setNeutralButton("Ok", null).show();
                    return;
                } else {
                    cust.setPassportNum(Long.parseLong(passport));
                }

                String addressS = address.getText().toString();
                if (name.equals("")) {
                    new AlertDialog.Builder(cont).setTitle("Alert !").setMessage("Address Field is empty")
                            .setNeutralButton("Ok", null).show();
                    return;
                } else {
                    cust.setAddress(addressS);
                }

                String path = imgPath.getText().toString();
                if (path.equals("")) {
                    new AlertDialog.Builder(cont).setTitle("Alert !").setMessage("Image path Field is empty")
                            .setNeutralButton("Ok", null).show();
                    return;
                } else {
                    cust.setPhotoPath(path);
                }
                if(viewMode ==0) {
                    showOKDialog("Confirmation Message", "Are you sure that you want to add this Customer ", cont);
                }else{
                    showOKDialog("Confirmation Message", "Are you sure that you want to Update this Customer ", cont);
                }
//                    Log.i("MyActivity", "Destinations name " + dest.toString());
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
                                Exception e=null;
                                if(viewMode ==0){
                                    e=dbDAO.addCustomer(cust);
                                    cust=null;
                                    updateCust=null;
                                    viewMode=0;
                                }else{
                                    cust.setDbId(updateCust.getDbId());
                                     e=dbDAO.updateCustomer(cust);
                                    cust=null;
                                    updateCust=null;
                                    viewMode=0;
                                }

                                if (e == null) {
                                    textToast("Customer has been added/updated successfully", cont);
                                    finish();
                                    //Start CustomerMain Activity
                                    Intent myIntent = new Intent(AddCustomerActivity.this, CustomerActivity.class);
                                    AddCustomerActivity.this.startActivity(myIntent);

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

    Uri mCapturedImageURI;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {

            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(mCapturedImageURI, projection, null, null, null);
            int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String capturedImageFilePath = cursor.getString(column_index_data);
            cursor.close();
            ImageView imageView = (ImageView) findViewById(R.id.image_view);
            Bitmap resized = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(capturedImageFilePath), 900, 700, true);
            imageView.setImageBitmap(resized);
            EditText path = (EditText) findViewById(R.id.custPhotoPath);
            path.setText(capturedImageFilePath);
        }
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) findViewById(R.id.image_view);
            Bitmap resized = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(picturePath), 900, 700, true);
            imageView.setImageBitmap(resized);
            EditText path = (EditText) findViewById(R.id.custPhotoPath);
            path.setText(picturePath);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        //Start CustomerMain Activity
        Intent myIntent = new Intent(AddCustomerActivity.this, CustomerActivity.class);
        AddCustomerActivity.this.startActivity(myIntent);
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

