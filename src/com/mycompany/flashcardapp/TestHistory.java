package com.mycompany.flashcardapp;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TestHistory extends Fragment implements OnClickListener {

	View rootview;
	ListView list_view_test_history;
	DataBaseAdapter db;
	public static TestHistoryAdapter newTestHistoryAdapter;
	public static final String FRAGMENT_TAG = "TAG_TestHistory";
	ArrayList<HistoryClass> newHistoryClasseList = new ArrayList<HistoryClass>();
	TextView test_name_text, date_text, time_text, score_text;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			rootview = inflater.inflate(R.layout.test_history_layout, null);
		return rootview;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		newHistoryClasseList.clear();
		
		db = new DataBaseAdapter(getActivity());
		db.open();
		Cursor newCursor = db.getHistoryInfo();
		while (newCursor.moveToNext()){

			HistoryClass new_HistoryClass = new HistoryClass();

			new_HistoryClass.setTestName(newCursor.getString(newCursor.getColumnIndex("TestName")));
			new_HistoryClass.setDate(newCursor.getString(newCursor.getColumnIndex("Date")));
			new_HistoryClass.setTime(newCursor.getString(newCursor.getColumnIndex("Time")));
			new_HistoryClass.setScore(newCursor.getString(newCursor.getColumnIndex("Score")));
			
			newHistoryClasseList.add(new_HistoryClass);
		}
		
		db.close();
		
		Log.v("newHistoryClasseList.size()", ""+newHistoryClasseList.size());
		
		HomeActivity.main_title.setText("Test History");
		
		HomeActivity.back_title.setVisibility(View.VISIBLE);
		HomeActivity.back_title.setText("< Home");
		HomeActivity.back_title.setOnClickListener(this);
		HomeActivity.next_title.setVisibility(View.INVISIBLE);

		test_name_text = (TextView) rootview.findViewById(R.id.test_name_text);
		date_text = (TextView) rootview.findViewById(R.id.date_text);
		time_text = (TextView) rootview.findViewById(R.id.time_text);
		score_text = (TextView) rootview.findViewById(R.id.score_text);

		test_name_text.setTypeface(HomeActivity.Helvetica);
		date_text.setTypeface(HomeActivity.Helvetica);
		time_text.setTypeface(HomeActivity.Helvetica);
		score_text.setTypeface(HomeActivity.Helvetica);
		
		list_view_test_history = (ListView) rootview.findViewById(R.id.list_view_test_history);
		newTestHistoryAdapter = new TestHistoryAdapter(getActivity());
		list_view_test_history.setAdapter(newTestHistoryAdapter);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.back_title:
			HomeActivity.fragment_manager.popBackStack();
			break;

		default:
			break;
		}
	}

	public class TestHistoryAdapter extends BaseAdapter {

		ViewHolder viewHolder;
		LayoutInflater inflater;
		Context c;

		public TestHistoryAdapter(Context c) {
			this.c = c;
			inflater = LayoutInflater.from(c);
		}

		@Override
		public int getCount() {
			return newHistoryClasseList.size();
		}

		@Override
		public Object getItem(int position) {
			return newHistoryClasseList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public class ViewHolder {
			@SuppressWarnings("unchecked")
			public <T extends View> T get(View view, int id) {
				SparseArray<View> viewHolder = (SparseArray<View>) view
						.getTag();
				if (viewHolder == null) {
					viewHolder = new SparseArray<View>();
					view.setTag(viewHolder);
				}
				View childView = viewHolder.get(id);
				if (childView == null) {
					childView = view.findViewById(id);
					viewHolder.put(id, childView);
				}

				return (T) childView;
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			viewHolder = new ViewHolder();
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.list_view_test_history_layout, null);
			}
			
			TextView test_name, test_history_date, test_history_time, test_history_score;
			
			test_name = viewHolder.get(convertView, R.id.test_name);
			test_history_date = viewHolder.get(convertView, R.id.test_history_date);
			test_history_time = viewHolder.get(convertView, R.id.test_history_time);
			test_history_score = viewHolder.get(convertView, R.id.test_history_score);

			test_name.setTypeface(HomeActivity.Helvetica);
			test_history_date.setTypeface(HomeActivity.Helvetica);
			test_history_time.setTypeface(HomeActivity.Helvetica);
			test_history_score.setTypeface(HomeActivity.Helvetica);
			
			test_name.setText(newHistoryClasseList.get(position).getTestName());
			test_history_date.setText(newHistoryClasseList.get(position).getDate());
			test_history_time.setText(newHistoryClasseList.get(position).getTime());
			test_history_score.setText(newHistoryClasseList.get(position).getScore());
			
			/*convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
				}
			});*/
			return convertView;
		}
	}
}
