package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.Utils.About;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutUsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_about_us);
    Element registration = new Element();
    registration.setValue("Registration Form");
    registration.setTitle("Registration Form");
    registration.setIconDrawable(R.drawable.pdf);
    registration.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        goToUrl("https://drive.google.com/file/d/1-EHbOKJpnTUWQoTPKhtr6nkJvg6LjXGK/view?usp=drivesdk");
      }
    });

    Element rulebook = new Element();
    rulebook.setTitle("Rule Book");
    rulebook.setValue("Rule book");
    rulebook.setIconDrawable(R.drawable.pdf);
    rulebook.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
       goToUrl("https://drive.google.com/file/d/1-09YS6A5luCK0JT8rJVnyG1xuTB3k5Qr/view?usp=drivesdk");
      }
    });

    Element youtube = new Element();
    youtube.setTitle("Watch us on Youtube");
    youtube.setValue("Watch us on Youtube");
    youtube.setIconDrawable(R.drawable.about_icon_youtube);
    youtube.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        openYoutube(view);
      }
    });

    View aboutPage = new About(this)
        .isRTL(false)
        .setImage(R.drawable.suvclogo_1)
        .setDescription(getString(R.string.about_us))
        .addGroup("Important Documents")
        .addItem(registration)
        .addItem(rulebook)
        .addGroup("Connect with us")
        .addEmail("suvc20@gmail.com")
        .addWebsite("http://suvcrste.com/")
        .addFacebook("suvcrste")
        .addItem(youtube)
        .addInstagram("suvcrste")
        .create();
     setContentView(aboutPage);
  }


  public void openYoutube(View view) {
    goToUrl("https://m.youtube.com/suvcrste");
  }


  public void openFacebook(View view) {
    goToUrl("http://www.facebook.com/suvcrste");
  }


  private void goToUrl(String url) {
    Uri uriUrl = Uri.parse(url);
    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
    startActivity(launchBrowser);
  }


  public void openWebsite(View view) {
    goToUrl("http://suvcrste.com/");
  }


}
