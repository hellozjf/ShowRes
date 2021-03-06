package com.hellozjf.study.java.springmvc.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
public class HelloController {

    @RequestMapping(value = "/getRes.do", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getRes() {

        JSONObject ret = new JSONObject();
        JSONArray xdata = new JSONArray();
        JSONArray ydata = new JSONArray();
        String from = null;
        String to = null;

        try {
            File file = new File("d:/res.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            // InputStream in =
            // this.getClass().getClassLoader().getResourceAsStream("res.txt");
            // BufferedReader reader = new BufferedReader(new
            // InputStreamReader(in));
            List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
            String s = null;
            while ((s = reader.readLine()) != null) {
                JSONObject jsonObject;
                try {
                    jsonObject = JSONObject.parseObject(s);
                } catch (Exception e) {
                    continue;
                }
                if (jsonObject != null && jsonObject.containsKey("code")
                        && jsonObject.containsKey("data")
                        && jsonObject.getJSONArray("data").size() != 0) {
                    jsonObjects.add(jsonObject);
                }
            }

            for (int i = jsonObjects.size() - 1; i >= 0; i--) {
                JSONObject jsonObject = jsonObjects.get(i);
                if (jsonObject.getIntValue("code") == 200) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    int size = jsonArray.size() <= 20 ? jsonArray.size() : 20;
                    for (int j = 0; j < size; j++) {
                        JSONArray array = jsonArray.getJSONArray(j);

                        xdata.add(array.getString(0));
                        ydata.add(array.getInteger(1));
                    }
                    from = jsonObject.getString("from");
                    to = jsonObject.getString("to");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ret.put("xdata", xdata);
        ret.put("ydata", ydata);
        ret.put("from", from.subSequence(0, 4) + "年" + from.subSequence(4, 6) + "月" + from.subSequence(6, 8) + "日" + from.subSequence(8, 10) + "时");
        ret.put("to", to.subSequence(0, 4) + "年" + to.subSequence(4, 6) + "月" + to.subSequence(6, 8) + "日" + to.substring(8, 10) + "时");
        return ret.toJSONString();
    }
}
