package com.example.myapplication.Utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.LinearLayoutCompat;
import com.example.myapplication.AboutUsActivity;
import com.example.myapplication.R;
import mehdi.sakout.aboutpage.AboutPage;

public class About extends AboutPage {
  private Context context;

  /**
   * The AboutPage requires a context to perform it's functions. Give it a context associated to an
   * Activity or a Fragment. To avoid memory leaks, don't pass a {@link
   * Context#getApplicationContext() Context.getApplicationContext()} here.
   */
  public About(Context context) {
    super(context);
    this.context = context;
  }

  @Override
  public View create() {
    View view = super.create();
    TextView textView = view.findViewById(R.id.description);
    textView.setTextSize(15f);
    textView.setGravity(Gravity.START);
    textView.setWidth(LayoutParams.MATCH_PARENT);

    return view;

  }
}
