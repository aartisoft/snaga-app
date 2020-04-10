package com.sportswear.app.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sportswear.app.Adapter.OrderAdapter;
import com.sportswear.app.Helper.DBHelper;
import com.sportswear.app.Item.CityList;
import com.sportswear.app.Item.SubCityList;
import com.sportswear.app.R;
import com.sportswear.app.Util.Constant_Api;
import com.sportswear.app.Util.Method;
import com.sportswear.app.Util.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;

public class ShowCaert extends AppCompatActivity {
    Button btnSend;

    EditText edtName;
    EditText edtNumberOfPeople;
    EditText edtPhone;
    EditText edtEmail;
    EditText edtOrderList;
    EditText edtComment;
    EditText edtAddress;
    EditText edtRoomNo;
    static EditText dateText, timeText;

    static Button btnDate;
    static Button btnTime;

    ScrollView sclDetail;
    ProgressBar progressBar;
    TextView txtAlert;

    public static DBHelper dbhelper;
    ArrayList<ArrayList<Object>> data;

    String Name, NumberOfPeople, Date, Time, Phone, Date_Time, Address, Email, roomNo;
    String OrderList = "";
    String Comment = "",mobileid;

    private static int mYear;
    private static int mMonth;
    private static int mDay;
    private static int mHour;
    private static int mMinute;
    Spinner spinCity, spinSubCity;

    // declare static variables to store tax and currency data
    public static double Tax;
    public static String Currency = "  USD";

    public static final String TIME_DIALOG_ID = "timePicker";
    public static final String DATE_DIALOG_ID = "datePicker";

    String Result;
    String TaxCurrencyAPI;
    int IOConnect = 0;
    private String type;
    double amount, total;
    String cwall;
    private String order, cat_type, date, time, ORDER_LIST = null;
    double payable;

    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter adapter;
    //  private String selectedRoomNo="Please select Room No";
    private RelativeLayout rel_room;
    List<CityList> cities = new ArrayList<>();
    List<String> cityList = new ArrayList<>();
    List<SubCityList> subcities = new ArrayList<>();
    List<String> subcityList = new ArrayList<>();
    private String city, subcity;
    Double delivery;
    private ArrayAdapter<String> cityAdapter;
    private ProgressDialog dialog;

    JSONObject jsonObject;
    private String appendlist = "";
    private ArrayAdapter<String> subcityAdapter;
    private int spinpos = 0;
    private String coupon_code,user_ID;

    //double amount,total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_caert);
     dbhelper = new DBHelper(this);

        edtOrderList = findViewById(R.id.edtOrderList);
        edtName = (EditText) findViewById(R.id.edtName);
        edtNumberOfPeople = (EditText) findViewById(R.id.edtNumberOfPeople);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        //edtRoomNo = (EditText) findViewById(R.id.edtRoomNo);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        btnDate = (Button) findViewById(R.id.btnDate);
        btnTime = (Button) findViewById(R.id.btnTime);
        spinCity = findViewById(R.id.city);
        spinSubCity = findViewById(R.id.subcity);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtComment = (EditText) findViewById(R.id.edtComment);
        btnSend = (Button) findViewById(R.id.btnSend);
        // sclDetail = (ScrollView) findViewById(R.id.sclDetail);
        progressBar = (ProgressBar) findViewById(R.id.prgLoading);
        txtAlert = (TextView) findViewById(R.id.txtAlert);
        //autoCompleteTextView = findViewById(R.id.auto_text_bg);
        rel_room = findViewById(R.id.rel_room);

        edtName.setText(SharedPref.getUserName(this));
        edtPhone.setText(SharedPref.getMobileNumber(this));
        user_ID = SharedPref.getUserId(this);



      //  new getTaxCurrency().execute();
    }

    public  void placeOrder(View view){
        //to get time and date
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        Date = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        Time = currentTime.format(calendar.getTime());
        String uniqueID = UUID.randomUUID().toString();


        dbhelper.deleteAllData();
                dbhelper.close();
//        Log.e("appendlist", order + appendlist);
//        Log.e("jsonORDERLIST", ORDER_LIST);
//       "order_list", order + appendlist;
//        nameValuePairs.add(new BasicNameValuePair("json_order_list", ORDER_LIST));

        mobileid= Method.getAndroidID(ShowCaert.this);
        Date_Time = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        Name = edtName.getText().toString();
        Email = edtEmail.getText().toString();
        Address = edtAddress.getText().toString();
       Phone = edtPhone.getText().toString();
        cat_type= "advance";
        Comment = edtComment.getText().toString();
        if (Name.equalsIgnoreCase("")) {
            Toast.makeText(ShowCaert.this, R.string.entername, Toast.LENGTH_SHORT).show();
            edtName.requestFocus();
        } else if (Phone.equalsIgnoreCase("")) {
            Toast.makeText(ShowCaert.this, R.string.enterphone, Toast.LENGTH_SHORT).show();
        } else if (Address.equalsIgnoreCase("")) {
            Toast.makeText(ShowCaert.this, R.string.enteradd, Toast.LENGTH_SHORT).show();
            edtAddress.requestFocus();
        }else if (Email.equalsIgnoreCase("")) {
                       Toast.makeText(ShowCaert.this, R.string.enteremail, Toast.LENGTH_SHORT).show();
                       edtEmail.requestFocus();
                   }  /*else if ((data.size() == 0)) {
                       Toast.makeText(ActivityReservation.this, R.string.order_alert, Toast.LENGTH_SHORT).show();
                   }*/
                   else if (Phone.length() < 9) {
            Toast.makeText(ShowCaert.this, R.string.tenphone, Toast.LENGTH_SHORT).show();
            edtPhone.requestFocus();
//                } else if (city == null || cities.equals("")) {
//                    Toast.makeText(ShowCaert.this, "Please Select City", Toast.LENGTH_SHORT).show();
//                    edtEmail.requestFocus();
        } else {


            String type="OrderListFinal";

            OrderAdapter orderAdapter = new OrderAdapter(this);
            orderAdapter.execute(type,Phone , Address,Email, OrderList,Name ,user_ID,Comment,mobileid,OrderList,uniqueID);
        }

    }
//        btn_send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in = new Intent(ActivityCart.this, ShowCaert.class);
//                startActivity(in);
//            }
//        });

    @Override
    protected void onStart() {
        super.onStart();
        getDataFromDatabase();
    }
    public void getDataFromDatabase() {

        data = dbhelper.getAllData();

        double Order_price = 0;
        double Total_price = 0;
        double tax = 0;

        for (int i = 0; i < data.size(); i++) {
            ArrayList<Object> row = data.get(i);

            String Menu_name = row.get(1).toString();
            String Quantity = row.get(2).toString();

            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat formatData = (DecimalFormat)numberFormat;
            formatData.applyPattern("#.##");
            double Sub_total_price = Double.parseDouble(formatData.format(Double.parseDouble(row.get(3).toString())));

            Order_price += Sub_total_price;

            OrderList += (Quantity + " " + Menu_name + " " + Sub_total_price + " USD,\n");
            total+=Order_price;
        }

        if (OrderList.equalsIgnoreCase("")) {
            OrderList += getString(R.string.no_order_menu);
        }

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat formatData = (DecimalFormat)numberFormat;
        formatData.applyPattern("#.##");
        //tax = Double.parseDouble(formatData.format(Order_price * (Tax / 100)));
        //Total_price = Double.parseDouble(formatData.format(Order_price - tax));
        OrderList += "\nTotal Order : " + Order_price + " " + Currency ;
//        OrderList += "\nWallet Amount Used : " + cwall + " " + Currency ;
//        OrderList += "\nTotal Payable : " + (total-Integer.parseInt(cwall)) + " " + Currency ;
        edtOrderList.setText(OrderList);
    }
    public class sendData extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(ShowCaert.this, "", getString(R.string.placing_order), true);

        }

        @Override
        protected Void doInBackground(Void... params) {
            //Toast.makeText(ActivityReservation.this, "City :"+city, Toast.LENGTH_SHORT).show();
            Result = placeOrder(Name, "1", Phone, Comment, Email);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            dialog.dismiss();
            resultAlert(Result);
        }
    }

    public void resultAlert(String HasilProses) {

        try {
            String[] separated = HasilProses.split("end");

            String response = separated[1];
            JSONObject jsonObject = new JSONObject(response);

            JSONArray jsonArray = jsonObject.getJSONArray("ORDER_PLACED");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject object = jsonArray.getJSONObject(i);
                String error = object.getString("error");
                int order_id = Integer.parseInt(object.getString("order_id"));
                String message = object.getString("message");
                Intent intent = new Intent(ShowCaert.this,
                        ActivityConfirmMessage.class).putExtra("order_id", order_id);
                dbhelper.deleteAllData();
                startActivity(intent);
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private String placeOrder(String name, String number_of_people, String phone, String comment, String email) {
        //dialog = ProgressDialog.show(ActivityReservation.this, "", getString(R.string.placing_order), true);
        String amount = String.valueOf(cwall);
//        if (!cat_type.equals("advance")) {
//            date = "";
//            time = "";
//        }
        ORDER_LIST = String.valueOf(jsonObject);
        if (comment == null)
            comment = "No Comments";
        String result = "";
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(Constant_Api.add_order);
        String date_time = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        ;
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(15);
            nameValuePairs.add(new BasicNameValuePair("user_id", SharedPref.getUserId(this)));
            nameValuePairs.add(new BasicNameValuePair("name", name));
          //  nameValuePairs.add(new BasicNameValuePair("number_of_people", number_of_people));
            nameValuePairs.add(new BasicNameValuePair("date_time", ""));
            nameValuePairs.add(new BasicNameValuePair("phone", phone));
            nameValuePairs.add(new BasicNameValuePair("amount", amount));
            Log.e("appendlist", order + appendlist);
            Log.e("jsonORDERLIST", ORDER_LIST);
            nameValuePairs.add(new BasicNameValuePair("order_list", order + appendlist));
            nameValuePairs.add(new BasicNameValuePair("json_order_list", ORDER_LIST));
            nameValuePairs.add(new BasicNameValuePair("comment", Comment));
            nameValuePairs.add(new BasicNameValuePair("city", city));
            nameValuePairs.add(new BasicNameValuePair("sub_city", subcity));
            nameValuePairs.add(new BasicNameValuePair("email", Email));
            nameValuePairs.add(new BasicNameValuePair("app_id", Constant_Api.HOTEL_ID));
            nameValuePairs.add(new BasicNameValuePair("mobileid", Method.getAndroidID(ShowCaert.this)));
            nameValuePairs.add(new BasicNameValuePair("address", Address));
            nameValuePairs.add(new BasicNameValuePair("cat_type", cat_type));
            nameValuePairs.add(new BasicNameValuePair("coupon_code", coupon_code));

//            if (cat_type.equals("advance")) {
//                nameValuePairs.add(new BasicNameValuePair("date", date));
//                nameValuePairs.add(new BasicNameValuePair("time", time));
//            }

            nameValuePairs.add(new BasicNameValuePair("order_type", Constant_Api.TYPE_HOME));
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
            HttpResponse response = client.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity());
            Log.e("placeorder", responseBody);
            result = responseBody;
            //dialog.dismiss();
        } catch (Exception ex) {
            result = "Unable to connect.";
        }
        return result;



        /*String amount = String.valueOf(cwall);
        if (!cat_type.equals("advance")) {
            date = "";
            time = "";
        }
        if (comment==null)
            comment="No Comments";
        String url = Constant_Api.add_order + "user_id=" + SharedPref.getUserId(this) + "&name=" + name
                + "&number_of_people=" + number_of_people + "&date_time=" + "" + "&phone=" + phone
                + "&amount=" + amount + "&order_list=" + order + "&json_order_list=" + ORDER_LIST
                + "&comment=" + comment + "&city=" + city + "&email=" + email
                + "&app_id=" + Constant_Api.HOTEL_ID + "&mobileid=" + Method.getAndroidID(ActivityReservation.this) + "&address=" + ""
                + "&cat_type=" + cat_type + "&date=" + date + "&time=" + time + "&order_type=" + Constant_Api.TYPE_HOME;

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("App-Id", Constant_Api.HOTEL_ID);
        client.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.e("OrderResponse", new String(responseBody));
                String res = new String(responseBody);
                String[] separated = res.split("end");

                String response= separated[1];

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("ORDER_PLACED");

                    for (int i = 0; i <= jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        String error = object.getString("error");
                        int order_id = object.getInt("order_id");
                        String success = object.getString("message");

                        if (error.equals("false")) {
                            Intent intent = new Intent(ActivityReservation.this, ActivityConfirmMessage.class).putExtra("order_id", order_id);
                            dbhelper.deleteAllData();
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), success, Toast.LENGTH_LONG).show();
                        }

                    }
                    dialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("catchres", e.toString());
                    Toast.makeText(ActivityReservation.this, "Error Response", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("failure", error.toString());
                Toast.makeText(ActivityReservation.this, "Error Response", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    public String getRequest(
            String name,
            String number_of_people,
            String date_time,
            String phone,
            String order_list,
            String comment,
            String email) {
        String result = "";
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(Constant_Api.add_order);
//        HttpClient client = new DefaultHttpClient();
//        HttpPost request = new HttpPost(Constant_Api.add_order);

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(15);
            nameValuePairs.add(new BasicNameValuePair("user_id", SharedPref.getUserId(this)));
            nameValuePairs.add(new BasicNameValuePair("name", name));
//            nameValuePairs.add(new BasicNameValuePair("number_of_people", number_of_people));
            nameValuePairs.add(new BasicNameValuePair("date_time", ""));
            nameValuePairs.add(new BasicNameValuePair("amount", String.valueOf(cwall)));
            nameValuePairs.add(new BasicNameValuePair("phone", phone));
            nameValuePairs.add(new BasicNameValuePair("order_list", order));
            nameValuePairs.add(new BasicNameValuePair("comment", comment));
            nameValuePairs.add(new BasicNameValuePair("city", city));
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("app_id", Constant_Api.HOTEL_ID));
            nameValuePairs.add(new BasicNameValuePair("mobileid", Method.getAndroidID(ShowCaert.this)));
            nameValuePairs.add(new BasicNameValuePair("address", Address));
            nameValuePairs.add(new BasicNameValuePair("cat_type", cat_type));
//            if (cat_type.equals("advance")) {
//                nameValuePairs.add(new BasicNameValuePair("date", date));
//                nameValuePairs.add(new BasicNameValuePair("time", time));
//            }

            nameValuePairs.add(new BasicNameValuePair("order_type", type));
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
            HttpResponse response = client.execute(request);
            result = request(response);
        } catch (Exception ex) {
            result = "Unable to connect.";
        }
        return result;
    }
    private static String pad(int c) {
        if (c >= 10) {
            return String.valueOf(c);
        } else {
            return "0" + String.valueOf(c);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dbhelper.close();
        finish();
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public static String request(HttpResponse response) {
        String result = "";
        try {
            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                str.append(line + "\n");
            }
            in.close();
            result = str.toString();
        } catch (Exception ex) {
            result = "Error";
        }
        return result;
    }


}
