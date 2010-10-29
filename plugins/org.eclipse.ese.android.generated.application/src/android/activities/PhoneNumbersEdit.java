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
 * PhoneNumbersActivity.
 */
public class PhoneNumbersEdit extends Activity {
	

	private EditText wCellPhone;

	private EditText wWorkPhone;

	
	private Button wBackPhoneNumbers;
	
	private Long rowId;
    private MyContactDbAdapter dbHelper;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle instanceState) {
		super.onCreate(instanceState);
		dbHelper = new MyContactDbAdapter(this);
	    dbHelper.open();
		setContentView(R.layout.edit_phonenumbers);
	
		rowId = instanceState != null ? instanceState.getLong(MyContactDbAdapter.KEY_ROWID) : null;
		if (rowId == null) {
			Bundle extras = getIntent().getExtras();
			rowId = extras != null ? extras.getLong(MyContactDbAdapter.KEY_ROWID) : null;
		}
		
		initControls();
		
		populateFields();
	}

	private void initControls() {
		wCellPhone = (EditText) findViewById(R.id.wCellPhone);

		wWorkPhone = (EditText) findViewById(R.id.wWorkPhone);

	
		wBackPhoneNumbers = (Button) findViewById(R.id.wBackPhoneNumbers);
		wBackPhoneNumbers.setText("Back");
		wBackPhoneNumbers.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				 setResult(RESULT_OK);
	        	 finish();
			}
		});
	
	}
	
    private void populateFields() {
        if (rowId != null) {
            Cursor c = dbHelper.fetchNote(rowId);
            startManagingCursor(c);
            
            wCellPhone.setText(c.getString(c.getColumnIndexOrThrow(MyContactDbAdapter.KEY_CELLPHONE)));
            
            wWorkPhone.setText(c.getString(c.getColumnIndexOrThrow(MyContactDbAdapter.KEY_WORKPHONE)));
            
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
        String cellPhone = wCellPhone.getText().toString();
        
        String workPhone = wWorkPhone.getText().toString();
        

		dbHelper.updateNote(rowId,cellPhone,workPhone);
	
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }

	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append(wCellPhone.getText().toString());

		result.append(wWorkPhone.getText().toString());


		return result.toString();
	}

}
