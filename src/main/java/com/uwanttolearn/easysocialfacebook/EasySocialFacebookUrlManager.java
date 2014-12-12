package com.uwanttolearn.easysocialfacebook;

import android.content.Context;

import com.uwanttolearn.easysocial.EasySocialCredential;
import com.uwanttolearn.easysocialfacebook.utilities.EasySocialFacebookPreferenceUtility;

/**
 * Created by Hafiz Waleed Hussain on 12/6/2014.
 * EasySocialFacebookUrlManager is a Package level class. In this class we handle all our Url
 * related work.
 */
class EasySocialFacebookUrlManager {

    /** Constants */
    private static final String FACEBOO_API_VERSION_NUMBER = "v2.0";
    private static final String FACEBOOK_GRAPH_SERVER = "https://graph.facebook.com/";
    private static final String FACEBOOK_REQUEST_URL =  FACEBOOK_GRAPH_SERVER+FACEBOO_API_VERSION_NUMBER+"/";

    /** EasySocialCredential object containing info of Social Network credentials */
    private EasySocialCredential _EasySocialCredential;

    private EasySocialFacebookUrlManager(){
        // No Argument constructor
    }


    /**
     * One argument constructor. It take Credential as parameter.
     * @param easySocialCredential
     */
    public EasySocialFacebookUrlManager(EasySocialCredential easySocialCredential) {
        _EasySocialCredential = easySocialCredential;
    }

    /**
     * This method create a Facebook Login Url and return as String.
     * @return
     */
    public String getLoginUrl() {
        String url = "https://www.facebook.com/dialog/oauth?";
        String permissionAsString = getPermissionsAsString(_EasySocialCredential.getPermissions());
        return String.format(url + "client_id=%s&redirect_uri=%s&scope=%s",
                _EasySocialCredential.getAppId(),
                _EasySocialCredential.getRedirectUrl(),
                permissionAsString);
    }

    /**
     * This method create a Facebook AccessToken url and return as String.
     * @return
     */
    public String getAccessTokenUrl(){
        String url = FACEBOOK_GRAPH_SERVER+"oauth/access_token?";
        return String.format(url+"client_id=%s&redirect_uri=%s&client_secret=%s&code=",
                _EasySocialCredential.getAppId(),
                getRedirectUrl(),
                _EasySocialCredential.getAppSecretId());
    }

    /**
     * This method create UserInfo Url and return as String.
     * @param context Context take as a parameter.
     * @return
     */
    public String getUserInfoUrl(Context context){
        return FACEBOOK_REQUEST_URL+"me?"+getAccessTokenAsUrlParameter(context);
    }

    /**
     * This method create Post Message Url and return as String
     * @param context Context Context take as a parameter.
     * @param facebookUserId facebookUserId which get in UserInfo.
     * @return
     */
    public String getPostMessageUrl(Context context,String facebookUserId){
        return FACEBOOK_REQUEST_URL+facebookUserId+"/feed?"+getAccessTokenAsUrlParameter(context);
    }

    /**
     * This method create GetFriends Url and return as String.
     * @param context Context take as a parameter.
     * @param facebookUserId facebookUserId which get in UserInfo.
     * @return
     */
    public String getFriendsUrl(Context context, String facebookUserId){
        return FACEBOOK_REQUEST_URL+facebookUserId+"/friends?"+getAccessTokenAsUrlParameter(context);
    }

    /**
     * This method only return a Redirect Url which user give when app created on a Social Network.
     * @return
     */
    public String getRedirectUrl(){
        return _EasySocialCredential.getRedirectUrl();
    }



    /**
     * This method is used to take the permissions of a SocialNetwork as String array and return as
     * a String appended with comma.
     * @param permissions
     * @return
     */
    private String getPermissionsAsString(String[] permissions) {
        if (permissions == null) return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < permissions.length; i++) {
            stringBuilder.append(permissions[i] + ",");
        }
        try {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        } catch (StringIndexOutOfBoundsException e) {
            return "";
        }
        return stringBuilder.toString();
    }

    private String getAccessTokenAsUrlParameter(Context context){
        return "access_token="+ EasySocialFacebookPreferenceUtility.getAccessToken(context);
    }


}


