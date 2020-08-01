package com.example.stocklist.StockList.Model;

import com.example.stocklist.Interface.ISearchView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelSearch {
    String name;
    String[] companyType;
    String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String[] companyType) {
        this.companyType = companyType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}
