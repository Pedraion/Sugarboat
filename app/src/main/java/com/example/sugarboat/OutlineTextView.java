package com.example.sugarboat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class OutlineTextView extends AppCompatTextView {

    private Paint strokePaint;

    public OutlineTextView(Context context) {
        super(context);
        init();
    }

    public OutlineTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OutlineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        strokePaint = new Paint();
        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(Color.BLACK); // Outline color
        strokePaint.setStrokeWidth(5); // Outline width
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Draw the outline
        setTextColor(Color.BLACK);
        strokePaint.setTextSize(getTextSize());
        strokePaint.setTypeface(getTypeface());
        strokePaint.setFlags(getPaintFlags());
        canvas.drawText(getText().toString(), 0, getBaseline(), strokePaint);

        // Draw the regular text
        setTextColor(Color.WHITE);
        super.onDraw(canvas);
    }
}
