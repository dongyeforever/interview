package vip.lovek.interview.animate.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import vip.lovek.interview.utils.Utils;

/**
 * author： yuzhirui@douban.com
 * date: 2022-02-08 20:56
 */
public class PointView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Point point = new Point(0, 0);

    {
        paint.setStrokeWidth(Utils.dp2px(15));
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
        invalidate();
    }

    public PointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPoint(point.x, point.y, paint);
    }
}
