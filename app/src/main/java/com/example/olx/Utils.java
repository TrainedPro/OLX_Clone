package com.example.olx;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Utils {

    private static ArrayList<ModelAd> products;
    private static final ArrayList<User> users = new ArrayList<>();
    public static final Map<Integer, ArrayList<Integer>> userFavorites = new HashMap<>(); // Maps user ID to list of favorite ad IDs

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static long getTimeStamp() {
        return System.currentTimeMillis();
    }

    public static String[] getCategories() {
        return new String[]{"Phone", "TV"};
    }

    public static ArrayList<ModelAd> getProducts() {
        if (products == null) {
            products = new ArrayList<>();
            products.add(new ModelAd(1, 101, "Ad Title 1", "Phone", "Description of ad 1", "New York", 299.99f));
            products.add(new ModelAd(2, 102, "Ad Title 2", "TV", "Description of ad 2", "Los Angeles", 19.99f));
            products.add(new ModelAd(3, 103, "Ad Title 3", "Furniture", "Description of ad 3", "Chicago", 89.99f));
            products.add(new ModelAd(4, 104, "Ad Title 4", "Clothing", "Description of ad 4", "Houston", 49.99f));
            products.add(new ModelAd(5, 105, "Ad Title 5", "Vehicles", "Description of ad 5", "Phoenix", 10999.99f));
        }

        return products;
    }

    public static void addToFavourites(Context context, int userId, int adId) {
        ArrayList<Integer> favorites = userFavorites.getOrDefault(userId, new ArrayList<>());
        if (favorites != null && !favorites.contains(adId)) {
            favorites.add(adId);
            userFavorites.put(userId, favorites);
            Utils.toast(context, "Added To Favourites");
        } else {
            Utils.toast(context, "Already in Favourites");
        }
    }

    public static void removeFromFavourites(Context context, int userId, int adId) {
        ArrayList<Integer> favorites = userFavorites.get(userId);
        if (favorites != null && favorites.contains(adId)) {
            favorites.remove(Integer.valueOf(adId));
            Utils.toast(context, "Removed From Favourites");
        } else {
            Utils.toast(context, "Not in Favourites");
        }
    }

    public static ArrayList<ModelAd> getUserFavourites(int userId) {
        ArrayList<Integer> favIds = userFavorites.get(userId);
        ArrayList<ModelAd> favAds = new ArrayList<>();
        if (favIds != null) {
            for (int id : favIds) {
                for (ModelAd ad : products) {
                    if (ad.getId() == id) {
                        favAds.add(ad);
                    }
                }
            }
        }
        return favAds;
    }

    public static User registerUser(String userName, String email, String password, String phone) {

        for (User user : users) {
            if (user.getEmail() != null && user.getEmail().equals(email)) {
                return null; // Email already registered
            }
            if (user.getPhone() != null && user.getPhone().equals(phone)) {
                return null; // Phone already registered
            }
        }

        int userId = users.size() + 1;
        User newUser = new User(userId, userName, email, password, phone);
        users.add(newUser);
        Log.d("User Added", newUser.toString());
        Log.d("User Added", users.size() + ", " + users);
        return newUser;
    }

    public static User loginUser(String identifier, String password) {
        Log.d("Users Size", users.size() + " ," + users.toString());
        for (User user : users) {
            Log.d("Verification", "Identifier: " + identifier + ", Email: " + user.getEmail() + ", Phone: " + user.getPhone() + ", Password: " + user.getPassword());

            // Check if identifier matches email or phone number and password matches
            if ((user.getEmail() != null && user.getEmail().equals(identifier)) ||
                    (user.getPhone() != null && user.getPhone().equals(identifier))) {
                if (user.getPassword().equals(password)) {
                    return user;
                } else {
                    return null; // Password doesn't match
                }
            }
        }
        return null; // User not found or invalid credentials
    }


    public static User getUser(int id) {
        for (User user : users) {
            if (user.getEmail() != null && user.getId() == id) {
                return user;
            }
        }
        return null; // Invalid credentials
    }

    public static ArrayList<User> getAllUsers() {

        return users;
    }


    public static boolean deleteUser(int userId) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == userId) {
                iterator.remove();
                userFavorites.remove(userId); // Also remove the user's favorites
                return true;
            }
        }
        return false; // User not found
    }

    public static void setUserInfo(int id, String name, String email, String phone, String password) {
        for (User user : users) {
            if (user.getId() == id) {
                if (name != null) {
                    Log.d("Edit Profile", "Updated Info");
                    user.setUserName(name);
                }
                if (email != null) {
                    user.setEmail(email);
                }
                if (phone != null) {
                    user.setPhone(phone);
                }
                if (password != null) {
                    user.setPassword(password);
                }
                return;
            }
        }
        Log.d("Edit Profile", "User " + id + " Not Found");

    }

    public static class User {
        private int id;
        private String userName;
        private String email;
        private String password;
        private String phone;

        public User(int id, String userName, String email, String password, String phone) {
            this.id = id;
            this.userName = userName;
            this.email = email;
            this.password = password;
            this.phone = phone;
        }

        public int getId() {
            return id;
        }

        public String getUserName() {
            return userName;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getPhone() {
            return phone;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
