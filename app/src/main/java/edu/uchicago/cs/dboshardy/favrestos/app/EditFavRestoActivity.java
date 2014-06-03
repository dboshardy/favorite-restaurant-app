package edu.uchicago.cs.dboshardy.favrestos.app;

import android.annotation.TargetApi;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class EditFavRestoActivity extends ActionBarActivity {
    private ArrayList<Resto> mRestos;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fav_resto);
        getActionBar().setTitle(getString(R.string.restaurant_details_title));
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new EditFavRestoFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_fav_resto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public class EditFavRestoFragment extends Fragment {

        private Button mCancelButton;
        private Button mUpdateButton;
        private Button mFetchButton;
        private EditText mSearchResto;
        private EditText mSearchCity;
        private EditText mEditPhone;
        private EditText mEditAddress;
        private EditText mEditYelpURL;
        private TextView mTextPhone;
        private TextView mTextAddress;
        private TextView mTextYelpURL;
        private CheckBox mFavoriteCheckBox;
        private View mFavoriteColorView;
        private ImageView mImage;
        private String mYelpResponse;
        private JSONObject mJson;
        private JSONArray mJS;
        private String mName;
        private String mCity;

        //TODO: Add viewgroups and instantiate them
        public EditFavRestoFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_edit_fav_resto, container, false);
            mSearchCity = (EditText) rootView.findViewById(R.id.search_city);
            mSearchCity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    mCity = charSequence.toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            mSearchResto = (EditText) rootView.findViewById(R.id.search_restaurant);
            mSearchResto.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    mName = charSequence.toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            mCancelButton = (Button) rootView.findViewById(R.id.cancel_button);
            mUpdateButton = (Button) rootView.findViewById(R.id.update_button);
            mFetchButton = (Button) rootView.findViewById(R.id.fetch_button);
            mFetchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    YelpFetchTask fetcher = new YelpFetchTask();
                    fetcher.execute();

                }
            });
            mImage = (ImageView) rootView.findViewById(R.id.image_view);
            mEditPhone = (EditText) rootView.findViewById(R.id.edit_phone);
            mEditAddress = (EditText) rootView.findViewById(R.id.edit_address);
            mEditYelpURL = (EditText) rootView.findViewById(R.id.edit_yelp_url);
            mTextPhone = (TextView) rootView.findViewById(R.id.text_phone);
            mTextAddress = (TextView) rootView.findViewById(R.id.text_address);
            mTextYelpURL = (TextView) rootView.findViewById(R.id.text_yelp_url);
            mFavoriteCheckBox = (CheckBox) rootView.findViewById(R.id.favorite_checked);
            mFavoriteColorView = rootView.findViewById(R.id.favorite_color_view);
            return rootView;
        }

        private class YelpFetchTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... arg0) {
                try {

                    String consumerKey = "iCV4K4v8qdqSskKh37_Nfw";
                    String consumerSecret = "BQLT9aAzLVoKlwHdW3w7c1J4ts0";
                    String token = "KtQN1zaRnPMcaMRNcYo-vvp1NoPA3efG";
                    String tokenSecret = "Ph9OoNJDsEuiQmA_iRirVio-4iE";

                    Yelp yelp = new Yelp(consumerKey, consumerSecret, token, tokenSecret);

                    String response = yelp.search(mName, mCity);

                    Log.i("!!!!!", response);
                    mYelpResponse = response;

                } catch (Exception e) {
                    Toast.makeText(getActivity(),"Could not fetch details from Yelp.",Toast.LENGTH_LONG);

                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);

                try {
                    mJson = new JSONObject(mYelpResponse);
                    //json=json.getJSONObject("businesses");


                    mJS = mJson.getJSONArray("businesses");
                    mRestos = new ArrayList<Resto>(mJS.length());

                    for (int i = 0; i < mJS.length(); i++) {
                        JSONObject entry = mJS.getJSONObject(i);
                        String name = entry.getString("name");
                        URL mobileUrl = new URL(entry.getString("mobile_url"));
                        int phone = Integer.parseInt(entry.getString("display_phone"));
                        double rating = Double.parseDouble(entry.getString("rating"));
                        URL imageUrl = new URL(entry.getString("image_url"));
                        JSONObject location = entry.getJSONObject("location");
                        JSONObject region = entry.getJSONObject("region");
                        JSONObject center = region.getJSONObject("center");
                        double lat = Double.parseDouble(center.getString("latitude"));
                        double lon = Double.parseDouble(center.getString("longitude"));
                        String address = location.getString("address");
                        Resto resto = new Resto(name,mobileUrl,address,lat,lon,imageUrl,phone,rating);
                        mRestos.add(resto);
                    }



                } catch (JSONException e) {
                    Log.e("ERROR:", "JSON PARSING ERROR");
                    Toast.makeText(getActivity(),"Could not parse businesses.",Toast.LENGTH_LONG);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

        }
        private class ImageFetchTask extends AsyncTask<String,Void,Void>{
            private final HttpClient Client = new DefaultHttpClient();
            private String mContent;
            private String mErrorMessage = null;
            //TODO: get this working

            @Override
            protected Void doInBackground(String... urls) {
                try {
                    URL imageUrl = new URL(urls[0]);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
    }
}
