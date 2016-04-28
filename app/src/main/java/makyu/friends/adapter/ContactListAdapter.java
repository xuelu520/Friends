package makyu.friends.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import makyu.friends.R;
import makyu.friends.beans.Contact;

/**
 * Created by -(^_^)- on 2016/4/28.
 */
public class ContactListAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<Contact> mDatas;

    public ContactListAdapter(Context context, List<Contact> datas) {
        this.mInflater = LayoutInflater.from(context);
        this.mDatas    = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.my_list_view_item, parent, false); //加载布局
            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.phone = (TextView) convertView.findViewById(R.id.phone);

            convertView.setTag(holder);
        } else {
            //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ViewHolder) convertView.getTag();
        }
        Contact phone = mDatas.get(position);
        holder.name.setText(phone.getName());
        holder.phone.setText(phone.getPhone());
        return convertView;
    }

    private class ViewHolder {
        TextView name;
        TextView phone;
    }
}
