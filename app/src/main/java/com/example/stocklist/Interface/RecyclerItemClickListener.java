package com.example.stocklist.Interface;

import android.view.View;

import com.example.stocklist.StockList.Model.ModelSearch;

import java.util.HashMap;

public interface RecyclerItemClickListener {
    void onClickItem(View view, HashMap<String,ModelSearch> modelSearchmap);

}
