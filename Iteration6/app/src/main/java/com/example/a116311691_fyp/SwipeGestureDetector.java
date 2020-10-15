package com.example.a116311691_fyp;import android.content.Context;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

public class SwipeGestureDetector extends SimpleOnGestureListener{

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private ViewFlipper cViewFlipper ;
    private Context context ;
    public SwipeGestureDetector(ViewFlipper cViewFlipper,Context context){
        this.cViewFlipper = cViewFlipper ;
        this.context = context ;
    }
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        try {
            // right to left swipe
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                cViewFlipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.left_in));
                cViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.left_out));
                cViewFlipper.showNext();
                return true;
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                cViewFlipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.right_in));
                cViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(context,R.anim.right_out));
                cViewFlipper.showPrevious();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void setTopViewFlipper(ViewFlipper viewFlipper){
        cViewFlipper = viewFlipper ;
    }


}