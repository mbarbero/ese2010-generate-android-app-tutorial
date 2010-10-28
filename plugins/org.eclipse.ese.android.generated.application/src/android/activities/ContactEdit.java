/*******************************************************************************
 * Copyright (c) 2006, 2007, 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package android.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * ContactActivity.
 */
public class ContactEdit extends Activity {
	
	private static final int ACTIVITY_NEXT = 7;

	private EditText wFirstName;

	private EditText wLastName;

	private EditText wPhoneNumber;

	private EditText wEmail;

	private Spinner wCountry;

	private Button wSave;

	private Button wNext;

	
	private Long rowId;
    private MyContactDbAdapter dbHelper;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle instanceState) {
		super.onCreate(instanceState);
		dbHelper = new MyContactDbAdapter(this);
	    dbHelper.open();
		setContentView(R.layout.edit_contact);
	
		rowId = instanceState != null ? instanceState.getLong(MyContactDbAdapter.KEY_ROWID) : null;
		if (rowId == null) {
			Bundle extras = getIntent().getExtras();
			rowId = extras != null ? extras.getLong(MyContactDbAdapter.KEY_ROWID) : null;
		}
		
		initControls();
		
		populateFields();
	}

	private void initControls() {
		wFirstName = (EditText) findViewById(R.id.wFirstName);

		wLastName = (EditText) findViewById(R.id.wLastName);

		wPhoneNumber = (EditText) findViewById(R.id.wPhoneNumber);

		wEmail = (EditText) findViewById(R.id.wEmail);

		wCountry = (Spinner) findViewById(R.id.wCountry);

		wSave = (Button) findViewById(R.id.wSave);
		wSave.setText("Save");
		wSave.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				 setResult(RESULT_OK);
	        	 finish();
			}
		});
		
		wNext = (Button) findViewById(R.id.wNext);
		wNext.setText("Next");
		wNext.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (rowId == null) {
					saveOrCreate();
				}
			
				Intent i = new Intent(v.getContext(), MoreContactInfoEdit.class);
		        i.putExtra(MyContactDbAdapter.KEY_ROWID, rowId);
		        startActivityForResult(i, ACTIVITY_NEXT);
			}
		});

	}
	
    private void populateFields() {
        if (rowId != null) {
            Cursor c = dbHelper.fetchNote(rowId);
            startManagingCursor(c);
            
            wFirstName.setText(c.getString(c.getColumnIndexOrThrow(MyContactDbAdapter.KEY_FIRSTNAME)));
            
            wLastName.setText(c.getString(c.getColumnIndexOrThrow(MyContactDbAdapter.KEY_LASTNAME)));
            
            wPhoneNumber.setText(c.getString(c.getColumnIndexOrThrow(MyContactDbAdapter.KEY_PHONENUMBER)));
            
            wEmail.setText(c.getString(c.getColumnIndexOrThrow(MyContactDbAdapter.KEY_EMAIL)));
            
            wCountry.setSelection(c.getInt(c.getColumnIndexOrThrow(MyContactDbAdapter.KEY_COUNTRY)));

        }
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(MyContactDbAdapter.KEY_ROWID, rowId);
    }
    
    @Override
    protected void onPause() {
        super.onPause();  
		saveOrCreate();
    }
    
    private void saveOrCreate() {
        String firstName = wFirstName.getText().toString();
        
        String lastName = wLastName.getText().toString();
        
        String phoneNumber = wPhoneNumber.getText().toString();
        
        String email = wEmail.getText().toString();
        
        int country = wCountry.getSelectedItemPosition();


		if (rowId == null) {
	        long id = dbHelper.createContact(firstName,lastName,phoneNumber,email,country);
	        if (id > 0) {
	            rowId = id;
	        }
	    } else {
	        dbHelper.updateNote(rowId,firstName,lastName,phoneNumber,email,country);
	    }
    
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }

	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append(wFirstName.getText().toString());

		result.append(wLastName.getText().toString());

		result.append(wPhoneNumber.getText().toString());

		result.append(wEmail.getText().toString());

		result.append(wCountry.getSelectedItemPosition());

		// We don't display the "Save" Button.


		return result.toString();
	}

}
