package vip.lovek.interview.animate.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;

import vip.lovek.interview.utils.BitmapUtils;
import vip.lovek.interview.utils.Utils;

/**
 * author： yuzhirui@douban.com
 * date: 2022-02-09 21:47
 */
public class ScalableImageView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float IMAGE_WIDTH = Utils.dp2px(200);
    public static final float OVER_SCALE_FACTOR = 1.5f;
    Bitmap bitmap;
    float originalOffsetX;
    float originalOffsetY;
    float offsetX;
    float offsetY;
    float smallScale;
    float bigScale;
    GestureDetectorCompat gestureDetector;
    boolean big = false;
    float currentScale; // 0 ~ 1f
    ObjectAnimator scaleAnimator;
    OverScroller scroller;
    HenGestureListener henGestureListener = new HenGestureListener();
    HenFlingRunner henFlingRunner = new HenFlingRunner();
    ScaleGestureDetector scaleGestureDetector;
    HenScaleGestureListener henScaleGestureListener = new HenScaleGestureListener();

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapUtils.getBitmap(getContext(), (int) IMAGE_WIDTH);
        scroller = new OverScroller(getContext());
        gestureDetector = new GestureDetectorCompat(getContext(), henGestureListener);
//        gestureDetector.setIsLongpressEnabled(false);
        scaleGestureDetector = new ScaleGestureDetector(getContext(), henScaleGestureListener);
    }

    public float getCurrentScale() {
        return currentScale;
    }

    public void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
        invalidate();
    }

    public ObjectAnimator getScaleAnimator() {
        if (scaleAnimator == null) scaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", 0, 1);
        scaleAnimator.setFloatValues(smallScale, bigScale);
        return scaleAnimator;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        originalOffsetX = (float) ((getWidth() - bitmap.getWidth()) / 2.0);
        originalOffsetY = (float) ((getHeight() - bitmap.getHeight()) / 2.0);

        if ((float) bitmap.getWidth() / bitmap.getHeight() > (float) getWidth() / getHeight()) {
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight() * OVER_SCALE_FACTOR;
        } else {
            smallScale = (float) getHeight() / bitmap.getHeight();
            bigScale = (float) getWidth() / bitmap.getWidth() * OVER_SCALE_FACTOR;
        }
        currentScale = smallScale;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float scaleFraction = (currentScale - smallScale) / (currentScale - bigScale);
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction);
        canvas.scale(currentScale, currentScale, getWidth() / 2f, getHeight() / 2f);
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = scaleGestureDetector.onTouchEvent(event);
        if (!scaleGestureDetector.isInProgress()) {
            result = gestureDetector.onTouchEvent(event);
        }
        return result;
    }

    class HenGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            big = !big;
            if (big) {
                offsetX = (e.getX() - getWidth() / 2f) - (e.getX() - getWidth() / 2f) * bigScale / smallScale;
                offsetY = (e.getY() - getHeight() / 2f) - (e.getY() - getHeight() / 2f) * bigScale / smallScale;
                getScaleAnimator().start();
            } else {
                getScaleAnimator().reverse();
            }
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent down, MotionEvent event, float distanceX, float distanceY) {
            if (big) {
                offsetX -= distanceX;
                offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2);
                offsetY -= distanceY;
                offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) / 2);
                invalidate();
            }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent down, MotionEvent event, float velocityX, float velocityY) {
            if (big) {
                scroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                        (int) ((bitmap.getWidth() * bigScale - getWidth()) / -2f),
                        (int) (bitmap.getWidth() * bigScale - getWidth() / 2f),
                        (int) ((bitmap.getHeight() * bigScale - getHeight()) / -2f),
                        (int) ((bitmap.getHeight() * bigScale - getHeight()) / 2f));
                postOnAnimation(henFlingRunner);
            }
            return false;
        }
    }

    class HenFlingRunner implements Runnable {
        @Override
        public void run() {
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.getCurrX();
                offsetY = scroller.getCurrY();
                invalidate();
                postOnAnimation(this);
            }
        }
    }

    class HenScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {
        float initialScale;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            currentScale = initialScale * detector.getScaleFactor();
            invalidate();
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            initialScale = currentScale;
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }
}
