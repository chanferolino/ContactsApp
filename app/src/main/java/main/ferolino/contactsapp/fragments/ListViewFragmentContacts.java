package main.ferolino.contactsapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import main.ferolino.contactsapp.ContactDetail;
import main.ferolino.contactsapp.R;
import main.ferolino.contactsapp.adapter.ContactsAdapter;
import main.ferolino.contactsapp.models.Contacts;

/**
 * Created by Christian on 8/4/2016.
 */
public class ListViewFragmentContacts extends Fragment implements AdapterView.OnItemClickListener {
    private ListView mListView;
    private List<Contacts> LContacts= new ArrayList<>();

    private List<Contacts> Empty= new ArrayList<>();
    private String fname,lname,cnum;

    public static ListViewFragmentContacts newInstance() {
        return new ListViewFragmentContacts();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listviewcontacts, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // find all the views
        mListView = (ListView) view.findViewById(R.id.listView);
        ContactsAdapter adapter2 = new ContactsAdapter(getActivity(),
                R.layout.item_contacts, LContacts);
        mListView.setAdapter(adapter2);

        /////////// GET CONTACT DATAS ///////////////////////////////////////////
        Firebase ref = new Firebase("https://contactsapp-99cc1.firebaseio.com/");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren())  {
                    fname = String.valueOf(messageSnapshot.child("firstName").getValue());
                    lname = String.valueOf(messageSnapshot.child("lastName").getValue());
                    cnum = String.valueOf(messageSnapshot.child("contactNo").getValue());
                    Log.d("chan",fname+" ,"+lname);
                    Log.d("chan","Number: "+cnum);
                    Contacts con = new Contacts(fname,lname,cnum);
                    LContacts.add(con);
                    ((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
                    // create a new instance of adapter
                    ContactsAdapter adapter = new ContactsAdapter(getActivity(),
                            R.layout.item_contacts, LContacts);
                        LContacts.clear();

                    // set the adapter
                    mListView.setAdapter(adapter);
        // set item click listener
        adapter.clear();
        adapter.notifyDataSetChanged();

        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
String pos;
        Intent intent = new Intent(getActivity(), ContactDetail.class);
        Log.d("Position1",position+"");

        pos = Integer.toString(position);
        intent.putExtra("Firstname",fname);
        intent.putExtra("Lastname",lname);
        intent.putExtra("Cnumber",cnum);
        intent.putExtra("Position", pos);

        startActivity(intent);

    }
}
