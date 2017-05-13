package com.dyraid.dyraid.items.userlog;

import com.dyraid.dyraid.items.user.User;

import java.util.Date;

/**
 * Created by emekaokoro on 5/12/17.
 */

public class UserLog {

    public User user;
    public Date log_time;
    public String comment;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getLog_time() {
        return log_time;
    }

    public void setLog_time(Date log_time) {
        this.log_time = log_time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UserLog(User user, Date log_time, String comment) {
        this.user = user;
        this.log_time = log_time;
        this.comment = comment;
    }
}
