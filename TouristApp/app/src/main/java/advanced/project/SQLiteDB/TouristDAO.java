package advanced.project.SQLiteDB;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.LinkedList;

import advanced.project.DataModels.Booking;
import advanced.project.DataModels.Customer;
import advanced.project.DataModels.Destination;
import advanced.project.DataModels.Flight;

/**
 * Created by BassamJ on 3/25/2015.
 */
public class TouristDAO {
    private Context objCont = null;
    private String dbName = "touristDB";

    // Table Names
    private static final String TABLE_CUSTOMER = "customer";
    private static final String TABLE_FLIGHT = "flight";
    private static final String TABLE_DESTINATION = "destination";
    private static final String TABLE_CUSTOMER_TO_DESTINATION = "customer_to_destination";

    // Common column names (id Field Exists in all tables)
    private static final String KEY_ID = "id";

    // customers Table - column names
    private static final String CUSTOMER_NAME = "cname";
    private static final String CUSTOMER_ID = "cid";
    private static final String CUSTOMER_PASSPORT_NUM = "cpassnum";
    private static final String CUSTOMER_PHOTO_PATH = "cphotopath";
    private static final String CUSTOMER_ADDRESS = "caddress";

    // Destination Table - column names
    private static final String DEST_NAME = "dname";
    private static final String DEST_COUNTRY = "dcountry";
    private static final String DEST_LONG = "dlongitude";
    private static final String DEST_LAT = "dlatitude";
    private static final String DEST_PHOTO_PATH = "dphotopath";

    // Flight Table - column names
    private static final String FLIGHT_COMPANY_NAME = "fcompanyname";
    private static final String FLIGHT_COST = "fcost";
    private static final String FLIGHT_DEP_DATE = "fdepdate";
    private static final String FLIGHT_ARRI_DATE = "farridate";
    private static final String FLIGHT_DEP_DEST = "fdepdest";

    // Customer to destination  Table - column names
    private static final String KEY_CUSTOMER_ID = "cid";
    private static final String KEY_FLIGHT_ID = "fid";
//    private static final String KEY_DESTINATION_ID = "did";

    // Table Create Statements
    private static final String CREATE_TABLE_CUSTOMER = "CREATE TABLE IF NOT EXISTS "
            + TABLE_CUSTOMER + "(" + KEY_ID + " INTEGER PRIMARY KEY , " + CUSTOMER_NAME
            + " TEXT NOT NULL, " + CUSTOMER_ID + " INTEGER NOT NULL, " + CUSTOMER_PASSPORT_NUM + " INTEGER NOT NULL, "
            + CUSTOMER_PHOTO_PATH + " TEXT, " + CUSTOMER_ADDRESS + " TEXT NOT NULL " + ")";

    // + " DATETIME" +
    // Destination table create statement
    private static final String CREATE_TABLE_DESTINATION = "CREATE TABLE IF NOT EXISTS " + TABLE_DESTINATION
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + DEST_NAME + " TEXT,"
            + DEST_COUNTRY + " TEXT NOT NULL," + DEST_LONG + " REAL,"
            + DEST_LAT + " REAL," + " TEXT," + DEST_PHOTO_PATH + " TEXT" + ")";

    // Flight table create statement
    private static final String CREATE_TABLE_FLIGHT = "CREATE TABLE IF NOT EXISTS "
            + TABLE_FLIGHT + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + FLIGHT_COMPANY_NAME + " TEXT NOT NULL," + FLIGHT_COST + " INTEGER NOT NULL,"
            + FLIGHT_DEP_DATE + " DATETIME NOT NULL," + FLIGHT_ARRI_DATE + " DATETIME NOT NULL," + FLIGHT_DEP_DEST + " INTEGER NOT NULL ,"
            + "FOREIGN KEY (" + FLIGHT_DEP_DEST + ") REFERENCES " + TABLE_DESTINATION + "(" + KEY_ID + ")ON UPDATE CASCADE ON DELETE CASCADE )";

    // Customer To Destination table create statement
    private static final String CREATE_TABLE_CUSTOMER_TO_DEST = "CREATE TABLE IF NOT EXISTS "
            + TABLE_CUSTOMER_TO_DESTINATION + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_CUSTOMER_ID + " INTEGER," + KEY_FLIGHT_ID + " INTEGER,"
            + "FOREIGN KEY (" + KEY_CUSTOMER_ID + ") REFERENCES " + TABLE_CUSTOMER + "(" + KEY_ID + ")ON UPDATE CASCADE ON DELETE NO ACTION "
            + "FOREIGN KEY (" + KEY_FLIGHT_ID + ") REFERENCES " + TABLE_FLIGHT + "(" + KEY_ID + ")ON UPDATE CASCADE ON DELETE NO ACTION "
            + ")";

    public TouristDAO(Context cont) {
        objCont = cont;
    }

    //create DataBase if not exist or open existing Database
    public Exception createDB() {
        SQLiteDatabase db = null;
        try {
            ContextWrapper obj = new ContextWrapper(objCont);
            db = obj.openOrCreateDatabase(dbName, android.content.Context.MODE_PRIVATE, null);
            // creating required tables
            db.execSQL(CREATE_TABLE_CUSTOMER);
            db.execSQL(CREATE_TABLE_DESTINATION);
            db.execSQL(CREATE_TABLE_FLIGHT);
            db.execSQL(CREATE_TABLE_CUSTOMER_TO_DEST);
            db.close();
            return null;
        } catch (Exception e) {
            db.close();
            return e;
        }
    }

    //get instance of DataBase , Reference for the database
    private SQLiteDatabase getSQLObject() {
        ContextWrapper obj = new ContextWrapper(objCont);
        return obj.openOrCreateDatabase(dbName, android.content.Context.MODE_PRIVATE, null);
    }

    public Exception addDestination(Destination objDest) {
        SQLiteDatabase db;
        db = getSQLObject();
        try {
            db.execSQL("insert into " + TABLE_DESTINATION + "(" + DEST_NAME + "," + DEST_COUNTRY + "," + DEST_LONG + "," + DEST_LAT + "," + DEST_PHOTO_PATH + ") values ('" + objDest.getName() + "','" + objDest.getCountry()
                    + "'," + "'" + objDest.getLongitude() + "'," + "'" + objDest.getLatitude() + "','" + objDest.getPhotoPath() + "')");
            db.close();
            return null;
        } catch (Exception e) {
            db.close();
            return e;
        }
    }

    public Exception loadDestination(Destination objDest) {
        SQLiteDatabase db;
        db = getSQLObject();
        try {
            db.execSQL("insert into " + TABLE_DESTINATION + "("+KEY_ID+","+ DEST_NAME + "," + DEST_COUNTRY + "," + DEST_LONG + "," + DEST_LAT + "," + DEST_PHOTO_PATH + ") values ('"+objDest.getDbId() +"','"+ objDest.getName() + "','" + objDest.getCountry()
                    + "'," + "'" + objDest.getLongitude() + "'," + "'" + objDest.getLatitude() + "','" + objDest.getPhotoPath() + "')");
            db.close();
            return null;
        } catch (Exception e) {
            db.close();
            return e;
        }
    }

    public Destination getSingleDestination(int destId) {
//        cursor =  this.db.rawQuery("select * from " + BanksTable.NAME +
        SQLiteDatabase db;
        db = getSQLObject();
        String SqlQuery = "select * from " + TABLE_DESTINATION + " where " + KEY_ID + " = " + destId;
        Cursor objCur = db.rawQuery(SqlQuery, null);
        Destination objDest = null;
        if (objCur != null) {
//            Log.i("MyActivity", "Destinations " +destId);
            objCur.moveToFirst();
            objDest = new Destination();
//            objDest.setDbId(objCur.getInt(objCur.getColumnIndex(KEY_ID)));
            objDest.setName(objCur.getString(objCur.getColumnIndex(DEST_NAME)));
            objDest.setCountry(objCur.getString(objCur.getColumnIndex(DEST_COUNTRY)));
            objDest.setLongitude(objCur.getLong(objCur.getColumnIndex(DEST_LONG)));
            objDest.setLatitude(objCur.getLong(objCur.getColumnIndex(DEST_LAT)));
            objDest.setPhotoPath(objCur.getString(objCur.getColumnIndex(DEST_PHOTO_PATH)));
//                    Log.i("MyActivity", "Destinations " +objDest.toString());

        }

        return objDest;
    }

    public Exception deleteDestination(long objDestId) {
//        Log.i("MyActivity", "Destinations " +objDestId);
        SQLiteDatabase db;
        db = getSQLObject();
        try {
            db.delete(TABLE_DESTINATION, KEY_ID + " = ?",
                    new String[]{String.valueOf(objDestId)});
            db.close();
            return null;
        } catch (Exception e) {
            db.close();
            return e;
        }
    }

    public Exception deleteTableDestination() {
        SQLiteDatabase db;
        db = getSQLObject();
        try {
            db.execSQL("delete from " + TABLE_DESTINATION);
            db.close();
            return null;
        } catch (Exception e) {
            db.close();
            return e;
        }
    }


    public LinkedList<Destination> getDestinationList() {
        SQLiteDatabase db;
        db = getSQLObject();
        String SqlQuery = "select * from " + TABLE_DESTINATION;
        Cursor objCur = db.rawQuery(SqlQuery, null);
        LinkedList<Destination> destList = new LinkedList<Destination>();
        if (objCur != null) {
            if (objCur.moveToFirst()) {
                do {
                    Destination objDest = new Destination();
                    objDest.setDbId(objCur.getInt(objCur.getColumnIndex(KEY_ID)));
                    objDest.setName(objCur.getString(objCur.getColumnIndex(DEST_NAME)));
                    objDest.setCountry(objCur.getString(objCur.getColumnIndex(DEST_COUNTRY)));
                    objDest.setLongitude(objCur.getLong(objCur.getColumnIndex(DEST_LONG)));
                    objDest.setLatitude(objCur.getLong(objCur.getColumnIndex(DEST_LAT)));
                    objDest.setPhotoPath(objCur.getString(objCur.getColumnIndex(DEST_PHOTO_PATH)));
//                    Log.i("MyActivity", "Destinations " +objDest.toString());
                    destList.addFirst(objDest);
                } while (objCur.moveToNext());
            }
        }
        // String SqlQuery = "select * from " + TABLE_FLIGHT+" where "+FLIGHT_DEP_DEST+" = "+destId;
        return destList;
    }


    public LinkedList<Destination> searchDestination(Destination destObj) {
        SQLiteDatabase db;
        db = getSQLObject();
        String SqlQuery = "select * from " + TABLE_DESTINATION + " where " + generateDestinationQuery(destObj);
        Log.i("MyActivity", "Destinations " +SqlQuery);
        Cursor objCur = db.rawQuery(SqlQuery, null);
        LinkedList<Destination> destList = new LinkedList<Destination>();
        if (objCur != null) {
            if (objCur.moveToFirst()) {
                do {
                    Destination objDest = new Destination();
                    objDest.setDbId(objCur.getInt(objCur.getColumnIndex(KEY_ID)));
                    objDest.setName(objCur.getString(objCur.getColumnIndex(DEST_NAME)));
                    objDest.setCountry(objCur.getString(objCur.getColumnIndex(DEST_COUNTRY)));
                    objDest.setLongitude(objCur.getLong(objCur.getColumnIndex(DEST_LONG)));
                    objDest.setLatitude(objCur.getLong(objCur.getColumnIndex(DEST_LAT)));
                    objDest.setPhotoPath(objCur.getString(objCur.getColumnIndex(DEST_PHOTO_PATH)));
//                    Log.i("MyActivity", "Destinations " +objDest.toString());
                    destList.addFirst(objDest);
                } while (objCur.moveToNext());
            }
        }
        // String SqlQuery = "select * from " + TABLE_FLIGHT+" where "+FLIGHT_DEP_DEST+" = "+destId;
        return destList;
    }

    public String generateDestinationQuery(Destination dest) {
        String query = "";
        String connector = " AND ";
        boolean isFirst=true;
        if (dest.getName() != null && !dest.getName().equals("")) {
            if(isFirst){
                isFirst=false;
            }
            query +=" "+DEST_NAME+" = '"+dest.getName()+"' ";
        }

        if (dest.getCountry() != null && !dest.getCountry().equals("")) {
            if(isFirst){
                isFirst=false;
                query +=" "+ DEST_COUNTRY+" = '"+dest.getCountry()+"' ";
            }else{
                query +=" AND "+DEST_COUNTRY+" = '"+dest.getCountry()+"' ";
            }
        }
        return query;
    }

    public Exception addFlight(Flight objFlight) {
        SQLiteDatabase db;
        db = getSQLObject();
        try {
//            Log.i("MyActivity", "Flights " +objFlight.toString());
            db.execSQL("insert into " + TABLE_FLIGHT + "(" + FLIGHT_COMPANY_NAME + "," + FLIGHT_COST + "," + FLIGHT_DEP_DATE + "," + FLIGHT_ARRI_DATE + "," + FLIGHT_DEP_DEST
                    + ") values ('" + objFlight.getCompanyName() + "','" + objFlight.getCost()
                    + "'," + "'" + objFlight.getDepDate() + "'," + "'" + objFlight.getArriDate() + "','" + objFlight.getDestDbId() + "')");
            db.close();
            return null;
        } catch (Exception e) {
            db.close();
            return e;
        }
    }

    public Exception loadFlight(Flight objFlight) {
        SQLiteDatabase db;
        db = getSQLObject();
        try {
//            Log.i("MyActivity", "Flights " +objFlight.toString());
            db.execSQL("insert into " + TABLE_FLIGHT + "(" +KEY_ID+ ","+ FLIGHT_COMPANY_NAME + "," + FLIGHT_COST + "," + FLIGHT_DEP_DATE + "," + FLIGHT_ARRI_DATE + "," + FLIGHT_DEP_DEST
                    + ") values ('" +objFlight.getDbId() + "','" +objFlight.getCompanyName() + "','" + objFlight.getCost()
                    + "'," + "'" + objFlight.getDepDate() + "'," + "'" + objFlight.getArriDate() + "','" + objFlight.getDestDbId() + "')");
            db.close();
            return null;
        } catch (Exception e) {
            db.close();
            return e;
        }
    }

    public Exception deleteFlight(long objFlightId) {
//        Log.i("MyActivity", "Destinations " +objDestId);
        SQLiteDatabase db;
        db = getSQLObject();
        try {
            db.delete(TABLE_FLIGHT, KEY_ID + " = ?",
                    new String[]{String.valueOf(objFlightId)});
            db.close();
            return null;
        } catch (Exception e) {
            db.close();
            return e;
        }
    }

    public Exception deleteTableFlight() {
        SQLiteDatabase db;
        db = getSQLObject();
        try {
            db.execSQL("delete from " + TABLE_FLIGHT);
            db.close();
            return null;
        } catch (Exception e) {
            db.close();
            return e;
        }
    }


    public LinkedList<Flight> getFlightList() {
        SQLiteDatabase db;
        db = getSQLObject();
        String SqlQuery = "select * from " + TABLE_FLIGHT;
        Cursor objCur = db.rawQuery(SqlQuery, null);
        LinkedList<Flight> flightList = new LinkedList<Flight>();
        if (objCur != null) {
            if (objCur.moveToFirst()) {
                do {
                    Flight objFlight = new Flight();
                    objFlight.setDbId(objCur.getInt(objCur.getColumnIndex(KEY_ID)));
                    objFlight.setCompanyName(objCur.getString(objCur.getColumnIndex(FLIGHT_COMPANY_NAME)));
                    objFlight.setCost(objCur.getInt(objCur.getColumnIndex(FLIGHT_COST)));
                    objFlight.setDepDate(objCur.getString(objCur.getColumnIndex(FLIGHT_DEP_DATE)));
                    objFlight.setArriDate(objCur.getString(objCur.getColumnIndex(FLIGHT_ARRI_DATE)));
                    objFlight.setDestDbId(objCur.getInt(objCur.getColumnIndex(FLIGHT_DEP_DEST)));
//                    Log.i("MyActivity", "Flights " +objFlight.toString());
                    flightList.addFirst(objFlight);
                } while (objCur.moveToNext());
            }
        }
        return flightList;
    }

    public LinkedList<Flight> searchFlight(Flight flightObj) {
        SQLiteDatabase db;
        db = getSQLObject();
        String SqlQuery = "select * from " + TABLE_FLIGHT + " where " + generateFlightQuery(flightObj);
                    Log.i("MyActivity", "Flights " +SqlQuery);
        Cursor objCur = db.rawQuery(SqlQuery, null);
        LinkedList<Flight> flightList = new LinkedList<Flight>();
        if (objCur != null) {
            if (objCur.moveToFirst()) {
                do {
                    Flight objFlight = new Flight();
                    objFlight.setDbId(objCur.getInt(objCur.getColumnIndex(KEY_ID)));
                    objFlight.setCompanyName(objCur.getString(objCur.getColumnIndex(FLIGHT_COMPANY_NAME)));
                    objFlight.setCost(objCur.getInt(objCur.getColumnIndex(FLIGHT_COST)));
                    objFlight.setDepDate(objCur.getString(objCur.getColumnIndex(FLIGHT_DEP_DATE)));
                    objFlight.setArriDate(objCur.getString(objCur.getColumnIndex(FLIGHT_ARRI_DATE)));
                    objFlight.setDestDbId(objCur.getInt(objCur.getColumnIndex(FLIGHT_DEP_DEST)));
//                    Log.i("MyActivity", "Flights " +objFlight.toString());
                    flightList.addFirst(objFlight);
                } while (objCur.moveToNext());
            }
        }
        return flightList;
    }

    public String generateFlightQuery(Flight flight) {
        String query = "";
        boolean isFirst=true;
        if (flight.getCompanyName() != null && !flight.getCompanyName().equals("")) {
            if(isFirst){
                isFirst=false;
            }
            query +=" "+FLIGHT_COMPANY_NAME+" = '"+flight.getCompanyName()+"' ";
        }

        if (flight.getCost()>0) {
            if(isFirst){
                isFirst=false;
                query +=" "+ FLIGHT_COST+" = '"+flight.getCost()+"' ";
            }else{
                query +=" AND "+FLIGHT_COST+" = '"+flight.getCost()+"' ";
            }
        }

        if (flight.getDepDate() != null && !flight.getDepDate().equals("")) {
            if(isFirst){
                isFirst=false;
                query +=" "+ FLIGHT_DEP_DATE+" = '"+flight.getDepDate()+"' ";
            }else{
                query +=" AND "+FLIGHT_DEP_DATE+" = '"+flight.getDepDate()+"' ";
            }
        }

        if (flight.getArriDate() != null && !flight.getArriDate().equals("")) {
            if(isFirst){
                isFirst=false;
                query +=" "+ FLIGHT_ARRI_DATE+" = '"+flight.getArriDate()+"' ";
            }else{
                query +=" AND "+FLIGHT_ARRI_DATE+" = '"+flight.getArriDate()+"' ";
            }
        }
        return query;
    }

    public LinkedList<Flight> getDestinationFlightsList(int destId) {
        SQLiteDatabase db;
        db = getSQLObject();
        String SqlQuery = "select * from " + TABLE_FLIGHT + " where " + FLIGHT_DEP_DEST + " = " + destId;
        Cursor objCur = db.rawQuery(SqlQuery, null);
        LinkedList<Flight> flightList = new LinkedList<Flight>();
        if (objCur != null) {
            if (objCur.moveToFirst()) {
                do {
                    Flight objFlight = new Flight();
                    objFlight.setDbId(objCur.getInt(objCur.getColumnIndex(KEY_ID)));
                    objFlight.setCompanyName(objCur.getString(objCur.getColumnIndex(FLIGHT_COMPANY_NAME)));
                    objFlight.setCost(objCur.getInt(objCur.getColumnIndex(FLIGHT_COST)));
                    objFlight.setDepDate(objCur.getString(objCur.getColumnIndex(FLIGHT_DEP_DATE)));
                    objFlight.setArriDate(objCur.getString(objCur.getColumnIndex(FLIGHT_ARRI_DATE)));
                    objFlight.setDestDbId(objCur.getInt(objCur.getColumnIndex(FLIGHT_DEP_DEST)));
//                    Log.i("MyActivity", "Flights " +objFlight.toString());
                    flightList.addFirst(objFlight);
                } while (objCur.moveToNext());
            }
        }
        return flightList;
    }


    public Exception addCustomer(Customer objCustomer) {
        SQLiteDatabase db;
        db = getSQLObject();
        try {
//            Log.i("Debug", "Flights " +objCustomer.toString());
            db.execSQL("insert into " + TABLE_CUSTOMER + " (" + CUSTOMER_NAME + "," + CUSTOMER_ID + "," + CUSTOMER_PASSPORT_NUM + "," + CUSTOMER_PHOTO_PATH + "," + CUSTOMER_ADDRESS
                    + ") values ('" + objCustomer.getName() + "','" + objCustomer.getId()
                    + "','" + objCustomer.getPassportNum() + "','" + objCustomer.getPhotoPath() + "','" + objCustomer.getAddress() + "')");
            db.close();

            return null;
        } catch (Exception e) {
            db.close();
            return e;
        }
    }

    public Exception loadCustomer(Customer objCustomer) {
        SQLiteDatabase db;
        db = getSQLObject();
        try {
//            Log.i("Debug", "Flights " +objCustomer.toString());
            db.execSQL("insert into " + TABLE_CUSTOMER + " (" +KEY_ID+ "," + CUSTOMER_NAME + "," + CUSTOMER_ID + "," + CUSTOMER_PASSPORT_NUM + "," + CUSTOMER_PHOTO_PATH + "," + CUSTOMER_ADDRESS
                    + ") values ('" +objCustomer.getDbId() + "','" + objCustomer.getName() + "','" + objCustomer.getId()
                    + "','" + objCustomer.getPassportNum() + "','" + objCustomer.getPhotoPath() + "','" + objCustomer.getAddress() + "')");
            db.close();

            return null;
        } catch (Exception e) {
            db.close();
            return e;
        }
    }

    public Exception deleteCustomer(long objCustomerId) {
//        Log.i("MyActivity", "Destinations " +objDestId);
        SQLiteDatabase db;
        db = getSQLObject();
        try {
            db.delete(TABLE_CUSTOMER, KEY_ID + " = ?",
                    new String[]{String.valueOf(objCustomerId)});
            db.close();
            return null;
        } catch (Exception e) {
            db.close();
            return e;
        }
    }

    public Exception deleteTableCustomer() {
        SQLiteDatabase db;
        db = getSQLObject();
        try {
            db.execSQL("delete from " + TABLE_CUSTOMER);
            db.close();
            return null;
        } catch (Exception e) {
            db.close();
            return e;
        }
    }


    public LinkedList<Customer> getCustomerList() {
        SQLiteDatabase db;
        db = getSQLObject();
        String SqlQuery = "select * from " + TABLE_CUSTOMER;
        Cursor objCur = db.rawQuery(SqlQuery, null);
        LinkedList<Customer> customerList = new LinkedList<Customer>();
        if (objCur != null) {
            if (objCur.moveToFirst()) {
                do {
                    Customer objCust = new Customer();
                    objCust.setDbId(objCur.getInt(objCur.getColumnIndex(KEY_ID)));
                    objCust.setName(objCur.getString(objCur.getColumnIndex(CUSTOMER_NAME)));
                    objCust.setId(objCur.getLong(objCur.getColumnIndex(CUSTOMER_ID)));
                    objCust.setPassportNum(objCur.getLong(objCur.getColumnIndex(CUSTOMER_PASSPORT_NUM)));
                    objCust.setAddress(objCur.getString(objCur.getColumnIndex(CUSTOMER_ADDRESS)));
                    objCust.setPhotoPath(objCur.getString(objCur.getColumnIndex(CUSTOMER_PHOTO_PATH)));
//                    Log.i("MyActivity", "Flights " +objCust.toString());
                    customerList.addFirst(objCust);
                } while (objCur.moveToNext());
            }
        }
        return customerList;
    }

    public LinkedList<Customer> searchCustomer(Customer custObj ) {
        SQLiteDatabase db;
        db = getSQLObject();
        String SqlQuery = "select * from " + TABLE_CUSTOMER + " where " + generateCustomerQuery(custObj);
//        Log.i("MyActivity", "Flights " +SqlQuery);
        Cursor objCur = db.rawQuery(SqlQuery, null);
        LinkedList<Customer> customerList = new LinkedList<Customer>();
        if (objCur != null) {
            if (objCur.moveToFirst()) {
                do {
                    Customer objCust = new Customer();
                    objCust.setDbId(objCur.getInt(objCur.getColumnIndex(KEY_ID)));
                    objCust.setName(objCur.getString(objCur.getColumnIndex(CUSTOMER_NAME)));
                    objCust.setId(objCur.getLong(objCur.getColumnIndex(CUSTOMER_ID)));
                    objCust.setPassportNum(objCur.getLong(objCur.getColumnIndex(CUSTOMER_PASSPORT_NUM)));
                    objCust.setAddress(objCur.getString(objCur.getColumnIndex(CUSTOMER_ADDRESS)));
                    objCust.setPhotoPath(objCur.getString(objCur.getColumnIndex(CUSTOMER_PHOTO_PATH)));
//                    Log.i("MyActivity", "Flights " +objCust.toString());
                    customerList.addFirst(objCust);
                } while (objCur.moveToNext());
            }
        }
        return customerList;
    }

    public String generateCustomerQuery(Customer cust) {
        String query = "";
        boolean isFirst=true;
        if (cust.getName() != null && !cust.getName().equals("")) {
            if(isFirst){
                isFirst=false;
            }
            query +=" "+CUSTOMER_NAME+" = '"+cust.getName()+"' ";
        }
        if (cust.getId()>=0) {
            if(isFirst){
                isFirst=false;
                query +=" "+ CUSTOMER_ID+" = '"+cust.getId()+"' ";
            }else{
                query +=" AND "+CUSTOMER_ID+" = '"+cust.getId()+"' ";
            }
        }

        if (cust.getPassportNum()>=0) {
            if(isFirst){
                isFirst=false;
                query +=" "+ CUSTOMER_PASSPORT_NUM+" = '"+cust.getPassportNum()+"' ";
            }else{
                query +=" AND "+CUSTOMER_PASSPORT_NUM+" = '"+cust.getPassportNum()+"' ";
            }
        }

        if (cust.getAddress() != null && !cust.getAddress() .equals("")) {
            if(isFirst){
                isFirst=false;
                query +=" "+ CUSTOMER_ADDRESS+" = '"+cust.getAddress() +"' ";
            }else{
                query +=" AND "+CUSTOMER_ADDRESS+" = '"+cust.getAddress() +"' ";
            }
        }
        return query;
    }


    public void dropTableCustomers() {
        SQLiteDatabase db;
        db = getSQLObject();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
    }

    public Exception updateCustomer(Customer cust) {
        SQLiteDatabase db;
        db = getSQLObject();
        try {
//            Log.i("MyActivity", "Flights " +cust.toString());
            ContentValues cv = new ContentValues();
            //These Fields should be your String values of actual column names
            cv.put(CUSTOMER_NAME, cust.getName());
            cv.put(CUSTOMER_ID, cust.getId());
            cv.put(CUSTOMER_PASSPORT_NUM, cust.getPassportNum());
            cv.put(CUSTOMER_ADDRESS, cust.getAddress());
            cv.put(CUSTOMER_PHOTO_PATH, cust.getPhotoPath());
            db.update(TABLE_CUSTOMER, cv, KEY_ID + "=" + cust.getDbId(), null);
            db.close();
            return null;
        } catch (Exception e) {
            db.close();
            return e;
        }
    }


    public Exception bookFlight(long custId, long flightId) {
        SQLiteDatabase db;
        db = getSQLObject();
        try {
//            Log.i("Debug", "Flights " +objCustomer.toString());
            db.execSQL("insert into " + TABLE_CUSTOMER_TO_DESTINATION + " (" + KEY_CUSTOMER_ID + "," + KEY_FLIGHT_ID
                    + ") values ('" + custId + "','" + flightId + "')");
            db.close();

            return null;
        } catch (Exception e) {
            db.close();
            return e;
        }
    }

    public Exception loadBookFlight(Booking booking) {
        SQLiteDatabase db;
        db = getSQLObject();
        try {
//            Log.i("Debug", "Flights " +objCustomer.toString());
            db.execSQL("insert into " + TABLE_CUSTOMER_TO_DESTINATION + " (" + KEY_ID+ "," +KEY_CUSTOMER_ID + "," + KEY_FLIGHT_ID
                    + ") values ('" +booking.getDbId()+ "','" + booking.getcId() + "','" + booking.getfId() + "')");
            db.close();

            return null;
        } catch (Exception e) {
            db.close();
            return e;
        }
    }

    public LinkedList<Booking> getBookingList() {
        SQLiteDatabase db;
        db = getSQLObject();
        String SqlQuery = "select * from " + TABLE_CUSTOMER_TO_DESTINATION;
        Cursor objCur = db.rawQuery(SqlQuery, null);
        LinkedList<Booking> bookingList = new LinkedList<Booking>();
        if (objCur != null) {
            if (objCur.moveToFirst()) {
                do {
                    Booking objBooking = new Booking();
                    objBooking.setDbId(objCur.getInt(objCur.getColumnIndex(KEY_ID)));
                    objBooking.setfId(objCur.getInt(objCur.getColumnIndex(KEY_FLIGHT_ID)));
                    objBooking.setcId(objCur.getInt(objCur.getColumnIndex(KEY_CUSTOMER_ID)));
//                    Log.i("MyActivity", "Destinations " +objDest.toString());
                    bookingList.addFirst(objBooking);
                } while (objCur.moveToNext());
            }
        }
        // String SqlQuery = "select * from " + TABLE_FLIGHT+" where "+FLIGHT_DEP_DEST+" = "+destId;
        return bookingList;
    }


    public Exception deleteTablebooking() {
        SQLiteDatabase db;
        db = getSQLObject();
        try {
            db.execSQL("delete from " + TABLE_CUSTOMER_TO_DESTINATION);
            db.close();
            return null;
        } catch (Exception e) {
            db.close();
            return e;
        }
    }


    public Exception clearDatabaseData() {
        Exception e;

        e = deleteTablebooking();
        if (e != null)
            return e;
        e = deleteTableCustomer();
        if (e != null)
            return e;
        e = deleteTableFlight();
        if (e != null)
            return e;
        e = deleteTableDestination();
        if (e != null)
            return e;
        return e;
    }
}
