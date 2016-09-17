package samples.android.com.sqllitedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ronak on 9/17/2016.
 */
public class TweetAdapter extends BaseAdapter{

    MainActivity main=null;

    public TweetAdapter(MainActivity main) {
        this.main = main;
    }

    @Override
    public int getCount() {
        return this.main.getTweetList().size();
    }

    @Override
    public Object getItem(int i) {
        return this.main.getTweetList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    static class ViewHolderItem {
        TextView name;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderItem holder = new ViewHolderItem();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) main.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cell, null);
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolderItem) view.getTag();
        }


        try {
            holder.name.setText(this.main.getTweetList().get(i).get("hashtags").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }
}
