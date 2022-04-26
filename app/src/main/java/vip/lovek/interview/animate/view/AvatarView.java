package vip.lovek.interview.animate.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import vip.lovek.interview.utils.BitmapUtils;
import vip.lovek.interview.utils.Utils;

/**
 * author： yuzhirui@douban.com
 * date: 2022-02-07 21:37
 */
public class AvatarView extends View {
    public static final float RADIUS = Utils.dp2px(120);
    Bitmap bitmap;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    PorterDuffXfermode xfermode;
    private static final float PADDING = Utils.dp2px(50);
    private static final float EDGE_WIDTH = Utils.dp2px(3);
    RectF savedArea = new RectF();

    {
        bitmap = BitmapUtils.getBitmap(getContext(), (int) RADIUS);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        savedArea.set(PADDING, PADDING, PADDING + RADIUS, PADDING + RADIUS);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = (int) ((PADDING + RADIUS) * 2);
        int height = (int) ((PADDING + RADIUS) * 2);

        width = resolveSize(width, widthMeasureSpec);
        height = resolveSize(height, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.parseColor("#009688"));
        canvas.drawOval(PADDING, PADDING, PADDING + RADIUS, PADDING + RADIUS, paint);
        // 离屏缓冲
        int saved = canvas.saveLayer(savedArea, paint);
        canvas.drawOval(PADDING + EDGE_WIDTH, PADDING + EDGE_WIDTH, PADDING + RADIUS - EDGE_WIDTH, PADDING + RADIUS - EDGE_WIDTH, paint);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(bitmap, PADDING, PADDING, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saved);
    }
}
