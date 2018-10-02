package com.example.widiari.cameraopencv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    private static String TAG = "MainActivity";
    JavaCameraView javaCameraView;

    Mat mRgba;
    BaseLoaderCallback mLoaderCallBack = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case BaseLoaderCallback.SUCCESS:{
                    javaCameraView.enableView();
                    break;
                }
                default:{
                    super.onManagerConnected(status);
                    break;
                }
            }


        }
    };

    static {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        javaCameraView = (JavaCameraView)findViewById(R.id.java_camera_view);
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCvCameraViewListener(this);

//        cameraBridgeViewBase=findViewById(R.id.javacam);
//        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
//        cameraBridgeViewBase.setCvCameraViewListener(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (javaCameraView!=null)
            javaCameraView.disableView();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (javaCameraView!=null)
            javaCameraView.disableView();
    }
    @Override
    protected void onResume(){
        super.onResume();
        if (OpenCVLoader.initDebug()){
            Log.i(TAG, "Opencv loaded succesfully");
            mLoaderCallBack.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }else {
            Log.i(TAG, "OpenCV not Loaded");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_11, this, mLoaderCallBack);
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();
        return mRgba;
    }
}
