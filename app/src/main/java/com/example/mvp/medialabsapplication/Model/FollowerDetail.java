package com.example.mvp.medialabsapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mausamkumari on 2/19/17.
 */

public class FollowerDetail implements Parcelable {

    public long id;

    @SerializedName("name")
    public String name;

    @SerializedName("login")
    public String login;

    @SerializedName("avatar_url")
    public String avatar_url;

    @SerializedName("followers")
    public int followers;

    @SerializedName("following")
    public int following;

    @SerializedName("public_repos")
    public int public_repos;

    @SerializedName("location")
    public String location;

    @SerializedName("email")
    public String email;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.login);
        parcel.writeString(this.avatar_url);
        parcel.writeInt(this.followers);
        parcel.writeInt(this.following);
        parcel.writeInt(this.public_repos);
        parcel.writeString(this.location);
        parcel.writeString(this.email);
    }

    protected FollowerDetail(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.login = in.readString();
        this.avatar_url = in.readString();
        this.followers = in.readInt();
        this.following = in.readInt();
        this.public_repos = in.readInt();
        this.location = in.readString();
        this.email = in.readString();
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
