package edu.uncc.mad.huduku.utils;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import edu.uncc.mad.huduku.R;

public class GuiArrayAdapter extends ArrayAdapter<ImageView> {

	private ArrayList<ImageView> logos;
	private Context context;

	public GuiArrayAdapter(Context c, ArrayList<ImageView> logos) {
		super(c, R.layout.activity_main, new ArrayList<ImageView>());
		this.logos = logos;
		this.context = c;
	}

	@Override
	public View getView(int pos, View view, ViewGroup parent) {
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.layout_gui_adapter, parent, false);
		}

		ImageView iv = (ImageView) view.findViewById(R.id.imageViewLogos);
		switch (pos) {

		case 0:
			iv.setImageResource(R.drawable.yelp_logo);
			break;

		case 1:
			iv.setImageResource(R.drawable.citygrid_logo);
			break;

		case 2:
			iv.setImageResource(R.drawable.google_places_logo);
			break;
		}

		return view;
	}

	@Override
	public int getCount(){
		return 3;
	}
	
}
