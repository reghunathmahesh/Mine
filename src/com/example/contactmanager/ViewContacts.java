package com.example.contactmanager;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewContacts extends Activity {

	String selectedName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.view);

		ListView listView = (ListView) findViewById(R.id.listview);
		
		

		String name, phone;
		ContentResolver cr = getContentResolver();
		ArrayList<String> names = new ArrayList<String>();

		Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, ContactsContract.Data.DISPLAY_NAME);
		Cursor pcur = cr.query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, ContactsContract.Data.DISPLAY_NAME);

		while (cursor.moveToNext() && pcur.moveToNext()) {

			name = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			phone = pcur
					.getString(pcur
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

			names.add(name + "\n" + phone);

		}
		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, names));

		

	}

}
