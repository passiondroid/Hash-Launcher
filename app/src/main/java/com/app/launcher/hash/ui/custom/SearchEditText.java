package com.app.launcher.hash.ui.custom;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.app.launcher.hash.R;
import com.app.launcher.hash.rxBus.RxBus;
import com.app.launcher.hash.rxBus.ScanButtonClickEvent;
import com.app.launcher.hash.ui.home.main.MainFragment;
import com.google.zxing.integration.android.IntentIntegrator;

import io.reactivex.functions.Consumer;

/**
 * Created by arif on 10/11/17.
 */

public class SearchEditText extends AppCompatEditText {

    private int actionX, actionY;
    private Drawable drawableLeft;

    public SearchEditText(Context context) {
        super(context);
        init(context);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(ContextCompat.getDrawable(context, R.drawable.black_border));
        }else{
            setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.black_border));
        }

        setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.barcode_sign),null,null,null);
    }

    @Override
    public void setCompoundDrawables(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        if (left != null) {
            drawableLeft = left;
        }
        super.setCompoundDrawables(left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Rect bounds;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            actionX = (int) event.getX();
            actionY = (int) event.getY();
//            if (drawableBottom != null
//                    && drawableBottom.getBounds().contains(actionX, actionY)) {
//                clickListener.onClick(DrawablePosition.BOTTOM);
//                return super.onTouchEvent(event);
//            }

//            if (drawableTop != null
//                    && drawableTop.getBounds().contains(actionX, actionY)) {
//                clickListener.onClick(DrawablePosition.TOP);
//                return super.onTouchEvent(event);
//            }

            // this works for left since container shares 0,0 origin with bounds
            if (drawableLeft != null) {
                bounds = null;
                bounds = drawableLeft.getBounds();

                int x, y;
                int extraTapArea = (int) (13 * getResources().getDisplayMetrics().density  + 0.5);

                x = actionX;
                y = actionY;

                if (!bounds.contains(actionX, actionY)) {
                    /** Gives the +20 area for tapping. */
                    x = (int) (actionX - extraTapArea);
                    y = (int) (actionY - extraTapArea);

                    if (x <= 0)
                        x = actionX;
                    if (y <= 0)
                        y = actionY;

                    /** Creates square from the smallest value */
                    if (x < y) {
                        y = x;
                    }
                }

                if (bounds.contains(x, y)) {
//                    clickListener.onClick(DrawableClickListener.DrawablePosition.LEFT);
                    RxBus.publish(new ScanButtonClickEvent());
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    return false;

                }
            }

//            if (drawableRight != null) {
//                bounds = null;
//                bounds = drawableRight.getBounds();
//
//                int x, y;
//                int extraTapArea = 13;
//
//                /**
//                 * IF USER CLICKS JUST OUT SIDE THE RECTANGLE OF THE DRAWABLE
//                 * THAN ADD X AND SUBTRACT THE Y WITH SOME VALUE SO THAT AFTER
//                 * CALCULATING X AND Y CO-ORDINATE LIES INTO THE DRAWBABLE
//                 * BOUND. - this process help to increase the tappable area of
//                 * the rectangle.
//                 */
//                x = (int) (actionX + extraTapArea);
//                y = (int) (actionY - extraTapArea);
//
//                /**Since this is right drawable subtract the value of x from the width
//                 * of view. so that width - tappedarea will result in x co-ordinate in drawable bound.
//                 */
//                x = getWidth() - x;
//
//                 /*x can be negative if user taps at x co-ordinate just near the width.
//                 * e.g views width = 300 and user taps 290. Then as per previous calculation
//                 * 290 + 13 = 303. So subtract X from getWidth() will result in negative value.
//                 * So to avoid this add the value previous added when x goes negative.
//                 */
//
//                if(x <= 0){
//                    x += extraTapArea;
//                }
//
//                 /* If result after calculating for extra tappable area is negative.
//                 * assign the original value so that after subtracting
//                 * extratapping area value doesn't go into negative value.
//                 */
//
//                if (y <= 0)
//                    y = actionY;
//
//                /**If drawble bounds contains the x and y points then move ahead.*/
//                if (bounds.contains(x, y) && clickListener != null) {
//                    clickListener
//                            .onClick(DrawableClickListener.DrawablePosition.RIGHT);
//                    event.setAction(MotionEvent.ACTION_CANCEL);
//                    return false;
//                }
//                return super.onTouchEvent(event);
//            }

        }
        return super.onTouchEvent(event);
    }
}
