package com.johnmelodyme.facialexpressionml.Helper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * @Author: John Melody Melissa
 * @Project: Firebase Face Detection
 * @Inpired : By GF TAN SIN DEE <3
 */

public class RectOverlay extends GraphicOverlay.Graphic {
    private int Rect_colour = Color.GREEN;
    private float Stroke_width = 0x1.0p2f;
    private Paint Rect_paint;
    private GraphicOverlay graphicOverlay;
    private Rect mRect;

    public RectOverlay(GraphicOverlay graphicOverlay, Rect rect) {
        super(graphicOverlay);

        Rect_paint = new Paint();
        Rect_paint.setColor(Rect_colour);
        Rect_paint.setStyle(Paint.Style.STROKE);
        Rect_paint.setStrokeWidth(Stroke_width);

        this.graphicOverlay = graphicOverlay;
        this.mRect = rect;

        postInvalidate();
    }

    @Override
    public void draw(Canvas canvas) {
        RectF rectF = new RectF(mRect);
        rectF.left = translateX(rectF.left);
        rectF.right = translateX(rectF.right);
        rectF.top = translateY(rectF.top);
        rectF.bottom = translateY(rectF.bottom);

        canvas.drawRect(rectF, Rect_paint);
    }
}
