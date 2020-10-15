package com.example.a116311691_fyp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

//START
//https://www.youtube.com/watch?v=BWaZa1j2xVg&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=7 //youtube tutorial directed me to below link
//https://developer.android.com/training/volley/requestqueue.html entire class copied & pasted from this link

//singleton pattern
//"will create an instance of the request object which will be live as long as the app runs"
//handle network requests

/*i.e. no longer need
'RequestQueue requestQueue = Volley.newRequestQueue(this);
requestQueue.add(stringRequest);'
in each activity
 */

//instead you just need 'RequestHandler.getInstance(this).addToRequestQueue(stringRequest);' e.g. for C of CRUD in RegisterOrg

public class RequestHandler {
    private static RequestHandler instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private RequestHandler(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized RequestHandler getInstance(Context context) {
        if (instance == null) {
            instance = new RequestHandler(context);
        }
        return instance;
    }

    //this method will give us the request queue
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    //this method will add request object to the request queue
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}

//END