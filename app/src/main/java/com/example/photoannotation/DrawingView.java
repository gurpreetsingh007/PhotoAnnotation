package com.example.photoannotation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.Stack;


public class DrawingView extends android.support.v7.widget.AppCompatImageView{

    private ArrayList<Path> paths = new ArrayList<>();
    private Stack<Path> recentpaths = new Stack<>();
    private ArrayList<Integer> colors = new ArrayList<>();
    private int currentColor = Color.TRANSPARENT;
    private ArrayList<Integer> widths = new ArrayList<>();
    private int currentWidth = 6;
    Paint paint = new Paint();
    Path path;
    Canvas passcanvas;

    boolean erasing;
    public DrawingView(Context context) {
        super(context);
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        setLayerType(View.LAYER_TYPE_HARDWARE,null);

    }

    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void addPath(Path path) {
        paths.add(path);
        recentpaths.push(path);
        colors.add(currentColor);
        widths.add(currentWidth);
    }

    public Path getLastPath() {
        if (paths.size() > 0) {
            return paths.get(paths.size() - 1);
        }
        return null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int i = 0;
//        if(erasing){
//            paint.setColor(Color.BLACK);
//            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//            canvas.drawPath(path,paint);
//            Log.d("Anyhing", String.valueOf(paint.getColor()));
//            Log.d("path", path.toString());
//        }
//        else{
            for (Path path : paths) {
                if (erasing){
                    paint.setColor(Color.TRANSPARENT);
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                    paint.setDither(true);
                    paint.setAntiAlias(true);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(widths.get(i));
                }
                else {
                    paint.setColor(colors.get(i));
                    paint.setDither(true);
                    paint.setAntiAlias(true);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(widths.get(i));
                }
//                paint.setColor(colors.get(i));
//                paint.setDither(true);
//                paint.setAntiAlias(true);
//                paint.setStyle(Paint.Style.STROKE);
//                paint.setStrokeWidth(widths.get(i));
                canvas.drawPath(path, paint);
                i++;
            }
//        }
    }

    public void setCurrentColor(int color) {
        paint.setXfermode(null);
        currentColor = color;
        erasing = false;
    }

    public void setCurrentWidth(int width) {
        currentWidth = (width + 1) * 2;
    }

    public void erase() {
        paths.clear();
        colors.clear();
        widths.clear();
        invalidate();
        erasing = false;
    }
    public Path undo() {
        if (paths.size() > 0) {
            paths.remove(paths.size()-1);
            invalidate();
            erasing = false;
        }
        return null;
    }
    public void erasermode(){
        erasing = true;
    }


//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        float x = event.getX();
//        float y = event.getY();
////        DrawingView drawingView = (DrawingView) view;
//        path = new Path();
//        switch (event.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN:
//                path.reset();
//                path = new Path();
//                path.moveTo(x, y);
//                addPath(path);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                path = getLastPath();  //getting the last path
//                if (path != null) {
//                    if (erasing){
//                        paint.setColor(Color.TRANSPARENT);
//                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//                        paint.setDither(true);
//                        paint.setAntiAlias(true);
//                        paint.setStyle(Paint.Style.STROKE);
//
//                    }
//                    path.lineTo(x, y);
//                }
//                break;
//        }
//        invalidate();
//        return true;
//    }
}
