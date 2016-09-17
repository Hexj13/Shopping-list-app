package com.ivjr.shoppinglist.mvc.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.ivjr.shoppinglist.R;
import com.ivjr.shoppinglist.components.ListCreationDialog;
import com.ivjr.shoppinglist.mvc.controller.MainListViewAdapter;
import com.ivjr.shoppinglist.mvc.model.MainListItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ListCreationDialog.Listener, AdapterView.OnItemClickListener {

	private static final String TAG = "MainActivity";

	private static final String STATE_NORMAL = "normal";
	private static final String STATE_DELETE = "delete";

	private TextView mToolbarTitleTextView;
	private Toolbar mActionBarToolbar;
	private FloatingActionButton mNewListButton;
	private List<MainListItem> mMainListItems = new ArrayList<>();
	private MainListViewAdapter mMainListViewAdapter;
	private ImageView mToolbarBackBtn;
	private ImageView mToolbarDeleteBtn;
	private String currentState = STATE_NORMAL;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initToolbar();
		initMainListView();
		initNewListButton();
	}

	@Override
	protected void onStart() {
		super.onStart();
		setState(STATE_NORMAL);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mNewListButton.show();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mNewListButton.hide();
	}

	// ---------------------------------------------------------------------
	// --- INITIALIZATION
	// ---------------------------------------------------------------------

	private void initToolbar() {
		mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
		setSupportActionBar(mActionBarToolbar);
		ActionBar supportActionBar = getSupportActionBar();
		if (supportActionBar != null) supportActionBar.setDisplayShowTitleEnabled(false);
		mToolbarTitleTextView = (TextView) mActionBarToolbar.findViewById(R.id.toolbar_title);
		mToolbarBackBtn = (ImageView) mActionBarToolbar.findViewById(R.id.toolbar_back_btn);
		mToolbarDeleteBtn = (ImageView) mActionBarToolbar.findViewById(R.id.toolbar_delete_btn);
		mToolbarBackBtn.setOnClickListener(this);
		mToolbarDeleteBtn.setOnClickListener(this);
	}

	private void initMainListView() {
		ListView listView = (ListView) findViewById(R.id.mainListView);
		mMainListViewAdapter = new MainListViewAdapter(this, mMainListItems);
		listView.setAdapter(mMainListViewAdapter);
		listView.setOnItemClickListener(this);
	}

	private void initNewListButton() {
		mNewListButton = (FloatingActionButton) findViewById(R.id.newListButton);
		mNewListButton.setOnClickListener(this);
	}

	// ---------------------------------------------------------------------
	// --- BASE LOGIC
	// ---------------------------------------------------------------------

	public void setState(String state) {
		currentState = state;
		if (state.equals(STATE_NORMAL)) {
			mToolbarTitleTextView.setText(R.string.app_name);
			mActionBarToolbar.setBackgroundResource(R.color.colorPrimary);
			mToolbarBackBtn.setVisibility(View.GONE);
			mToolbarDeleteBtn.setVisibility(View.VISIBLE);
			mNewListButton.show();

		} else if (state.equals(STATE_DELETE)) {
			mToolbarTitleTextView.setText(R.string.toolbarDeleteTitle);
			mActionBarToolbar.setBackgroundResource(R.color.red);
			mToolbarBackBtn.setVisibility(View.VISIBLE);
			mToolbarDeleteBtn.setVisibility(View.GONE);
			mNewListButton.hide();
		}
		mMainListViewAdapter.setViewState(state);
	}

	// ---------------------------------------------------------------------
	// --- View.OnClickListener implementation
	// ---------------------------------------------------------------------

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.newListButton:
				mNewListButton.hide();
				ListCreationDialog dialog = new ListCreationDialog(this);
				dialog.setListener(this).show();
				break;
			case R.id.toolbar_delete_btn:
				setState(STATE_DELETE);
				break;
			case R.id.toolbar_back_btn:
				setState(STATE_NORMAL);
				break;
		}
	}

	// ---------------------------------------------------------------------
	// --- ListCreationDialog.Listener implementation
	// ---------------------------------------------------------------------

	@Override
	public void onCreateList(String listName) {
		// TEMP: !!!
		mMainListItems.add(new MainListItem(listName));
		mMainListViewAdapter.notifyDataSetChanged();
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		mNewListButton.show();
	}

	// ---------------------------------------------------------------------
	// --- AdapterView.OnItemClickListener implementation
	// ---------------------------------------------------------------------

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (currentState.equals(STATE_NORMAL)) {
			// TODO: go to list activity
		} else if (currentState.equals(STATE_DELETE)) {
			// TODO: make a dialog.
			// TEMP: !!!
			mMainListItems.remove(position);
			mMainListViewAdapter.notifyDataSetChanged();
		}
	}
}
