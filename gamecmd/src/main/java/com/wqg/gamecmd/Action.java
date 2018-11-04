package com.wqg.gamecmd;

import android.graphics.Rect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Action {
    public static JSONArray IntoRoom() throws JSONException {
        JSONArray jsonArray=new JSONArray();
        int i=0;
        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面0",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面0_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面1",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面1_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面2",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面2_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面3",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面3_child2"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面4",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面4_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面5",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面5_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面6",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面6_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面7",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面7_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面8",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面8_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面9",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{CmdWindow.Room},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面11",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面11_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面12",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面12_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面13",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面13_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面14",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面14_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面15",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面15_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面16",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面16_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面17",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面17_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面18",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面18_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面19",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面19_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面20",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面20_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面21",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面21_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面22",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面22_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面23",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面23_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面24",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面24_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面25",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面25_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面26",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面26_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面27",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面27_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面28",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面28_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面29",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面29_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面30",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面30_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面31",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面31_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面32",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面32_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面33",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面33_child0"},new int[]{1},200,0.5d))));

        JSONObject []jsonObjects=new JSONObject[i+1];
        for (int I=0;I<jsonObjects.length;I++){
            jsonObjects[I]= IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("IntoRoom"+I);
        }

        jsonArray.put(new IntentionJSONObject("IntoRoom"+i++,System.currentTimeMillis(),"页面10",
                        IntentionJSONObject.IntentionAction(jsonObjects,
                                IntentionJSONObject.IntentionAction_ACTION_INTENTION_ADD(Allow())
                        )
                ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage("页面10_child26"),new JSONArray())
                )
        );
        return jsonArray;
    }
    public static JSONArray Allow() throws JSONException {
        JSONArray jsonArray=new JSONArray();
        int i=0;
        jsonArray.put(new IntentionJSONObject("关闭弹幕",System.currentTimeMillis(),"页面10",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面10_child2"},new int[]{1},1000,0.5d)
                        ,IntentionJSONObject.IntentionAction_ACTION_OCR("游戏金币",2,new Rect(95,460,100,15))
                        ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("关闭弹幕")
                )));


    /*    jsonArray.put(new IntentionJSONObject("下注",System.currentTimeMillis(),"页面10",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_INTENTION_ADD(downBets("页面10_child6"))
                        , IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("下注")
                )
                ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage("页面10_child15"),new JSONArray())
        ));*/
        return jsonArray;
    }
    public static JSONArray FreeTimeCheck() throws JSONException {
        JSONArray jsonArray=new JSONArray();
        jsonArray.put(new IntentionJSONObject("空闲时间查询金币",System.currentTimeMillis(),"页面10",
                IntentionJSONObject.IntentionAction(
                         IntentionJSONObject.IntentionAction_ACTION_OCR("游戏金币",2,new Rect(95,460,100,15))
                        ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("空闲时间查询金币")
                )
                ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage("页面10_child10"),new JSONArray())
        ));
        return jsonArray;
    }
    public static JSONArray AllowTime() throws JSONException {
        JSONArray jsonArray=new JSONArray();
        jsonArray.put(new IntentionJSONObject("下注时间",System.currentTimeMillis(),"页面10",
                IntentionJSONObject.IntentionAction(IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("下注时间")
                )
                ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage("页面10_child15"),new JSONArray())
        ));
        return jsonArray;
    }
    public static JSONArray Bets( JSONObject SynthesisMath) throws JSONException {

        JSONArray jsonArray=new JSONArray();
        int i=0;
       /* jsonArray.put(new IntentionJSONObject("is压住筹码1000",System.currentTimeMillis(),"页面10",
                        IntentionJSONObject.IntentionAction(
                                IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("is压住筹码1000")
                                ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("to压住筹码1000")
                        )
                        ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage("页面10_child6"),new JSONArray())
                )
        );

        jsonArray.put(new IntentionJSONObject("to压住筹码1000",System.currentTimeMillis(),"页面10",
                        IntentionJSONObject.IntentionAction(
                                IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面10_child4"},new int[]{1},200,0.5d)
                        )
                        ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage("页面10_child4"),new JSONArray())
                )
        );*/

/*        jsonArray.put(new IntentionJSONObject("is压住筹码10000",System.currentTimeMillis(),"页面10",
                        IntentionJSONObject.IntentionAction(
                                IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("is压住筹码10000")
                                ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("to压住筹码10000")
                        )
                        ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage("页面10_child8"),new JSONArray())
                )
        );

        jsonArray.put(new IntentionJSONObject("to压住筹码10000",System.currentTimeMillis(),"页面10",
                        IntentionJSONObject.IntentionAction(
                                IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面10_child3"},new int[]{1},200,0.5d)
                        )
                        ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage("页面10_child3"),new JSONArray())
                )
        );*/

        String ISbs="页面10_child29";
        String BS="页面10_child27";
        String isBSname="is压住筹码10万";
        String toBSname="to压住筹码10万";
        switch (CmdWindow.BS){
            case 100:
            {
                ISbs="页面10_child5";
                BS="页面10_child25";
                isBSname="is压住筹码100";
                toBSname="to压住筹码100";
            }break;
            case 1000:
            {
                ISbs="页面10_child6";
                BS="页面10_child4";
                isBSname="is压住筹码1000";
                toBSname="to压住筹码1000";
            }break;
            case 10000:
            {
                ISbs="页面10_child8";
                BS="页面10_child3";
                isBSname="is压住筹码1万";
                toBSname="to压住筹码1万";
            }break;
            case 100000:
            {
                ISbs="页面10_child29";
                BS="页面10_child27";
                isBSname="is压住筹码10万";
                toBSname="to压住筹码10万";
            }break;
            case 500000:
            {
                ISbs="页面10_child30";
                BS="页面10_child28";
                isBSname="is压住筹码50万";
                toBSname="to压住筹码50万";
            }break;
        }
        jsonArray.put(new IntentionJSONObject(isBSname,System.currentTimeMillis(),"页面10",
                        IntentionJSONObject.IntentionAction(
                                IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE(isBSname)
                                ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE(toBSname)
                        )
                        ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage(ISbs),new JSONArray())
                )
        );

        jsonArray.put(new IntentionJSONObject(toBSname,System.currentTimeMillis(),"页面10",
                        IntentionJSONObject.IntentionAction(
                                IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{BS},new int[]{1},200,0.5d)
                        )
                        ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage(BS),new JSONArray())
                )
        );
        jsonArray.put(new IntentionJSONObject("添加下注",System.currentTimeMillis(),"页面10",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_INTENTION_ADD(downBets(SynthesisMath,ISbs,CmdWindow.x))
                        , IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("添加下注")
                )
                ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage("页面10_child15"),new JSONArray())
        ));
        return jsonArray;
    }
    public static JSONArray downBets( JSONObject SynthesisMath,String Bets, int x) throws JSONException {

        JSONArray bestJSONArray=SynthesisMath.getJSONArray("best");
        JSONArray bestbigJSONArray=SynthesisMath.getJSONArray("bestbig");
        String[]best=new String[]{"页面10_child17","页面10_child18","页面10_child19","页面10_child20"};
        String[]bestbig=new String[]{"页面10_child21","页面10_child22","页面10_child23","页面10_child24"};
        JSONObject[]jsonObjects=new JSONObject[4];

        jsonObjects[0]=  IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{best[bestJSONArray.getInt(0)]},new int[]{4*x},200,0.5d);
        jsonObjects[1]=  IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{best[bestJSONArray.getInt(1)]},new int[]{4*x},200,0.5d);
        jsonObjects[2]=  IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{bestbig[bestbigJSONArray.getInt(0)]},new int[]{1*x},200,0.5d);
        jsonObjects[3]=  IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{bestbig[bestbigJSONArray.getInt(1)]},new int[]{1*x},200,0.5d);
        JSONArray jsonArray=new JSONArray();
      /*  jsonArray.put(new IntentionJSONObject("续押",System.currentTimeMillis(),"页面10",
                IntentionJSONObject.IntentionAction(IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面10_child7"},new int[]{1},200,0.5d)
                )
                ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage("页面10_child15","页面10_child7"),new JSONArray())
        ));*/
     /*   jsonArray.put(new IntentionJSONObject("系统提示",System.currentTimeMillis(),"页面11",
                IntentionJSONObject.IntentionAction(IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面11_child0"},new int[]{1},200,0.5d)
                        ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("续押")
                        ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("下注")
                        ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("系统提示")
                )
        ));*/
        if (bestJSONArray.getInt(0)==1||bestJSONArray.getInt(1)==1){
            jsonArray.put(new IntentionJSONObject("下注",System.currentTimeMillis(),"页面10",
                    IntentionJSONObject.IntentionAction(jsonObjects
                            ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("下注")
                           // ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("续押")
                           // ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("系统提示")
                            //,IntentionJSONObject.IntentionAction_ACTION_INTENTION_ADD(downBetsMini())
                    )
                    ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage("页面10_child15","页面10_child17","页面10_child18","页面10_child19","页面10_child20","页面10_child21","页面10_child22","页面10_child23","页面10_child24",Bets),new JSONArray())
            ));
        }else {
            jsonArray.put(new IntentionJSONObject("下注",System.currentTimeMillis(),"页面10",
                    IntentionJSONObject.IntentionAction(jsonObjects
                            ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("下注")
                           // ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("续押")
                           // ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("系统提示")
                    )
                    ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage("页面10_child15","页面10_child17","页面10_child18","页面10_child19","页面10_child20","页面10_child21","页面10_child22","页面10_child23","页面10_child24",Bets),new JSONArray())
            ));
        }

        return jsonArray;
    }

    public static JSONArray downBetsMini() throws JSONException {

        JSONArray jsonArray=new JSONArray();
        int i=0;

        jsonArray.put(new IntentionJSONObject("is压住筹码100",System.currentTimeMillis(),"页面10",
                        IntentionJSONObject.IntentionAction(
                                IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("is压住筹码100")
                                ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("to压住筹码100")
                        )
                        ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage("页面10_child5"),new JSONArray())
                )
        );
        jsonArray.put(new IntentionJSONObject("to压住筹码100",System.currentTimeMillis(),"页面10",
                        IntentionJSONObject.IntentionAction(
                                IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面10_child25"},new int[]{1},200,0.5d)
                        )
                        ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage("页面10_child25"),new JSONArray())
                )
        );
        jsonArray.put(new IntentionJSONObject("补偿押注",System.currentTimeMillis(),"页面10",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面10_child18"},new int[]{1},200,0.5d)
                        ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("补偿押注")
                )
                ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage("页面10_child15","页面10_child5","页面10_child18"),new JSONArray())
        ));
        return jsonArray;
    }
    public static JSONArray continueBets() throws JSONException {

        JSONArray jsonArray=new JSONArray();
        jsonArray.put(new IntentionJSONObject("继续下注",System.currentTimeMillis(),"页面10",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_INTENTION_ADD(FreeTimeCheck())
                        ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("继续下注")
                )
                ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage("页面10_child9"),new JSONArray())
        ));
        return jsonArray;
    }
    public static JSONArray gethongbao(String ID,String G) throws JSONException {
        JSONArray jsonArray=new JSONArray();
        int i=0;
        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面0",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面0_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面1",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面1_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面2",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面2_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面3",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面3_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面4",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面4_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面5",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面5_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面6",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面6_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面7",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面7_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面8",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面8_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面9",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面9_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面10",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面10_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面11",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面11_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面12",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面12_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面13",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面13_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面14",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面14_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面15",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面15_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面16",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面16_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面17",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面17_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面18",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面18_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面19",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面19_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面20",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面20_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面21",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面21_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面22",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面22_child2"},new int[]{1},200,0.5d)
                        ,IntentionJSONObject.IntentionAction_ACTION_INPUT(IntentionJSONObject.INPUT_DEFAULT,ID)
                        ,IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面22_child4"},new int[]{1},200,0.5d)
                )));


        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面23",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面23_child3"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面24",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面24_child1"},new int[]{1},200,0.5d)
                        ,IntentionJSONObject.IntentionAction_ACTION_INPUT(IntentionJSONObject.INPUT_DEFAULT,G)
                        ,IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面24_child3"},new int[]{1},200,0.5d)
                )));

        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面26",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面26_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面27",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面27_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面28",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面28_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面29",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面29_child0"},new int[]{1},200,0.5d))));


        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面32",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面32_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面33",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面33_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面34",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面34_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("gethongbao"+i++,System.currentTimeMillis(),"页面35",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面35_child0"},new int[]{1},200,0.5d))));

        JSONObject []jsonObjects=new JSONObject[i];
        for (int I=0;I<jsonObjects.length;I++){
            jsonObjects[I]= IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("gethongbao"+I);
        }
        jsonArray.put(new IntentionJSONObject("恭喜你猜中了",System.currentTimeMillis(),"页面30",
                IntentionJSONObject.IntentionAction(jsonObjects
                        ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("恭喜你猜中了")
                )
        ));
        return jsonArray;
    }
    public static JSONArray sendhongbao(int g) throws JSONException {
        JSONArray jsonArray=new JSONArray();
        int i=0;
        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面0",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面0_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面1",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面1_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面2",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面2_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面3",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面3_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面4",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面4_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面5",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面5_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面6",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面6_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面7",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面7_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面8",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面8_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面9",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面9_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面10",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面10_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面11",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面11_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面12",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面12_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面13",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面13_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面14",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面14_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面15",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面15_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面16",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面16_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面17",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面17_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面18",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面18_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面19",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面19_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面20",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面20_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面21",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面21_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面22",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面22_child1"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面23",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面23_child3"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面24",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面24_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面25",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面25_child0"},new int[]{1},200,0.5d))));
        String dataS[]= new String[]{"shangfen","asdas","1+1","134665165","haha","tryyy","avvvv","uuuu","kl;jkl","sd","sb","cnm","___"};
        String text= dataS[(int)(Math.random()*(dataS.length))] + ((int) (Math.random() * 10));
        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面26",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面26_child1"},new int[]{1},500,0.5d)
                        ,IntentionJSONObject.IntentionAction_ACTION_INPUT(IntentionJSONObject.INPUT_DEFAULT,text)
                        ,IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面26_child2"},new int[]{1},500,0.5d)
                        ,IntentionJSONObject.IntentionAction_ACTION_INPUT(IntentionJSONObject.INPUT_DEFAULT,g+"")
                        ,IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面26_child5"},new int[]{1},500,0.5d)
                        ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("sendhongbao"+i)
                )));
   /*     jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面26",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面26_child2"},new int[]{1},200,0.5d)
                        ,IntentionJSONObject.IntentionAction_ACTION_INPUT(IntentionJSONObject.INPUT_DEFAULT,"100001")
                        ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("sendhongbao"+i)
                )));*/

        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面27",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面27_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面28",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面28_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面29",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面29_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面30",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面30_child0"},new int[]{1},200,0.5d))));

        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面32",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面32_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面33",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面33_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面34",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面34_child0"},new int[]{1},200,0.5d))));
        jsonArray.put(new IntentionJSONObject("sendhongbao"+i++,System.currentTimeMillis(),"页面35",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面35_child0"},new int[]{1},200,0.5d))));


        JSONObject []jsonObjects=new JSONObject[i];
        for (int I=0;I<jsonObjects.length;I++){
            jsonObjects[I]= IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("sendhongbao"+I);
        }
        JSONObject jsonObject=new IntentionJSONObject("恭喜您发送红包成功",System.currentTimeMillis(),"页面31",
                IntentionJSONObject.IntentionAction(jsonObjects
                        , IntentionJSONObject.IntentionAction_ACTION_BAIDU_OCR("红包ID",new Rect(403, 225, 60, 20))
                        ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("恭喜您发送红包成功")
                ));
        jsonObject.put("data",g);
        jsonArray.put(jsonObject);
        return jsonArray;
    }
    public static JSONArray FreeHongBao() throws JSONException {

        JSONArray jsonArray=new JSONArray();
        int i=0;
        jsonArray.put(new IntentionJSONObject("FreeHongBao"+i++,System.currentTimeMillis(),"页面22",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面22_child5"},new int[]{1},200,0.5d)
                )
        ));
        jsonArray.put(new IntentionJSONObject("FreeHongBao"+i++,System.currentTimeMillis(),"页面30",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面30_child0"},new int[]{1},200,0.5d)
                )
        ));
        jsonArray.put(new IntentionJSONObject("FreeHongBao"+i++,System.currentTimeMillis(),"页面31",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面31_child0"},new int[]{1},200,0.5d)
                )
        ));
        JSONObject []jsonObjects=new JSONObject[i];
        for (int I=0;I<jsonObjects.length;I++){
            jsonObjects[I]= IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("FreeHongBao"+I);
        }
        jsonArray.put(new IntentionJSONObject("发放红包",System.currentTimeMillis(),"页面23",
                IntentionJSONObject.IntentionAction(jsonObjects
                        ,         IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面23_child1"},new int[]{1},200,0.5d)
                        ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("发放红包")
                )
                ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage("页面23_child1"),new JSONArray())
        ));
        return jsonArray;
    }
    static public JSONArray FourMix0() throws JSONException {

        JSONArray jsonArray=new JSONArray();
        String ISbs="页面10_child29";
        String BS="页面10_child27";
        String isBSname="is压住筹码10万";
        String toBSname="to压住筹码10万";
        switch (CmdWindow.BS){
            case 100:
            {
                ISbs="页面10_child5";
                BS="页面10_child25";
                isBSname="is压住筹码100";
                toBSname="to压住筹码100";
            }break;
            case 1000:
            {
                ISbs="页面10_child6";
                BS="页面10_child4";
                isBSname="is压住筹码1000";
                toBSname="to压住筹码1000";
            }break;
            case 10000:
            {
                ISbs="页面10_child8";
                BS="页面10_child3";
                isBSname="is压住筹码1万";
                toBSname="to压住筹码1万";
            }break;
            case 100000:
            {
                ISbs="页面10_child29";
                BS="页面10_child27";
                isBSname="is压住筹码10万";
                toBSname="to压住筹码10万";
            }break;
            case 500000:
            {
                ISbs="页面10_child30";
                BS="页面10_child28";
                isBSname="is压住筹码50万";
                toBSname="to压住筹码50万";
            }break;
        }
        jsonArray.put(new IntentionJSONObject(isBSname,System.currentTimeMillis(),"页面10",
                        IntentionJSONObject.IntentionAction(
                                IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE(isBSname)
                                ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE(toBSname)
                        )
                        ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage(ISbs),new JSONArray())
                )
        );

        jsonArray.put(new IntentionJSONObject(toBSname,System.currentTimeMillis(),"页面10",
                        IntentionJSONObject.IntentionAction(
                                IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{BS},new int[]{1},200,0.5d)
                        )
                        ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage(BS),new JSONArray())
                )
        );
        jsonArray.put(new IntentionJSONObject("添加下注",System.currentTimeMillis(),"页面10",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_INTENTION_ADD(FourMix(ISbs,CmdWindow.x))
                        , IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("添加下注")
                )
                ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage("页面10_child15"),new JSONArray())
        ));
        return jsonArray;
    }
    static public JSONArray FourMix(String Bets, int x) throws JSONException {
        String[]best=new String[]{"页面10_child17","页面10_child18","页面10_child19","页面10_child20"};
        int[] clicks=new int[]{x,x,x,x};
        JSONArray jsonArray=new JSONArray();
        jsonArray.put(new IntentionJSONObject("四小下注",System.currentTimeMillis(),"页面10",
                IntentionJSONObject.IntentionAction(IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(best,clicks,200,0.5d)
                        ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("四小下注")
                )
                ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage("页面10_child15","页面10_child17","页面10_child18","页面10_child19","页面10_child20","页面10_child21","页面10_child22","页面10_child23","页面10_child24",Bets),new JSONArray())
        ));
        return jsonArray;
    }
    static public JSONArray FourMixContinue() throws JSONException {
        JSONArray jsonArray=new JSONArray();
        jsonArray.put(new IntentionJSONObject("空闲时间",System.currentTimeMillis(),"页面10",
                IntentionJSONObject.IntentionAction(
                        IntentionJSONObject.IntentionAction_ACTION_INTENTION_ADD(FourMix0())
                        ,IntentionJSONObject.IntentionAction_ACTION_INTENTION_REMOVE("空闲时间")
                )
                ,IntentionJSONObject.IntentionState(IntentionJSONObject.intentionStateChildPage("页面10_child10"),new JSONArray())
        ));
        return jsonArray;
    }
}
