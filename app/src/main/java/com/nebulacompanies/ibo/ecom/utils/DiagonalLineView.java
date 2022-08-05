package com.nebulacompanies.ibo.ecom.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.nebulacompanies.ibo.R;

public class DiagonalLineView extends View {

    private int dividerColor;
    private Paint paint;

    public DiagonalLineView(Context context)
    {
        super(context);
        init(context);
    }

    public DiagonalLineView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public DiagonalLineView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context)
    {
        Resources resources = context.getResources();
        dividerColor = resources.getColor(R.color.grey);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(dividerColor);
        paint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawLine(0, getHeight(), getWidth(), 0, paint);
    }

}