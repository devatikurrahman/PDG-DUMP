package com.mycompany.flashcardapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

public class HomeActivity extends Activity {

	FrameLayout frame_layout;
	public static FragmentManager fragment_manager;
	public static TextView back_title, main_title, next_title;
	public static Typeface Helvetica;
	public static Typeface HelveticaBold;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_home);
		copyDatabase();
		frame_layout = (FrameLayout) findViewById(R.id.frame_layout);
		fragment_manager = getFragmentManager();
		
		fragment_manager.beginTransaction().replace(R.id.frame_layout, new HomeFragment(),
				HomeFragment.FRAGMENT_TAG).addToBackStack(null).commit();

		Helvetica = Typeface.createFromAsset(getAssets(), "Helvetica.ttf");
		HelveticaBold = Typeface.createFromAsset(getAssets(), "Helvetica Bold.ttf");
		
		main_title = (TextView) findViewById(R.id.main_title);
		main_title.setTypeface(HelveticaBold);
		
		back_title = (TextView) findViewById(R.id.back_title);
		back_title.setTypeface(Helvetica);
		next_title = (TextView) findViewById(R.id.next_title);
		next_title.setTypeface(Helvetica);
	}
	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}



	@Override
	public void onBackPressed() {
		try{
			Score newScore = (Score) fragment_manager.findFragmentByTag("TAG_Score");
			QuestionPagerView newQuestionPagerView = (QuestionPagerView) fragment_manager.
					findFragmentByTag("TAG_QuestionPagerView");

			if((newScore != null && newScore.isVisible())||
					(newQuestionPagerView != null && newQuestionPagerView.isVisible())){
				return;
			}

			if (fragment_manager.findFragmentByTag("TAG_HomeFragment") != null 
					&& fragment_manager.findFragmentByTag("TAG_HomeFragment").isVisible()) {
				
				finish();
			} else {
				super.onBackPressed();
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	@SuppressLint("SdCardPath")
	private void copyDatabase() {
		File dbExists = new File("/data/data/" + getPackageName()+ "/databases");
		if (!dbExists.exists()) {
			
			Log.v("copy database", "copy database");
			dbExists.mkdir();
			dbExists.mkdirs();
			try {
				InputStream dbIn = getAssets().open("FlashCardDatabase.db");
				OutputStream dbOut = new FileOutputStream(
						dbExists.getAbsolutePath() + File.separator
								+ "FlashCardDatabase.db");

				byte[] buffer = new byte[1024];
				int lenth;
				while ((lenth = dbIn.read(buffer)) > 0) {
					dbOut.write(buffer, 0, lenth);
				}
				dbOut.flush();
				dbOut.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
