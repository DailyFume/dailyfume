package com.example.daily_fume;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class LikeRequest extends StringRequest {
    final static private String URL = "http://43.200.245.161/likelist.php"; // 찜 목록(리스트) 등록 db
    private Map<String, String> parameters;

    public LikeRequest(String listname, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("listname", listname);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
