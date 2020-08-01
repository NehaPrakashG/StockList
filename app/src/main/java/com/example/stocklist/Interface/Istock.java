package com.example.stocklist.Interface;

import com.example.stocklist.StockList.Model.ModelSearch;
import com.example.stocklist.StockList.Model.ModelStocks;

import java.util.HashMap;

public interface Istock {
    interface Model {


        HashMap<String,ModelSearch> GetFilteredList(ModelSearch modelSearch); //optimizes list on the basis of user input
        HashMap<String,ModelSearch> MatchStockNames(String stocknames, HashMap<String,ModelSearch> modelHashMap,int Tag);

    }

    interface View {

        void showProgress(); //shows progress bar on the screen

        void hideProgress();//hides progress bar on the screen
        void AdapterData(ModelStocks modelStocks, ModelSearch modelSearch);
        void RefreshList();

        //void CheckValidation(List<ModelStocks> list); //validates  user input to display elements

    }

    interface Presenter {

        void onDestroy(); //


    }

}




