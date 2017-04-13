package gz.rmbgysz.ahitat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by gzoli on 2017.02.15..
 */

public class FavoritesAdapter extends BaseAdapter{

    private final UpdateFavoritesInterface listener;
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Favorite> mDataSource;
    private final ArrayList<String> mSelectedForDelete;

    public FavoritesAdapter(Context mContext, ArrayList<Favorite> mDataSource) {
        this.mContext = mContext;
        this.mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mDataSource = mDataSource;
        this.listener = (UpdateFavoritesInterface) mContext;
        mSelectedForDelete = new ArrayList<String>();
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
                    if (!mSelectedForDelete.contains(favorite.getDatum())) {
                        mSelectedForDelete.add(favorite.getDatum());
                        //Toast.makeText(mContext,
                        //        "Adding item : " + favorite.getDatum(), Toast.LENGTH_LONG)
                        //        .show();
                    }
                }

                else {
                    if (mSelectedForDelete.contains(favorite.getDatum())) {
                        mSelectedForDelete.remove(favorite.getDatum());
                        //Toast.makeText(mContext,
                        //        "Deleting item : " + favorite.getDatum(), Toast.LENGTH_LONG)
                        //        .show();
                    }
                }
                listener.updateMenu(mSelectedForDelete.size());
            }
        });

        TextView datum = (TextView) rowView.findViewById(R.id.datum);
        TextView de_cim = (TextView) rowView.findViewById(R.id.de_cim);
        TextView du_cim = (TextView) rowView.findViewById(R.id.du_cim);

        datum.setText(favorite.getFormattedDatum());
        de_cim.setText(favorite.getDe_cim());
        du_cim.setText(favorite.getDu_cim());

        return rowView;
    }
}
