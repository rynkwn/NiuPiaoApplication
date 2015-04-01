package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.modsoussi.niupiaoapp.R;

import java.util.ArrayList;

import models.Country;

/**
 * Created by modsoussi on 3/31/2015.
 */
public class CountryListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Country> countries = new ArrayList<Country>();

    public CountryListAdapter(Context context, ArrayList<Country> countries){
        this.context = context;
        this.countries = countries;
    }

    @Override
    public int getCount() {
        return countries.size();
    }

    @Override
    public Country getItem(int position) {
        return countries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getRank();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null){
            view = LayoutInflater.from(context)
                    .inflate(R.layout.country_layout, null);
        }

        ((TextView)view.findViewById(R.id.country_rank)).setText(""+getItem(position).getRank());
        ((TextView)view.findViewById(R.id.country_name)).setText(getItem(position).getName());
        ((TextView)view.findViewById(R.id.country_landmass)).setText(getItem(position).getLandmass());

        return view;
    }
}
