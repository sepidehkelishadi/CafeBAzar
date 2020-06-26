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

public class MainActivity extends AppCompatActivity {

    WebView myWebView;
    TextView myResultView;

    String username;
    String password;

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
}
