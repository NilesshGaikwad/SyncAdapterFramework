package com.techmainstay.nilsyncdemo.ac;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Nilesh on 27-Dec-17.
 */

public class AuthenticatorService extends Service {


    // Instance field that stores the authenticator object
    private Authenticator mAuthenticator;
    @Override
    public void onCreate() {
        // Create a new authenticator object
        System.out.println("authenticator service");
        mAuthenticator = new Authenticator(this);
    }
    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("authenticator service binder");
        return mAuthenticator.getIBinder();
    }


}
