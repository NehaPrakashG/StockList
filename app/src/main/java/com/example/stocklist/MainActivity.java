package com.example.stocklist;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stocklist.Adapter.StocksAdapter;
import com.example.stocklist.Common.ResponseParser;
import com.example.stocklist.Interface.Istock;
import com.example.stocklist.Interface.IstockDetails;
import com.example.stocklist.Interface.RecyclerViewClickListener;
import com.example.stocklist.StockDetails.Model.Model.ModelStockDetail;
import com.example.stocklist.StockList.Model.ModelSearch;
import com.example.stocklist.StockList.Model.ModelStocks;
import com.example.stocklist.StockDetails.Model.Presenter.PresenterStockDetails;
import com.example.stocklist.StockList.Presenter.PresenterStockList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Istock.View, IstockDetails.View {
    RecyclerView recyclerViewmv;
    FrameLayout placeholder;
    ResponseParser responseParser;
    RecyclerViewClickListener listener = null;
    HashMap<String, ModelSearch> modelSearches = new HashMap<String, ModelSearch>();
    private ProgressBar pbLoad;
    private List<ModelStocks> stockList = new ArrayList<>();
    private PresenterStockList stocklistpresenter;
    private IstockDetails.Presenter presenterStockDetails;
    private StocksAdapter stocksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pbLoad = findViewById(R.id.pb_load); //progressbar
        stocklistpresenter = new PresenterStockList(this);
        presenterStockDetails = new PresenterStockDetails(this);
        presenterStockDetails.start();
        responseParser = new ResponseParser(this);
    }

    @Override
    public void showProgress() {
        pbLoad.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        pbLoad.setVisibility(View.GONE);

    }

    @Override
    public void AdapterData(ModelStocks modelStocks, ModelSearch modelSearch) {
        modelSearches = stocklistpresenter.GetSearched(modelSearch);
        try {
            stocksAdapter.add(modelStocks);
            stocksAdapter.notifyDataSetChanged();
            pbLoad.setVisibility(View.GONE);

        } catch (Exception e) {
            recyclerViewmv.getRecycledViewPool().clear();
            e.printStackTrace();
        }
    }

    @Override
    public void RefreshList() {
        stockList.clear();
    }


    @Override
    public void getDetailsList(ModelStockDetail detail) {  // Gets called when company is selected
        FragmentManager fm = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putParcelable("detail", detail); // Passes selected company data to fragment
        FragmentStockDetails fragment = new FragmentStockDetails();
        fragment.setArguments(bundle);
        fm.beginTransaction().replace(R.id.placeholder, fragment).addToBackStack(getString(R.string.FragmentStockDetails)).commit();

    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }

    @Override
    public void init() {
        recyclerViewmv = findViewById(R.id.recyclerViewmv);
        placeholder = findViewById(R.id.placeholder);
        RecyclerView.LayoutManager recyclevw = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewmv.setLayoutManager(recyclevw);
        listener = (view, id) -> {
            presenterStockDetails.requestDataFromServer(id);

        };

        recyclerViewmv.setHasFixedSize(true);
        stocksAdapter = new StocksAdapter(this, stockList, listener);
        recyclerViewmv.setAdapter(stocksAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchview_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FragmentManager fm = getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putSerializable("modelsearch", modelSearches);
                SearchFilterFragment fragment = new SearchFilterFragment();
                fragment.setArguments(bundle);
                fm.beginTransaction().replace(R.id.placeholder, fragment).addToBackStack(getString(R.string.SearchFilterFragment))
                        .commit();
                return false;
            }
        });
        return true;
    }


    @Override
    public void onBackPressed() {
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            super.onBackPressed();
        } else if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}