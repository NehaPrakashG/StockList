package com.example.stocklist.Interface;

import java.util.HashMap;
import java.util.List;

public interface ISearchView {

    interface Model{

        List MatchStockNames(String stocknames, HashMap<String,String> map);
    }

    interface View {
        void showProgress(); //shows progress bar on the screen

        void hideProgress();//hides progress bar on the screen

    }
    interface Presenter {

        void onDestroy(); //



    }

}
