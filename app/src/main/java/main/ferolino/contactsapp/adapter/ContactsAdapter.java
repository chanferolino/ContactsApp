package main.ferolino.contactsapp.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import main.ferolino.contactsapp.R;
import main.ferolino.contactsapp.models.Contacts;

/**
 * Created by Christian on 8/4/2016.
 */
public class ContactsAdapter extends ArrayAdapter<Contacts> {

    private Context mContext;
    private int         mLayoutId;
    private List<Contacts> mContacts;

    public ContactsAdapter(Context context, int resource, List<Contacts> contacts) {
        super(context, resource, contacts);

        mContext = context;
        mLayoutId = resource;
        mContacts = contacts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            // Inflate the layout
            convertView = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);

            // create the view holder
            viewHolder = new ViewHolder();
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtContactName);
            viewHolder.txtNo= (TextView) convertView.findViewById(R.id.txtNumber);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Set the movie data
        Contacts con = mContacts.get(position);

        if (con != null) {

            if (viewHolder.txtName != null) {
                viewHolder.txtName.setText(con.getLastName()+", " +con.getFirstName() );
            }
            if (viewHolder.txtNo != null) {
                viewHolder.txtNo.setText(con.getContactNo());
            }
        }

        return convertView;
    }

    private static class ViewHolder {
        public TextView  txtName;
        public TextView  txtNo;
    }


}
