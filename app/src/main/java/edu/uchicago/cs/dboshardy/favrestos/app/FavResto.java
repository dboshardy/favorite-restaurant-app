package edu.uchicago.cs.dboshardy.favrestos.app;


import android.media.Image;

import java.net.URL;

public class FavResto {

    public static final String RESTO_LIST = "edu.uchicago.cs.dboshardy.restolist";
    private int mId;
    private String mContent;
    private int mFavorite;
    private String mPhoneNumber;
    private String mAddress;
    private boolean isFavorite;
    private String mCity;
    private URL mYelpURL;
    private Image mImage;

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public URL getYelpURL() {
        return mYelpURL;
    }

    public void setYelpURL(URL yelpURL) {
        mYelpURL = yelpURL;
    }

    public Image getImage() {
        return mImage;
    }

    public void setImage(Image image) {
        mImage = image;
    }

    public FavResto(int id, String content, int favorite, String phoneNumber, String address, boolean isFavorite, String city, URL yelpURL, Image image) {
        mId = id;
        mContent = content;
        mFavorite = favorite;
        mPhoneNumber = phoneNumber;
        mAddress = address;
        this.isFavorite = isFavorite;
        mCity = city;
        mYelpURL = yelpURL;
        mImage = image;
    }

    public FavResto(int id, String content, int favorite) {
        mId = id;
        mFavorite = favorite;
        mContent = content;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getFavorite() {
        return mFavorite;
    }

    public void setFavorite(int favorite) {
        mFavorite = favorite;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
