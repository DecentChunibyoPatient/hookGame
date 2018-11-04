package com.wqg.gamecmd;

import android.content.Context;
import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONObject;

public interface CmdInterface {
    void CompletionOfIntention(JSONObject intention,JSONArray jsonArrayAction,JSONObject data);
    void RemoveIntention(JSONArray intention,String intentionNmae);
    void AddIntention();
    void sendAction();
    void start();
    void stop();
    void set();
    void close();
    void log();
    void Configure(int EyeThreadTime,int pageTime);
    void screenshot(Context context);
    void screenshot(Context context, Bitmap bitmap);
    void findPage(int CurrentPage, JSONArray ints);
    void unfindPage(  JSONArray ints);
    void findCurrentPage(  JSONArray ints);
    void unfindCurrentPage(  JSONArray ints);
}
