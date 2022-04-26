package vip.lovek.interview.animate.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import vip.lovek.interview.utils.Utils;

/**
 * author： yuzhirui@douban.com
 * date: 2022-02-07 23:06
 */
public class SportsView extends View {
    public static final float RADIUS = Utils.dp2px(60);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Rect rect = new Rect();
    Paint.FontMetrics fontMetrics;

    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setTextAlign(Paint.Align.CENTER); // 文字横向居中，纵向需要调整
        paint.setTextSize(Utils.dp2px(23));
        fontMetrics = paint.getFontMetrics();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 圆环
        paint.setColor(Color.parseColor("#dddddd"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(10));
        canvas.drawCircle(Utils.dp2px(200) + RADIUS, Utils.dp2px(50) + RADIUS, RADIUS, paint);
        // 进度条
        paint.setColor(Color.parseColor("#009688"));
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(Utils.dp2px(200), Utils.dp2px(50),
                Utils.dp2px(200) + RADIUS * 2, Utils.dp2px(50) + RADIUS * 2,
                -90, 36 * 8, false, paint);
        // 写字
        paint.setStyle(Paint.Style.FILL);
        // 调整字 y 轴上下高度

        /* 方法一
        paint.getTextBounds("abab", 0, "abab".length(), rect);// 获取文字边界，动态文字可能会上下跳动
        int yOffset = (rect.top + rect.bottom) / 2; // baseline上为负，以下为正
        */
        // 方法二
        float yOffset = (fontMetrics.ascent + fontMetrics.descent) / 2;
        canvas.drawText("6400步", Utils.dp2px(200) + RADIUS, Utils.dp2px(50) + RADIUS - yOffset, paint);

    }
}
