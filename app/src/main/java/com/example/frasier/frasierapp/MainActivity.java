package com.example.frasier.frasierapp;

import android.app.Activity;
//import android.app.PendingIntent;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothSocket;
//import android.content.Context;
//import android.content.Intent;
//import android.content.res.Resources;
//import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
//import android.os.Parcelable;
import android.view.Menu;
//import android.view.MenuInflater;
import android.view.MenuItem;
//import android.view.MotionEvent;
import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.RemoteViews;
//import android.widget.TextView;

import com.example.frasier.frasierapp.bluetooth.ConnectedThread;
//import com.example.frasier.frasierapp.RobotManagerApplication;
//import com.riverlab.robotmanager.bluetooth.BluetoothDevicesListActivity;
//import com.example.frasier.frasierapp.FrasierApplication;
//import com.riverlab.robotmanager.messages.MessageActivity;
//import com.riverlab.robotmanager.messages.MessageListActivity;
//import com.riverlab.robotmanager.robot.ViewRobotListActivity;
//import com.riverlab.robotmanager.voice_recognition.VoiceRecognitionThread;

//import java.util.ArrayList;
//import java.util.Set;




public class MainActivity extends Activity {

    //Thread global variables
    private ConnectedThread mConnectedThread;
    //private VoiceRecognititionThread mVoiceThread;

    //Handler constants and definition
    public static final int CONNECTION_MESSAGE = 0;

    //This Handler handles messages sent from the ConnectedThread for received bluetooth messages
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CONNECTION_MESSAGE:
                    String text = (String) msg.obj;
                    if (text.equals("connected")) {
                        System.out.println("connected");
                    } else if (text.equals("disconnected")) {
                        System.out.println("disconnected");
                    }
            }
        }
    };

        //private FrasierApplication mApplication;

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //mApplication = ((FrasierApplication) this.getApplication());

            //Create and start the primary worker threads
            mConnectedThread = new ConnectedThread(mHandler);
            //mVoiceThread = new VoiceRecognitionThread(mApplication, this);

            mConnectedThread.start();
            //mVoiceThread.start();

//             //Example:
//            Handler connectedHandler = mHandler.getConnectedThreadHandler();
//            Message msg = connectedHandler.obtainMessage(ConnectedThread.CONNECT_MESSAGE, mSelectedDevice.getName());
//            connectedHandler.sendMessage(msg);

            Handler connectedHandler = mConnectedThread.getHandler();
            Message msg = connectedHandler.obtainMessage(ConnectedThread.CONNECT_MESSAGE, "Nexus 10" ); //bluetooth address: '08:d4:2b:19:dd:ce'
            connectedHandler.sendMessage(msg);
        }

    @Override
    protected void onResume(){
        super.onResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Hxandle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

    public void shutdown()
    {
//Make sure all threads are done before shutting down
        //while (mApplication.getConnectedThreadHandler()); // != null && mApplication.getVoiceThreadHandler() != null);
        mConnectedThread.interrupt();
        //mVoiceThread.interrupt();
        mConnectedThread = null;
        //mVoiceThread = null;
        finish();
    }
}

