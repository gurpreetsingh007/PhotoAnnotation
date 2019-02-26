package com.example.photoannotation;

import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
public class TouchCanvas implements View.OnTouchListener {
    Path path;
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        DrawingView drawingView = (DrawingView) view;
        path = new Path();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                path.reset();
                path = new Path();
                path.moveTo(x, y);
                drawingView.addPath(path); //add path to array of paths to undo purposes
                drawingView.path = path; //
                break;
            case MotionEvent.ACTION_MOVE:
                path = drawingView.getLastPath();  //getting the last path
                if (path != null) {
                    path.lineTo(x, y);
                }
                break;
        }
        drawingView.invalidate();
        return true;
    }
}
