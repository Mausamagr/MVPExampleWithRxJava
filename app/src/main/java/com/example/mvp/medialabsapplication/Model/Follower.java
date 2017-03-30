package com.example.mvp.medialabsapplication.Model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mausamkumari on 2/19/17.
 */

public class Follower  implements Parcelable {
    public long id;
    @SerializedName("login")
    public String name;

    @SerializedName("avatar_url")
    public String avatarUrl;

    @SerializedName("url")
    public String url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.avatarUrl);
        parcel.writeString(this.url);
    }

    protected Follower(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.avatarUrl = in.readString();
        this.url = in.readString();
    }

    public static final Creator<Follower> CREATOR = new Creator<Follower>() {
        public Follower createFromParcel(Parcel source) {
            return new Follower(source);
        }

        public Follower[] newArray(int size) {
            return new Follower[size];
        }
    };
}
