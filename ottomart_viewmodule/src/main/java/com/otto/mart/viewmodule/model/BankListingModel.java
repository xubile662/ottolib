package com.otto.mart.viewmodule.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BankListingModel implements Parcelable {
    int id;
    String name;
    String code;
    String logo;

    public BankListingModel() {
    }

    protected BankListingModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        code = in.readString();
        logo = in.readString();
    }

    public static final Creator<BankListingModel> CREATOR = new Creator<BankListingModel>() {
        @Override
        public BankListingModel createFromParcel(Parcel in) {
            return new BankListingModel(in);
        }

        @Override
        public BankListingModel[] newArray(int size) {
            return new BankListingModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(code);
        dest.writeString(logo);
    }
}