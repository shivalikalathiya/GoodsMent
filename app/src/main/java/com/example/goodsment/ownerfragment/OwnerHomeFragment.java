package com.example.goodsment.ownerfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.goodsment.CardView;
import com.example.goodsment.R;
import com.example.goodsment.ownerLabourActivity;
import com.example.goodsment.ownerOrderActivity;

import java.util.ArrayList;

public class OwnerHomeFragment extends Fragment {

    ImageSlider imageSlider;

    CardView cardorder , cardlabour ;
    public OwnerHomeFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_owner_home, container, false);


        cardlabour = view.findViewById(R.id.cardlabour);
        cardorder = view.findViewById(R.id.cardorder);
        imageSlider =view.findViewById(R.id.imgslider);


        ArrayList<SlideModel> imagelist = new ArrayList<>();
        imagelist.add(new SlideModel(R.drawable.imgslider2,null));
        imagelist.add(new SlideModel(R.drawable.img_3,null));
        imagelist.add(new SlideModel(R.drawable.imgslider4,null));



        imageSlider.setImageList(imagelist);

        cardorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ownerOrderActivity.class));
            }
        });


        cardlabour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ownerLabourActivity.class));
            }
        });


        return view;

    }
}