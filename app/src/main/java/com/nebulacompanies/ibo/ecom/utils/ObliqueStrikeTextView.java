package com.nebulacompanies.ibo.ecom.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.nebulacompanies.ibo.R;

public class ObliqueStrikeTextView  extends androidx.appcompat.widget.AppCompatTextView{
    private int dividerColor;
    private Paint paint;

    public ObliqueStrikeTextView(Context context)
    {
        super(context);
        init(context);
    }

    public ObliqueStrikeTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public ObliqueStrikeTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context)
    {
        Resources resources = context.getResources();
        //replace with your color
        dividerColor = resources.getColor(R.color.black);

        paint = new Paint();
        paint.setColor(dividerColor);
        //replace with your desired width
        paint.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawLine(0, getHeight(), getWidth(), 0, paint);
    }
}
