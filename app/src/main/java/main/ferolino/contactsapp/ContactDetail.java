package main.ferolino.contactsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import main.ferolino.contactsapp.models.Contacts;

public class ContactDetail extends AppCompatActivity {
    private String position, fname, lname, cnum;
    private FirebaseDatabase contactsDatabase;
    private DatabaseReference contactsDatabaseReference;
    private EditText txtFname, txtLname, txtNumber;
    private Button btnCall;
    private Boolean isEdit = true;
    private MenuItem mi;
    private Menu menu;
    private int editSave = 1;
    public int position2 = 1;
    int posInt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Firebase.setAndroidContext(this);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        contactsDatabase = FirebaseDatabase.getInstance();
        contactsDatabaseReference = contactsDatabase.getReference("contactsapp-99cc1");
        btnCall = (Button) findViewById(R.id.btnCall);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + txtNumber.getText().toString()));

                try{
                    startActivity(intent);
                }
                catch (Exception ex){
                    Toast.makeText(getApplicationContext(),"Not enought load",Toast.LENGTH_SHORT).show();
                }
            }
        });
        txtFname = (EditText) findViewById(R.id.txtFname);
        txtLname = (EditText) findViewById(R.id.txtLname);
        txtNumber = (EditText) findViewById(R.id.txtNumber);

        txtFname.setEnabled(false);
        txtLname.setEnabled(false);
        txtNumber.setEnabled(false);
        Intent i = getIntent();
        position = i.getStringExtra("Position");
        posInt = Integer.parseInt(position);
        fname = i.getStringExtra("Firstname");
        lname = i.getStringExtra("Lastname");
        cnum = i.getStringExtra("Cnumber");
        posInt += 1;
        position = String.valueOf(posInt);

        Log.d("Position","Position : "+position);
        txtFname.setText(fname);
        txtLname.setText(lname);
        txtNumber.setText(cnum);
//        Firebase ref = new Firebase("https://contactsapp-99cc1.firebaseio.com/c"+ position);
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
//                    fname = String.valueOf(messageSnapshot.child("firstName").getValue());
//                    lname = String.valueOf(messageSnapshot.child("lastName").getValue());
//                    cnum = String.valueOf(messageSnapshot.child("contactNo").getValue());
//                    Log.d("chen", fname + " ," + lname);
//                    Log.d("chen", "Number: " + cnum);
//                    txtFname.setText(fname);
//                    txtLname.setText(lname);
//                    txtNumber.setText(cnum);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//
//
//        });
    }
    public void deleteContact(){

        Query deleteQuery = contactsDatabaseReference.orderByChild("firstName").equalTo(fname);

        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        this.menu = menu;

        if (position2 == -1) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_done));
            menu.getItem(1).setVisible(false);
            // mi.setIcon(R.drawable.ic_done);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (item.getItemId() == R.id.action_edit) {
            editSave++;

            //from EDIT ICON TO DONE ICON
            if (editSave % 2 == 0) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_done));
                txtFname.setEnabled(true);
                txtLname.setEnabled(true);
                txtNumber.setEnabled(true);
            }
            //from DONE ICON TO EDIT ICON
            else {
                if (isEdit == true) {
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_edit));
                    isEdit = true;
                    txtFname.setEnabled(true);
                    txtLname.setEnabled(true);
                    txtNumber.setEnabled(true);
                }
                // TO SAVE DATA
                else {
                    contactsDatabaseReference.child(position).child("firstName").setValue(txtFname.getText());

                    contactsDatabaseReference.child(position).child("lastName").setValue(txtLname.getText());

                    contactsDatabaseReference.child(position).child("contactNo").setValue(txtNumber.getText());


                }
            }


                if (id == R.id.action_delete) {
                    deleteContact();
                    return true;
                }


        }
        return super.onOptionsItemSelected(item);
    }
}
