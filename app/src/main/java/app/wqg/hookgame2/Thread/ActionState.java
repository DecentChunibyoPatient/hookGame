package app.wqg.hookgame2.Thread;

import org.json.JSONArray;
import org.json.JSONObject;

public interface ActionState {
    void Completion(JSONObject intention ,JSONArray jsonArrayAction,JSONObject data);
    void RemoveIntention(String name);
    void AddIntention(JSONObject jsonObject);
}
