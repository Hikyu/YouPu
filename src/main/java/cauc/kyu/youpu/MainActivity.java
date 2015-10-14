package cauc.kyu.youpu;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.shadev.easyloadingdemo.view.LoadingButton;

import org.json.JSONObject;

import config.Config;
import helper.CustomJsonRequest;


public class MainActivity extends ActionBarActivity {

    LinearLayout root;
    TextInputLayout textInputLayout;
    LoadingButton loadingButton;
    ImageView imageView;
    CookBookViewHolder cookBookViewHolder = new CookBookViewHolder();

    ApplicationContext applicationContext = ApplicationContext.getInstance();
    // SharedPreferences mSharedPref = getPreferences(Context.MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        findViews();
        initViews();
        initEvents();
       /* if (mSharedPref.getInt(Config.ID, 0) == 0) {
            // search and load a random mars pict.
            try {
                loadData(1);
            } catch (Exception e) {
                // please remember to set your own Flickr API!
                // otherwise I won't be able to show
                // a random Mars picture
                // imageError(e);
            }
        } else {
            // we already have a pict of the day: let's load it!
            //  loadImg(mSharedPref.getString(SHARED_PREFS_IMG_KEY, ""));
        }

*/
        loadData(3);
    }

    private void loadData(int cookId) {
        Log.i("load", "loaddata");
        final int id = cookId;
        String url;
        url = Config.URL + Integer.toString(id);
        CustomJsonRequest customJsonRequest = new CustomJsonRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("load", "success");
                            String name, meterailContent, methodContent, img;
                            response = response.getJSONObject("yi18");
                            name = response.getString("name");
                            cookBookViewHolder.name.setText(name);
                            meterailContent = response.getString("message");
                            cookBookViewHolder.meterail_content.setText(meterailContent);
                            //   methodContent=response.getString();
                            //    cookBookViewHolder.method_content.setText(methodContent);
                            img = response.getString("img");
                            String imgUrl = Config.REALNAME + img;
                            loadImg(imgUrl);

                        } catch (Exception e) {
                            //  txtError(e);
                            Snackbar
                                    .make(root, "检索失败", Snackbar.LENGTH_LONG)
                                    .setAction("知道了", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    })
                                    .show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar
                        .make(root, "检索失败", Snackbar.LENGTH_LONG)
                        .setAction("知道了", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();
            }
        });
        customJsonRequest.setPriority(Request.Priority.HIGH);
        applicationContext.add(customJsonRequest);
    }

    private void loadImg(String imgUrl) {
        ImageRequest request = new ImageRequest(imgUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        //   imageError(error);
                    }
                });

        // we don't need to set the priority here;
        // ImageRequest already comes in with
        // priority set to LOW, that is exactly what we need.
        applicationContext.add(request);
    }

    private void initViews() {
        textInputLayout.setHint("输入关键字");
        textInputLayout.setErrorEnabled(true);

    }

    private void initEvents() {

        loadingButton.setCallback(new LoadingButton.Callback() {
            @Override
            public void click() {
                //   textInputLayout.setError("error");

            }

            @Override
            public void complete() {

            }
        });
    }

    private void findViews() {
        final RelativeLayout searchHeaderRoot = (RelativeLayout) findViewById(R.id.search_header_root);
        root = (LinearLayout) findViewById(R.id.root);
        imageView = (ImageView) findViewById(R.id.img);
        textInputLayout = (TextInputLayout) findViewById(R.id.textInput);
        loadingButton = (LoadingButton) findViewById(R.id.searchBtn);
        FloatingActionButton searchBtn = (FloatingActionButton) findViewById(R.id.search);
        searchBtn.setIcon(R.drawable.search);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchHeaderRoot.getVisibility() == View.VISIBLE) {
                    searchHeaderRoot.setVisibility(View.GONE);
                } else {
                    searchHeaderRoot.setVisibility(View.VISIBLE);
                }
            }
        });
        FloatingActionButton addFavBtn = (FloatingActionButton) findViewById(R.id.addfav);
        addFavBtn.setIcon(R.drawable.addfav);
        addFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        FloatingActionButton myFavBtn = (FloatingActionButton) findViewById(R.id.myfav);
        myFavBtn.setIcon(R.drawable.lookfav);
        myFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cookBookViewHolder.name = (TextView) findViewById(R.id.cookname);
        cookBookViewHolder.meterail_content = (TextView) findViewById(R.id.meterial_content);
        cookBookViewHolder.method_content = (TextView) findViewById(R.id.method_content);
    }

    class CookBookViewHolder {
        TextView name;
        TextView meterail_content;
        TextView method_content;
    }

}
