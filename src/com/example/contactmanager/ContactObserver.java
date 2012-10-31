package com.example.contactmanager;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;

public class ContactObserver extends ContentObserver {
	static Context ctx;
	String ContactId, DisplayName;
	final String[] projection = new String[] { ContactsContract.Contacts._ID };
	Cursor curval, people;
	private static final String TAG = "NewContactTrackService";

	@Override
	public boolean deliverSelfNotifications() {
		// TODO Auto-generated method stub
		Log.e(TAG, "Self notification called");
		return true;

	}

	@Override
	public void onChange(boolean selfChange) {
		// TODO Auto-generated method stub

		Log.e(TAG, "Onchange Called");
		people = ctx.getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		while (people.moveToNext()) {

			ContactId = people.getString(people
					.getColumnIndex(ContactsContract.Contacts._ID));
			DisplayName = people.getString(people
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			Log.e(TAG, "New Contact with contact id " + ContactId);
			Log.e(TAG, "New Contact Displayname " + DisplayName);
		}
	}

	public ContactObserver(Handler handler) {
		super(handler);
		Log.e(TAG, "Handler Called");
		// TODO Auto-generated constructor stub
	}

	public void register(Context ctx) {
		Log.e(TAG, "Registering");
		ContactObserver.ctx = ctx;
		curval = ctx.getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, projection, null, null,
				null);
		curval.registerContentObserver(new ContactObserver(new Handler()));
		Log.e(TAG, "Registered");
	}
}