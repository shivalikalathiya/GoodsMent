package com.example.goodsment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class FragmentA extends Fragment {

    ListView lv;
    SearchView searchView;
    ArrayAdapter<String> adapter;
    String[] data = {"Timber/Plywood/Laminate","Electronics/Home Appliances","General","Catering/Restaurant",
            "Textile/Fashion Accessories","Furniture","Chemicals/Paints","Food items","Medical/Healthcare/Fitness/Pharmacy",
    "Books/Stationery/Toys/","Plastic/Rubber","Building/Construction","House Shifting"};


    public FragmentA() {
        // Required empty public constructor
    }




    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_a,container,false);
        lv = (ListView) view.findViewById(R.id.idListView);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,data);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Selected GoodsType.", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}