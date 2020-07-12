package com.example.instagram.Models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {

    //These keys must exactly match the keys in the Parse database
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";
    private static final String LOG = "Post";

    public String getDescription() {
        // Method getString() is defined in the Parse object class, is like a getter for the key
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        // Method put() is defined in the Parse object class, is like a setter for a key & value
        put(KEY_DESCRIPTION, description);
    }

    // Images are stored as files (or ParseFile) in Parse
    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }


    public void setImage(ParseFile image) {
        put(KEY_IMAGE, image);
    }

    // The user is stored as a special User (or ParseUser) in Parse
    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

}
