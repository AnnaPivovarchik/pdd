package com.anna.pdd.Entities.Java;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anna on 11/22/17.
 */

public class UserAnswer implements Parcelable{
    private int mId;
    private boolean mIsTrue;

    public UserAnswer(int id, boolean isTrue) {
        mId = id;
        mIsTrue = isTrue;
    }

    protected UserAnswer(Parcel in) {
        mId = in.readInt();
        mIsTrue = in.readByte() != 0;
    }

    public static final Creator<UserAnswer> CREATOR = new Creator<UserAnswer>() {
        @Override
        public UserAnswer createFromParcel(Parcel in) {
            return new UserAnswer(in);
        }

        @Override
        public UserAnswer[] newArray(int size) {
            return new UserAnswer[size];
        }
    };

    public int getId() {
        return mId;
    }

    public boolean isTrue() {
        return mIsTrue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeByte((byte) (mIsTrue ? 1 : 0));
    }
}
