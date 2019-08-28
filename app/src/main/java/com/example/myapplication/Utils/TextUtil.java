package com.example.myapplication.Utils;

import android.widget.EditText;

public class TextUtil {

  public static boolean isEmpty(EditText editText){
    return editText.getText().toString().isEmpty();
  }

  public static boolean isEqual(EditText et1, EditText et2){
    return et1.getText().toString().equals(et2.getText().toString());
  }

  public static String getText(EditText editText){
    return editText.getText().toString();
  }

}
