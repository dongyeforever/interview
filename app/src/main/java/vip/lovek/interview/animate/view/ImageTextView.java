package vip.lovek.interview.animate.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import vip.lovek.interview.utils.BitmapUtils;
import vip.lovek.interview.utils.Utils;

/**
 * author： yuzhirui@douban.com
 * date: 2022-02-08 11:16
 */
public class ImageTextView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public static final float WIDTH = Utils.dp2px(100);
    StaticLayout staticLayout;
    TextPaint textPaint = new TextPaint();
    Bitmap bitmap;
    float[] measureWidth = new float[0];

    {
        paint.setTextSize(Utils.dp2px(12));
        textPaint.setTextSize(Utils.dp2px(12));
//        staticLayout = new StaticLayout("将 Canvas 的变换理理解为 Canvas 的坐标系不变，每次变换是只对内部的绘制内容进⾏行行变换，同 时把 Canvas 的变换顺序看作是倒序的" +
//                "(即写在下⾯面的变换先执⾏行行)，可以更更加⽅方便便进⾏行行多重变 换的参数计算。", textPaint, (int) Utils.dp2px(200),
//                Layout.Alignment.ALIGN_NORMAL, 1, 0,
//                false);
        bitmap = BitmapUtils.getBitmap(getContext(), (int) WIDTH);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 多行文字
//        canvas.translate(Utils.dp2px(10), getHeight() / 2 + Utils.dp2px(100));
//        staticLayout.draw(canvas);

        // 图文混合
        canvas.drawBitmap(bitmap, getWidth() - WIDTH, 100, paint);
        String text = "将 Canvas 的变换理理解为 Canvas 的坐标系不变，每次变换是只对内部的绘制内容进⾏行行变换，同 时把 Canvas 的变换顺序看作是倒序的(即写在下⾯面的变换先执⾏)" +
                "，可以更加⽅便进行多重变换的参数计算。";
        int index = paint.breakText(text, true, getWidth(), measureWidth);
        canvas.drawText(text, 0, index, 0, 50, paint);
        int oldIndex = index;
        index = paint.breakText(text, oldIndex, text.length(), true, getWidth(), measureWidth);
        canvas.drawText(text, oldIndex, oldIndex + index, 0, 50 + paint.getFontSpacing(), paint);

        oldIndex += index;
        index = paint.breakText(text, oldIndex, text.length(), true, getWidth() - WIDTH, measureWidth);
        canvas.drawText(text, oldIndex, oldIndex + index, 0, 50 + paint.getFontSpacing() * 2, paint);

        oldIndex += index;
        index = paint.breakText(text, oldIndex, text.length(), true, getWidth() - WIDTH, measureWidth);
        canvas.drawText(text, oldIndex, oldIndex + index, 0, 50 + paint.getFontSpacing() * 3, paint);
    }

}
