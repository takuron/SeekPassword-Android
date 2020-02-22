package top.takuron.seekpassword.presenter;

import android.content.Context;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

public class MainPresenter {
    public static void seekpassword(LinearLayout father, final String password , final String code, final boolean checked, final OnSeekpasswordlinstener is){
        WebView wv = new WebView(father.getContext());
        WebSettings settings = wv.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        wv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
                view.evaluateJavascript("javascript:calljs('"+password+"','"+code+"',"+checked+")",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {
                                is.onReturn(s.replace("\"",""));
                            }
                        });
            }
        });



        father.addView(wv);
        wv.loadUrl("file:///android_asset/index.html");


    }

}
