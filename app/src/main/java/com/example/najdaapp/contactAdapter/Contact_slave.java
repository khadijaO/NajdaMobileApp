package com.example.najdaapp.contactAdapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.najdaapp.R;

public class Contact_slave extends AppCompatActivity {
Button modify_contact,delet_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slave);
modify_contact=findViewById(R.id.modify_contact);
modify_contact.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(getApplicationContext(), "modify", Toast.LENGTH_SHORT).show();
    }
});

    }
}