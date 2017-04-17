package gz.rmbgysz.ahitat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gzoli on 2017.02.15..
 */

public class FavoritesAdapter extends BaseAdapter{

    private final UpdateFavoritesInterface listener;
    private Context mContext;
    private LayoutInflater mInflater;
    private DatabaseHelper mydb;
    private ArrayList<Favorite> mDataSource;
    private final ArrayList<String> mSelectedForDelete;

    public FavoritesAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listener = (UpdateFavoritesInterface) mContext;
        mSelectedForDelete = new ArrayList<>();
        mydb = DatabaseHelper.getInstance(mContext);
        mDataSource = mydb.getAllFavoritesWithTitles();
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

    public ArrayList<String> getItemsSelectedForDelete() { return  mSelectedForDelete;}
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = mInflater.inflate(R.layout.list_item, parent, false);
        CheckBox selectedForDelete = (CheckBox) rowView.findViewById(R.id.deleteCheckBox);

        final Favorite favorite = (Favorite) getItem(position);
        selectedForDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;

                if (cb.isChecked()) {
                    if (!mSelectedForDelete.contains(favorite.getDate())) {
                        mSelectedForDelete.add(favorite.getDate());
                    }
                }

                else {
                    if (mSelectedForDelete.contains(favorite.getDate())) {
                        mSelectedForDelete.remove(favorite.getDate());
                    }
                }

                try {
                    listener.updateMenu(mSelectedForDelete.size());
                }
                catch (ClassCastException exception) {
                    exception.printStackTrace();
                }

            }
        });

        TextView datum = (TextView) rowView.findViewById(R.id.dailyDevotionDate);
        TextView de_cim = (TextView) rowView.findViewById(R.id.amTitle);
        TextView du_cim = (TextView) rowView.findViewById(R.id.pmTitle);

        datum.setText(favorite.getFormattedDatum());
        de_cim.setText(favorite.getAmTitle());
        du_cim.setText(favorite.getPmTitle());

        return rowView;
    }

    public void deleteItemsAndRefresh() {

        for (int j=0; j < mSelectedForDelete.size(); j ++) {
                mydb.deleteFavorite(mSelectedForDelete.get(j));
        }
        mSelectedForDelete.clear();
        mDataSource = mydb.getAllFavoritesWithTitles();
        this.notifyDataSetChanged();
        listener.updateMenu(mSelectedForDelete.size());
    }

}

