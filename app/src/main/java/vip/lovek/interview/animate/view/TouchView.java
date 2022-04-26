package vip.lovek.interview.animate.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import vip.lovek.interview.utils.BitmapUtils;
import vip.lovek.interview.utils.Utils;

/**
 * author： yuzhirui@douban.com
 * date: 2022-02-09 16:13
 */
public class TouchView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float IMAGE_WIDTH = Utils.dp2px(200);
    Bitmap bitmap;
    float offsetX;
    float offsetY;
    float downX;
    float downY;
    float originalX;
    float originalY;
    int tractPointId;

    public TouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapUtils.getBitmap(getContext(), (int) IMAGE_WIDTH);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                tractPointId = event.getPointerId(0);
                downX = event.getX();
                downY = event.getY();
                originalX = offsetX;
                originalY = offsetY;
                break;
            case MotionEvent.ACTION_MOVE:
                int index = event.findPointerIndex(tractPointId);
                offsetX = originalX + event.getX(index) - downX;
                offsetY = originalY + event.getY(index) - downY;
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                int actionIndex = event.getActionIndex();
                tractPointId = event.getPointerId(event.getActionIndex());
                downX = event.getX(actionIndex);
                downY = event.getY(actionIndex);
                originalX = offsetX;
                originalY = offsetY;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                actionIndex = event.getActionIndex();
                int pointerId = event.getPointerId(actionIndex);
                if (pointerId == tractPointId) {
                    int newIndex;
                    if (actionIndex == event.getPointerCount() - 1) {
                        newIndex = event.getPointerCount() - 2;
                    } else {
                        newIndex = event.getPointerCount() - 1;
                    }
                    tractPointId = event.getPointerId(newIndex);
                    downX = event.getX(actionIndex);
                    downY = event.getY(actionIndex);
                    originalX = offsetX;
                    originalY = offsetY;
                }
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }
}
