package com.androworld.allinonevideoeditor;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class Mainactivity extends AppCompatActivity implements View.OnClickListener {

    ImageView start;
    ImageView iv_share;
    ImageView iv_reta;
    ImageView iv_privecy;
    Dialog closeAppDialog;
    private Ads ads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startactivity);
        ads = new Ads();

        FrameLayout native_ad_container = findViewById(R.id.native_ad_container);
        ads.loadNativeAd(Mainactivity.this,native_ad_container);

        iv_share = findViewById(R.id.iv_share);
        iv_share.setOnClickListener(this);
        iv_reta = findViewById(R.id.iv_reta);
        iv_reta.setOnClickListener(this);
        iv_privecy = findViewById(R.id.iv_privecy);
        iv_privecy.setOnClickListener(this);
        start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                startActivity(new Intent(Mainactivity.this, StartActivity.class));
            }
        });
    }


    @Override public void onClick(View v) {


        switch (v.getId()) {

            case R.id.iv_share:

                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                }
                break;

            case R.id.iv_reta:

                ratingDialog(Mainactivity.this);
                break;

            case R.id.iv_privecy:

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://androworld202.blogspot.com/"));
                startActivity(i);
                break;


        }
    }

    public static void ratingDialog(Activity activity) {

        Intent i3 = new Intent(Intent.ACTION_VIEW, Uri
                .parse("market://details?id=" + activity.getPackageName()));
        activity.startActivity(i3);
    }

    @Override public void onBackPressed() {
        initCloseAppDialog();

        closeAppDialog.show();
        Window window = closeAppDialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    public void initCloseAppDialog() {
        closeAppDialog = new Dialog(this);
        closeAppDialog.requestWindowFeature(1);
        closeAppDialog.setContentView(R.layout.dialog_go_back);

        FrameLayout native_ad_containerx = closeAppDialog.findViewById(R.id.native_ad_container);
        ads.loadNativeAd(Mainactivity.this,native_ad_containerx);

        ((TextView) closeAppDialog.findViewById(R.id.tv_dialog_text)).setText(getString(R.string.sure_close_app));
        Button button = (Button) closeAppDialog.findViewById(R.id.bt_cancel);
        button.setText(getString(R.string.cancel));
        button.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                lambda$initCloseAppDialog$0$MainActivity(view);
            }
        });
        Button button2 = (Button) closeAppDialog.findViewById(R.id.bt_yes);
        button2.setText(getString(R.string.close));
        button2.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                lambda$initCloseAppDialog$1$MainActivity(view);
            }
        });

    }

    public  void lambda$initCloseAppDialog$0$MainActivity(View view) {
        closeAppDialog.dismiss();
    }

    public  void lambda$initCloseAppDialog$1$MainActivity(View view) {
        closeAppDialog.dismiss();
        finish();
    }

}
