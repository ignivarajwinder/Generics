package com.igniva.genererics.sqliteMySample.views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.igniva.genererics.R;
import com.igniva.genererics.sqliteMySample.model.ContactsPojo;
import com.igniva.genererics.sqliteMySample.sqliteHelper.ContactsDatabaseHelper;
import com.igniva.genererics.sqliteMySample.views.adapter.ContactsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivitySqlite extends AppCompatActivity {
    ContactsDatabaseHelper db;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.activity_main_sqlite)
    CoordinatorLayout mActivityMainSqlite;
    private ContactsPojo contactsPojo;
    ContactsAdapter contactsAdapter;
    ArrayList<ContactsPojo> arrayListContactsPojo=new ArrayList<>();
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sqlite);
        ButterKnife.bind(this);
        db = new ContactsDatabaseHelper(getApplicationContext());
        setUpToolBar();

        new GetAllContacts().execute();


    }

    private void setUpToolBar() {
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setTitle("ContactDbDemo");
//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @OnClick({R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab:
                showAddNewContactDialog();
                break;
        }
    }


    public void showAddNewContactDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
         final View dialogView = inflater.inflate(R.layout.custom_dialog_add_data, null);
        dialogBuilder.setView(dialogView);

        final EditText et_add_name = (EditText) dialogView.findViewById(R.id.et_add_name);
        final EditText et_add_contact = (EditText) dialogView.findViewById(R.id.et_add_contact);
        final EditText et_add_first = (EditText) dialogView.findViewById(R.id.et_add_first);
        final EditText et_add_second = (EditText) dialogView.findViewById(R.id.et_add_second);
        final EditText et_add_third = (EditText) dialogView.findViewById(R.id.et_add_third);

        TextView tv_save = (TextView) dialogView.findViewById(R.id.tv_save);


        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactsPojo= new ContactsPojo(et_add_name.getText().toString(),
                        et_add_contact.getText().toString(),
                        et_add_first.getText().toString(),
                        et_add_second.getText().toString(),
                        et_add_third.getText().toString());
                new DatabaseQweryHandler().execute();
            }
        });
        dialog = dialogBuilder.create();
        dialog.show();
    }


    public class DatabaseQweryHandler extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if (contactsPojo!=null)
            db.createContact(contactsPojo);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            arrayListContactsPojo.add(contactsPojo);
            contactsAdapter.notifyDataSetChanged();
//            mRecyclerView.scrollToPosition(arrayListContactsPojo.size()-1);

            dialog.dismiss();
            Toast.makeText(MainActivitySqlite.this, "Contact added successfully", Toast.LENGTH_SHORT).show();

        }
    }


    public class GetAllContacts extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            arrayListContactsPojo=db.getAllContacs();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            contactsAdapter=new ContactsAdapter(MainActivitySqlite.this,arrayListContactsPojo, db);
            LinearLayoutManager layoutManager=new LinearLayoutManager(MainActivitySqlite.this);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(contactsAdapter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null ){
            // Don't forget to close database connection
            db.closeDB();
    }
    }
}
