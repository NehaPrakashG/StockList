package com.example.stocklist;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stocklist.Adapter.SearchViewAdapter;
import com.example.stocklist.Adapter.TypeFilterAdapter;
import com.example.stocklist.Interface.Istock;
import com.example.stocklist.Interface.IstockDetails;
import com.example.stocklist.Interface.RecyclerItemClickListener;
import com.example.stocklist.Interface.RecyclerViewClickListener;
import com.example.stocklist.StockDetails.Model.Model.ModelStockDetail;
import com.example.stocklist.StockList.Model.ModelSearch;
import com.example.stocklist.StockList.Model.ModelStocks;
import com.example.stocklist.StockDetails.Model.Presenter.PresenterStockDetails;
import com.example.stocklist.StockList.Presenter.PresenterStockList;

import java.util.HashMap;

public class SearchFilterFragment extends Fragment implements Istock.View,IstockDetails.View{
    SearchViewAdapter adapter;
    RecyclerView recyclerViewtype, recyclerViewSearch;
    View view;
    SearchView search_filter_sv;
    private PresenterStockList stocklistpresenter;
    RecyclerItemClickListener itemClickListener= null;
    RecyclerViewClickListener recyclerViewClickListener= null;
    Context context;
    private IstockDetails.Presenter presenterStockDetails;
    FragmentActivity fragmentActivity;
    HashMap<String, ModelSearch> modelSearches = new HashMap<String, ModelSearch>();
    public SearchFilterFragment() {
    }

    public static SearchFilterFragment newInstance() {
        SearchFilterFragment fragment = new SearchFilterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        setHasOptionsMenu(true);
        assert bundle != null;
        if(bundle.getSerializable("modelsearch") != null)
            modelSearches = (HashMap<String, ModelSearch>) bundle.getSerializable("modelsearch");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_filter, container, false);
        stocklistpresenter = new PresenterStockList(this);
        presenterStockDetails = new PresenterStockDetails(this);
        context= getActivity();
        initUi();
        return view;
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {  //hides menu options
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        menuItem.setVisible(false);
    }
    private void initUi() {
        recyclerViewtype = view.findViewById(R.id.recyclerViewtype);
        RecyclerView.LayoutManager horizontalLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewtype.setLayoutManager(horizontalLayoutManager);
        recyclerViewtype.setHasFixedSize(true);

        itemClickListener = (view, modelSearchclick) -> {
            adapter = new SearchViewAdapter(context, modelSearchclick, recyclerViewClickListener);
            recyclerViewSearch.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        };
        recyclerViewSearch = view.findViewById(R.id.recyclerViewSearch);
        RecyclerView.LayoutManager verticalLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewSearch.setLayoutManager(verticalLayoutManager);
        recyclerViewSearch.setHasFixedSize(true);

        recyclerViewClickListener=(view,id)->{
            presenterStockDetails.requestDataFromServer(id);

        };
        search_filter_sv = view.findViewById(R.id.search_filter_sv);
        search_filter_sv.onActionViewExpanded();
        search_filter_sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {  //TAG 1 = searches names , TAG 2 = searches type
                HashMap<String, ModelSearch> Listname = stocklistpresenter.GetSearched(newText, modelSearches, 1);
                if (Listname.size() > 0) {
                     adapter = new SearchViewAdapter(context, Listname, recyclerViewClickListener);
                    recyclerViewSearch.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                HashMap<String, ModelSearch> ListType = stocklistpresenter.GetSearched(newText, modelSearches, 2);
                if (ListType.size() > 0) {
                    TypeFilterAdapter typeFilterAdapter = new TypeFilterAdapter(context, ListType,itemClickListener,newText);
                    recyclerViewtype.setAdapter(typeFilterAdapter);
                    typeFilterAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            this.fragmentActivity = (FragmentActivity) context;
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        this.fragmentActivity  = null;
    }
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void AdapterData(ModelStocks modelStocks, ModelSearch modelSearch) {

    }


    @Override
    public void RefreshList() {

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void getDetailsList(ModelStockDetail detail) {

        FragmentStockDetails fragment2 = new FragmentStockDetails();
        Bundle bundle = new Bundle();
        bundle.putParcelable("detail", detail);
        fragment2.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, fragment2);
        fragmentTransaction.addToBackStack(getString(R.string.FragmentStockDetails)).commit();
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void init() {

    }



}



