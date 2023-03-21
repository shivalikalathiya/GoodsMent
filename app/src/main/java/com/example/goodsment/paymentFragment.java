package com.example.goodsment;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.razorpay.Checkout;

public class paymentFragment extends Fragment {


    Button button;

    View view;
    public paymentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_payment, container, false);

        button=view.findViewById(R.id.button);

        Checkout.preload(getApplicationContext());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }

            private void startPayment() {
            }
        });

        return view;
    }
}