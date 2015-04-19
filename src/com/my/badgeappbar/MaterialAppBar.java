package com.my.badgeappbar;


import com.example.badgeappbar.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.PopupMenu;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MaterialAppBar extends LinearLayout{
	
	public static int TOUCH_FEEDBACK_HOLO_DARK = R.drawable.touch_feedback_holo_dark;
	public static int TOUCH_FEEDBACK_HOLO_LIGHT = R.drawable.touch_feedback_holo_light;
	
	private int this_width = 0;
	private int this_height = 0;
	private Context this_context;
	private LinearLayout button_area;
	private LinearLayout leftButtons;
	private LinearLayout rightButtons;
	private int default_touch_feedback = TOUCH_FEEDBACK_HOLO_DARK;
	private OnClickListener onClickListener;
	private int shadow_depth = 4;
	private boolean shadow_calculated = false;
	

	public MaterialAppBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this_context = context;
		// TODO Auto-generated constructor stub
		this.setOrientation(LinearLayout.VERTICAL);
		shadow_depth = (int)(2.00 * this_context.getResources().getDisplayMetrics().density);
		
		
		LinearLayout.LayoutParams button_area_params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 
				LinearLayout.LayoutParams.MATCH_PARENT);
		button_area_params.weight = 1;
		
		button_area = new LinearLayout(this_context);
		button_area.setOrientation(LinearLayout.HORIZONTAL);
		button_area.setLayoutParams(button_area_params);
		
		LinearLayout.LayoutParams shadow_params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, shadow_depth);
		shadow_params.weight = 0;
		
		View shadow = new View(this_context);
		shadow.setBackgroundResource(R.drawable.rectangle_grey_shawdow);
		shadow.setLayoutParams(shadow_params);
		
		this.addView(button_area);
		this.addView(shadow);
		
		leftButtons = new LinearLayout(this_context);
		rightButtons = new LinearLayout(this_context);
		
		LinearLayout.LayoutParams left_params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 
				LinearLayout.LayoutParams.MATCH_PARENT);
		left_params.weight = 1;
		leftButtons.setLayoutParams(left_params);
		leftButtons.setOrientation(LinearLayout.HORIZONTAL);
		
		LinearLayout.LayoutParams right_params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, 
				LinearLayout.LayoutParams.MATCH_PARENT);
		right_params.weight = 0;
		right_params.gravity = Gravity.RIGHT;
		rightButtons.setLayoutParams(right_params);
		rightButtons.setOrientation(LinearLayout.HORIZONTAL);
		
		
		button_area.addView(leftButtons);
		button_area.addView(rightButtons);
		
		this.setBackgroundColor(Color.TRANSPARENT);
		

		
	}
	
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this_width = MeasureSpec.getSize(widthMeasureSpec);
		this_height = MeasureSpec.getSize(heightMeasureSpec);
		
		if (!shadow_calculated) this.getLayoutParams().height = this_height + shadow_depth;
		shadow_calculated = true;
		setMeasuredDimension(this_width, this_height);
	}
	
	public void changeDefaulTouchFeedback(int resourceId){
		default_touch_feedback = resourceId;
	}
	
	public void addOverflowButton(String[] menu, PopupMenu.OnMenuItemClickListener listener, int icon){
		FrameLayout button = createButton("overflow", icon);;
		
		final PopupMenu popup = new PopupMenu(this_context, button);
		for(int i = 0; i < menu.length; i++){
			popup.getMenu().add(menu[i]);
		}
		
		popup.setOnMenuItemClickListener(listener);
		
		button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popup.show();
		}});
		
		rightButtons.addView(button, rightButtons.getChildCount());
		
	}

	public void addButtonToLeft(String name, int icon){
		leftButtons.addView(createButton(name, icon), 0);
	}
	
	public void addButtonToRight(String name, int icon){
		rightButtons.addView(createButton(name, icon));
	}
	
	
	public FrameLayout createButton(String name, int icon){
		int height = this.getLayoutParams().height;
		int padding = (int)(12.00 * this_context.getResources().getDisplayMetrics().density);
		int badge_size = (int)(8.00 * this_context.getResources().getDisplayMetrics().density);
		
		//create button
		LinearLayout.LayoutParams button_params = new LinearLayout.LayoutParams(height, height);
		FrameLayout button = new FrameLayout(this_context);
		button.setLayoutParams(button_params);
		button.setPadding(padding, padding, padding, padding);
		button.setClickable(true);
		button.setBackgroundResource(default_touch_feedback);
		if (name != null) button.setTag(name);
		
		
		//add icon to button
		FrameLayout.LayoutParams image_params = 
				new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 
						LayoutParams.WRAP_CONTENT, 
						Gravity.CENTER);
		ImageView image = new ImageView(this_context);
		image.setLayoutParams(image_params);
		image.setImageResource(icon);
		image.setTag("image");
		button.addView(image);
		
		
		//add badge to the button
		FrameLayout.LayoutParams badge_params = 
				new FrameLayout.LayoutParams(badge_size, 
						badge_size, 
						Gravity.TOP|Gravity.RIGHT);
		ImageView badge = new ImageView(this_context);
		badge.setLayoutParams(badge_params);
		badge.setImageBitmap(drawBadge(0, badge.getLayoutParams().height));
		button.addView(badge);
		badge.setVisibility(View.GONE);
		badge.setTag("badge");
		
		button.setOnClickListener(onClickListener);
		
		return button;
		
		
	}
	
	
	public void pushBadgeByName(String name, int color){
		FrameLayout button = (FrameLayout)this.findViewWithTag(name);
		ImageView badge = (ImageView)button.findViewWithTag("badge");
		badge.setImageBitmap(drawBadge(color, badge.getLayoutParams().height));
		badge.setVisibility(View.VISIBLE);
	}
	
	public void cancelBadgeByName(String name){
		FrameLayout button = (FrameLayout)this.findViewWithTag(name);
		ImageView badge = (ImageView)button.findViewWithTag("badge");
		badge.setVisibility(View.GONE);
	}
	
	
	
	public void changeIconByName(String name, int icon){
		if (icon < 1) return;
		FrameLayout button = (FrameLayout)this.findViewWithTag(name);
		ImageView image = (ImageView)button.findViewWithTag("image");
		image.setImageResource(icon);
		
	}
	
	
	public void setTitle(String title, int color){
		TextView textView = (TextView)leftButtons.findViewWithTag("title");
		if (textView != null) leftButtons.removeView(textView);
		
		LinearLayout.LayoutParams params = 
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		int delta = (int)((72.00 * this_context.getResources().getDisplayMetrics().density) - this.getLayoutParams().height);
		int textSize = 18;
		params.setMargins(delta, 0, 0, 0);
		textView = new TextView(this_context);
		textView.setTextSize(textSize);
		textView.setLayoutParams(params);
		textView.setTag("title");
		textView.getPaint().setFakeBoldText(true);
		if (leftButtons.getChildCount() > 0){
			leftButtons.addView(textView, 1);
		}else{
			leftButtons.addView(textView);
		}
		
		textView.setText(title);
		textView.setClickable(false);
		if (color != 0) textView.setTextColor(color);
		
		
	}
	
	public void setOnButtonClickListener(OnClickListener listener){
		onClickListener = listener;
		
		for (int i = 0; i < leftButtons.getChildCount(); i++){
			if (leftButtons.getChildAt(i) instanceof FrameLayout){
				leftButtons.getChildAt(i).setOnClickListener(listener);
			}
		}
		
		for (int i = 0; i < rightButtons.getChildCount(); i++){
			View v = rightButtons.getChildAt(i);
			if (v instanceof FrameLayout && !v.getTag().equals("overflow")){
				v.setOnClickListener(listener);
			}
		}
	}
	
	
	public String getNameByView(View v){
		String name = v.getTag().toString();
		return name;
	}
	
	public View getButtonByName(String name){
		return this.findViewWithTag(name);
	}
	
	public void setAppbarBackgroundColor(int color){
		button_area.setBackgroundColor(color);
	}
	
	@SuppressLint("NewApi") 
	public void setAppbarBackgroundDrawable(Drawable drawable){
		if (Build.VERSION.SDK_INT > 15){
			button_area.setBackground(drawable);
		}else{
			button_area.setBackgroundDrawable(drawable);
		}
		
		//this.setBackgroundColor(Color.TRANSPARENT);
	}
	
	private Bitmap drawBadge(int color, int size){
		if (color == 0) color = Color.RED;
		Bitmap bmp = Bitmap.createBitmap(size, size, Config.ARGB_8888);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(color);
		
		Canvas canvas = new Canvas(bmp);
		canvas.drawCircle(size / 2, size / 2, size / 2, paint);
		
		return bmp;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	

}
