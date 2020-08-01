package com.example.stocklist.StockList.Presenter;

import android.os.Handler;
import android.view.View;


import com.example.stocklist.Interface.Istock;
import com.example.stocklist.Networking.WebSocketEcho;
import com.example.stocklist.StockList.Model.ModelSearch;
import com.example.stocklist.StockList.Model.ModelStockList;
import com.example.stocklist.StockList.Model.ModelStocks;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import okhttp3.WebSocket;

import static android.os.Looper.getMainLooper;

public class PresenterStockList implements Istock.Presenter, WebSocketEcho.WebSocketInteractor {
    private Istock.View stockListView;

    private Istock.Model stocklListModel;
    HashMap<String, ModelSearch> data;
    public PresenterStockList(Istock.View stockListView) {
        WebSocketEcho.getInstance(this);
        this.stockListView = stockListView;
        this.stocklListModel = new ModelStockList();
        stockListView.showProgress();

    }

   public HashMap<String, ModelSearch> GetSearched(ModelSearch modelSearch) {
        data = new HashMap<>();
        stockListView.showProgress();
        data = stocklListModel.GetFilteredList(modelSearch);
        stockListView.hideProgress();
        return data;
    }

    public HashMap<String, ModelSearch> GetSearched(String match, HashMap<String, ModelSearch> map,int Tag) {
        stockListView.showProgress();
        HashMap<String, ModelSearch> data = stocklListModel.MatchStockNames(match, map,Tag);
        stockListView.hideProgress();
        return data;
    }

    @Override
    public void onDestroy() {
        this.stockListView = null;

    }


    @Override
    public void onOpen(WebSocket webSocket) {

    }

    @Override
    public void onGetMessage(String message) {
        stockListView.RefreshList();


        Gson gson = new Gson();
        if (message != null) {
            new Handler(getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONArray jsonArray= new JSONArray(message);
                        for(int i=0;i<jsonArray.length();i++){
                            String data = jsonArray.get(i).toString();
                            ModelStocks modelStocks = gson.fromJson(data, ModelStocks.class);
                            ModelSearch modelSearch = gson.fromJson(data, ModelSearch.class);
                            stocklListModel.GetFilteredList(modelSearch);
                            stockListView.AdapterData(modelStocks,modelSearch);


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }}); }

    }
}