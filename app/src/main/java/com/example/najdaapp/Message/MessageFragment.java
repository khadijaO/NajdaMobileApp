package com.example.najdaapp.Message;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.najdaapp.DBSqLite.DatabaseHelper;
import com.example.najdaapp.R;
import com.google.android.material.navigation.NavigationView;

public class MessageFragment extends Fragment {

    private MessageViewModel mViewModel;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    Button add_message,reset_form;
    EditText greeting, body;
    String greeting_text, body_text;

    DatabaseHelper db;
    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
      View root=inflater.inflate(R.layout.message_fragment, container, false);

//-----------------------------ADD MESSAGE--------------------------

        db=new DatabaseHelper (getContext());

        add_message=root.findViewById(R.id.addMessage);
        greeting=root.findViewById(R.id.greeting);
        body=root.findViewById(R.id.body);

        add_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                greeting_text=greeting.getText().toString();
                body_text=body.getText().toString();
                boolean  isAllFieldsChecked = CheckAllFields();
                if(isAllFieldsChecked){
                    MessageModule messageModule=new MessageModule(greeting_text,body_text,true,true);
                    db.addMessageGPS(messageModule);
                    Toast.makeText(getContext(), "Message Added successfully!!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        reset_form=root.findViewById(R.id.ResetMessage);
        reset_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add the data in DB
//                relation_contact.setText("");
                greeting.setText("");
                body.setText("");
            }
        });
//        MessageModule defaultmsg=new MessageModule("hi","help",true,true);
//        db.addMessageGPS(defaultmsg);
        MessageModule msg=db.getMessage(true);
        greeting.setText(msg.getSalutation());
        body.setText(msg.getBody());
//        Toast.makeText(this, "greeting "+msg.getSalutation()+" , "+msg.getBody(), Toast.LENGTH_SHORT).show();
        return root;

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        // TODO: Use the ViewModel
    }
    public  boolean CheckAllFields() {
        if (greeting.length() == 0) {
            greeting.setError(getString(R.string.name_phoneError));
            return false;
        }

        if (body.length() == 0) {
            body.setError(getString(R.string.name_phoneError) );
            return false;
        }


        // after all validation return true.
        return true;
    }
}