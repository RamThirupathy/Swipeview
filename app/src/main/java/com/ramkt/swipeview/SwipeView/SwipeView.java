/**
 * Created by Ram_Thirupathy on 5/9/2017.
 */
package com.ramkt.swipeview.SwipeView;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.ramkt.swipeview.R;


/**
 * Swipeview class to use for vertical swipe menu in any list,
 * {@link SwipeListener} has to set {@link #setSwipeListener(SwipeListener)} otherwise
 * will crash
 */
public class SwipeView extends FrameLayout implements View.OnTouchListener {
    private View containerLayout;
    private View frontLayout;
    private View swipeLayout;
    private View offsetView;

    private float downpointx;
    private float movepointx;
    private float downpointy;
    private float movepointy;
    private float frontOffset;
    private float scrollOffset = 75f;
    private int totalwidth;
    private int animDuration = 150;//ms default value

    private int offsetviewresid;
    private int frontviewresid;

    public static boolean disableSwipe;
    private boolean isSwiped;
    private static boolean isInSwipedMode;

    private SwipeListener swipeListener;

    public SwipeView(Context context) {
        super(context);
    }

    public SwipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context, attrs);
    }

    public SwipeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(context, attrs);
    }

    public void disableSwipe() {
        disableSwipe = true;
    }

    public void enableSwipe() {
        disableSwipe = false;
    }

    public boolean isSwiped() {
        return isSwiped;
    }

    public static boolean isInSwipedMode() {
        return isInSwipedMode;
    }

    public void setSwipeListener(SwipeListener listener) {
        this.swipeListener = listener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (disableSwipe) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                movepointx = (event.getRawX() - downpointx) + scrollOffset;
                movepointy = (event.getRawY() - downpointy);
                if (movepointx < 0 && (Math.abs(movepointx) > Math.abs(movepointy) || (isInSwipedMode && offsetView.getHeight() > Math.abs(movepointy)))) {
                    claimTouch();
                    if (Math.abs(movepointx) > (totalwidth - (offsetView.getWidth() - frontOffset))) {
                        frontLayout.setTranslationX(-(totalwidth - ((offsetView.getWidth() - frontOffset))));
                        swipeLayout.setTranslationX((offsetView.getWidth() - frontOffset));
                        isSwiped = true;
                        frontLayout.setOnTouchListener(null);
                        swipeListener.onMenuOpen();
                    } else {
                        if ((Math.abs(movepointx)) <= frontOffset) {
                            containerLayout.setTranslationX(movepointx);
                        } else {
                            frontLayout.setTranslationX(movepointx + frontOffset);
                        }
                        swipeLayout.setVisibility(View.VISIBLE);
                        swipeLayout.setTranslationX(totalwidth + movepointx);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                releaseTouch();
                if (movepointx < 0 && (Math.abs(movepointx)) > frontOffset) {
                    //swipe it
                    swipeListener.onSwipeStart();
                    isSwiped = true;
                    frontLayout.setOnTouchListener(null);
                    frontLayout.animate().x(-(totalwidth - (offsetView.getWidth() - frontOffset))).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                            containerLayout.setTranslationX(-frontOffset);
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            swipeListener.onMenuOpen();
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    }).setDuration(animDuration).start();
                    resetSwipeView((offsetView.getWidth() - frontOffset));

                } else {
                    //unswipe it
                    containerLayout.animate().x(0).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            frontLayout.setTranslationX(0);
                            isInSwipedMode = false;
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    }).setDuration(animDuration).start();
                    resetSwipeView(totalwidth);
                }
                break;
            case MotionEvent.ACTION_DOWN:
                downpointx = event.getRawX();
                downpointy = event.getRawY();
                swipeLayout.setTranslationX(totalwidth);
                break;

        }
        return true;
    }

    public void reset() {
        if (frontLayout == null) {
            return;
        }
        final boolean swipeState = isSwiped;
        isSwiped = false;
        frontLayout.animate().x(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                containerLayout.setTranslationX(0);
                offsetView.setTranslationX(0);
                frontLayout.setOnTouchListener(getListener());
                if (swipeState) {
                    isInSwipedMode = false;
                    swipeListener.onReset();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).setDuration(animDuration).start();
        resetSwipeView(totalwidth);
    }

    private void resetSwipeView(final float position) {
        swipeLayout.animate().x(position).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        }).setDuration(animDuration).start();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setViews();
    }

    private void claimTouch() {
        isInSwipedMode = true;
        getParent().requestDisallowInterceptTouchEvent(true);
    }

    private void releaseTouch() {
        getParent().requestDisallowInterceptTouchEvent(false);
    }

    private OnTouchListener getListener() {
        return this;
    }

    private void parseAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.SwipeView);
        animDuration = ta.getInteger(R.styleable.SwipeView_anim_duration, 0);
        frontOffset = ta.getDimensionPixelSize(R.styleable.SwipeView_left_offset, 0);
        offsetviewresid = ta.getResourceId(R.styleable.SwipeView_view_offset_id, 0);
        frontviewresid = ta.getResourceId(R.styleable.SwipeView_view_front_id, 0);
        ta.recycle();
    }

    private void setViews() {
        if (totalwidth == 0) {
            offsetView = findViewById(offsetviewresid);
            frontLayout = findViewById(frontviewresid);
            containerLayout = getChildAt(0);
            swipeLayout = getChildAt(1);
            swipeLayout.setVisibility(View.GONE);
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            totalwidth = metrics.widthPixels;
            frontLayout.setOnTouchListener(this);
        }
    }
}
