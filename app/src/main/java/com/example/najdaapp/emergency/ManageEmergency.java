package com.example.najdaapp.emergency;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.najdaapp.R;
import com.example.najdaapp.emergencyAdapter.CustomAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManageEmergency extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_emergency);

        ListView lv=findViewById(R.id.listE);
        Map<String, String> data = new HashMap<>();

        data.put(getString(R.string.Ambulance),"150");
        data.put(getString(R.string.Directory_enquiries),"160");
        data.put(getString(R.string.Police),"119");
        data.put(getString(R.string.Fire_brigade),"15");
        data.put(getString(R.string.Police)+" 2 ","120");
        data.put(getString(R.string.Gendarmerie_Royale),"177");

        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), data);
        lv.setAdapter(adapter);


    }
}