package com.mycompany.flashcardapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment implements OnClickListener {

	View rootview;
	TextView flashcards, multi_choices, high_scores, test_history;
	
	public static final String FRAGMENT_TAG = "TAG_HomeFragment";
	
	public static String view_selection = "";
	
	public static ArrayList<FlashQuesClass> newFlashQuesClasses = new ArrayList<FlashQuesClass>();
	
	public static ArrayList<String> flash_qus_title = new ArrayList<String>();
	public static ArrayList<String> flash_qus = new ArrayList<String>();
	public static ArrayList<String> flash_ans = new ArrayList<String>();
	public static ArrayList<String> flash_qus_ans = new ArrayList<String>();
	
	String temp_flash_qus_title = "";
	
	public static ArrayList<String> multi_qus_title = new ArrayList<String>();
	public static ArrayList<String> multi_qus = new ArrayList<String>();
	public static ArrayList<String> multi_ans = new ArrayList<String>();
	public static ArrayList<String> multi_qus_ans = new ArrayList<String>();

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			rootview = inflater.inflate(R.layout.home_fragment_layout, null);
		return rootview;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		HomeActivity.main_title.setText("Home");
		
		HomeActivity.back_title.setVisibility(View.INVISIBLE);
		HomeActivity.next_title.setVisibility(View.INVISIBLE);
		
		newFlashQuesClasses.clear();
		
		flash_qus_title.clear();
		flash_qus_ans.clear();
		flash_qus.clear();
		flash_ans.clear();
		
		multi_qus_title.clear();
		multi_qus_ans.clear();
		multi_qus.clear();
		multi_ans.clear();

		flashcards = (TextView) rootview.findViewById(R.id.flashcards);
		multi_choices = (TextView) rootview.findViewById(R.id.multi_choices);
		high_scores = (TextView) rootview.findViewById(R.id.high_scores);
		test_history = (TextView) rootview.findViewById(R.id.test_history);

		flashcards.setTypeface(HomeActivity.Helvetica);
		multi_choices.setTypeface(HomeActivity.Helvetica);
		high_scores.setTypeface(HomeActivity.Helvetica);
		test_history.setTypeface(HomeActivity.Helvetica);
		
		flashcards.setOnClickListener(this);
		multi_choices.setOnClickListener(this);
		high_scores.setOnClickListener(this);
		test_history.setOnClickListener(this);
	}
	
	@Override
	public void onResume() {
		new FlashPlistParsing(getActivity()).getProductsPlistValues();
		new MultiPlistParsing(getActivity()).getProductsPlistValues();
		
		HomeActivity.fragment_manager.popBackStack(1, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		
		super.onResume();
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.flashcards:
			view_selection = "flashcards";
			HomeActivity.fragment_manager.beginTransaction().
				replace(R.id.frame_layout, new QuestionType1(), QuestionType1.FRAGMENT_TAG).addToBackStack(null).commit();
			break;
			
		case R.id.multi_choices:
			view_selection = "multi_choices";
			HomeActivity.fragment_manager.beginTransaction().
				replace(R.id.frame_layout, new QuestionType1(), QuestionType1.FRAGMENT_TAG).addToBackStack(null).commit();
			break;
			
		case R.id.high_scores:
			view_selection = "high_scores";
			HomeActivity.fragment_manager.beginTransaction().
				replace(R.id.frame_layout, new HighScore(), HighScore.FRAGMENT_TAG).addToBackStack(null).commit();
			break;
			
		case R.id.test_history:
			view_selection = "test_history";
			HomeActivity.fragment_manager.beginTransaction().
				replace(R.id.frame_layout, new TestHistory(), TestHistory.FRAGMENT_TAG).addToBackStack(null).commit();
			break;

		default:
			break;
		}
	}
	
	public class FlashPlistParsing {   
		Context context;

		// constructor for  to get the context object from where you are using this plist parsing
		public FlashPlistParsing(Context ctx) {
			context = ctx;
		}
		
		@SuppressWarnings({ "static-access", "unused" })
		public List<HashMap<String, String>> getProductsPlistValues() {
			XmlResourceParser parser = context.getResources().getXml(R.xml.flashlist);
			
			boolean keytag = false;
	        boolean valuetag = false;
	        String keyStaring = null;
	        String stringvalue = null;
	        
	        HashMap<String, String> hashmap = new HashMap<String, String>();
	        List<HashMap<String, String>> listResult = new ArrayList<HashMap<String, String>>();
	        int event;
	        
	        try {
	        	event = parser.getEventType();
	        	FlashQuesClass newFlashQuesClass = new FlashQuesClass();
	        	 while (event != parser.END_DOCUMENT) {
	        		 switch (event) {
	        		 case 0:
	                     // start doccumnt nothing to do
	                     // System.out.println("\n" + parser.START_DOCUMENT
	                     // + "strat doccument");
	                     // System.out.println(parser.getName());
	        			 break;
	        			 
	        		 case 1:
	                     // end doccument
	                     // System.out
	                     // .println("\n" + parser.END_DOCUMENT + "end doccument");
	                     // System.out.println(parser.getName());
	        			 break;
	        			 
	        		 case 2:
	        			 if (parser.getName().equals("key")) {
	                         keytag = true;
	                         valuetag = false;
	                     }
	        			 if (parser.getName().equals("string")) {
	                         valuetag = true;
	                     }
	        			 break;
	        			 
	        		 case 3:
	        			 if (parser.getName().equals("dict")) {
	                         listResult.add(hashmap);
	                         //Log.v("size", listResult.size()+"");
	                         hashmap = null;
	                         hashmap = new HashMap<String, String>();
	                     }
	        			 break;
	        			 
	        		 case 4:
	        			 if (keytag) {
	                         if (valuetag == false) {
	                             hashmap.put("value", parser.getText());
	                             //starttag = false;
	                             
		                         //Log.v("parser.getText()", parser.getText()+"");
		                         if (parser.getText().equals("QuestionAnswer")) {
		                        	 //newFlashQuesClass.ans = parser.getText();
		                         } else if (parser.getText().equals("QuestionTitle")) {
		                        	 //newFlashQuesClass.ques = parser.getText();
		                         } else if (parser.getText() != null){
		                        	 /*for (int i = 0; i < flash_qus_title.size(); i++) {
		                        		 if (!flash_qus_title.get(i).equals(temp_flash_qus_title)) {
				                        	 flash_qus_title.add(parser.getText());
		                        		 }
		                        	 }*/
		                        	 flash_qus_title.add(parser.getText());
		                        	 
			                         //Log.v("title", parser.getText()+"");
		                        	 //temp_flash_qus_title = parser.getText();
		                         }
	                             keyStaring = parser.getText();
	                         }
	                     }
	        			 if (valuetag && keytag) {
	                         stringvalue = parser.getText();

	                         hashmap.put(keyStaring, stringvalue);
	                         //Log.v("", keyStaring);
	                         //Log.v("", stringvalue);
	                         //Log.v("temp_flash_qus_title", temp_flash_qus_title);
	                         flash_qus_ans.add(hashmap.get(keyStaring));
	                         //Log.v("keyStaring", "" + hashmap.get(keyStaring)+"");
	                         valuetag = false;
	                         keytag = false;
	                         //Toast.makeText(getApplication(), keyStaring, Toast.LENGTH_SHORT).show();
	                     }
	        			 break;
	        			 
	        		 default:
	                     break;
	        		 }
	        		 //FlashQuesClass newFlashQuesClass = new FlashQuesClass();
	        		 //newFlashQuesClass.title = parser.getText();
	        		 /*if (hashmap.get(keyStaring) == null) {
	        			 
	                     Log.v("parser.getText()", parser.getText()+"");
	        		 } else {
	                     Log.v("parser.getText()", parser.getText()+"");
	                     Log.v("keyStaring", "" + hashmap.get(keyStaring)+"");
	        		 }*/
	        		 event = parser.next();
	        	 }
	        } catch (XmlPullParserException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        Log.v("flash_qus_title.size()", ""+flash_qus_title.size());
	        Log.v("flash_qus_ans.size()", ""+flash_qus_ans.size());
	        for (int i = 0; i < flash_qus_ans.size(); i++) {
				if (i%2 == 0) {
					flash_ans.add(flash_qus_ans.get(i)); 
				} else {
					flash_qus.add(flash_qus_ans.get(i)); 
				}
				
			}
	        Log.v("flash_qus.size()", ""+flash_qus.size());
	        Log.v("flash_ans.size()", ""+flash_ans.size());
	        
	        return listResult;
		}
	}

	
	public class MultiPlistParsing {   
		Context context;

		// constructor for  to get the context object from where you are using this plist parsing
		public MultiPlistParsing(Context ctx) {
			context = ctx;
		}
		
		@SuppressWarnings({ "static-access", "unused" })
		public List<HashMap<String, String>> getProductsPlistValues() {
			XmlResourceParser parser = context.getResources().getXml(R.xml.mcqlist);
			
			boolean keytag = false;
	        boolean valuetag = false;
	        String keyStaring = null;
	        String stringvalue = null;
	        
	        HashMap<String, String> hashmap = new HashMap<String, String>();
	        List<HashMap<String, String>> listResult = new ArrayList<HashMap<String, String>>();
	        int event;
	        
	        try {
	        	event = parser.getEventType();
	        	FlashQuesClass newFlashQuesClass = new FlashQuesClass();
	        	 while (event != parser.END_DOCUMENT) {
	        		 switch (event) {
	        		 case 0:
	                     // start doccumnt nothing to do
	                     // System.out.println("\n" + parser.START_DOCUMENT
	                     // + "strat doccument");
	                     // System.out.println(parser.getName());
	        			 break;
	        			 
	        		 case 1:
	                     // end doccument
	                     // System.out
	                     // .println("\n" + parser.END_DOCUMENT + "end doccument");
	                     // System.out.println(parser.getName());
	        			 break;
	        			 
	        		 case 2:
	        			 if (parser.getName().equals("key")) {
	                         keytag = true;
	                         valuetag = false;
	                     }
	        			 if (parser.getName().equals("string")) {
	                         valuetag = true;
	                     }
	        			 break;
	        			 
	        		 case 3:
	        			 if (parser.getName().equals("dict")) {
	                         listResult.add(hashmap);
	                         //Log.v("size", listResult.size()+"");
	                         hashmap = null;
	                         hashmap = new HashMap<String, String>();
	                     }
	        			 break;
	        			 
	        		 case 4:
	        			 if (keytag) {
	                         if (valuetag == false) {
	                             hashmap.put("value", parser.getText());
	                             //starttag = false;
	                             

		                         if (parser.getText().equals("A")) {
		                        	 
		                         } else if (parser.getText().equals("B")) {
		                        	 
		                         } else if (parser.getText().equals("C")) {
		                        	 
		                         } else if (parser.getText().equals("D")) {
		                        	 
		                         } else if (parser.getText().equals("E")) {
		                        	 
		                         } else if (parser.getText().equals("QuestionAnswer")) {

		                         } else if (parser.getText().equals("QuestionTitle")) {

		                         }  else if (parser.getText() != null){
		                        	 multi_qus_title.add(parser.getText());
			                         //Log.v("title", parser.getText()+"");
		                         }
	                             keyStaring = parser.getText();
	                         }
	                     }
	        			 if (valuetag && keytag) {
	                         stringvalue = parser.getText();

	                         hashmap.put(keyStaring, stringvalue);
	                         //Log.v("", keyStaring);
	                         //Log.v("", stringvalue);
	                         multi_qus_ans.add(hashmap.get(keyStaring));
	                         Log.v("keyStaring", "" + hashmap.get(keyStaring)+"");
	                         valuetag = false;
	                         keytag = false;
	                         //Toast.makeText(getApplication(), keyStaring, Toast.LENGTH_SHORT).show();
	                     }
	        			 break;
	        			 
	        		 default:
	                     break;
	        		 }
	        		 //FlashQuesClass newFlashQuesClass = new FlashQuesClass();
	        		 //newFlashQuesClass.title = parser.getText();
	        		 /*if (hashmap.get(keyStaring) == null) {
	        			 
	                     Log.v("parser.getText()", parser.getText()+"");
	        		 } else {
	                     Log.v("parser.getText()", parser.getText()+"");
	                     Log.v("keyStaring", "" + hashmap.get(keyStaring)+"");
	        		 }*/
	        		 event = parser.next();
	        	 }
	        } catch (XmlPullParserException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        Log.v("multi_qus_title.size()", ""+multi_qus_title.size());
	        Log.v("multi_qus_ans.size()", ""+multi_qus_ans.size());
	        for (int i = 0; i < multi_qus_ans.size(); i++) {
				/*if (i%4 == 0) {
					flash_ans.add(flash_qus_ans.get(i)); 
				} else {
					flash_qus.add(flash_qus_ans.get(i)); 
				}*/
	        	
			}
	        //Log.v("flash_qus.size()", ""+flash_qus.size());
	        //Log.v("flash_ans.size()", ""+flash_ans.size());
	        
	        return listResult;
		}
	}
	
}
