package com.example.stocklist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stocklist.Interface.RecyclerViewClickListener;
import com.example.stocklist.R;
import com.example.stocklist.StockList.Model.ModelSearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchViewAdapter extends RecyclerView.Adapter<SearchViewAdapter.MyViewHolder> {
    private HashMap<String,ModelSearch>namesList=new HashMap<>();
    private Context context;
    ArrayList<ModelSearch> valueArrayList;
    private RecyclerViewClickListener mListener;
    int size;

    public SearchViewAdapter(Context context,HashMap<String, ModelSearch>searchmap, RecyclerViewClickListener listener) {
        this.context = context;
        this.namesList = searchmap;
        this.mListener = listener;
        this.size=namesList.size();
        this.valueArrayList= new ArrayList<ModelSearch>(searchmap.values());

    }

    @NonNull
    @Override
    public SearchViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.stock_item, parent, false);
        return new SearchViewAdapter.MyViewHolder(v, mListener);

    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewAdapter.MyViewHolder holder, int position) {


        holder.name_tv.setText(valueArrayList.get(position).getName());
       // holder.type_tv.setText(valueArrayList.get(position).getCompanyType().toString());


    }

    @Override
    public int getItemCount() {
        return valueArrayList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name_tv, type_tv,price_tv, id_tv;
        private RecyclerViewClickListener mListener;

        public MyViewHolder(View itemView,RecyclerViewClickListener listener) {

            super(itemView);
            mListener = listener;
            itemView.setOnClickListener(this);
            name_tv = itemView.findViewById(R.id.name_tv);  //views initiallized
            type_tv = itemView.findViewById(R.id.type_tv);  //views initiallized
            id_tv = itemView.findViewById(R.id.id_tv);
            id_tv.setVisibility(View.GONE);//views initiallized

        }

        @Override
        public void onClick(View v) {

            ModelSearch modelSearch= valueArrayList.get(getAdapterPosition());
            mListener.onClick(v, modelSearch.getId());

        }
    }
}
