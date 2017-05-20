package com.tenpa_mf.test3;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity implements SurfaceHolder.Callback,Runnable{

   public SurfaceView surfaceView;
   public SurfaceHolder mHolder;
   public float msurfaceX, msurfaceY,scale,scaleX,scaleY;
   public Thread thread;
   public Canvas canvas;
   public Paint paint;
   public Resources res;
    public float touch_x,touch_y;

    final float VIEW_WIDTH = 1080;
    final float VIEW_HEIGHT=1794;

   public Bitmap sht1,sht6,sht7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        res = getResources();
        sht1 = BitmapFactory.decodeResource(res,R.drawable.sht1);
        sht6 = BitmapFactory.decodeResource(res,R.drawable.sht6);
        sht7 = BitmapFactory.decodeResource(res,R.drawable.sht7);
        mHolder = surfaceView.getHolder();
        mHolder.addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        scaleX = surfaceView.getWidth() / VIEW_WIDTH;
        scaleY = surfaceView.getHeight() /  VIEW_HEIGHT;
        scale = scaleX > scaleY ? scaleY : scaleX;

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        msurfaceX = (float)width;
        msurfaceY = (float)height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread = null;
    }

    @Override
    public void run() {
        while(thread!=null) {
            paint = new Paint();
            paint.setTextSize(45.0f);
            paint.setColor(Color.WHITE);
            canvas = mHolder.lockCanvas();

            canvas.drawText("こんにちは", 100.0f, 100.0f, paint);
        canvas.drawBitmap(sht1,touch_x,touch_y,paint);
        canvas.drawBitmap(sht6,400.f,400.f,paint);
        canvas.drawBitmap(sht7,300.f,500.f,paint);

            float w = canvas.getWidth();
            float h = canvas.getHeight();
            mHolder.unlockCanvasAndPost(canvas);
            Log.d("サーフェイス幅は",String.valueOf(msurfaceX)+String.valueOf(msurfaceY));
            Log.d("canvas幅=", String.valueOf(w)+String.valueOf(h));
        }
    }

    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction()==event.ACTION_DOWN){
            //タップした座標に画像の中心が来るように調整
            //画像の中心点を検出
            float sht1_width = sht1.getWidth()/2;
            float sht1_height = sht1.getHeight()/2;

            //touch_x = event.getX()/scale;
            //touch_y = event.getY()/scale;

            touch_x = (event.getX()-sht1_width)/scale;
            touch_y = (event.getY()-sht1_height)/scale;
        }
        return false;
    }
}
