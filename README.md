EasySocialFacebook
==================
EasySocialFacebook is an Android module for Authentication,Sharing or anything you want. I try to make this as general as possible, so you can easily extend this. This module dependent on our EasySocial module. Which we use for Oauth2.0 authentication. You can find that on my github repo's.

How to use.

So simple add this and EasySocial module in your application. 
Now create a credential object.

        EasySocialCredential mCredentials = new EasySocialCredential.Builder("client-id",
                "client-secret-id",
                "https://www.uwanttolearn.com/").
                setPermissions(
                        new String[]{"email,publish_actions,user_friends"})
                .build();

Now create a EasySocialFacebook instance.

        mEasySocialFacebook = EasySocialFacebook.getInstance(mCredentials);

For login use this code snippet.

            mEasySocialFacebook.login(getActivity(),REQUEST_CODE);

      @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // In this method we will get response of our login method.
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_CODE){
                /** Handle the authentication response */
                mEasySocialFacebook.loginResponseHandler(context, data);
            }
        }
    }


For Geting user information.

        mEasySocialFacebook.getUserInfo(context,new EasySocialFacebook.UserInfoCallback() {
                @Override
                public void onComplete(JSONObject jsonObject) {
                        Log.d(TAG,jsonObject.toString());
                }
        });

For Getting friends.

        mEasySocialFacebook.getFriends(context,mUserId,new EasySocialFacebook.GetFriendsCallback() {
                @Override
                public void onComplete(JSONObject data) {
                        Log.d(TAG,data.toString());
                }
        });

For Sending post on Facebook.

        mEasySocialFacebook.sendPost(context,mUserId, "Testing Easy Social",new EasySocialFacebook.PostInfoCallback() {
                @Override
                public void onComplete(String postId) {
                        Log.d(TAG,"Post successfully send: "+postId);
                }
            });

For Getting user image.

        mEasySocialFacebook.getUserImageAsUri(context, new EasySocialFacebook.GetUserImageAsUri() {
                @Override
                        String imageUrl = data.optJSONObject("data").optString("url");
                        Log.d(TAG,"User Image Url: "+imageUrl);
                }
        });



For complete explanation you can read the tutorial in which I explain how to use this and how to extend this according to your requirements. http://www.uwanttolearn.com/android/facebook-module-using-easysocial-oauth2-0-module/
