package com.example.user.myapplication.stepcounter;

/** Activity class - for Managing and Update UI elements (views). */

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myapplication.R;

import java.io.IOException;
import java.io.InputStream;

public class StepCounterActivity extends AppCompatActivity {

    TextView stepCountTxV;
    TextView stepDetectTxV;

    Button startServiceBtn;
    Button stopServiceBtn;

    String countedStep;
    String DetectedStep;
    static final String State_Count = "Counter";
    static final String State_Detect = "Detector";

    boolean isServiceStopped;

    Animation animationCustomView;

    RelativeLayout parentLayout;
    int pLayoutHeight;
    int pLayoutWidth;
    RelativeLayout relativeLayout;
    int rLayoutT;
    int rLayoutB;
    int rLayoutL;
    int rLayoutR;
    int rLayoutHeight;
    int rLayoutWidth;
    LinearLayout childLayout;

    ImageView imageView2;


    private Intent intent;
    private static final String TAG = "SensorEvent";


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_counter_activity);

        // ___ instantiate intent ___ \\
        //  Instantiate the intent declared globally - which will be passed to startService and stopService.
        intent = new Intent(this, StepCounterService.class);

        init(); // Call view initialisation method.
    }

    // Initialise views.
    public void init() {

        isServiceStopped = true; // variable for managing service state - required to invoke "stopService" only once to avoid Exception.

        // Layout Background Image Management.
        try {
            // Parent Relative Layout Background.
            // Get input stream.
            InputStream inputStream = getAssets().open("h.jpg");
            // Load image as drawable.
            Drawable parentDrawable = Drawable.createFromStream(inputStream, null);
            // Set opacity (transparency) of image.
            parentDrawable.setAlpha(32);
            // Retrieve parent relativelayout.
            RelativeLayout parentLayout = (RelativeLayout)findViewById(R.id.parentLayout);
            // Set drawable image to imageview.
            parentLayout.setBackground(parentDrawable);

            // Child Linear Layout Background.
            InputStream inputStream2 = getAssets().open("c.jpg");
            Drawable childDrawable = Drawable.createFromStream(inputStream2, null);
            childDrawable.setAlpha(128);
            LinearLayout childLayout = (LinearLayout)findViewById(R.id.childLayout);
            childLayout.setBackground(childDrawable);

        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

        // ________________ Service Management (Start & Stop Service). ________________ //
        // ___ start Service & register broadcast receiver ___ \\
        startServiceBtn = (Button)findViewById(R.id.startServiceBtn);
        startServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start Service.
                startService(new Intent(getBaseContext(), StepCounterService.class));
                // register our BroadcastReceiver by passing in an IntentFilter. * identifying the message that is broadcasted by using static string "BROADCAST_ACTION".
                registerReceiver(broadcastReceiver, new IntentFilter(StepCounterService.BROADCAST_ACTION));
                Toast.makeText(StepCounterActivity.this, "Starting Service", Toast.LENGTH_SHORT).show();
                isServiceStopped = false;
            }
        });

        // ___ unregister receiver & stop service ___ \\
        stopServiceBtn = (Button)findViewById(R.id.stopServiceBtn);
        stopServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isServiceStopped) {
                    Toast.makeText(StepCounterActivity.this, "Stopping Service", Toast.LENGTH_SHORT).show();
                    // call unregisterReceiver - to stop listening for broadcasts.
                    unregisterReceiver(broadcastReceiver);
                    // stop Service.
                    stopService(new Intent(getBaseContext(), StepCounterService.class));
                    isServiceStopped = true;
                }
            }
        });
        // ___________________________________________________________________________ \\

        // Textviews
        stepCountTxV = (TextView)findViewById(R.id.stepCountTxV);
        stepDetectTxV = (TextView)findViewById(R.id.stepDetectTxV);

        // ImageView
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView2.setImageResource(R.drawable.snorlax_icon);
    }


    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();

        layoutsDimen();

        // Currently, when "onResume" is called, the animation continues from where it left off, but this commented code, retarts animation from the beginning.
        animationCustomView = (Animation)findViewById(R.id.custom_view);
        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                animationStart();
            }
        },1000);

    }

    // Method for monitoring layout dimensions.
    public void layoutsDimen() {
        parentLayout = (RelativeLayout) findViewById(R.id.parentLayout);
        Log.d("parent layout Height", String.valueOf(parentLayout.getHeight()));
        Log.d("parent layout Width", String.valueOf(parentLayout.getWidth()));
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        Log.d("Rlayout Bottom-most px", String.valueOf(relativeLayout.getBottom()));
        Log.d("Rlayout Right-most px", String.valueOf(relativeLayout.getRight()));

        childLayout = (LinearLayout) findViewById(R.id.childLayout);
        Rect rect = new Rect();
        childLayout.getLocalVisibleRect(rect);
        Log.d("Child layout Height", String.valueOf(rect.height()));
        Log.d("Child layout Width", String.valueOf(rect.width()));
        Log.d("ChildLayout B-most px", String.valueOf(rect.bottom));
        Log.d("ChildLayout R-most px", String.valueOf(rect.right));
    }

    // --------------------------------------------------------------------------- \\
    // ___ create Broadcast Receiver ___ \\
    // create a BroadcastReceiver - to receive the message that is going to be broadcast from the Service
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // call updateUI passing in our intent which is holding the data to display.
            updateViews(intent);
        }
    };
    // ___________________________________________________________________________ \\



    private void txvAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(100,-100,100,-100);
        translateAnimation.setDuration(200);
        translateAnimation.setInterpolator(new LinearOutSlowInInterpolator());
        stepCountTxV.startAnimation(translateAnimation);
        ScaleAnimation sclaeAnimation = new ScaleAnimation(0,1,0,1);
        sclaeAnimation.setDuration(200);
        sclaeAnimation.setInterpolator(new AnticipateOvershootInterpolator());
        stepDetectTxV.startAnimation(sclaeAnimation);

        TranslateAnimation translateAnimation3 = new TranslateAnimation(-100,0,-100,0);
        translateAnimation3.setDuration(200);
        translateAnimation3.setInterpolator(new CycleInterpolator(2));
        imageView2.startAnimation(translateAnimation3);
        ScaleAnimation sclaeAnimation3 = new ScaleAnimation(0,1,1,0);
        sclaeAnimation3.setDuration(200);
        sclaeAnimation3.setInterpolator(new BounceInterpolator());
        imageView2.startAnimation(sclaeAnimation3);
    }


    // --------------------------------------------------------------------------- \\
    // ___ retrieve data from intent & set data to textviews __ \\
    private void updateViews(Intent intent) {
        // retrieve data out of the intent.
        countedStep = intent.getStringExtra("Counted_Step");
        DetectedStep = intent.getStringExtra("Detected_Step");
        Log.d(TAG, String.valueOf(countedStep));
        Log.d(TAG, String.valueOf(DetectedStep));

        stepCountTxV.setText('"' + String.valueOf(countedStep) + '"' + " Steps Counted");
        stepDetectTxV.setText("Steps Detected = " + String.valueOf(DetectedStep) + '"');

        txvAnimation();
    }
    // ___________________________________________________________________________ \\


    public void animationStart() {
        Log.v("animation", "start");
        animationCustomView.init();
    }

}
