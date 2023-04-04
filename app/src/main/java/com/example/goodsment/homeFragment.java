package com.example.goodsment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.ArrayList;


public class homeFragment extends Fragment {

    ImageSlider imageSlider;

    private CardView goodsbike,goodstruck;

    Button gotosecond,gosecond;
    private BottomSheetDialog bottomSheetDialog;

    View view;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


            view = inflater.inflate(R.layout.fragment_home, container, false);

        FirebaseCrashlytics.getInstance();
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("Home");
        }

            imageSlider = view.findViewById(R.id.image_slider);

            goodstruck=view.findViewById(R.id.goodstruck);
            goodsbike=view.findViewById(R.id.goodsbike);



            goodstruck.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingInflatedId")
                @Override
                public void onClick(View v) {
                    bottomSheetDialog = new BottomSheetDialog(getActivity(),R.style.BottamSheetDailogTheme);
                    View SheetView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.bottamsheetlayout,null);
                    bottomSheetDialog.setContentView(SheetView);
                    bottomSheetDialog.show();
                    SheetView.findViewById(R.id.gotosecond).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(),Map2.class);
                            startActivity(intent);
                        }
                    });

                    SheetView.findViewById(R.id.gosecond).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(),Map2.class);
                            startActivity(intent);
                        }
                    });


                }
            });






            //imgslider
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.topimg, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.imgslider2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.imgslider4, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels,ScaleTypes.FIT);



        return view;



    }
}
