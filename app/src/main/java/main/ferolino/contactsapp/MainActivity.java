package main.ferolino.contactsapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import main.ferolino.contactsapp.fragments.ListViewFragmentContacts;
import main.ferolino.contactsapp.models.Contacts;

public class MainActivity extends AppCompatActivity {
//101apps.co.za/index.php/item/182-firebase-realtime-database.tutorial.html
    private ListViewFragmentContacts mListViewFragment;
    private FirebaseDatabase contactsDatabase;
    private DatabaseReference contactsDatabaseReference;
    private Menu menu;
    public long count=0;
    public String firstname,lastname,contact_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        contactsDatabase = FirebaseDatabase.getInstance();
        contactsDatabaseReference = contactsDatabase.getReference("contactsapp-99cc1");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListViewFragment = ListViewFragmentContacts.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, mListViewFragment)
                .commit();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if (fab != null) {

                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LayoutInflater li = LayoutInflater.from(MainActivity.this);
                            View promptsView = li.inflate(R.layout.addprocess, null);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    MainActivity.this);

                            // set prompts.xml to alertdialog builder
                            alertDialogBuilder.setView(promptsView);

                            final EditText FirstName = (EditText) promptsView
                                    .findViewById(R.id.etFirstName);
                            final EditText LastName = (EditText) promptsView
                                    .findViewById(R.id.etLastName);
                            final EditText ContactNo = (EditText) promptsView
                                    .findViewById(R.id.etContactNo);


                            // set dialog message
                            alertDialogBuilder
                                    .setTitle("Add Contact")
                                    .setCancelable(false)
                                    .setPositiveButton("Add",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    firstname= FirstName.getText().toString();
                                                    lastname= LastName.getText().toString();
                                                    contact_no= ContactNo.getText().toString();
                                                    Contacts con = new Contacts(firstname,lastname,contact_no);
                                                    Log.d("Chan","C1 first name: "+con.getFirstName());
                                                    Log.d("Chan","C1 last name: "+con.getLastName());
                                                    Log.d("Chan","C1 contact number: "+con.getContactNo());
                                                    Firebase ref = new Firebase("https://contactsapp-99cc1.firebaseio.com/");

                                                    Firebase ref2 = new Firebase("https://contactsapp-99cc1.firebaseio.com/");

                                                    ref2.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot snapshot) {
                                                            Log.e("Count " ,""+snapshot.getChildrenCount());
                                                           count = snapshot.getChildrenCount();
                                                        }

                                                        @Override
                                                        public void onCancelled(FirebaseError firebaseError) {

                                                        }

                                                    });
                                                    Firebase newContact = ref.child("c"+count);
                                                    newContact.setValue(con);




                                                }
                                            })
                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();
                        }
                    });



                }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
