package edu.uchicago.cs.dboshardy.favrestos.app;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class EditFavRestoActivity extends ActionBarActivity {
    private Resto mResto;
    private ArrayList<Resto> mRestos = new ArrayList<Resto>();

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
        private Drawable mImage;
        private String mYelpResponse;
        private JSONObject mJson;
        private JSONArray mJS;
        private String mName;
        private String mCity;
        private ImageView mImageView;

        //TODO: Add viewgroups and instantiate them
        public EditFavRestoFragment() {
        }
        private void drawRestoViews(final Resto resto) {
            if(mImage != null){
                mImageView.setImageDrawable(mImage);
            }
            mEditPhone.setText(resto.getPhoneNumber());
            mEditAddress.setText(resto.getAddress());
            mEditYelpURL.setText(String.valueOf(resto.getYelpURL()));
            mTextPhone.setTextColor(getApplicationContext().getResources().getColor(R.color.blue));
            mTextPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: call intent


                }
            });
            mTextAddress.setTextColor(getApplicationContext().getResources().getColor(R.color.blue));
            mTextAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: map intent

                }
            });
            mTextYelpURL.setTextColor(getApplicationContext().getResources().getColor(R.color.blue));
            mTextYelpURL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: browser intent
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(resto.getYelpURL())));
                    startActivity(browserIntent);
                }
            });
            mFavoriteCheckBox.setChecked(resto.isFavorite());
            mFavoriteCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mFavoriteCheckBox.isChecked()){
                        resto.setFavorite(0);
                    }
                    else {
                        resto.setFavorite(1);
                    }
                    mFavoriteCheckBox.setChecked(resto.isFavorite());
                }
            });
            if(resto.isFavorite()){
                mFavoriteColorView.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.orange));
            }
            else {
                mFavoriteColorView.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.green));
            }
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(resultCode==5){
                mResto = (Resto) data.getExtras().get("resto");
                String[] params = {String.valueOf(mResto.getImageUrl())};
                ImageFetchTask fetcher = new ImageFetchTask();
                fetcher.execute(params);
                drawRestoViews(mResto);
            }
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        public String getCity() {
            return mCity;
        }

        public void setCity(String city) {
            mCity = city;
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
            mCancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent data = new Intent();
                    if (getParent() == null) {
                        setResult(RESULT_CANCELED, data);
                    } else {
                        getParent().setResult(RESULT_CANCELED, data);
                    }
                    finish();
                }
            });
            mUpdateButton = (Button) rootView.findViewById(R.id.update_button);
            mUpdateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent data = new Intent();
                    data.putExtra(Resto.RESTO,mResto);
                    if (getParent() == null) {
                        setResult(RESULT_OK, data);
                    } else {
                        getParent().setResult(RESULT_OK, data);
                    }
                    finish();
                }
            });
            mFetchButton = (Button) rootView.findViewById(R.id.fetch_button);
            mFetchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!(mCity == null || mName == null)) {
                        YelpFetchTask fetcher = new YelpFetchTask();
                        fetcher.execute();
                    }
                    else {
                        Toast.makeText(EditFavRestoActivity.this,"Please enter a name and city and press the \"Fetch\" button.",Toast.LENGTH_LONG);
                    }
                }
            });

            mImageView = (ImageView) rootView.findViewById(R.id.image_view);
            if(mImage != null){

            }
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
                    Toast.makeText(getActivity(), "Could not fetch details from Yelp.", Toast.LENGTH_LONG);

                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                try {
                    mJson = new JSONObject(mYelpResponse);
                    mJS = mJson.getJSONArray("businesses");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mRestos = new ArrayList<Resto>(mJS.length());
                Resto resto = null;

                for (int i = 0; i < mJS.length(); i++) {
                    try {
                        JSONObject entry = mJS.getJSONObject(i);
                        String name = entry.getString("name");
                        URL mobileUrl = new URL(entry.getString("mobile_url"));
                        String phone;
                        if (entry.has("display_phone")) {
                            phone = entry.getString("display_phone");
                        } else {
                            phone = "None";
                        }
                        URL imageUrl;
                        double rating = Double.parseDouble(entry.getString("rating"));
                        if (entry.has("image_url")) {
                            imageUrl = new URL(entry.getString("image_url"));
                        } else {
                            imageUrl = null;
                        }
                        JSONObject location = entry.getJSONObject("location");
                        String address = location.getJSONArray("address").getString(0);
                        String city = location.getString("city");
                        resto = new Resto(name, mobileUrl, address, imageUrl, phone, rating, city);
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), "Could not parse businesses.", Toast.LENGTH_LONG);
                        Log.e("ERROR:", "JSON PARSING ERROR for: " + i);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    if (resto != null) {
                        mRestos.add(resto);
                        resto = null;
                    }
                }

                Bundle args = new Bundle();
                args.putSerializable(Resto.RESTO_LIST,mRestos);
                RestoFragment dialog = RestoFragment.newInstance(mRestos);
                dialog.show(getFragmentManager(),"dialog");

            }


        }

        private class ImageFetchTask extends AsyncTask<String, Void, Drawable> {
            private final HttpClient Client = new DefaultHttpClient();
            private String mContent;
            private String mErrorMessage = null;

            @Override
            protected Drawable doInBackground(String... urls) {
                //TODO: Make image stuff work
                Drawable d = null;
                try {
                    InputStream is = (InputStream) new URL(urls[0]).getContent();
                    d = Drawable.createFromStream(is, "image");

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return d;
            }

            @Override
            protected void onPostExecute(Drawable drawable) {
                super.onPostExecute(drawable);
                mImage = drawable;
            }
        }
    }

}
