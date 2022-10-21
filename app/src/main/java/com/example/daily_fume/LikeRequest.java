//package com.example.daily_fume;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import com.android.volley.Response;
//import com.android.volley.toolbox.StringRequest;
//
//public class LikeRequest extends StringRequest {
//    final static private String URL = "http://43.201.60.239/likelist.php";
//    private Map<String, Integer> parameters;
//
//    public LikeRequest(String listname, int user_uid Response.Listener<String, Integer> listener) {
//        super(Method.POST, URL, listener, null);
//        parameters = new HashMap<>();
//        parameters.put("listname", listname);
//        parameters.put("user_uid", user_uid);
//    }
//
//    @Override
//    public Map<String, String> getParams() {
//        return parameters;
//    }
//}
