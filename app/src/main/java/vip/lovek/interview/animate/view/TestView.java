package vip.lovek.interview.animate.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * author： yuzhirui@douban.com
 * date: 2022-02-07 12:03
 */
public class TestView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path path = new Path();
    PathMeasure pathMeasure;

    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        path.reset();
        path.addRect(getWidth() / 2 - 150, getHeight() / 2 - 300, getWidth() / 2 + 150, getHeight() / 2,
                Path.Direction.CCW); // CCW 逆时针
        path.addCircle(getWidth() / 2, getHeight() / 2, 150, Path.Direction.CCW);
        path.addCircle(getWidth() / 2, getHeight() / 2, 400, Path.Direction.CCW);

        pathMeasure = new PathMeasure(path, false);
        pathMeasure.getLength(); // 周长
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawLine(100, 100, 300, 100, paint);
//        canvas.drawCircle(getWidth() / 2, getHeight() / 2, Utils.dp2px(150), paint);

        path.setFillType(Path.FillType.EVEN_ODD); // 镂空效果
        canvas.drawPath(path, paint);
    }
}
