package edu.uncc.mad.huduku.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import edu.uncc.mad.huduku.R;
import edu.uncc.mad.huduku.core.constants.CoreConstants;

public class URLActivity extends Activity {

	private WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_url);
		
		webView = (WebView)findViewById(R.id.urlWebView);
		webView.setWebViewClient(new MyWebViewClient());
		
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		
		/**
		 * Fetch the page title and the url from the calling intent. 
		 */
		Intent callingIntent = getIntent();
		String pageTitle = callingIntent.getStringExtra(CoreConstants.URL_HELPER_PAGE_TITLE);
		String pageUrl = callingIntent.getStringExtra(CoreConstants.URL_HELPER_PAGE_URL);
		
		setTitle(pageTitle);
		
		webView.loadUrl(pageUrl);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.url, menu);
		return true;
	}
	
	private class MyWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
	}
}