package edu.uchicago.cs.dboshardy.favrestos.app;

import java.io.Serializable;
import java.net.URL;

/**
 * Created by drew on 6/1/14.
 */
public class Resto implements Serializable {
    public static final String RESTO_LIST = "edu.uchicago.cs.dboshardy.favrestos.app.resto_list";
    public static final String RESTO = "edu.uchicago.cs.dboshardy.favrestos.app.resto_to_deal_with";
    private int mID = 0;
    private String mName;
    private URL mYelpURL;
    private String mAddress;
    private URL mImageUrl;
    private byte[] mImage;
    private String mPhoneNumber;
    private double mRating;
    private int mFavorite;
    private String mNotes = "";
    private String mCity;

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    //full constructor
    public Resto(int id, String name, int favorite, URL yelpURL, String address, URL imageUrl, String phoneNumber, double rating, String notes, String city) {
        mID = id;
        mName = name;
        mFavorite = favorite;
        mYelpURL = yelpURL;
        mAddress = address;
        mImageUrl = imageUrl;
        mPhoneNumber = phoneNumber;
        mRating = rating;
        mNotes = notes;
        mCity = city;
    }

    //overloaded to remove favorite
    public Resto(String name, URL yelpURL, String address, URL imageUrl, String phoneNumber, double rating, String city) {
        mName = name;
        mYelpURL = yelpURL;
        mAddress = address;
        mImageUrl = imageUrl;
        mPhoneNumber = phoneNumber;
        mRating = rating;
        mCity = city;
        setNotes("");
        setFavorite(0);
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public double getRating() {
        return mRating;
    }

    public void setRating(double rating) {
        mRating = rating;
    }

    public boolean isFavorite() {
        boolean result = false;
        if (mFavorite == 1) {
            result = true;
        }
        return result;
    }

    public int getFavorite() {
        return mFavorite;
    }

    public void setFavorite(int favorite) {
        if (favorite == 1 || favorite == 0) {
            this.mFavorite = favorite;
        }
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

    public URL getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(URL imageUrl) {
        mImageUrl = imageUrl;
    }

    public byte[] getImage() {
        return mImage;
    }

    public void setImage(byte[] image) {
        mImage = image;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getRatingAsASCII() {
        double rating = mRating;
        StringBuilder builder = new StringBuilder();
        while (rating != 0) {
            if (rating < 0) {
                builder.append(Character.toString((char) 189));
                break;
            } else {
                builder.append("*");
            }
            rating--;
        }

        return builder.toString();
    }

    @Override
    public String toString() {
        return mName + " | " + mAddress + " | " + getRatingAsASCII();
    }

    public boolean hasID() {
        boolean result = false;
        if (mID != 0) {
            result = true;
        }
        return result;
    }
}
