package com.example.contactsapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},1);
    ListView listview = (ListView) findViewById(R.id.listview);
    ArrayList<String> names = getContactNames();
    ArrayAdapter contactsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, names);
    listview.setAdapter(contactsAdapter);
    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showContact(view, names.get(position));
      }
    });
  }

  private void showContact(View view, String name){
    ContentResolver resolver = getContentResolver();
    Cursor infoCursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null,"DISPLAY_NAME = '" + name + "'", null, null);
    if (infoCursor.moveToFirst()) {
      String contactId = infoCursor.getString(infoCursor.getColumnIndex(ContactsContract.Contacts._ID));
      Cursor phones = resolver.query(Phone.CONTENT_URI, null, Phone.CONTACT_ID + " = " + contactId, null, null);
      if (phones.moveToNext()){
        String number = phones.getString(phones.getColumnIndex(Phone.NUMBER));

        Intent intent = new Intent(this, ContactActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("number", number);
        startActivity(intent);
      } else {
        Toast.makeText(getApplicationContext(),"Contact has no phone number on device!",Toast.LENGTH_SHORT).show();
      }
      phones.close();
    }
    infoCursor.close();
  }

  private ArrayList<String> getContactNames(){
    ArrayList<String> names = new ArrayList<>();
    ContentResolver resolver = getContentResolver();
    Cursor contactsCursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,
        new String[]{Contacts.DISPLAY_NAME}, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");
    while(contactsCursor.moveToNext()) {
      String name = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
      names.add(name);
    }
    contactsCursor.close();
    return names;
  }

}
