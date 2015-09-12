package com.example.den.hw19.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.den.hw19.R;
import com.example.den.hw19.models.NotificationsModel;

import java.util.List;

/**
 * Created by Den on 12.09.15.
 */
public class NotificationsAdapter extends BaseAdapter{

    private Context mContext;
    private List<NotificationsModel> mData;

    public NotificationsAdapter(Context mContext, List<NotificationsModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    static class ViewHolder {
        TextView txtItem;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtItem = (TextView) convertView.findViewById(R.id.txtItem_AM);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtItem.setText(
                        "Message: "      + mData.get(position).getMessage()   + "\n" +
                        "Title: "       + mData.get(position).getTitle()      + "\n" +
                        "Subtitle: "    + mData.get(position).getSubtitle()   + "\n" +
                        "Ticket text: " + mData.get(position).getTicketText() + "\n");

        return convertView;
    }
}
