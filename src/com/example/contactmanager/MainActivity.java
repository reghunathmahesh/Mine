package com.example.contactmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		Button view = (Button) findViewById(R.id.viewButton);
		Button add = (Button) findViewById(R.id.createButton);
		Button delete = (Button) findViewById(R.id.deleteButton);

		view.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						ViewContacts.class);

				startActivity(intent);

				Log.i("MainActivity", "Completed Displaying Contact list");
			}
		});

		add.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent intent = new Intent(MainActivity.this, AddContact.class);
				startActivity(intent);

			}
		});

		delete.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(MainActivity.this,
						DeleteContact.class);

				startActivity(intent);

			}
		});

	}

}