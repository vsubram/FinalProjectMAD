package edu.uncc.mad.huduku;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.uncc.mad.huduku.activities.URLActivity;
import edu.uncc.mad.huduku.core.constants.CoreConstants;

public class DealsActivity extends Activity {
	
	private List<String> dealsText;
	private List<String> dealsUrl;
	private ListView listViewDeals;
	private ArrayAdapter<String> listViewAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deals);
		
		dealsText = getIntent().getStringArrayListExtra(CoreConstants.DEALS_TEXT_LIST);
		dealsUrl = getIntent().getStringArrayListExtra(CoreConstants.DEALS_TEXT_URLS);
		setTitle(getIntent().getCharSequenceExtra(CoreConstants.RESTAURANT_NAME));
		
		/**
		 * List View Initializations
		 */
		listViewDeals = (ListView) findViewById(R.id.listViewDeals);

		/**
		 * Creating a list view adapter
		 */
		listViewAdapter = new ArrayAdapter<String>(getBaseContext(),
				android.R.layout.simple_list_item_1, android.R.id.text1, dealsText);

		// setting values for the newly created list view
		listViewDeals.setAdapter(listViewAdapter);
		
		listViewDeals.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int clickedDealIndex, long arg3) {
				
				Intent i = new Intent(getApplicationContext(), URLActivity.class);
				i.putExtra(CoreConstants.URL_HELPER_PAGE_TITLE, getIntent().getStringExtra(CoreConstants.RESTAURANT_NAME));
				i.putExtra(CoreConstants.URL_HELPER_PAGE_URL, dealsUrl.get(clickedDealIndex));
				
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.deals, menu);
		return true;
	}

}
