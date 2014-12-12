package com.uwanttolearn.easysocialfacebook;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.uwanttolearn.easysocial.EasySocialAuthActivity;
import com.uwanttolearn.easysocial.EasySocialCredential;
import com.uwanttolearn.easysocial.webrequests.GetWebRequest;
import com.uwanttolearn.easysocial.webrequests.PostWebRequest;
import com.uwanttolearn.easysocial.webrequests.WebRequest;
import com.uwanttolearn.easysocialfacebook.utilities.EasySocialFacebookPreferenceUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Hafiz Waleed Hussain on 12/6/2014.
 * This is the main class which interact with Facebook Servers and get data for us in our app.
 * This class use as a Singleton.
 */
public class EasySocialFacebook {

    /** Class own reference*/
    private static EasySocialFacebook _EasySocialFacebook;
    /** EasySocialFacebookUrlManager is handle Facebook API'S Url's*/
    private static EasySocialFacebookUrlManager _EasySocialFacebookUrlManager;

    /**
     * EasySocialFacebook instantiation method.
     * @param easySocialCredential Take Facebook credential as a parameter.
     * @return EasySocialFacebook object.
     */
    public static final EasySocialFacebook getInstance(EasySocialCredential easySocialCredential){
        if(_EasySocialFacebook == null)
            _EasySocialFacebook = new EasySocialFacebook();
            _EasySocialFacebookUrlManager = new EasySocialFacebookUrlManager(easySocialCredential);
        return _EasySocialFacebook;
    }

    /**
     * No argument private constructor.
     */
    private EasySocialFacebook(){
    }

    /**
     * This method is used to authenticate user from Facebook. To manage the response,
     * it is compulsory to override onActivityResult method.
     * @param activity It take activity as a reference.
     * @param requestCode requestCode is used to handle the response.
     */
    public void login(Activity activity, int requestCode){
        Intent intent = new Intent(activity, EasySocialAuthActivity.class);
        intent.putExtra(EasySocialAuthActivity.URL,_EasySocialFacebookUrlManager.getLoginUrl());
        intent.putExtra(EasySocialAuthActivity.REDIRECT_URL, _EasySocialFacebookUrlManager.getRedirectUrl());
        intent.putExtra(EasySocialAuthActivity.ACCESS_TOKEN, _EasySocialFacebookUrlManager.getAccessTokenUrl());
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * UserInfoCallback interface is used to get User Information from Facebook. This
     * interface object send in getUserInfo method as parameter.
     */
    public static interface UserInfoCallback{ void onComplete(JSONObject jsonObject);}

    /**
     * This method get the User Information from Facebook and send back to the caller
     * using Callback UserInfoCallback as JSONObject or null if error occur.
     * @param context Context of the calling component.
     * @param userInfoCallback Callback interface for User Information.
     */
    public void getUserInfo(Context context, final UserInfoCallback userInfoCallback){

        GetWebRequest getWebRequest = new GetWebRequest(new WebRequest.Callback() {
            @Override
            public void requestComplete(String line) {
                try {
                    JSONObject jsonObject = new JSONObject(line);
                    userInfoCallback.onComplete(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                    userInfoCallback.onComplete(null);
                }
            }
        });
        getWebRequest.executeRequest(_EasySocialFacebookUrlManager.getUserInfoUrl(context));
    }

    /**
     * PostInfoCallback interface is used to post message to Facebook wall. This
     * interface object send in sendPost method as parameter.
     */
    public static interface PostInfoCallback{ void onComplete(String postId);}

    /**
     * This method send the message on the Facebook Wall and send back the Post Id using
     * PostInfoCallback as String or null if error occur.
     * @param context Context of the calling component.
     * @param facebookUserId Facebook userId.
     * @param postInfoCallback Callback interface for message post.
     */
    public void sendPost(Context context, String facebookUserId, final PostInfoCallback postInfoCallback){
        String urlParams = "message="+"Hi Testing ";
        PostWebRequest postWebRequest = new PostWebRequest(new WebRequest.Callback() {
            @Override
            public void requestComplete(String data) {
                postInfoCallback.onComplete(data);
            }
        });
        postWebRequest.executeRequest(_EasySocialFacebookUrlManager.getPostMessageUrl(context,facebookUserId),urlParams);
    }

    /**
     * GetFriendsCallback interface is used when we want to post message to Facebook wall. This
     * interface object we send in sendPost method.
     */
    public static interface GetFriendsCallback{void onComplete(JSONObject data);}

    /**
     * This method get the Friends list from Facebook and send back to the caller
     * using Callback GetFriendsCallback as JSONObject or null if error occur.
     * @param context Context of the calling component.
     * @param facebookUserId Facebook Facebook userId.
     * @param getFriendsCallback GetFriendsCallback interface for Friends list data.
     */
    public void getFriends(Context context, String facebookUserId, final GetFriendsCallback getFriendsCallback){
        GetWebRequest getWebRequest = new GetWebRequest(new WebRequest.Callback() {
            @Override
            public void requestComplete(String line) {
                try {
                    JSONObject jsonObject = new JSONObject(line);
                    getFriendsCallback.onComplete(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                    getFriendsCallback.onComplete(null);
                }
            }
        });
        getWebRequest.executeRequest(_EasySocialFacebookUrlManager.getFriendsUrl(context, facebookUserId));
    }

}