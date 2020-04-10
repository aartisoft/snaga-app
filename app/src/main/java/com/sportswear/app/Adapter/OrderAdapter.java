package com.sportswear.app.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sportswear.app.Activity.ActivityCart;
import com.sportswear.app.Activity.ActivityConfirmMessage;
import com.sportswear.app.Activity.ActivityReservation;
import com.sportswear.app.Activity.ShowCaert;
import com.sportswear.app.Util.Constant_Api;
import com.sportswear.app.Util.Method;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import io.fabric.sdk.android.services.concurrency.AsyncTask;

public class OrderAdapter extends AsyncTask<String,Void,String> {
    AlertDialog alertDialog;

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This will normally run on a background thread. But to better
     * support testing frameworks, it is recommended that this also tolerates
     * direct execution on the foreground thread, as part of the {@link #execute} call.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param voids The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    Context context;
   // int order_id;
    public OrderAdapter(Context ctx) {
     context =  ctx;
    }

    @Override
    protected String doInBackground(String... voids) {

        String type = voids[0];
       // String regUrl =  "http://snaga.kacee.co.zw/snaga/place_order.php";
        String regUrl = Constant_Api.add_order;

        if (type.equals("OrderListFinal")){
            try{
                String Phone = voids[1];
                String Address = voids[2];
                String Email = voids[3];
                String order = voids[4];
                String name = voids[5];
                String user_ID = voids[6];
                String Comment = voids[7];
                String mobileid = voids[8];
                String OrderList = voids[9];
                URL url = new URL(regUrl);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter( new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(Phone,"UTF-8")+"&"
                        + URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(Address,"UTF-8")+"&"
                        + URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(Email,"UTF-8")+"&"
                        + URLEncoder.encode("order_list","UTF-8")+"="+URLEncoder.encode(order,"UTF-8")+"&"
                        + URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                        + URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(user_ID,"UTF-8")+"&"
                        + URLEncoder.encode("comment","UTF-8")+"="+URLEncoder.encode(Comment,"UTF-8")+"&"
                        + URLEncoder.encode("json_order_list","UTF-8")+"="+URLEncoder.encode(OrderList,"UTF-8")+"&"
                        + URLEncoder.encode("mobileid","UTF-8")+"="+URLEncoder.encode(mobileid,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream,"iso-8859-1"));
                String result = "success";
                String line= "";
                while ((line = bufferedReader.readLine())!= null){
                    result += line;

                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            }catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }


    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");


    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();
        try {
            String[] separated = result.split("end");

            String response = separated[1];
            JSONObject jsonObject = new JSONObject(response);

            JSONArray jsonArray = jsonObject.getJSONArray("ORDER_PLACED");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject object = jsonArray.getJSONObject(i);
                String error = object.getString("error");
                int order_id = Integer.parseInt(object.getString("order_id"));
                String message = object.getString("message");
//                Intent intent = new Intent(context.getApplicationContext(),
//                        ActivityConfirmMessage.class).putExtra("order_id", order_id);
//               // dbhelper.deleteAllData();
//                context.startActivity(intent);
               // finish();
                Intent intent = new Intent();
            intent.setClass(context.getApplicationContext(), ActivityConfirmMessage.class)
            .putExtra("order_id",order_id);
            context.startActivity(intent);
            alertDialog.dismiss();
             //   Toast.makeText(context, "orderID="+ order_id, Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }





//        //mobileid= Method.getAndroidID(ShowCaert.this);
//            Intent intent = new Intent();
//            intent.setClass(context.getApplicationContext(), ActivityConfirmMessage.class);
//            context.startActivity(intent);
//            alertDialog.dismiss();

    }
}

