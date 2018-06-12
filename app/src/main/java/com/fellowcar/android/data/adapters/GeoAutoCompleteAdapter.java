package com.fellowcar.android.data.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.fellowcar.android.Application;
import com.fellowcar.android.data.network.model.osrm.GeocodingResponseItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fellowcar project
 * Created by ANDREY. Y on 1/18/2018.
 * Email: inittey@gmail.com
 */

public class GeoAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
    private ArrayList<String> data;

    public GeoAutoCompleteAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        data = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return data.get(position);
    }


    @NonNull
    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {


                        Application.getGeocodingAPI().getGeocoder(constraint.toString(), 5, "json", 1, "ru", 1, "ru").enqueue(new Callback<List<GeocodingResponseItem>>() {
                            @Override
                            public void onResponse(@NonNull Call<List<GeocodingResponseItem>> call, @NonNull Response<List<GeocodingResponseItem>> response) {
                                @NonNull List<GeocodingResponseItem> dataOfAPI = response.body();

                                //clear latest data and rewrite array
                                data.clear();
                                notifyDataSetChanged();

                                for (GeocodingResponseItem item : dataOfAPI) {
                                    data.add(item.getDisplay_name());
                                    Log.d("ISP", "has been added: " + item.getDisplay_name());
                                }
                            }


                            @Override
                            public void onFailure(@NonNull Call<List<GeocodingResponseItem>> call, @NonNull Throwable t) {
                                Log.d("ISP", "error loading data. Status: " + call.isCanceled());
                            }
                        });


                    // Now assign the values and count to the FilterResults object
                    filterResults.values = data;
                    filterResults.count = data.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence contraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return myFilter;
    }

}