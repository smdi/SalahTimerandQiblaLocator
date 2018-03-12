package al_muntaqimcrescent2018.com.salahtimerandqiblalocator;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.airbnb.lottie.LottieAnimationView;
import com.sdsmdg.tastytoast.TastyToast;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Imran on 04-02-2018.
 */

public class Qibla extends Fragment {

    private ProgressBar progressBar;
    private WebView webView;
    private static  String url = "" ;
    private CircleImageView cimg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.qibla,container,false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getGeoLOcation(getActivity());
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        try {
            if (Build.VERSION.SDK_INT < 16) {
                getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



            url =  "https://qiblafinder.withgoogle.com/intl/en/finder/ar";

        progressBar = (ProgressBar) view.findViewById(R.id.qiblaprog);
//        progressBar.setProgress(100);
        progressBar.setIndeterminateTintList(ColorStateList.valueOf(Color.CYAN));
        cimg = (CircleImageView) view.findViewById(R.id.CircularImageOntop);
        cimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TastyToast.makeText(getActivity() ,"Qibla finder",TastyToast.LENGTH_SHORT,TastyToast.INFO).show();
            }
        });



        webView = (WebView) view.findViewById(R.id.webViewinActivity);
//        getWebFast();
        webView.setWebViewClient(new MyClient());
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().getLoadsImagesAutomatically();
//        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                progressBar.setProgress(newProgress);

                if (newProgress == 100) {

                    progressBar.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                }

            }
        });

        webView.loadUrl(url);


    }

    private void getGeoLOcation(Activity activity) {

        String provider = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);


        if(!provider.contains("gps")) { //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            activity.sendBroadcast(poke);

        }
    }
//
//    public void getWebFast() {
//
//        WebSettings webSettings = webView.getSettings();
//
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl(url);
////        webView.getSettings().setBuiltInZoomControls(false);
//        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webView.getSettings().setAppCacheEnabled(true);
////        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
////        webView.setScrollbarFadingEnabled(true);
//        webView.getSettings().setDomStorageEnabled(true);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webSettings.getUseWideViewPort();
//        webSettings.setLoadWithOverviewMode(true) ;
//        webSettings.setUseWideViewPort(true);
//        webSettings.setSupportZoom(true);
//        webSettings.getSaveFormData();
//        webSettings.setEnableSmoothTransition(true);
////        webView.getSettings().setJavaScriptEnabled(true);
////        webView.getSettings().setBuiltInZoomControls(true);
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
//            webView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
//        }
//        else{
//            webView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
//        }
//    }

    private class MyClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }
    }


}
