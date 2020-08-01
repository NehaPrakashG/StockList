package com.example.stocklist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stocklist.Common.ResponseParser;
import com.example.stocklist.Common.Utils;
import com.example.stocklist.Interface.RecyclerViewClickListener;
import com.example.stocklist.MainActivity;
import com.example.stocklist.R;
import com.example.stocklist.StockList.Model.ModelStocks;
import com.example.stocklist.StockList.Presenter.PresenterStockList;

import java.util.HashMap;
import java.util.List;

public class StocksAdapter extends RecyclerView.Adapter<StocksAdapter.MyViewHolder>{
    private List<ModelStocks> stocksList;
    private Context context;
    private RecyclerViewClickListener mListener;
    PresenterStockList presenterStockList;
    HashMap<String,String> SearchListHash = new HashMap<>();

    ResponseParser parser;
    public StocksAdapter(MainActivity mainActivity, List<ModelStocks> stocksList,RecyclerViewClickListener listener) {
        this.context = mainActivity;
        this.stocksList = stocksList;
        this.mListener = listener;
        presenterStockList = new PresenterStockList(mainActivity);

    }


    public void add(ModelStocks stocks) {
        stocksList.add(stocks);
    }
    @NonNull
    @Override
    public StocksAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.stock_item, parent, false);
        return new MyViewHolder(v, mListener);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        try {
            ModelStocks stocks = stocksList.get(position);
            holder.name_tv.setText(stocks.getName());
            holder.id_tv.setText(stocks.getId());
            holder.price_tv.setText(Utils.Convertdouble(stocks.getPrice()));
            holder.type_tv.setText(stocks.getCompanyType().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return stocksList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name_tv, type_tv,price_tv, id_tv;
        private RecyclerViewClickListener mListener;

        public MyViewHolder(View itemView,RecyclerViewClickListener listener) {

            super(itemView);
            mListener = listener;
            itemView.setOnClickListener(this);
            name_tv = itemView.findViewById(R.id.name_tv);  //views initiallized
            id_tv = itemView.findViewById(R.id.id_tv);  //views initiallized
            type_tv = itemView.findViewById(R.id.type_tv);
            price_tv = itemView.findViewById(R.id.price_tv);

        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v, id_tv.getText().toString());

        }
    }


}


