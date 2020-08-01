package com.example.stocklist.Adapter;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stocklist.Interface.Istock;
import com.example.stocklist.Interface.RecyclerItemClickListener;
import com.example.stocklist.R;
import com.example.stocklist.StockList.Model.ModelSearch;
import com.example.stocklist.StockList.Presenter.PresenterStockList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.reactivex.subjects.PublishSubject;

public class TypeFilterAdapter extends RecyclerView.Adapter<TypeFilterAdapter.MyViewHolder> {
    PresenterStockList presenterStockList;
    Context context;
    private Istock.View Iview;
    private RecyclerItemClickListener mListener;

    ArrayList<String> modelmap;
    ArrayList<String> typeArray;
    ArrayList<ModelSearch>clickList;
    HashMap<String ,ModelSearch>modelSearchHashMapmain;
    String searchName;

    private PublishSubject<View> itemViewClickSubject = PublishSubject.create();

    public TypeFilterAdapter(Context context, HashMap<String, ModelSearch> modelSearches,RecyclerItemClickListener listener,String searchtext) {
        this.modelmap=new ArrayList<String>(modelSearches.keySet());
        this.clickList=new ArrayList<ModelSearch>(modelSearches.values());
        this.modelSearchHashMapmain=modelSearches;
        this.context=context;
        this.mListener=listener;
        this.searchName=searchtext;
        CreateCompanyTypeList(clickList);

      //  this.Iview= stockListPresenter;

    }

    public ArrayList<String> CreateCompanyTypeList(ArrayList<ModelSearch> modelSearch){
        typeArray= new ArrayList<>();
        for(int i=0;i<modelSearch.size();i++){
            String str = TextUtils.join(",", modelSearch.get(i).getCompanyType());
            String [] compenyType = str.split(",",str.length());
            for(int j=0;j<compenyType.length;j++){
                if(compenyType[j].trim().toUpperCase().contains(searchName)||compenyType[j].trim().toLowerCase().contains(searchName)){
                    typeArray.add(compenyType[j]);
                }
            }
        }
        Set<String> set = new HashSet<>(typeArray);
        typeArray.clear();
        typeArray.addAll(set);
        return  typeArray;
    }

    @NonNull
    @Override
    public TypeFilterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.typefiltered_item_layout, parent, false);
        return new MyViewHolder(v,mListener);

    }


    @Override
    public void onBindViewHolder(@NonNull TypeFilterAdapter.MyViewHolder holder, int position) {
            holder.Typename_tv.setText(typeArray.get(position));


    }

    @Override
    public int getItemCount() {
        return typeArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView Typename_tv;
        private RecyclerItemClickListener mListener;

        public MyViewHolder(View itemView,RecyclerItemClickListener listener) {

            super(itemView);
            this.mListener=listener;
            itemView.setOnClickListener(this);

            Typename_tv = itemView.findViewById(R.id.Typename_tv);  //views initiallized

        }

        @Override
        public void onClick(View v) {
            HashMap<String,ModelSearch>modelSearchHashMap= new HashMap<>();
            ModelSearch modelSearch = new ModelSearch();
            for(Map.Entry<String,ModelSearch> modelSearchEntry: modelSearchHashMapmain.entrySet()){
                String str = TextUtils.join(",", modelSearchEntry.getValue().getCompanyType());
                String [] compenyType = str.split(",",str.length());
                for (String s : compenyType) {
                    try {
                        if (s.toLowerCase().contains(Typename_tv.getText().toString().toLowerCase())) {
                            modelSearch = modelSearchEntry.getValue();
                            modelSearchHashMap.put(modelSearch.getId(), modelSearch);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            mListener.onClickItem(v, modelSearchHashMap);


        }
    }
}