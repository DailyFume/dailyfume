package com.example.daily_fume;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class DeleteRequest extends StringRequest {
    final static private String URL = "http://43.201.60.239/delete_user.php";
    private Map<String, String> parameters;

    public DeleteRequest(String uemail, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("email", uemail);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
