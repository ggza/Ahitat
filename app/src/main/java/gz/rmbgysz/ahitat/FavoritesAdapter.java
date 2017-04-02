package gz.rmbgysz.ahitat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gzoli on 2017.02.15..
 */

public class FavoritesAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Favorite> mDataSource;

    public FavoritesAdapter(Context mContext, ArrayList<Favorite> mDataSource) {
        this.mContext = mContext;
        this.mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mDataSource = mDataSource;
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = mInflater.inflate(R.layout.list_item, parent, false);

        TextView datum = (TextView) rowView.findViewById(R.id.datum);
        TextView de_cim = (TextView) rowView.findViewById(R.id.de_cim);
        TextView du_cim = (TextView) rowView.findViewById(R.id.du_cim);

        Favorite favorite = (Favorite) getItem(position);

        datum.setText(favorite.getDatum());
        de_cim.setText(favorite.getDe_cim());
        du_cim.setText(favorite.getDu_cim());

        return rowView;
    }
}
