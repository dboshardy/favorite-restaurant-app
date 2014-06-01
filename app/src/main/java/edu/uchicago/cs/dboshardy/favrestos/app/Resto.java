package edu.uchicago.cs.dboshardy.favrestos.app;

import android.media.Image;

import java.net.URL;

/**
 * Created by drew on 6/1/14.
 */
public class Resto {
    private String mName;
    private URL mYelpURL;
    private String mAddress;
    private double mLatitude;
    private double mLongitude;
    private URL mImageUrl;
    private Image mImage;
    private int mPhoneNumber;

    public Resto(String name, URL yelpURL, String address, double latitude, double longitude, URL imageUrl, int phoneNumber) {
        mName = name;
        mYelpURL = yelpURL;
        mAddress = address;
        mLatitude = latitude;
        mLongitude = longitude;
        mImageUrl = imageUrl;
        mPhoneNumber = phoneNumber;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public URL getYelpURL() {
        return mYelpURL;
    }

    public void setYelpURL(URL yelpURL) {
        mYelpURL = yelpURL;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public URL getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(URL imageUrl) {
        mImageUrl = imageUrl;
    }

    public Image getImage() {
        return mImage;
    }

    public void setImage(Image image) {
        mImage = image;
    }

    public int getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        mPhoneNumber = phoneNumber;
    }
}

