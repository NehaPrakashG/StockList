package com.example.stocklist.StockDetails.Model.Presenter;

import com.example.stocklist.Interface.IstockDetails;
import com.example.stocklist.Networking.Callback;
import com.example.stocklist.Networking.ResponseTask;
import com.example.stocklist.StockDetails.Model.Model.ModelStockDetail;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class PresenterStockDetails implements IstockDetails.Presenter {
    private IstockDetails.View detailview;

    public PresenterStockDetails(IstockDetails.View mView) {
        this.detailview = mView;
    }


    @Override
    public void start() {
        detailview.init();

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void requestDataFromServer(String SockName) {
        ResponseTask.getResponse(SockName, new Callback<Response<ResponseBody>>() {
            @Override
            public void returnResult(Response<ResponseBody> users) {
                assert users.body() != null;
                Gson gson = new Gson();
                ModelStockDetail modelStockDetail;
                try {
                    String jsonData = users.body().string();
                    modelStockDetail = gson.fromJson(jsonData, ModelStockDetail.class);
                    System.out.println(modelStockDetail);
                    detailview.getDetailsList(modelStockDetail);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void returnError(String message) {
                detailview.showError(message);
            }
        });
    }



}