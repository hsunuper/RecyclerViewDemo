package huangyang.com.bawey.recyclerviewdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String url = "http://app.u17.com/v3/appV3_3/android/phone/comic/boutiqueListNew?sexType=1&android_id=4058040115108878&v=3330110&model=GT-P5210&come_from=Tg002";

    RecyclerView recyclerView;
    private ArrayList<String> apk=new ArrayList<>();
    private MyAdapter adapter;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String u = (String) msg.obj;
            Log.d("zzzz",u);
            Gson gson = new Gson();
            RBean bean = gson.fromJson(u, RBean.class);
            String aa = bean.getData().getReturnData().getEditTime();
            apk.add(aa);
            Log.d("ggg",apk.toString());
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            adapter = new MyAdapter(MainActivity.this, apk);
            recyclerView.setAdapter(adapter);

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        

    }

    private void initData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                String string = response.body().string();
                Log.i("aaa", "run: "+ string);
                Message message = new Message();
                message.obj=string;
                handler.sendMessage(message);
            }
        });

    }
}
