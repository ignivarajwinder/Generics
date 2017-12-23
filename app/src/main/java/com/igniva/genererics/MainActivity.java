package com.igniva.genererics;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static com.igniva.genererics.Utils.callResponse;
import static com.igniva.genererics.Utils.onCallBack;

public class MainActivity extends AppCompatActivity {
    TextView tv_click;

    CustomLinearLayout cll_main;
//    CustomLinearLayoutSecond cll_main_second;
    ArrayList<Integer> arrayListInteger=new ArrayList<>();
    ArrayList<String> arrayListString=new ArrayList<>();
    ArrayList<CallBackPojo<String>> arrayListCallBackPojo=new ArrayList<>();
    ArrayList<PojoType> arrayListCallBackPojoType=new ArrayList<>();
    HashMap<String,String> hashMap=new HashMap<>();
    boolean isInternetWorking;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

setLayoutWithCustomLinearLayout();
setGenericMethod();

    }


    private void setLayoutWithCustomLinearLayoutSecond() {
//        cll_main_second=(CustomLinearLayoutSecond) findViewById(R.id.cll_main_second);
    }

    private void setLayoutWithCustomLinearLayout() {
        cll_main=(CustomLinearLayout) findViewById(R.id.cll_main);
        cll_main.setCustomColor(getResources().getColor(R.color.white));
        cll_main.setContext(MainActivity.this, new onCallBack<CallBackPojo>() {
            @Override
            public void onSuccess(CallBackPojo callBackPojo) {
                Toast.makeText(MainActivity.this, ((String)callBackPojo.getValue()), Toast.LENGTH_SHORT).show();
                setlayout();
            }

            @Override
            public void onFailure(CallBackPojo callBackPojo) {

            }
        });

        setlayout();
    }

    private void setGenericMethod() {
        arrayListString.add("Chunnu");
        arrayListString.add("Munnu");
        arrayListString.add("Punnu");

        hashMap.put("One","One");
        hashMap.put("Two","Two");
        hashMap.put("Three","Three");

        arrayListInteger.add(0);
        arrayListInteger.add(1);
        arrayListInteger.add(2);

        arrayListCallBackPojo.add(new CallBackPojo<String>("Chunnu"));
        arrayListCallBackPojo.add(new CallBackPojo<String>("Munnu"));
        arrayListCallBackPojo.add(new CallBackPojo<String>("Punnu"));

        arrayListCallBackPojoType.add(new PojoType("Chunnu",10,"Chandigarh"));
        arrayListCallBackPojoType.add(new PojoType("Munnu",12,"Mohali"));
        arrayListCallBackPojoType.add(new PojoType("Punnu",14,"Kharar"));

        tv_click=(TextView)findViewById(R.id.tv_click);
        tv_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callResponse(MainActivity.this,arrayListCallBackPojoType, tv_click,new onCallBack<CallBackPojo>() {
                    @Override
                    public void onSuccess(CallBackPojo callBackPojo) {
                        tv_click.setText(((String)callBackPojo.getValue()));
//                        Toast.makeText(MainActivityMultipleTables.this, ((String)callBackPojo.getValue()), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(CallBackPojo callBackPojo) {
                        tv_click.setText(((String)callBackPojo.getValue()));
//                        Toast.makeText(MainActivityMultipleTables.this, ((String)callBackPojo.getValue()), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }


    private void setlayout() {
        if (isInternetConnection(MainActivity.this)) {
            cll_main.setLayout(R.layout.layout_main);
//            TextView textView=cll_main.findViewById(R.id.tv_text);
//            textView.setText("Main layout aa gya");
//            textView.setBackgroundColor(getColor(R.color.colorAccent));

            swipeRefreshLayout = (SwipeRefreshLayout) cll_main.findViewById(R.id.swipe_refresh_layout);
            swipeRefreshLayout.setColorSchemeResources(
                    R.color.colorPrimary, R.color.colorPrimaryDark,
                    R.color.colorAccent);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(false);

                    if (isInternetConnection(MainActivity.this)) {
//                        cll_main.setLayout(R.layout.layout_main);
                        setlayout();
                    }
                    else
                    {
//                        cll_main.removeAllViews();
                        cll_main.setLayout(R.layout.no_internet_connection);
                    }

                }


            });

        }
        else {
//            cll_main.setLayout(R.layout.no_internet_connection);
//            TextView textView=cll_main.findViewById(R.id.tv_text);
//            textView.setText("o 22 internet nai chal rea");
        }
    }


    public boolean isInternetConnection(Context context) {
        boolean HaveConnectedWifi = false;
        boolean HaveConnectedMobile = false;
        try {

            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                if (ni.getType() == ConnectivityManager.TYPE_WIFI)
                    if (ni.isConnectedOrConnecting())
                        HaveConnectedWifi = true;
                if (ni.getType() == ConnectivityManager.TYPE_MOBILE)
                    if (ni.isConnectedOrConnecting())
                        HaveConnectedMobile = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return HaveConnectedWifi || HaveConnectedMobile;
    }

public void callApi()
{
    Toast.makeText(MainActivity.this, "retry chal pya", Toast.LENGTH_SHORT).show();
};

    public void playAudioFile(String path, String fileName){
        //set up MediaPlayer
        MediaPlayer mp = new MediaPlayer();

        try {
            mp.setDataSource(path + File.separator + fileName);
            mp.prepare();
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




//    public void playFile(String fileToPlay) {
//        // see where we find a suitable autiotrack
//        MediaExtractor extractor = new MediaExtractor();
//        try {
//            extractor.setDataSource(fileToPlay);
//        } catch (IOException e) {
//            out.release();
//            return;
//        }
//        extractor.selectTrack(0);
//
//        String fileType = typeForFile(fileToPlay);
//        if (fileType == null) {
//            out.release();
//            extractor.release();
//            return;
//        }
//
//        MediaCodec codec = MediaCodec.createDecoderByType(fileType);
//        MediaFormat wantedFormat = extractor.getTrackFormat(0);
//        codec.configure(wantedFormat, null, null, 0);
//        codec.start();
//
//        ByteBuffer[] inputBuffers = codec.getInputBuffers();
//        ByteBuffer[] outputBuffers = codec.getOutputBuffers();
//
//        // Allocate our own buffer
//        int maximumBufferSizeBytes = 0;
//        for (ByteBuffer bb : outputBuffers) {
//            int c = bb.capacity();
//            if (c > maximumBufferSizeBytes) maximumBufferSizeBytes = c;
//        }
//        setupBufferSizes(maximumBufferSizeBytes / 4);
//
//        final MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
//        MediaFormat format = null;
//        while (true) {
//            long timeoutUs = 1000000;
//            int inputBufferIndex = codec.dequeueInputBuffer(timeoutUs);
//            if (inputBufferIndex >= 0) {
//                ByteBuffer targetBuffer = inputBuffers[inputBufferIndex];
//                int read = extractor.readSampleData(targetBuffer, 0);
//                int flags = extractor.getSampleFlags();
//                if (read > 0)
//                    codec.queueInputBuffer(inputBufferIndex, 0, read, 0, flags);
//                else
//                    codec.queueInputBuffer(inputBufferIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
//                extractor.advance();
//            }
//
//            int outputBufferIndex = codec.dequeueOutputBuffer(bufferInfo, timeoutUs);
//            if (outputBufferIndex >= 0) {
//                final boolean last = bufferInfo.flags == MediaCodec.BUFFER_FLAG_END_OF_STREAM;
//
//                int s = bufferInfo.size / 4;
//                ByteBuffer bytes = outputBuffers[outputBufferIndex];
//                ((ByteBuffer) bytes.position(bufferInfo.offset)).asShortBuffer().get(shorts, 0, s * 2);
//                process(shorts, 0, s * 2);
//
//                codec.releaseOutputBuffer(outputBufferIndex, false);
//                if (last)
//                    break;
//            } else if (outputBufferIndex == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
//                outputBuffers = codec.getOutputBuffers();
//            } else if (outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
//                format = codec.getOutputFormat();
//            }
//        }
//
//        extractor.release();
//        codec.stop();
//        codec.release();
//    }

//    short[] target=new short[LENGTH OF 4 MINUTES];
//    int idx=0;
//    void process(short[] audio, int l)
//    {
//        for(int i=0;i<l;i++)
//            target[idx++]+=audio[i]/2;
//    }


}
