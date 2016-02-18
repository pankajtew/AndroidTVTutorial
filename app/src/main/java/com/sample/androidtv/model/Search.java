package com.sample.androidtv.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pankaj on 17/2/16.
 */
public class Search implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Search> CREATOR = new Parcelable.Creator<Search>() {
        @Override
        public Search createFromParcel(Parcel in) {
            return new Search(in);
        }

        @Override
        public Search[] newArray(int size) {
            return new Search[size];
        }
    };
    private String Year;
    private String Type;
    private String Poster;
    private String imdbID;
    private String Title;

    protected Search(Parcel in) {
        Year = in.readString();
        Type = in.readString();
        Poster = in.readString();
        imdbID = in.readString();
        Title = in.readString();
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String Year) {
        this.Year = Year;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String Poster) {
        this.Poster = Poster;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    @Override
    public String toString() {
        return "Class Search[IMDB Item] [Year = " + Year + ", Type = " + Type + ", Poster = " + Poster + ", imdbID = " + imdbID + ", Title = " + Title + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Year);
        dest.writeString(Type);
        dest.writeString(Poster);
        dest.writeString(imdbID);
        dest.writeString(Title);
    }

}