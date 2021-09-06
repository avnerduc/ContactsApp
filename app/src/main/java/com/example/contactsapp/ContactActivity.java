package com.example.contactsapp;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ContactActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contact);

    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);

    String name = getIntent().getStringExtra("name");
    String number = getIntent().getStringExtra("number");
    TextView nameTextView = (TextView)findViewById(R.id.conactNameView);
    TextView numberTextView = (TextView)findViewById(R.id.contactNumberView);
    nameTextView.setText(name);
    numberTextView.setText(number);
  }

  public boolean onOptionsItemSelected(MenuItem item){
    Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
    startActivityForResult(myIntent, 0);
    return true;
  }

}