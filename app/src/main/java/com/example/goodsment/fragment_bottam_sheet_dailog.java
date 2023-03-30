package com.example.goodsment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class fragment_bottam_sheet_dailog extends Fragment {

    private CardView cardLocal;

    View view ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bottam_sheet_dailog, container, false);

        cardLocal=view.findViewById(R.id.cardLocal);

        cardLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Map2.class);
                startActivity(intent);
            }
        });

        return  view;


    }
}