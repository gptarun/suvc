package com.example.myapplication;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

public class BackgroundWorker extends AsyncTask<String, Void, String> {
    Context context;
    BackgroundWorker(Context context){
        this.context = context;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        if (type.equalsIgnoreCase("register")) {
            try {
                String user_name = params[1];
                String name = params[2];
                String email = params[3];
                String pwd = params[4];
                String datetime = params[5];
                String collegeName = params[6];

                URL url = new URL("http://suvcrste.com/app_register.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_login", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
                        + URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(pwd, "UTF-8") + "&"
                        + URLEncoder.encode("user_nicename", "UTF-8") + "=" + URLEncoder.encode(name,"UTF-8") + "&"
                        + URLEncoder.encode("user_email", "UTF-8") + "=" + URLEncoder.encode(email,"UTF-8") + "&"
                        + URLEncoder.encode("user_display_name","UTF-8") + "=" + URLEncoder.encode(name,"UTF-8") + "&"
                        + URLEncoder.encode("registration_datetime","UTF-8") + "=" + URLEncoder.encode(datetime,"UTF-8")
                        + URLEncoder.encode("team_college","UTF-8") + "=" + URLEncoder.encode(collegeName,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                System.out.println(reader.readLine());
                inputStream.close();
                return "Registration Succesful!";
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("login")){
            String uid = params[1];
            String pwd = params[2];
            try {
                URL url = new URL("http://suvcrste.com/app_login.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8") + "=" + URLEncoder.encode(uid,"UTF-8") + "&"
                        + URLEncoder.encode("password","UTF-8") +"=" + URLEncoder.encode(pwd,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String res = reader.readLine();
                inputStream.close();
                return res;
            } catch (IOException e){
                e.printStackTrace();
            }
        } else if (type.equals("notifications")){
            try{
                String teamid = params[1];
                URL url = new URL("http://suvcrste.com/app_notification.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("teamid","UTF-8") + "=" +URLEncoder.encode(teamid,"UTF-8");
                writer.write(post_data);
                writer.flush();
                writer.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String res = reader.readLine();
                inputStream.close();
                return res;
            } catch (IOException e){
                e.printStackTrace();
            }
        } else if (type.equals("notificationcount")){
            try{
                String teamid = params[1];
                URL url = new URL("http://suvcrste.com/app_notification_count.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("teamid","UTF-8") + "=" +URLEncoder.encode(teamid,"UTF-8");
                writer.write(post_data);
                writer.flush();
                writer.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String res = reader.readLine();
                inputStream.close();
                return res;
            } catch (IOException e){
                e.printStackTrace();
            }
        } else if (type.equals("getData")){
            try{
                String username = params[1];
                String password = params[2];
                URL url = new URL("http://suvcrste.com/app_getUser.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8") + "=" +URLEncoder.encode(username,"UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" +URLEncoder.encode(password,"UTF-8");
                writer.write(post_data);
                writer.flush();
                writer.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String res = reader.readLine();
                inputStream.close();
                return res;
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        else if (type.equals("test")){
            try {
                URL url = new URL("http://suvcrste.com/test_api.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String res = reader.readLine();
                inputStream.close();
                return res;
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(String result) {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public String status(String s){
        return s;
    }
}
