package com.uwanttolearn.easysocialfacebook;

import android.content.Intent;
import android.net.Uri;

/**
 * Created by Hafiz Waleed Hussain on 12/8/2014.
 * This class is used as AccessToken Parser Utility.
 */
final class EasySocialFacebookAccessTokenParse {

    /**
     * This method is used to parse the AccessToken from a RawData
     * @param data Take an intent as a parameter.
     * @return AccessToken as String or null.
     */
    public static final String parseAccessToken(Intent data){
        String line = data.getStringExtra("data");
        String url = "https://www.uwanttolearn.com?"+line;
        Uri uri = Uri.parse(url);
        return uri.getQueryParameter("access_token");
    }
}
