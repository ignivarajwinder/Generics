package com.example.roomexample;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.roomexample.dbHandler.AppDatabase;
import com.example.roomexample.entities.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RoomDataBaseWithSingelTableActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_main)
    RelativeLayout mActivityMain;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    User user;
    AppDatabase db;
    List<User> arrayList = new ArrayList<>();
    private AlertDialog dialog;

    private static User addUser(final AppDatabase db, User user) {
        db.userDao().insertAll(user);
        return user;
    }

    private void populateWithTestData(AppDatabase db,
                                             String first_name,
                                             String last_name,
                                             String contact,
                                             String first_interest,
                                             String second_interest,
                                             String third_interest) {
        try {
            user = new User();
            user.setFirstName(first_name);
            user.setLastName(last_name);
            user.setContact(contact);
            user.setInterest_1(first_interest);
            user.setInterest_2(second_interest);
            user.setInterest_3(third_interest);
//        user.setAge(25);

            new DatabaseQweryHandler().execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_data_base_with_singel_table);
        ButterKnife.bind(this);
        try {
            db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "users").build();

           new GetAllContacts().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        try {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.custom_dialog_add_data, null);
            dialogBuilder.setView(dialogView);

            final EditText et_add_first_name = (EditText) dialogView.findViewById(R.id.et_add_first_name);
            final EditText et_add_last_name = (EditText) dialogView.findViewById(R.id.et_add_last_name);
            final EditText et_add_contact = (EditText) dialogView.findViewById(R.id.et_add_contact);
            final EditText et_add_first_interest = (EditText) dialogView.findViewById(R.id.et_add_first);
            final EditText et_add_second_interest = (EditText) dialogView.findViewById(R.id.et_add_second);
            final EditText et_add_third_interest = (EditText) dialogView.findViewById(R.id.et_add_third);

            TextView tv_save = (TextView) dialogView.findViewById(R.id.tv_save);


            tv_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    populateWithTestData(db,
                            et_add_first_name.getText().toString(),
                            et_add_last_name.getText().toString(),
                            et_add_contact.getText().toString(),
                            et_add_first_interest.getText().toString(),
                            et_add_second_interest.getText().toString(),
                            et_add_third_interest.getText().toString()
                    );
                }
            });
            dialog = dialogBuilder.create();
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {

        ViewHolder viewHolder;
        List<User> arrayList = new ArrayList<>();

        public MainActivityAdapter(List<User> arrayList) {
            this.arrayList = arrayList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item_main_activity, parent, false);
            viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            viewHolder.tv_main.setText(arrayList.get(position).getFirstName() + " " + arrayList.get(position).getLastName());
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_main)
            TextView tv_main;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }


            @OnClick(R.id.tv_main)
            public void onViewClicked() {
//                startActivity(new Intent(MainActivity.this,RoomDataBaseWithSingelTableActivity.class));
            }


        }
    }



    public class DatabaseQweryHandler extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            addUser(db, user);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }


    public class GetAllContacts extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            arrayList = db.userDao().getAll();


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            LinearLayoutManager layoutManager = new LinearLayoutManager(RoomDataBaseWithSingelTableActivity.this);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);
            MainActivityAdapter mainActivityAdapter = new MainActivityAdapter(arrayList);
            mRecyclerView.setAdapter(mainActivityAdapter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }



}
