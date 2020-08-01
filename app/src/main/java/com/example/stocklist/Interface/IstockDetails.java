package com.example.stocklist.Interface;

import com.example.stocklist.StockDetails.Model.Model.ModelStockDetail;

public interface IstockDetails {


    interface View {
        void getDetailsList(ModelStockDetail detail);

        void showError(String message);

        void init();

    }

    interface Presenter {
        void start();

        void onDestroy();

        void requestDataFromServer(String SockName);

    }
}
