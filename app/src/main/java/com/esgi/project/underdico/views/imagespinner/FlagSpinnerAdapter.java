package com.esgi.project.underdico.views.imagespinner;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class FlagSpinnerAdapter extends BaseAdapter {

    Context context;

    private static final List<Pair<String, Integer>> flags = Constants.flags;

    public FlagSpinnerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return flags.size();
    }

    @Override
    public Object getItem(int position) {
        return flags.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        FlagSpinnerViewHolder countryViewHolder;

        if(convertView == null) {
            itemView = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.image_spinner_row, parent, false);
            countryViewHolder = new FlagSpinnerViewHolder();
            countryViewHolder.setCountryImage((ImageView)itemView.findViewById(R.id.flagImageView));
            itemView.setTag(countryViewHolder);
        } else {
            countryViewHolder = (FlagSpinnerViewHolder)itemView.getTag();
        }

        countryViewHolder.getCountryImage().setImageDrawable(context.getDrawable(flags.get(position).second));
        return itemView;
    }


}
