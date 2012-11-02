package com.example.contactmanager;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DeleteContact extends Activity {

	String selectedContact[],selectedName;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
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


		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				

				selectedName = (String) arg0.getItemAtPosition(arg2);

				selectedContact = selectedName.split("\n",2);

				
				
				AlertDialog alertDialog = new AlertDialog.Builder(DeleteContact.this).create();
			       alertDialog.setTitle("Delete");
			       alertDialog.setMessage("This contact will be deleted.");
			       alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						if(deleteContact(selectedContact[1],selectedContact[0])){
							
							Toast.makeText(DeleteContact.this, "Contact Deleted",
									Toast.LENGTH_SHORT).show();

						}
						
						finish();
					}
				});
			       
			       alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					
						dialog.dismiss();					
					}
				});
			       alertDialog.show();

				//finish();

			}
		});
		

		/*Button deletebtn = (Button) findViewById(R.id.deletecontact);

		deletebtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (deleteContact(phone, name))
					Toast.makeText(DeleteContact.this, "Contact Deleted",
							Toast.LENGTH_SHORT).show();

				finish();

			}
		});*/

	}

	public boolean deleteContact(String phone, String name) {
		Uri contactUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,
				Uri.encode(phone));

		ContentResolver cr = getContentResolver();

		Cursor cur = cr.query(contactUri, null, null, null, null);
		try {
			if (cur.moveToFirst()) {
				do {
					if (cur.getString(
							cur.getColumnIndex(PhoneLookup.DISPLAY_NAME))
							.equalsIgnoreCase(name)) {
						String lookupKey = cur
								.getString(cur
										.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
						Uri uri = Uri.withAppendedPath(
								ContactsContract.Contacts.CONTENT_LOOKUP_URI,
								lookupKey);
						cr.delete(uri, null, null);

						return true;
					}

				} while (cur.moveToNext());
			}

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		return false;
	}

}
