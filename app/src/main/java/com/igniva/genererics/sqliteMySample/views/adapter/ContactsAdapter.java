package com.igniva.genererics.sqliteMySample.views.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.igniva.genererics.R;
import com.igniva.genererics.sqliteMySample.model.ContactsPojo;
import com.igniva.genererics.sqliteMySample.sqliteHelper.ContactsDatabaseHelper;
import com.igniva.genererics.sqliteMySample.views.MainActivitySqlite;

import java.util.ArrayList;

/**
 * Created by Biri Infotech on 12/17/2017.
 */

public  class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {

    Context context;
    ArrayList<ContactsPojo> arrayListContactsPojo;
    MyViewHolder holder;
    private AlertDialog dialog;
    private ContactsPojo contactsPojo, contactsPojoUpdate;
    ContactsDatabaseHelper db;
    int mPosition=-1;

    public ContactsAdapter(Context context, ArrayList<ContactsPojo> arrayListContactsPojo, ContactsDatabaseHelper db) {

        this.context=context;
        this.arrayListContactsPojo=arrayListContactsPojo;
        this.db=db;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_contact_detail, parent, false);
        holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_name.setText(arrayListContactsPojo.get(position).getName().toString().trim());
    }

    @Override
    public int getItemCount() {
        return arrayListContactsPojo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
TextView tv_name;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name=(TextView)itemView.findViewById(R.id.tv_name);

            tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPosition=getAdapterPosition();
                    showUpdateContactDialog(getAdapterPosition());
                }
            });

        }
    }

    public void showUpdateContactDialog(int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(((MainActivitySqlite)context));
        LayoutInflater inflater = ((MainActivitySqlite)context).getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog_add_data, null);
        dialogBuilder.setView(dialogView);

        contactsPojo =db.getContact(Long.parseLong(arrayListContactsPojo.get(position).getId()));

        final EditText et_add_name = (EditText) dialogView.findViewById(R.id.et_add_name);
        final EditText et_add_contact = (EditText) dialogView.findViewById(R.id.et_add_contact);
        final EditText et_add_first = (EditText) dialogView.findViewById(R.id.et_add_first);
        final EditText et_add_second = (EditText) dialogView.findViewById(R.id.et_add_second);
        final EditText et_add_third = (EditText) dialogView.findViewById(R.id.et_add_third);

        et_add_name.setText(contactsPojo.getName());
        et_add_contact.setText(contactsPojo.getContact());
        et_add_first.setText(contactsPojo.getInterest1());
        et_add_second.setText(contactsPojo.getInterest2());
        et_add_third.setText(contactsPojo.getInterest3());

        TextView tv_save = (TextView) dialogView.findViewById(R.id.tv_save);

        tv_save.setText("UPDATE");
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactsPojoUpdate= new ContactsPojo(et_add_name.getText().toString(),
                        et_add_contact.getText().toString(),
                        et_add_first.getText().toString(),
                        et_add_second.getText().toString(),
                        et_add_third.getText().toString());

                contactsPojoUpdate.setId(contactsPojo.getId());

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

            if (contactsPojoUpdate!=null)
                db.updateContact(contactsPojoUpdate);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            arrayListContactsPojo.set(mPosition,contactsPojoUpdate);
            notifyDataSetChanged();
//            mRecyclerView.scrollToPosition(arrayListContactsPojo.size()-1);

            dialog.dismiss();
            Toast.makeText(context, "Contact updated successfully", Toast.LENGTH_SHORT).show();

        }
    }



}
