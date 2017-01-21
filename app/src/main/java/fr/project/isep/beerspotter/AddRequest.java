package fr.project.isep.beerspotter;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;



public class AddRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL ="http://beerspotter.mybluemix.net/api/places";
    private Map<String,String> params;
    public AddRequest(String name, Double longitude, Double latitude, Double price, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener,null);
        params = new HashMap<>();
        params.put("name",name);
        params.put("longitude",longitude+"");
        params.put("latitude",latitude+"");
        params.put("price",price+"");

    }

    public Map<String,String >getParams()
    {
        return params;
    }
}
