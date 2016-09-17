package com.ivjr.shoppinglist.mvc.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ivjr.shoppinglist.R;
import com.ivjr.shoppinglist.mvc.model.MainListItem;

import java.util.List;

public class MainListViewAdapter extends BaseAdapter{

	private static final String STATE_NORMAL = "normal";
	private static final String STATE_DELETE = "delete";

	private List<MainListItem> mDataProvider;
	private LayoutInflater mLayoutInflater;
	private String currentState = STATE_NORMAL;

	public MainListViewAdapter(Context context, List<MainListItem> dataProvider) {
		mDataProvider = dataProvider;
		mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mDataProvider.size();
	}

	@Override
	public Object getItem(int position) {
		return mDataProvider.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) view = mLayoutInflater.inflate(R.layout.main_list_item, parent, false);

		MainListItem item = (MainListItem) getItem(position);

		ImageView foreground = (ImageView) view.findViewById(R.id.mainListItemDeletionForeground);
		ImageView foregroundIcon = (ImageView) view.findViewById(R.id.mainListItemDeletionForegroundIcon);

		if (currentState.equals(STATE_NORMAL)) {
			foreground.setVisibility(View.GONE);
			foregroundIcon.setVisibility(View.GONE);
		} else if (currentState.equals(STATE_DELETE)) {
			foreground.setVisibility(View.VISIBLE);
			foregroundIcon.setVisibility(View.VISIBLE);
		}

		((TextView) view.findViewById(R.id.mainListItemTitle)).setText(item.getName());

		// TODO: make a preview of non completed shopping list items.

		return view;
	}

	public void setViewState(String state) {
		currentState = state;
		notifyDataSetChanged();
	}
}
