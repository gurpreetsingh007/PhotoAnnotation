package com.example.photoannotation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

//import java.nio.file.Path;
import java.util.ArrayList;

public class PaintView extends View {

//    public static int BRUSH_SIZE = 10;
//    public static final int DEFAULT_COLOR = Color.RED;
//    public static final int DEFAULT_BG_COLOR = Color.WHITE;
//    public static final float TOUCH_TOLERANCE = 4;
//    private float mX,mY;  //move cordinates
//    private Path mPath;
//    private Paint mPaint;
//    private ArrayList<Touchrespond> paths = new ArrayList<>();
//    private int currentColor;
//    private int backgroundColor = DEFAULT_BG_COLOR;
//    private int strokeWidth;
//    private boolean embross,blur;
//    private MaskFilter mEmboss,mBlur;
//    private Bitmap mBitmap;
//    private Canvas mCanvas;
//    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    public int width;
    public int height;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    Context context;
    private Path mPath;
    private Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;

    boolean isEraser;
    boolean drawmode;
    Paint paint;
    Path path1;

    public PaintView(Context context) {
        super(context);
    }

    public PaintView(Context c,AttributeSet attrs) {
        super(c, attrs);
        context = c;
//        paint = new Paint();
//        path1 = new Path();
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5f);
    }

    public void drawmode(boolean drawmode){
        this.drawmode = drawmode;
    }

    //draw on a defined Canvas or Bitmap
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // drawing the path created during touch event, draw with mPath with mPaint on canvas
        if(drawmode){
            canvas.drawPath(mPath,mPaint);
        }
//        canvas.drawPath(mPath,mPaint);
    }

    //On ACTION_DOWN start touch according to x,y cordinates,values
    private void startTouch(float x, float y){
        mPath.moveTo(x,y);
        mX = x;
        mY = y;
    }
    //On ACTION_MOVE move touch according to x,y cordinates,values
    private void moveTouch(float x, float y){
        float dx = Math.abs(x-mX);
        float dy = Math.abs(y-mY);
        if(dx >=TOLERANCE || dy >=TOLERANCE){
            mPath.quadTo(mX,mY,(x+mX)/2,(y+mY)/2);
            mX = x;
            mY = y;
        }
    }
    // clear the canvas
    public void clean(){
        mPath.reset();
        isEraser = false;
        drawmode = false;
        invalidate();
    }

    //On ACTION_UP stop touch
    private void upTouch(){
        mPath.lineTo(mX,mY);
    }

    //On touch events on the canvas
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float Xpos = event.getX();    // getting X cordinate of the touch
        float Ypos = event.getY();    //getting Y cordinate of the touch
        //cheking the events that occur
        if(drawmode){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                // starting point of drawing
                startTouch(Xpos,Ypos);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
//                if(isEraser){
////                    clean();
//                    mPath.lineTo(Xpos,Ypos);
//                    mCanvas.drawPath(mPath,mPaint);
//                    mPath.reset();
//                    mPath.moveTo(Xpos,Ypos);
//                }else{
                    // ending point of drawing
                    moveTouch(Xpos,Ypos);
                    invalidate();
//                }
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
            default:
                return false;
            }
        }
//        invalidate();  // validate the view and draw it
        return true;
    }
    public void setEraser(boolean isEraser){
        this.isEraser = isEraser;
        if(isEraser) {
//            mPaint.setColor(Color.RED);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//            mPaint.setColor(Color.WHITE);
        }
        else {
            mPaint.setXfermode(null);
        }
    }

//    public void clearscreen(){
//        setDrawingCacheEnabled(false);
//        onSizeChanged(width,height,width,height);
//        invalidate();
//        setDrawingCacheEnabled(true);
//
//    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        width = w;
//        height = h;
//        mBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
//        mCanvas = new Canvas(mBitmap);
//    }
    //    public PaintView(Context context) {
//        this(context, null);
//    }

//    public PaintView(Context context,AttributeSet attrs) {
//        super(context, attrs);
//        mPaint = new Paint();
//
//        mPaint.setAntiAlias(true);
//        mPaint.setDither(true);
//        mPaint.setXfermode(null);
//        mPaint.setAlpha(0xff);
//
//        mPaint.setColor(DEFAULT_COLOR);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeJoin(Paint.Join.ROUND);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
//
//        mEmboss = new EmbossMaskFilter(new float[]{1,1,1},0.4f,6,3.5f);
//        mBlur = new BlurMaskFilter(5,BlurMaskFilter.Blur.NORMAL);
//
//    }

//    public void initializescreen(DisplayMetrics metrics){
//        int height = metrics.heightPixels;
//        int width = metrics.widthPixels;
//
//        mBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
//        mCanvas = new Canvas(mBitmap);
//
//        currentColor = DEFAULT_COLOR;
//        strokeWidth= BRUSH_SIZE;
//    }

//    public void normal(){
//        embross= false;
//        blur = false;
//    }
//    public void emboss(){
//        embross = true;
//        blur = false;
//    }
//    public void blur(){
//        embross = false;
//        blur = true;
//    }
//    public void clear(){
//        backgroundColor = DEFAULT_BG_COLOR;
//        paths.clear();
//        normal();
//        invalidate();
//    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    protected void onDraw(Canvas canvas) {
//        canvas.save();
//        mCanvas.drawColor(backgroundColor);
//
//        for(Touchrespond tr: paths){
//            mPaint.setColor(tr.color);
//            mPaint.setStrokeWidth(tr.strokeWidth);
//            mPaint.setMaskFilter(null);
//
//            if(tr.emboss){
//                mPaint.setMaskFilter(mEmboss);
//            }
//            else if (tr.blur){
//                mPaint.setMaskFilter(mBlur);
//            }
//            mCanvas.drawPath((android.graphics.Path) tr.path,mPaint);
//        }
//        canvas.drawBitmap(mBitmap,0,0,mBitmapPaint);
//        canvas.restore();
//
//
//    }
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    public boolean onTouchEvent(MotionEvent event){
//        float x = event.getX();
//        float y = event.getY();
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN :
//                touchStart(x,y);
//                invalidate();
//                break;
//            case MotionEvent.ACTION_MOVE :
//                touchMove(x,y);
//                invalidate();
//                break;
//            case MotionEvent.ACTION_UP :
//                touchUP(x,y);
//                invalidate();
//                break;
//        }
//        return true;
//    }

//    private void touchUP(float x, float y) {
//        mPath.lineTo(mX,mY);
//
//    }
//    private void touchMove(float x, float y){
//        float dx = Math.abs(x-mX);
//        float dy = Math.abs(y-mY);
//
//        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE){
//            mPath.quadTo(mX,mY,(x+mX)/2,(y+mY)/2);
//            mX= x;
//            mY=y;
//        }
//
//    }
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void touchStart(float x, float y){
//        mPath = new Path();
//        Touchrespond tr = new Touchrespond(currentColor,embross,blur,strokeWidth, (java.nio.file.Path) mPath);
//        paths.add(tr);
//
//        mPath.reset();
//        mPath.moveTo(x,y);
//        mX = x;
//        mY = y;
//    }

}
//          XXXXMMMMMMLLLLLL  FILE

//    <?xml version="1.0" encoding="utf-8"?>
//<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
//        xmlns:app="http://schemas.android.com/apk/res-auto"
//        xmlns:tools="http://schemas.android.com/tools"
//        android:layout_width="match_parent"
//        android:layout_height="match_parent"
//        tools:context=".MainActivity">
//
//<Button
//        android:id="@+id/picbutton"
//                android:layout_width="wrap_content"
//                android:layout_height="wrap_content"
//                android:layout_marginStart="8dp"
//                android:layout_marginTop="8dp"
//                android:layout_marginEnd="8dp"
//                android:layout_marginBottom="8dp"
//                android:lineSpacingExtra="8sp"
//                android:onClick="OpenCamera"
//                android:text="@string/takepic1"
//                android:textAllCaps="false"
//                android:textSize="18sp"
//                app:layout_constraintBottom_toBottomOf="parent"
//                app:layout_constraintEnd_toEndOf="parent"
//                app:layout_constraintHorizontal_bias="0.026"
//                app:layout_constraintStart_toStartOf="parent"
//                app:layout_constraintTop_toTopOf="parent"
//                app:layout_constraintVertical_bias="0.91" />
//
//<Button
//        android:id="@+id/drawbutton"
//                android:layout_width="wrap_content"
//                android:layout_height="wrap_content"
//                android:layout_marginStart="8dp"
//                android:layout_marginTop="8dp"
//                android:layout_marginEnd="8dp"
//                android:layout_marginBottom="8dp"
//                android:lineSpacingExtra="8sp"
//                android:text="@string/draw"
//                android:textAllCaps="false"
//                android:textSize="18sp"
//                app:layout_constraintBottom_toBottomOf="parent"
//                app:layout_constraintEnd_toEndOf="parent"
//                app:layout_constraintHorizontal_bias="0.358"
//                app:layout_constraintStart_toStartOf="parent"
//                app:layout_constraintTop_toTopOf="parent"
//                app:layout_constraintVertical_bias="0.91" />
//
//<Button
//        android:id="@+id/colorbutton"
//                android:layout_width="wrap_content"
//                android:layout_height="wrap_content"
//                android:layout_marginStart="8dp"
//                android:layout_marginTop="8dp"
//                android:layout_marginEnd="8dp"
//                android:layout_marginBottom="8dp"
//                android:lineSpacingExtra="8sp"
//                android:text="@string/color"
//                android:textAllCaps="false"
//                android:textSize="18sp"
//                app:layout_constraintBottom_toBottomOf="parent"
//                app:layout_constraintEnd_toEndOf="parent"
//                app:layout_constraintHorizontal_bias="0.68"
//                app:layout_constraintStart_toStartOf="parent"
//                app:layout_constraintTop_toTopOf="parent"
//                app:layout_constraintVertical_bias="0.91" />
//
//<Button
//        android:id="@+id/clearbutton"
//                android:layout_width="wrap_content"
//                android:layout_height="wrap_content"
//                android:layout_marginStart="8dp"
//                android:layout_marginTop="8dp"
//                android:layout_marginEnd="8dp"
//                android:layout_marginBottom="8dp"
//                android:lineSpacingExtra="8sp"
//                android:text="@string/clear"
//                android:textAllCaps="false"
//                android:textSize="18sp"
//                app:layout_constraintBottom_toBottomOf="parent"
//                app:layout_constraintEnd_toEndOf="parent"
//                app:layout_constraintHorizontal_bias="1.0"
//                app:layout_constraintStart_toStartOf="parent"
//                app:layout_constraintTop_toTopOf="parent"
//                app:layout_constraintVertical_bias="0.91" />
//
//<ImageView
//        android:id="@+id/imageview"
//                android:layout_width="412dp"
//                android:layout_height="540dp"
//                android:layout_marginBottom="75dp"
//                android:contentDescription="@string/image"
//                app:layout_constraintBottom_toTopOf="@+id/picbutton"
//                app:layout_constraintEnd_toEndOf="parent"
//                app:layout_constraintStart_toStartOf="parent"
//                app:layout_constraintTop_toTopOf="parent"
//                app:srcCompat="@android:drawable/progress_horizontal" />
//
//
//
//</android.support.constraint.ConstraintLayout>