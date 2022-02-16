package com.example.najdaapp.emergency;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
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

public class EmergencyFragment extends Fragment {

    private EmergencyViewModel mViewModel;
    Button add_emergency,reset_form,manage_emergency;
    EditText number_emergency, name_emergency;
    DatabaseHelper db;
    String numberEmergency, nameEmergency;
    public static EmergencyFragment newInstance() {
        return new EmergencyFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
      View root =inflater.inflate(R.layout.emergency_fragment, container, false);
        add_emergency=root.findViewById(R.id.addEmergency);
        reset_form=root.findViewById(R.id.ResetEmergency);
        manage_emergency=root.findViewById(R.id.manageEmergency);
        number_emergency=root.findViewById(R.id.emergency_number);
        name_emergency=root.findViewById(R.id.emergency_name);
        //        ------------------GET default emergency------------------


        db=new DatabaseHelper(getContext());

//        EmergencyModel contact_user=new EmergencyModel( "19","police");
//        db.addEmergency(contact_user);
        EmergencyModel r=db.getEmergency("");
        number_emergency.setText(r.getPhoneNo());
        name_emergency.setText(r.getName());
//        ------------------------------




//        ------------------Add emergency------------------

        add_emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberEmergency=number_emergency.getText().toString();
                nameEmergency=name_emergency.getText().toString();
                //add the data in DB
                boolean  isAllFieldsChecked = CheckAllFields();
                if(isAllFieldsChecked){
                    EmergencyModel contact_user=new EmergencyModel( numberEmergency,nameEmergency);
                    db.addEmergency(contact_user);
                    showAlert("Emergency modified");
                }
            }
        });
//        ------------------------------------------------

        //        ------------------RESET emergency------------------
        reset_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_emergency.setText("");
                number_emergency.setText("");
//                db.deleteAll();

            }
        });
//        ------------------------------------------------

        //        ------------------mANAGE emergency------------------
        manage_emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(),ManageEmergency.class);
                startActivity(i);
            }
        });
//        ------------------------------------------------
      return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EmergencyViewModel.class);
        // TODO: Use the ViewModel
    }
    public  boolean CheckAllFields() {
        if (name_emergency.length() == 0) {
            name_emergency.setError(getString(R.string.name_phoneError));
            return false;
        }

        if (number_emergency.length() == 0) {
            number_emergency.setError(getString(R.string.name_phoneError) );
            return false;
        }

        if (!android.util.Patterns.PHONE.matcher(numberEmergency).matches()) {
            number_emergency.setError(getString(R.string.phoneFormatError));
            return false;
        }
        // after all validation return true.
        return true;
    }
    public void showAlert(String message){

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Toast.makeText(getContext(), "rj3", Toast.LENGTH_SHORT).show();
        super.onActivityResult(requestCode, resultCode, data);
    }
}