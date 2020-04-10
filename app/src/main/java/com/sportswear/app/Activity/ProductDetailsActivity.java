package com.sportswear.app.Activity;
import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.sportswear.app.R;
import com.sportswear.app.Util.Constant_Api;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProductDetailsActivity extends AppCompatActivity {
    private Button addToCartButton;
    private ImageView productImage;
    private TextView productPrice;
    private TextView productName;
    private TextView productDescription;
    private String pid ="", state = "Normal", pName,pImage,pPrice,pDes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        pDes =  getIntent().getStringExtra("pDes");
        pPrice =  getIntent().getStringExtra("pPrice");
        pImage =  getIntent().getStringExtra("pImage");
        pName =  getIntent().getStringExtra("pName");
        productDescription=  findViewById(R.id.single_product_description_details);
        productPrice=findViewById(R.id.single_product_price_details);
        productName=  findViewById(R.id.single_product_name_details);
        productImage= findViewById(R.id.single_product_image_details);

      // getProductDetails();
productPrice.setText("$"+pPrice);
productDescription.setText(pDes);
productName.setText(pName);
        Glide.with(ProductDetailsActivity.this).load(pImage).into(productImage);

//productImage.setText(pid);
    }

//    private void getProductDetails() {
//        AsyncHttpClient singleProduct = new AsyncHttpClient();
//        singleProduct.get(Constant_Api.single+pid ,null, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                Log.d("Response", new String(responseBody));
//                String res = new String(responseBody);
//
//                try {
//                    JSONObject jsonObject = new JSONObject(res);
//
//                    JSONArray jsonArray = jsonObject.getJSONArray("SINGLE_PRODUCT");
//
//                    for (int i = 0; i < jsonArray.length(); i++) {
//
//                        JSONObject object = jsonArray.getJSONObject(i);
//
//                         pPrice =object.getString("price");
//                         pDes = object.getString("des");
//                         pName = object.getString("name");
//                         pImage = object.getString("image");
//
//                    }
//
//                    try {
//                        Glide.with(ProductDetailsActivity.this).load(pImage).into(productImage);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    productName.setText(pName);
//                    productDescription.setText(pDes);
//                    productPrice.setText(pPrice);
//                    //progressBar.hide();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//
//            }
//        });
//    }
}
