# Swipe View
Swipe view to use in the list

# Features
1. Swipe left to open the menu
2. Adjust the swipe offset easily
3. Add main view and menu view easily

# Demo

### Swipe Half
<img src="https://github.com/RamThirupathy/Swipeview/blob/master/swipeview.gif" width="35%"> 

### Swipe Zero
<img src="https://github.com/RamThirupathy/Swipeview/blob/master/swipeview1.gif" width="35%"> 

### Swipe Full
<img src="https://github.com/RamThirupathy/Swipeview/blob/master/swipeview2.gif" width="35%"> 

### Step 1
Add the following view
```java
<com.ramkt.swipeview.SwipeView.SwipeView
    xmlns:swipe="http://schemas.android.com/apk/res-auto"
    xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@+id/swipeView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        swipe:anim_duration="250"
        swipe:left_offset="@dimen/imw"//left offset can be 0dp up to width of view(assigned to view_front_id attribute)
        swipe:view_front_id="@+id/row"//is the view(generally image) has to position in left
        swipe:view_offset_id="@+id/program_4">

        <include layout="@layout/program_list"></include>//add your main layout

        <include layout="@layout/swipe_view"></include>//add your menu layout
    </com.ramkt.swipeview.SwipeView.SwipeView>
```
### Step 2
Make sure to set swipe:view_front_id
```java
swipe:view_front_id="@+id/row"
```

Contributions
-------

Any contributions are welcome!

Thanks
-------
