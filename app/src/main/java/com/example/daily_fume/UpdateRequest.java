package com.example.daily_fume;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class UpdateRequest extends StringRequest {
    final static private String URL = "http://43.200.245.161/update_user.php";
    private Map<String, String> parameters;

    public UpdateRequest(String uemail, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("email", uemail);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
