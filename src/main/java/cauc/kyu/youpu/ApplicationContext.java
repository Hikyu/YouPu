package cauc.kyu.youpu;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2015/6/13.
 */
public class ApplicationContext extends Application {
    public static final String TAG = ApplicationContext.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static ApplicationContext mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }


    public static synchronized ApplicationContext getInstance() {
        return mInstance;
    }


    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }


    public <T> void add(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancel() {
        mRequestQueue.cancelAll(TAG);
    }
}
