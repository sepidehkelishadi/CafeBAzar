package cafebazar.challenge.cafebazar;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cafebazar.challenge.cafebazar.api.MyApiEndpointInterface;
import cafebazar.challenge.cafebazar.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    WebView myWebView;
    TextView myResultView;

    String username;
    String password;

    public static final String BASE_URL = "https://www.Imageplus.ir/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myResultView = (TextView) this.findViewById(R.id.myResult);

        myWebView = (WebView) this.findViewById(R.id.myWebView);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl("file:///android_asset/index.html");

        myWebView.addJavascriptInterface(new JavaScriptInterface(this, myWebView), "MyHandler");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        Button btn = (Button) this.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callJavaScriptFunctionAndGetResultBack();
            }
        });


        sendDataToApi(username,password);
    }


    public void getData(String username, String password) {

        this.username = username;
        this.password = password;
        Toast.makeText(getApplicationContext(), username + " " + password, Toast.LENGTH_LONG).show();
    }

    public void callJavaScriptFunctionAndGetResultBack() {
        Log.v("mylog", "MainActivity.callJavascriptFunction is called");
        myWebView.loadUrl("javascript:window.MyHandler.setResult( getData() )");
    }

    public void javascriptCallFinished(final String val) {
        Log.e("mylog", "MyActivity.javascriptCallFinished is called : " + val);
        Toast.makeText(this, "result : " + val, Toast.LENGTH_LONG).show();

        // I need to run set operation of UI on the main thread.
        // therefore, the above parameter "val" must be final
        runOnUiThread(new Runnable() {
            public void run() {
                myResultView.setText("Result : " + val);
            }
        });
    }



    public void sendDataToApi(String username,String password){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        MyApiEndpointInterface apiService =
                retrofit.create(MyApiEndpointInterface.class);

        User user = new User("tk2lQ6QomkqfdLiHBPQVw", "10");
        Call<User> call = apiService.createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.e("qqqq", response.toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("qqqq", "aaaa");
            }
        });
    }

}
