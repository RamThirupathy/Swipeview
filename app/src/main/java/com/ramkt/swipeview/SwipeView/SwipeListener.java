/**
 * Created by Ram_Thirupathy on 5/10/2017.
 */
package com.ramkt.swipeview.SwipeView;


/**
 * Interface to be set in {@link SwipeView#setSwipeListener(SwipeListener)}
 */
public interface SwipeListener {
    void onSwipeStart();
    void onMenuOpen();
    void onReset();
}
