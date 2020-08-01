package com.example.stocklist;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.stocklist.Common.Utils;
import com.example.stocklist.StockDetails.Model.Model.ModelStockDetail;
import com.squareup.picasso.Picasso;

public class FragmentStockDetails extends Fragment {
    View view;
    TextView name_tv, price_tv, id_tv, highprice_tv, address_tv, website_tv;
    ImageView image_vw;
    ModelStockDetail modelStockDetail;

    public FragmentStockDetails() {
    }

    public static FragmentStockDetails newInstance() {
        FragmentStockDetails fragment = new FragmentStockDetails();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            modelStockDetail = bundle.getParcelable("detail"); // Gets selected  data
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        menuItem.setVisible(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_stock_details, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initUi();
        return view;
    }

    private void initUi() {
        name_tv = view.findViewById(R.id.name_tv);
        id_tv = view.findViewById(R.id.id_tv);
        website_tv = view.findViewById(R.id.website_tv);
        price_tv = view.findViewById(R.id.price_tv);
        address_tv = view.findViewById(R.id.address_tv);
        highprice_tv = view.findViewById(R.id.highprice_tv);
        id_tv = view.findViewById(R.id.id_tv);
        image_vw = view.findViewById(R.id.image_vw);

        SetData(); // setting model data to views
    }

    private void SetData() {
        name_tv.setText(modelStockDetail.getName());
        id_tv.setText(modelStockDetail.getId());
        price_tv.setText(Utils.Convertdouble(modelStockDetail.getPrice()));
        address_tv.setText(modelStockDetail.getAddress());
        highprice_tv.setText(Utils.Convertdouble(modelStockDetail.getAllTimeHigh()));
        website_tv.setText(modelStockDetail.getWebsite());
        Picasso.get().load(modelStockDetail.getImageUrl()).into(image_vw);

        website_tv.setOnClickListener(new View.OnClickListener() {  //launches website in browser
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent("android.intent.action.MAIN");
                    i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                    i.addCategory("android.intent.category.LAUNCHER");
                    i.setData(Uri.parse(website_tv.getText().toString()));
                    startActivity(i);
                } catch (ActivityNotFoundException e) {
                    // Chrome is not installed
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(website_tv.getText().toString()));
                    startActivity(i);
                }
            }
        });

    }


}