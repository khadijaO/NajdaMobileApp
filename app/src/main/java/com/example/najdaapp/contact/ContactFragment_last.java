package com.example.najdaapp.contact;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.najdaapp.DBSqLite.DatabaseHelper;
import com.example.najdaapp.R;

public class ContactFragment_last extends Fragment {
    Button existing_contact,add_contact,reset_form,manage_contact;
    EditText number_contact, name_contact, relation_contact;
    String numberContact, nameContact, relationContact;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private static final int REQUEST_SELECT_CONTACT = 1;
    DatabaseHelper d;

    private ContactFragmentLastViewModel mViewModel;

    public static ContactFragment_last newInstance() {
        return new ContactFragment_last();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {



            View root = inflater.inflate(R.layout.contact_fragment_last_fragment, container, false);
number_contact=root.findViewById(R.id.contact_phone);
        name_contact=root.findViewById(R.id.contact_name);
        /*----------------------------   RELATION SPINNER  ---------------------------*/
//        set relations
        final Spinner spinner = (Spinner) root.findViewById(R.id.relation_contact);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.relation, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        relationContact = spinner.getSelectedItem().toString();
        /*-----------------------------------------------------------------------------------*/

        /*----------------------------   EXISTING CONTACTS   ---------------------------*/
        existing_contact = root.findViewById(R.id.existing_contacts_button);

        existing_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ContactModel m=new ContactModel(1, "111111","khadija", "mother");
//                Toast.makeText(getApplicationContext(), m.getPhoneNo().toString(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
//                startActivityForResult(intent, REQUEST_SELECT_CONTACT);

                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUEST_SELECT_CONTACT);
            }
        });
        /*-------------------------------------------------------------------*/

        /*----------------------------   ADD CONTACTS   ---------------------------*/
        add_contact=root.findViewById(R.id.addContact);
        add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberContact=number_contact.getText().toString();
                nameContact=name_contact.getText().toString();
                //add the data in DB
                boolean  isAllFieldsChecked = CheckAllFields();
                if(isAllFieldsChecked){
                    d=new DatabaseHelper(getContext());
                    ContactModel contact_user=new ContactModel( numberContact,nameContact, relationContact);
                    d.addContact(contact_user);
                    showAlert("Contact added , check All Contacts");
                    name_contact.setText("");
                    number_contact.setText("");
                    spinner.setSelection(0);
                }
            }
        });
        /*-------------------------------------------------------------------*/
        /*----------------------------   RESET CONTACTS   ---------------------------*/
        reset_form=root.findViewById(R.id.ResetContact);
        reset_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add the data in DB
//                relation_contact.setText("");
                name_contact.setText("");
                number_contact.setText("");
                spinner.setSelection(0);
            }
        });
        /*-------------------------------------------------------------------*/
        /*----------------------------   MANAGE CONTACTS   ---------------------------*/
        manage_contact=root.findViewById(R.id.manage_contacts);
        manage_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(), ContactManager.class);
                startActivity(i);
            }
        });
        /*-------------------------------------------------------------------*/




        return root;


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

    public void showAlert(String message) {

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        switch (requestCode) {
//            case (REQUEST_SELECT_CONTACT):
//                if (resultCode == Activity.RESULT_OK) {
//
//                    Uri contactData = data.getData();
//                    Cursor c = managedQuery(contactData, null, null, null, null);
//                    if (c.moveToFirst()) {
//
//                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
//                        String hasPhone = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));
//                        String phone = null;
//                        try {
//                            if (hasPhone.equalsIgnoreCase("1")) {
//                                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
//                                phones.moveToFirst();
//                                phone = phones.getString(phones.getColumnIndexOrThrow("data1"));
//                            }
//                            String name = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
//                            Toast.makeText(getApplicationContext(), name+phone, Toast.LENGTH_SHORT).show();
//
//                            number_contact.setText(phone);
//                            name_contact.setText(name);
////                            db.addcontact(new ContactModel(0, name, phone));
////                            list = db.getAllContacts();
////                            customAdapter.refresh(list);
//                        } catch (Exception ex) {
//                        }
//                    }
//                }
//                break;
//        }
        Toast.makeText(getContext(), "rj3ti", Toast.LENGTH_SHORT).show();

        super.onActivityResult(requestCode, resultCode, data);
    }
}