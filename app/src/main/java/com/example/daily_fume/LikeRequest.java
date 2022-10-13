package com.example.daily_fume;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class LikeRequest extends StringRequest {
    final static private String URL = "http://43.201.60.239/likelist.php";
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
