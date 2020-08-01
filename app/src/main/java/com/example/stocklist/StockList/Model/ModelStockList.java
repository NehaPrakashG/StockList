package com.example.stocklist.StockList.Model;


import android.text.TextUtils;

import com.example.stocklist.Interface.Istock;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.OkHttpClient;


public class ModelStockList implements Istock.Model {
    private OkHttpClient client;
    HashMap<String, ModelSearch> SearchListHash = new HashMap<>();
    HashMap<String, ModelSearch> getSearchListHash;
    HashMap<String, ModelSearch> getfilterListHash;


    @Override
    public HashMap<String, ModelSearch> GetFilteredList(ModelSearch stocks) {

        if (!SearchListHash.containsKey(stocks.getId())) {
            SearchListHash.put(stocks.getId(), stocks);
        }
        SearchListHash.putAll(SearchListHash);


        return SearchListHash;


    }


    @Override
    public HashMap<String, ModelSearch> MatchStockNames(String stocknames, HashMap<String, ModelSearch> map, int Tag) {
        getSearchListHash = new HashMap<>();
        for (Map.Entry<String, ModelSearch> nameTitle : map.entrySet()) {

            if (Tag == 1) {
                if (nameTitle.getValue().getName().trim().toLowerCase().contains(stocknames) ||
                        nameTitle.getValue().getName().toUpperCase().contains(stocknames)) {
                    getSearchListHash.put(nameTitle.getKey(), nameTitle.getValue());
                }
            } else if (Tag == 2) {
                ModelSearch modelSearch= new ModelSearch();
                modelSearch=nameTitle.getValue();
                String str = TextUtils.join(",", modelSearch.getCompanyType());
                if ( str.toLowerCase().contains(stocknames.toLowerCase())) {
                    getSearchListHash.put(nameTitle.getKey(), nameTitle.getValue());


                }

                }


            }




        return getSearchListHash;

    }
}


