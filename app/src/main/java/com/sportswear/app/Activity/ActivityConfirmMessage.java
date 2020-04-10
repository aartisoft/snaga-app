package com.sportswear.app.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.sportswear.app.R;
import com.sportswear.app.Util.Constant_Api;
import com.sportswear.app.Util.Method;
import com.sportswear.app.Util.SharedPref;

import im.delight.android.webview.AdvancedWebView;

public class ActivityConfirmMessage extends AppCompatActivity implements AdvancedWebView.Listener {

	private TextView textView;
	private int order;
	private String uniqueID;
	private int  mobileid;
	private AdvancedWebView mWebView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_about_us);
		toolbar.setTitleTextColor(getResources().getColor(R.color.finestBlack));
		toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.left_arrow));
		toolbar.setTitle(getResources().getString(R.string.title_confirm));
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
        //mobileid= Integer.parseInt(SharedPref.getMobileNumber(this));
		//order  =  getIntent().getIntExtra(order_id);
//		if (savedInstanceState == null) {
		Bundle extras = getIntent().getExtras();
//			if(extras == null) {
//				order = 0;
//			} else {
//               // mobileid= Method.getAndroidID(ActivityConfirmMessage.this);
				order= extras.getInt("order_id");
//			}
//		} else {
			//order= (Integer) savedInstanceState.getSerializable("order_id");
//		}
		Toast.makeText(this, "your order_id:"+ order, Toast.LENGTH_SHORT).show();

		mWebView = (AdvancedWebView) findViewById(R.id.webview);
		mWebView.setListener(this, this);
		mWebView.setWebChromeClient(new WebChromeClient() {

			@Override
			public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
				AdvancedWebView newWebView = new AdvancedWebView(ActivityConfirmMessage.this);
				// myParentLayout.addView(newWebView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
				transport.setWebView(newWebView);
				resultMsg.sendToTarget();

				return true;
			}

		});

			// disable third-party cookies only
		mWebView.setThirdPartyCookiesEnabled(false);
		mWebView.setDesktopMode(false);
// or disable cookies in general
		mWebView.setCookiesEnabled(false);
		mWebView.setMixedContentAllowed(false);
		mWebView.loadUrl(Constant_Api.bill+order);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

			case android.R.id.home:
				// app icon in action bar clicked; go home
				Intent intent = new Intent(ActivityConfirmMessage.this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(ActivityConfirmMessage.this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}

	@Override
	public void onPageStarted(String url, Bitmap favicon) { }

	@Override
	public void onPageFinished(String url) { }

	@Override
	public void onPageError(int errorCode, String description, String failingUrl) { }

	@Override
	public void onDownloadRequested(String url, String suggestedFilename, String mimeType,
									long contentLength, String contentDisposition, String userAgent) { }

	@Override
	public void onExternalPageRequest(String url) { }



}

