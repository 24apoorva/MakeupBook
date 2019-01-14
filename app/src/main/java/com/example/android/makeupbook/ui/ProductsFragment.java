package com.example.android.makeupbook.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.android.makeupbook.R;
import com.example.android.makeupbook.adapters.ProductsRecyclerViewAdapter;
import com.example.android.makeupbook.network.MyResultReceiver;
import com.example.android.makeupbook.network.NetworkingService;
import com.example.android.makeupbook.objects.Products;
import com.example.android.makeupbook.Database.Item;
import com.example.android.makeupbook.Database.ItemViewModel;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsFragment extends Fragment implements MyResultReceiver.Receiver{
    public ProgressBar p;
    public static String PRODUCTSURL = "urlReq";
    public static String FULLURL = "full data url";
    public static String BRANDTYPE = "com.example.android.makeupbook.ui.isbrand";
    private boolean isBrand = false;
    private final String SAVEDATA = "savedlist";
    private String mainUrl;
    private View rootView;
    private ItemViewModel itemViewModel;
    private MyResultReceiver mReceiver;
    private static final int RUNNING = 24;
    private static final int FINISHED = 25;
    private static final int ERROR = 26;
    @BindView(R.id.eyeProducts_recyclerView)
    RecyclerView mRecyclerView;
    private int code;
    @BindView(R.id.products_framelayout)
    FrameLayout frameLayout;


    public ProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView == null) {
            // Inflate the layout for this fragment
            rootView = inflater.inflate(R.layout.fragment_products_tab, container, false);
            ButterKnife.bind(this, rootView);
            p = rootView.findViewById(R.id.progress_rec);
            if (getArguments() != null) {
                String url = getArguments().getString(PRODUCTSURL);
                mainUrl = getArguments().getString(FULLURL);
                isBrand = getArguments().getBoolean(BRANDTYPE);
               startDataService(url);
            }

        }


        return rootView;
    }

    private void startDataService(String url){
        mReceiver = new MyResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        final Intent intent = new Intent(Intent.ACTION_SYNC, null,getContext(),NetworkingService.class);
        intent.putExtra("urlvaluereciver",url);
        intent.putExtra("receiver", mReceiver);
        intent.putExtra("command", "query");
        getContext().startService(intent);
    }

    public void onPause() {
        super.onPause();
        mReceiver.setReceiver(null); // clear receiver so no leaks.
    }


    private void displaySelectedProducts(ArrayList<Products> productsList,
                                         boolean footer) {
        itemViewModel = ViewModelProviders.of(getActivity()).get(ItemViewModel.class);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        final ProductsRecyclerViewAdapter mAdapter = new ProductsRecyclerViewAdapter(getContext(), productsList, footer,
                new ProductsRecyclerViewAdapter.OnItemClicked() {
                    @Override
                    public void onFooterClick(int position) {
                        code=1;
                        startDataService(mainUrl);
                    }

                    @Override
                    public void imageClick(Item item) {

                        itemViewModel.insertItem(item);
                        Toast.makeText(getContext(),"Item added to the list",Toast.LENGTH_SHORT).show();
                    }

                });
         mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case RUNNING:
                p.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                break;
            case FINISHED:
                mRecyclerView.setVisibility(View.VISIBLE);
                ArrayList<Products> makeupProducts= resultData.getParcelableArrayList("results");
                if(makeupProducts.size() == 0){
                    code = 1;
                    startDataService(mainUrl);
                }else{
                p.setVisibility(View.GONE);
                if (isBrand ) {
                    displaySelectedProducts(makeupProducts,false);
                } else if (code == 1) {
                    displaySelectedProducts(makeupProducts,false);
                } else {
                    displaySelectedProducts(makeupProducts,true);
                }}


                break;
            case ERROR:
                // handle the error;
                break;
        }
    }
}
