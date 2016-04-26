package com.everything.fragments.hydrantFragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.everything.R;
import com.everything.adapters.ApiAdapter;
import com.everything.fragments.BaseFragment;
import com.everything.service.RetrofitServiceHelper;
import com.everything.service.ServiceErrorCallback;
import com.everything.service.model.Computer;
import com.everything.service.model.ComputersContainer;
import com.everything.utils.ActivityHelper;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

/**
 * Created by Mirek on 2016-03-18.
 */
public class LiskovPrinciplFakeAPIFragment extends BaseFragment {
    private static final String LIST_DATA = "list_data";
    @Bind(R.id.fake_api_list_view)
    ListView fakeApiListView;

    ApiAdapter fakeAPIAdapter = null;
    ArrayList<Computer> computers = null;
    private Parcelable state;
//wefwefwef
    @Override
    public void onResume() {
        super.onResume();
        fakeAPIAdapter = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
//some change
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null){
            try{
                this.computers = (ArrayList<Computer>) savedInstanceState.getSerializable(LIST_DATA);
                setAdapterData();
            }catch (Exception e){

            }
        }
        if (this.computers != null) {
            setAdapterData();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.fragmentLayout = R.layout.liskov_fake_api_fragment;

        View view = inflater.inflate(fragmentLayout, container, false);
        ButterKnife.bind(this, view);
        super.initViews(view);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(LIST_DATA,computers);
    }

    @Override
    public void onPause() {
        state = fakeApiListView.onSaveInstanceState();
        super.onPause();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (state != null) {
            fakeApiListView.onRestoreInstanceState(state);
        }
    }

    @OnClick(R.id.download_data_oryginal)
    void onDownloadDataButtonPressed() {
        DataProvider dataProvider = new RealDataProvider(getActivity());
        dataProvider.getCustomers();
    }

    void setAdapterData() {
        if (fakeAPIAdapter == null && computers != null) {
            fakeAPIAdapter = new ApiAdapter(getActivity(), R.layout.fake_api_list_item, computers);
            fakeApiListView.setAdapter(fakeAPIAdapter);
        } else if(fakeAPIAdapter != null) {
            if(fakeApiListView.getAdapter() == null){
                fakeApiListView.setAdapter(fakeAPIAdapter);
            }else {
                fakeAPIAdapter.notifyDataSetChanged();
            }
        }
    }

    class RealDataProvider extends ServiceErrorCallback implements DataProvider {
        public RealDataProvider(Context context) {
            super(context);
        }

        @Override
        public void getCustomers() {
            RetrofitServiceHelper.getRetrofitService()
                    .getDataInterface()
                    .getComputers()
                    .enqueue(this);
        }

        @Override
        public void onSuccess(Response response) {
            ComputersContainer computersContainer = (ComputersContainer) response.body();
            computers = computersContainer.getComputers();
            setAdapterData();
        }

        @Override
        public void onFailure(String errorMessage) {
            ActivityHelper.showErrorDialog("Some request error", getContext());
        }
    }

    class FakeDataProvider implements DataProvider {
        @Override
        public void getCustomers() {
            computers = new ArrayList<>(
                    Arrays.asList(new Computer(1, "Mieczysław"),
                            new Computer(2, "Waldemar"),
                            new Computer(3, "Jakiś inny"))
            );
        }
    }

    interface DataProvider {
        void getCustomers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fakeAPIAdapter = null;
    }
}
