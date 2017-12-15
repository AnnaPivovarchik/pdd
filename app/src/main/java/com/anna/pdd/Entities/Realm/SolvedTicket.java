package com.anna.pdd.Entities.Realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by anna on 11/23/17.
 */

public class SolvedTicket extends RealmObject {

    @PrimaryKey
    private long mId;
    private int mRightCount;
    private int mAllCount;
    private int mTicketId;

    public int getTicketId() {
        return mTicketId;
    }

    public void setTicketId(int ticketId) {
        mTicketId = ticketId;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public int getRightCount() {
        return mRightCount;
    }

    public void setRightCount(int rightCount) {
        mRightCount = rightCount;
    }

    public int getAllCount() {
        return mAllCount;
    }

    public void setAllCount(int allCount) {
        mAllCount = allCount;
    }
}
