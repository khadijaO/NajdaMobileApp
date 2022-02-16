package com.example.najdaapp.contactAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.najdaapp.DBSqLite.DatabaseHelper;
import com.example.najdaapp.R;
import com.example.najdaapp.contact.ContactManager;
import com.example.najdaapp.contact.ContactModel;
import com.example.najdaapp.contact.ModifyContact;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    DatabaseHelper d;
    Context context;//i don't know what does it mean
    ArrayList<ContactModel> arrayList;

    public CustomAdapter(Context context, ArrayList<ContactModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }



    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return  this.arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, final ViewGroup viewGroup) {

       d=new DatabaseHelper(context);

        if (convertView ==  null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_slave, viewGroup, false);
        }
        Button Button1= (Button)  convertView  .findViewById(R.id.modify_contact);
        final TextView phone= (TextView)  convertView  .findViewById(R.id.phone);

        Button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ContactModel contactSelected =d.getContact(phone.getText().toString());
                Intent i=new Intent(context, ModifyContact.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("contact", contactSelected);
                context.startActivity(i);
//                Toast.makeText(context, ""+contactSelected.getRelation(), Toast.LENGTH_SHORT).show();

            }

        });



        Button Button2= (Button)  convertView  .findViewById(R.id.Delete_contact);
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContactModel contactSelected =d.getContact(phone.getText().toString());
                d.deleteContact(contactSelected);
Intent i=new Intent(context, ContactManager.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

context.startActivity(i);
                Toast.makeText(context, "contact Deleted please refresh", Toast.LENGTH_SHORT).show();



            }
        });

        TextView number = (TextView) convertView.findViewById(R.id.phone);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView relation = (TextView) convertView.findViewById(R.id.relation);

        name.setText(arrayList.get(i).getName());
        number.setText(arrayList.get(i).getPhoneNo());
        relation.setText(arrayList.get(i).getRelation());
        return convertView;

    }
}
