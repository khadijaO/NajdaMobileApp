package com.example.najdaapp.contact;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.najdaapp.DBSqLite.DatabaseHelper;
import com.example.najdaapp.R;

public class ModifyContact extends AppCompatActivity {
    Button edit_contact,reset_form,manage_contact;
    EditText number_contact, name_contact, relation_contact;
    String numberContact, nameContact, relationContact;
    String oldPhone;

    private static final int REQUEST_MODIFY_CONTACT = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_contact);


        number_contact = findViewById(R.id.contact_phone);
        name_contact = findViewById(R.id.contact_name);


        /*----------------------------   RELATION SPINNER  ---------------------------*/
//        set relations
        final Spinner spinner = (Spinner) findViewById(R.id.relation_contact);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.relation, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        relationContact = spinner.getSelectedItem().toString();
        /*-----------------------------------------------------------------------------------*/

        /*----------------------------   RECEIVE EXTRA  ---------------------------*/
        Bundle extras = new Bundle();
        Intent i = getIntent();

        ContactModel contact = (ContactModel) i.getParcelableExtra("contact");
         oldPhone=contact.getPhoneNo();
        number_contact.setText(contact.getPhoneNo());
        name_contact.setText(contact.getName());
        int spinnerPosition = adapter.getPosition(contact.getRelation());
        spinner.setSelection(spinnerPosition);
//        Toast.makeText(getApplicationContext(), contact.getName(), Toast.LENGTH_SHORT).show();
/* -----------------------------------------------------------------------------------*/
        /*----------------------------   MODIFY  ---------------------------*/
       edit_contact=findViewById(R.id.addContact);
       edit_contact.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               DatabaseHelper d = new DatabaseHelper(getApplicationContext());
               numberContact = number_contact.getText().toString();
               nameContact = name_contact.getText().toString();
               //add the data in DB

               boolean isAllFieldsChecked = CheckAllFields();
               if (isAllFieldsChecked) {
                   relationContact = spinner.getSelectedItem().toString();

                   ContactModel contact_user = new ContactModel(numberContact, nameContact, relationContact);
                   d.updateContact(contact_user,oldPhone );
                   showAlert("Contact updated successfully ");
               }
           }});



        /* -----------------------------------------------------------------------------------*/
        /* ------------------------------RESET--------------------------------*/
reset_form=findViewById(R.id.ResetContact);
reset_form.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        name_contact.setText("");
        number_contact.setText("");
        spinner.setSelection(0);
    }
});
        /* -----------------------------------------------------------------------------------*/
        /*----------------------------   MANAGE CONTACTS   ---------------------------*/
        manage_contact=findViewById(R.id.manage_contacts);
        manage_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), ContactManager.class);
                startActivity(i);
            }
        });
        /*-------------------------------------------------------------------*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    public  boolean CheckAllFields() {
        if (name_contact.length() == 0) {
            name_contact.setError(getString(R.string.name_phoneError));
            return false;
        }

        if (number_contact.length() == 0) {
            number_contact.setError(getString(R.string.name_phoneError) );
            return false;
        }

        if (!android.util.Patterns.PHONE.matcher(numberContact).matches()) {
            number_contact.setError(getString(R.string.phoneFormatError));
            return false;
        }
        // after all validation return true.
        return true;
    }
    public  void showAlert(String message ){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}