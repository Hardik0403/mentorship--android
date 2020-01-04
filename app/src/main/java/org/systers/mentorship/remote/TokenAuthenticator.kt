package org.systers.mentorship.remote

import android.util.Log
import android.widget.Toast
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.systers.mentorship.MentorshipApplication

class TokenAuthenticator : Authenticator {

    var STATUS_CODE_UNAUTHORISED = 401

    override fun authenticate(route: Route, response: Response): Request? {
        if(response.code() == STATUS_CODE_UNAUTHORISED){
            Log.i("TAG","Logout")
            val context = MentorshipApplication.getContext()
            Toast.makeText(context, "LOGGGGGGGGGGGGGIING OUT", Toast.LENGTH_LONG).show()
            return null
        }else {
            return null
        }
    }
}