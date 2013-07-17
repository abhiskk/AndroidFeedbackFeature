package com.example.feedbacktestlib;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.*;
import android.os.Bundle;
import android.view.*;

import java.io.*;

public class FingerPaint extends GraphicsActivity {

    public MyView myView;
    public Bitmap testBitmap;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (!FeedbackActivity.state.contains(FeedbackActivity.StateParameters.tablet))
        super.setTheme(R.style.AppBaseTheme);

//        if (FeedbackActivity.state.contains(FeedbackActivity.StateParameters.tablet)) {
//            Display d = getWindowManager().getDefaultDisplay();
//
//            if (d.getHeight() > 800) {
//                WindowManager.LayoutParams params = getWindow().getAttributes();
//                params.height = 800;
//                this.getWindow().setAttributes(params);
//            }
//        }

        testBitmap = BitmapFactory.decodeFile(FeedbackActivity.baseDir + File.separator + FeedbackActivity.screenShotFileName);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (testBitmap.getHeight() > testBitmap.getWidth())
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else if (testBitmap.getHeight() < testBitmap.getWidth())
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        myView = new MyView(this);

        setContentView(myView);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0xFF000000);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(20);

    }

    @Override
    protected void onPause() {
        super.onPause();
        (new File(FeedbackActivity.baseDir + File.separator + FeedbackActivity.screenShotFileName)).delete();
        OutputStream fout;
        File imageFile = new File(FeedbackActivity.baseDir + File.separator + FeedbackActivity.screenShotFileName);

        try {
            fout = new FileOutputStream(imageFile);
            myView.mBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fout);
            fout.flush();
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Paint mPaint;

    public class MyView extends View {

        public Bitmap mBitmap;
        private Canvas mCanvas;
        private Path mPath;
        private Paint mBitmapPaint;

        public MyView(Context c) {
            super(c);

            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            mBitmap = testBitmap.copy(Bitmap.Config.ARGB_8888, true);
            mCanvas = new Canvas(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(0xFFAAAAAA);

            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

            canvas.drawPath(mPath, mPaint);
        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(float x, float y) {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;
            }
        }

        private void touch_up() {
            mPath.lineTo(mX, mY);
            mCanvas.drawPath(mPath, mPaint);
            mPath.reset();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }
    }

    private static final int ERASE_MENU_ID = Menu.FIRST;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, ERASE_MENU_ID, 0, "Clear changes");
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xFF);

        switch (item.getItemId()) {
            case ERASE_MENU_ID:
                myView.mBitmap = FeedbackActivity.defaultScreenshot.copy(Bitmap.Config.ARGB_8888, true);
                myView.mCanvas = new Canvas(myView.mBitmap);
                myView.invalidate();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}


