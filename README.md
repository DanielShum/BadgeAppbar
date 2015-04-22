<p>android material design action bar <br></p>

<p>BadgeAppbar is a material design project.<br>
It is an action bar view which a badge can be pushed.<br>
<br>
<a href="https://github.com/DanielShum/BadgeAppbar/blob/master/res/drawable/appbar.png?raw=true" target="_blank"><img src="https://github.com/DanielShum/BadgeAppbar/raw/master/res/drawable/appbar.png?raw=true" style="max-width:100%;"></a><br>
<br>
add a button to left side:<br>
addButtonToLeft(String button_name, int icon_resourceId)<br>
<br>
add a button to right side:<br>
addButtonToLeft(String button_name, int icon_resourceId)<br></p>

<p>push a badge to button:<br>
pushBadgeByName(String button_name, int color_default_red)<br>
<br>
cancel a badge:<br>
cancelBadgeByName(String button_name)<br>
<br>
add an overflow button:<br>
addOverflowButton(String[] menu, PopupMenu.OnMenuItemClickListener listener, int icon)<br>
<br>
push a badge to overflow button:<br>
pushBadgeByName("overflow", Color.RED);<br>
<br>
To change the default touch feedback:<br>
changeDefaulTouchFeedback(int button_touch_feedback_resourceId)<br>
<br>
Set a title to the app bar:<br>
setTitle(String title, int color)<br> 
eg. - appbar.setTitle("this is title", Color.WHITE); <br></p>