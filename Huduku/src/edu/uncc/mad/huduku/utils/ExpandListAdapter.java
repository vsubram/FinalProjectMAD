package edu.uncc.mad.huduku.utils;

import java.util.HashMap;
import java.util.List;

import edu.uncc.mad.huduku.R;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private HashMap<String, String> listData;
	private List<String> listHeaders;
	TextView contentTextView;
	TextView headerTextView;

	public ExpandListAdapter(Context context, List<String> listHeaders,
			HashMap<String, String> listData) {
		this.context = context;
		this.listHeaders = listHeaders;
		this.listData = listData;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return this.listData.get(this.listHeaders.get(groupPosition));
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final String contentText = (String) getChild(groupPosition,
				childPosition);

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.content_list, null);
		}

		contentTextView = (TextView) convertView
				.findViewById(R.id.contentTextView);
		contentTextView.setText(contentText);

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this.listHeaders.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		 if(listHeaders != null && listHeaders.size() == 0){
			Log.d("huduku", "no headers found");
			return 0;
		 }
		 return listHeaders.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(R.layout.header_list, null);
		}

		headerTextView = (TextView) convertView
				.findViewById(R.id.headerTextView);
		headerTextView.setTypeface(null, Typeface.BOLD);
		headerTextView.setText(headerTitle);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
