package app.wqg.hookgame2.Thread;

import android.graphics.Rect;

import org.json.JSONArray;
import org.json.JSONObject;

public interface HaarState {
    void start();
    void stop();
    void findPage(int CurrentPage,JSONArray jsonArray);
    void unfindPage(JSONArray jsonArray);
    void findCurrentPage(JSONArray jsonArray);
    void unfindCurrentPage(JSONArray jsonArray);
}
