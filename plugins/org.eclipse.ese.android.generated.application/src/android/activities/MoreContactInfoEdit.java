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
 * MoreContactInfoActivity.
 */
public class MoreContactInfoEdit extends Activity {
	

	private EditText wMore;

	private EditText wMore2;

	private Button wBack;

	
	private Long rowId;
    private MyContactDbAdapter dbHelper;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle instanceState) {
		super.onCreate(instanceState);
		dbHelper = new MyContactDbAdapter(this);
	    dbHelper.open();
		setContentView(R.layout.edit_morecontactinfo);
	
		rowId = instanceState != null ? instanceState.getLong(MyContactDbAdapter.KEY_ROWID) : null;
		if (rowId == null) {
			Bundle extras = getIntent().getExtras();
			rowId = extras != null ? extras.getLong(MyContactDbAdapter.KEY_ROWID) : null;
		}
		
		initControls();
		
		populateFields();
	}

	private void initControls() {
		wMore = (EditText) findViewById(R.id.wMore);

		wMore2 = (EditText) findViewById(R.id.wMore2);

		wBack = (Button) findViewById(R.id.wBack);
		wBack.setText("Back");
		wBack.setOnClickListener(new Button.OnClickListener() {
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
            
            wMore.setText(c.getString(c.getColumnIndexOrThrow(MyContactDbAdapter.KEY_MORE)));
            
            wMore2.setText(c.getString(c.getColumnIndexOrThrow(MyContactDbAdapter.KEY_MORE2)));
            
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
        String more = wMore.getText().toString();
        
        String more2 = wMore2.getText().toString();
        

		dbHelper.updateNote(rowId,more,more2);
	
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }

	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append(wMore.getText().toString());

		result.append(wMore2.getText().toString());

		// We don't display the "Back" Button.


		return result.toString();
	}

}
