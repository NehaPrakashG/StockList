package com.example.stocklist.Common;

import android.app.Activity;
import android.content.Context;

import com.example.stocklist.StockDetails.Model.Model.ModelStockDetail;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class ResponseParser {
    JSONObject Object;
    ModelStockDetail modelStockDetail = new ModelStockDetail();
    private Activity activity;
    private Context context;
    private Gson gson;

    public ResponseParser(Activity activity) {

        this.activity = activity;
        if (activity.getApplicationContext() != null) {
            this.context = activity.getApplicationContext();
        }
        gson = new Gson();

    }

    public ResponseParser(Context context) {
        this.context = context;
        gson = new Gson();
    }

    public ModelStockDetail detailsResponseModel(ModelStockDetail json, int statusCode) {
        String jsonString = gson.toJson(json);
        try {
            Object = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {

            JSONObject obj = new JSONObject(jsonString);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return modelStockDetail;
    }


}


