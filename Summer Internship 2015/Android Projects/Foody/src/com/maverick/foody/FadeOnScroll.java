package com.maverick.foody;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class FadeOnScroll extends ScrollView {
	
	private OnScrollViewListener mOnScrollViewListener;

	public FadeOnScroll(Context context) {
		super(context);
	}
	public FadeOnScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public FadeOnScroll(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public interface OnScrollViewListener {
	    void onScrollChanged( FadeOnScroll v, int l, int t, int oldl, int oldt );
	}
	
	public void setOnScrollViewListener(OnScrollViewListener l) {
	    this.mOnScrollViewListener = l;
	}
	
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
	    mOnScrollViewListener.onScrollChanged( this, l, t, oldl, oldt );
	    super.onScrollChanged( l, t, oldl, oldt );
	}
}