package com.dyraid.dyraid.items.meal;

import com.dyraid.dyraid.items.user.User;

import java.util.Date;

/**
 * Created by emekaokoro on 5/24/17.
 */

public class Meal {

    private User user;
    private String food_name;
    private Integer calories;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Meal(User user, String food_name, Integer calories) {
        this.user = user;
        this.food_name = food_name;
        this.calories = calories;
    }
}