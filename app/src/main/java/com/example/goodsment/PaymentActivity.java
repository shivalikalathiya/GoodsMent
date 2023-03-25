package com.example.goodsment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;


public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {


    Button payment;
    TextView paytext;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        payment=findViewById(R.id.payment);
        paytext=findViewById(R.id.paytext);

        Checkout.preload(getApplicationContext());


        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                makepayment();
            }
        });
    }
    private void makepayment() {
             Checkout checkout = new Checkout();
            checkout.setKeyID("rzp_test_sqrqKLzFmNULDF");

            checkout.setImage(R.drawable.applogo);
            final Activity activity = this;


            try {
                JSONObject options = new JSONObject();

                options.put("name", "GoodsMent");
                options.put("description", "Reference No. #123456");
                options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
                //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
                options.put("theme.color", "#3399cc");
                options.put("currency", "INR");
                options.put("amount", "50000");//500*100
                options.put("prefill.email", "shivalikalathiya48@gmail.com");
                options.put("prefill.contact","9081063320");
                JSONObject retryObj = new JSONObject();
                retryObj.put("enabled", true);
                retryObj.put("max_count", 4);
                options.put("retry", retryObj);

                checkout.open(activity, options);

            } catch(Exception e) {
                Log.e("TAG", "Error in starting Razorpay Checkout", e);
            }

    }


    @Override
    public void onPaymentSuccess(String s) {
        paytext.setText("Sucessful payment ID :"+s);
    }

    @Override
    public void onPaymentError(int i, String s) {

        paytext.setText("Failed and cause is :"+s);

    }
}