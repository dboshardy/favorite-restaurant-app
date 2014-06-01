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
    private double mRating;


    public Resto(String name, URL yelpURL, String address, double latitude, double longitude, URL imageUrl, int phoneNumber, double rating) {
        mName = name;
        mYelpURL = yelpURL;
        mAddress = address;
        mLatitude = latitude;
        mLongitude = longitude;
        mImageUrl = imageUrl;
        mPhoneNumber = phoneNumber;
        mRating = rating;
    }
    public double getRating() {
        return mRating;
    }

    public void setRating(double rating) {
        mRating = rating;
    }



    public String getDisplayName(){
        return mName + " | " + mAddress + " | " + getRatingAsASCII();
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

    public String getRatingAsASCII() {
        double rating = mRating;
        StringBuilder builder = new StringBuilder();
        while(rating !=0){
            if(rating < 0){
                builder.append(Character.toString((char)189));
                break;
            }
            else {
                builder.append("*");
            }
            rating--;
        }

        return builder.toString();
    }
}

