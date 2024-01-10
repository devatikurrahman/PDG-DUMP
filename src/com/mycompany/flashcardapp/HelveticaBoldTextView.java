package com.mycompany.flashcardapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class HelveticaBoldTextView extends TextView {
	public static Typeface HelveticaBold;
	private Context context;
	
	public HelveticaBoldTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		setHelvetica();
	}

	/*
	 * public AvenirTextView(Context context, AttributeSet attrs, int defStyle)
	 * { super(context, attrs, defStyle); this.context = context; setAvenir(); }
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	public void onDraw(Canvas canvas) {

		super.onDraw(canvas);

	}

	private void setHelvetica() {
		HelveticaBold = Typeface.createFromAsset(context.getAssets(), "Helvetica Bold.ttf");
	}

}
