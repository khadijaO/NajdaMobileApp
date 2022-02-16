package com.example.najdaapp.emergencyAdapter;

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
import com.example.najdaapp.emergency.EmergencyFragment;
import com.example.najdaapp.emergency.EmergencyModel;

import java.util.HashMap;
import java.util.Map;

public class CustomAdapter extends BaseAdapter {
    DatabaseHelper d;
    Context context;//i don't know what does it mean
    Map<String,String> arrayList=new HashMap<String ,String>();

    public CustomAdapter(Context context,  Map<String,String> arrayList) {
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
    public View getView( final int i, View convertView, final ViewGroup viewGroup) {
 final  String nameE=arrayList.keySet().toArray()[i].toString(),
        phoneE=arrayList.get(arrayList.keySet().toArray()[i]);

        if (convertView ==  null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_slave_2, viewGroup, false);
        }
        Button Button1= (Button)  convertView  .findViewById(R.id.setEmergency);

        Button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                DatabaseHelper db;
                db=new DatabaseHelper(context);

                EmergencyModel contact_user=new EmergencyModel(phoneE ,nameE);
                db.addEmergency(contact_user);
                Toast.makeText(context, "set default"+arrayList.get(arrayList.keySet().toArray()[i]), Toast.LENGTH_SHORT).show();
         }

        });

        TextView number = (TextView) convertView.findViewById(R.id.phone);
        TextView name = (TextView) convertView.findViewById(R.id.name);

        number.setText(arrayList.get(arrayList.keySet().toArray()[i]));

        name.setText(arrayList.keySet().toArray()[i].toString());
        return convertView;

    }
}
