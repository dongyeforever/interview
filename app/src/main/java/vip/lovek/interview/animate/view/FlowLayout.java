package vip.lovek.interview.animate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * author： yuzhirui@douban.com
 * date: 2022-03-14 23:49
 */
public class FlowLayout extends ViewGroup {
    private List<List<View>> mLineViews = new ArrayList();
    private List<Integer> mLineHeight = new ArrayList();

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //由于onMeasure会执行多次,避免重复的计算控件个数和高度,这里需要进行清空操作
        mLineViews.clear();
        mLineHeight.clear();
        Log.e("TAG", "onMeasure...");
        // 宽高
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int viewGroupHeight = 0;
        int viewGroupWidth = 0;

        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            viewGroupHeight = heightSize;
            viewGroupWidth = widthSize;
        } else {
            int currentLineWidth = 0;
            int currentLineHeight = 0;

            List<View> lineView = new ArrayList();
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                measureChild(child, widthSize, heightMeasureSpec);
                MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
                int childWidth = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
                int childHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;
                if (childWidth + currentLineWidth > widthSize) {
                    // 换行
                    viewGroupWidth = Math.max(currentLineWidth, widthSize);
                    viewGroupHeight += currentLineHeight;
                    mLineHeight.add(currentLineHeight);
                    mLineViews.add(lineView);

                    //new新的一行
                    lineView = new ArrayList();
                    //添加行对象里的子View
                    lineView.add(child);
                    currentLineWidth = childWidth;
                } else {
                    currentLineWidth += childWidth;
                    currentLineHeight = Math.max(currentLineHeight, childHeight);
                    lineView.add(child);
                }

                if (i == childCount - 1) {
                    //最后一个子View的时候
                    //添加行对象
                    mLineViews.add(lineView);
                    viewGroupWidth = Math.max(childWidth, viewGroupWidth);
                    viewGroupHeight += childHeight;
                    //添加行高
                    mLineHeight.add(currentLineHeight);
                }
            }
        }
        setMeasuredDimension(viewGroupWidth, viewGroupHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("TAG", "onLayout...");
        int left = getPaddingLeft();
        int top = getPaddingTop();

        int lines = mLineViews.size();
        for (int i = 0; i < lines; i++) {
            int lineHeight = mLineHeight.get(i);
            List<View> viewList = mLineViews.get(i);

            int views = viewList.size();
            for (int j = 0; j < views; j++) {
                View view = viewList.get(j);
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
                int vl = left + marginLayoutParams.leftMargin;
                int vt = top + marginLayoutParams.topMargin;
                int vr = vl + view.getMeasuredWidth();
                int vb = vt + view.getMeasuredHeight();
                Log.e("TAG", vl + "," + vt + "," + vr + "," + vb);
                view.layout(vl, vt, vr, vb);
                left += view.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            }

            left = getPaddingLeft();
            top += lineHeight;
        }
    }
}
