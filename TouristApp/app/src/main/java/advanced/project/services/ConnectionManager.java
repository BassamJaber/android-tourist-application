package advanced.project.services;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import advanced.project.DataModels.Booking;
import advanced.project.DataModels.Customer;
import advanced.project.DataModels.Destination;
import advanced.project.DataModels.Flight;


/**
 * Created by Bassam on 3/16/15.
 */

public class ConnectionManager {
    // The object that makes the request
    AsyncHttpClient client = new AsyncHttpClient();

    /*
    This interface defines the methods that should be implemented by the class that
    uses this connection manager. When a request finishes, it calls the methods defined by this interface.
     */

    public interface ConnectionManagerCallback{
        void getRequestSucceededWithArrayOfCustomers(ArrayList<Customer>list);
        void getRequestSucceededWithArrayOfDestination(ArrayList<Destination>list);
        void getRequestSucceededWithArrayOfFlight(ArrayList<Flight>list);
        void getRequestSucceededWithArrayOfBooking(ArrayList<Booking>list);
        void postRequestSucceededCustomer();
        void postRequestSucceededFlight();
        void postRequestSucceededDestination();
        void postRequestSucceededBooking();

    }

    public ConnectionManagerCallback connectionManagerDelegate;

    /* The method called when you want to make a request
     When requesting a JSON server API like the one we use,
     you should handle all methods of the JSON Handler
     In our case, since we are expecting an array of students in JSON format,
     then the second method of the handler will be called on success
     */
    public void perfromHTTPGetRequestCustomers(String url , RequestParams params) {
        client.get(url, null , new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // now the conversion from json array into array of student objects should take place,
                // of course using JACKSON library which simplifies the conversion task.
                ObjectMapper mapper = new ObjectMapper();
                try {
                    ArrayList<Customer> list = mapper.readValue( response.toString(), new TypeReference<ArrayList<Customer>>(){} );
                    if(connectionManagerDelegate != null){
                        connectionManagerDelegate.getRequestSucceededWithArrayOfCustomers(list);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // You should handle it in a real application
                Log.i("Connection Manager", "Failure " +throwable.getMessage().toString());
                // new AlertDialog.Builder(this).setTitle("Error !").setMessage(e.getMessage().toString())
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // You should handle it in a real application
                Log.i("Connection Manager", "Failure " +throwable.getMessage().toString());
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i("Connection Manager", "Success" +responseString);
                // You should handle it in a real application
            }
        });
    }

    public void perfromHTTPGetRequestFlight(String url , RequestParams params) {
        client.get(url, null , new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // now the conversion from json array into array of student objects should take place,
                // of course using JACKSON library which simplifies the conversion task.
                ObjectMapper mapper = new ObjectMapper();
                try {
                    ArrayList<Flight> list = mapper.readValue( response.toString(), new TypeReference<ArrayList<Flight>>(){} );
                    if(connectionManagerDelegate != null){
                        connectionManagerDelegate.getRequestSucceededWithArrayOfFlight(list);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // You should handle it in a real application
                Log.i("Connection Manager", "Failure " +throwable.getMessage().toString());
                // new AlertDialog.Builder(this).setTitle("Error !").setMessage(e.getMessage().toString())
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // You should handle it in a real application
                Log.i("Connection Manager", "Failure " +throwable.getMessage().toString());
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i("Connection Manager", "Success" +responseString);
                // You should handle it in a real application
            }
        });
    }

    public void perfromHTTPGetRequestDestination(String url , RequestParams params) {
        client.get(url, null , new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // now the conversion from json array into array of student objects should take place,
                // of course using JACKSON library which simplifies the conversion task.
                ObjectMapper mapper = new ObjectMapper();
                try {
                    ArrayList<Destination> list = mapper.readValue( response.toString(), new TypeReference<ArrayList<Destination>>(){} );
                    if(connectionManagerDelegate != null){
                        connectionManagerDelegate.getRequestSucceededWithArrayOfDestination(list);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // You should handle it in a real application
                Log.i("Connection Manager", "Failure " +throwable.getMessage().toString());
                // new AlertDialog.Builder(this).setTitle("Error !").setMessage(e.getMessage().toString())
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // You should handle it in a real application
                Log.i("Connection Manager", "Failure " +throwable.getMessage().toString());
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i("Connection Manager", "Success" +responseString);
                // You should handle it in a real application
            }
        });
    }



    public void perfromHTTPGetRequestBooking(String url , RequestParams params) {
        client.get(url, null , new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // now the conversion from json array into array of student objects should take place,
                // of course using JACKSON library which simplifies the conversion task.
                ObjectMapper mapper = new ObjectMapper();
                try {
                    ArrayList<Booking> list = mapper.readValue( response.toString(), new TypeReference<ArrayList<Booking>>(){} );
                    if(connectionManagerDelegate != null){
                        connectionManagerDelegate.getRequestSucceededWithArrayOfBooking(list);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // You should handle it in a real application
                Log.i("Connection Manager", "Failure " +throwable.getMessage().toString());
                // new AlertDialog.Builder(this).setTitle("Error !").setMessage(e.getMessage().toString())
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // You should handle it in a real application
                Log.i("Connection Manager", "Failure " +throwable.getMessage().toString());
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i("Connection Manager", "Success" +responseString);
                // You should handle it in a real application
            }
        });
    }


    public void perfromHTTPPostRequestOnUrlWithParamsCustomer(String url ,ArrayList<Customer> cust) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonStr = mapper.writeValueAsString(cust);
            StringEntity strEntity =  new StringEntity(jsonStr);
            client.post(null, url, strEntity, "application/json", new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (connectionManagerDelegate != null){
                        connectionManagerDelegate.postRequestSucceededCustomer();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i("Connection Manager", "Failure " +error.getMessage().toString());
                }

                @Override
                public void onFinish() {
                    if (connectionManagerDelegate != null){
                        connectionManagerDelegate.postRequestSucceededCustomer();
                    }
                }
            });
        }catch (Exception e){
            // handle exception
        }
    }

    public void perfromHTTPPostRequestOnUrlWithParamsDestination(String url ,ArrayList<Destination> dest) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonStr = mapper.writeValueAsString(dest);
            StringEntity strEntity =  new StringEntity(jsonStr);
            client.post(null, url, strEntity, "application/json", new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (connectionManagerDelegate != null){
                        connectionManagerDelegate.postRequestSucceededDestination();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i("Connection Manager", "Failure " +error.getMessage().toString());
                }

                @Override
                public void onFinish() {
                    if (connectionManagerDelegate != null){
                        connectionManagerDelegate.postRequestSucceededDestination();
                    }
                }
            });
        }catch (Exception e){
            // handle exception
        }
    }

    public void perfromHTTPPostRequestOnUrlWithParamsFlight(String url ,ArrayList<Flight> flight) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonStr = mapper.writeValueAsString(flight);
            StringEntity strEntity =  new StringEntity(jsonStr);
            client.post(null, url, strEntity, "application/json", new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (connectionManagerDelegate != null){
                        connectionManagerDelegate.postRequestSucceededFlight();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i("Connection Manager", "Failure " +error.getMessage().toString());
                }

                @Override
                public void onFinish() {
                    if (connectionManagerDelegate != null){
                        connectionManagerDelegate.postRequestSucceededFlight();
                    }
                }
            });
        }catch (Exception e){
            // handle exception
        }
    }



    public void perfromHTTPPostRequestOnUrlWithParamsBooking(String url ,ArrayList<Booking> booking) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonStr = mapper.writeValueAsString(booking);
            StringEntity strEntity =  new StringEntity(jsonStr);
            client.post(null, url, strEntity, "application/json", new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (connectionManagerDelegate != null){
                        connectionManagerDelegate.postRequestSucceededBooking();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i("Connection Manager", "Failure " +error.getMessage().toString());
                }

                @Override
                public void onFinish() {
                    if (connectionManagerDelegate != null){
                        connectionManagerDelegate.postRequestSucceededBooking();
                    }
                }
            });
        }catch (Exception e){
            // handle exception
        }
    }
    public void registerCallback(ConnectionManagerCallback callbackClass){
        connectionManagerDelegate = callbackClass;
    }
}
