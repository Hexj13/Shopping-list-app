package com.ivjr.shoppinglist.components;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.ivjr.shoppinglist.R;

public class ListCreationDialog extends Dialog implements View.OnClickListener, TextView.OnEditorActionListener {

	private static final String TAG = "ListCreationDialog";

	private Listener mListener;
	private EditText newListNameEditText;

	public ListCreationDialog(Context context) {
		super(context, R.style.ShoppingListTheme_Dialog);
		setTitle(R.string.newListDialogTitle);
		setContentView(R.layout.list_creation_dialog);

		newListNameEditText = (EditText) findViewById(R.id.listCreationDialogNameEditText);
		Button saveButton = (Button) findViewById(R.id.listCreationDialogSaveButton);
		Button cancelButton = (Button) findViewById(R.id.listCreationDialogCancelButton);

		newListNameEditText.setOnEditorActionListener(this);
		saveButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.listCreationDialogSaveButton) dispatchCreateListEvent();
		dismiss();
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_DONE) {
			dispatchCreateListEvent();
			dismiss();
			return true;
		}
		return false;
	}

	private void dispatchCreateListEvent() {
		if (mListener == null) return;
		String listName = newListNameEditText.getText().toString();
		if (listName.length() != 0) mListener.onCreateList(listName);
	}

	public ListCreationDialog setListener(Listener listener) {
		mListener = listener;
		setOnDismissListener(listener);
		return this;
	}

	public interface Listener extends OnDismissListener {
		void onCreateList(String listName);

		@Override
		void onDismiss(DialogInterface dialog);
	}
}
