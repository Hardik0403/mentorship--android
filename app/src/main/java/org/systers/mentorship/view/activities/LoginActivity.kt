package org.systers.mentorship.view.activities

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject
import org.systers.mentorship.R
import org.systers.mentorship.remote.requests.Login
import org.systers.mentorship.utils.Constants.RC_GOOGLE_SIGNIN
import org.systers.mentorship.viewmodels.LoginViewModel
import java.util.*

/**
 * This activity will let the user to login using username/email and password.
 */
@Suppress("DEPRECATION")
class LoginActivity : BaseActivity(), GoogleApiClient.OnConnectionFailedListener {

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var username: String
    private lateinit var password: String

    private lateinit var googleApiClient: GoogleApiClient

    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        loginViewModel.successful.observe(this, Observer {
            successful ->
            hideProgressDialog()
            if (successful != null) {
                if (successful) {
                    Toast.makeText(this, R.string.logging_successful, Toast.LENGTH_LONG)
                            .show()
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Snackbar.make(getRootView(), loginViewModel.message, Snackbar.LENGTH_LONG)
                            .show()
                }
            }
        })

        btnLogin.setOnClickListener {
           login()
        }

        btnLoginWithOtherAccounts.setOnClickListener {
            ltLoginActivity.animate().alpha(0.5f).duration = 500
            ltOtherLoginOptions.animate().alpha(1f).duration = 1000
        }

        civGoogle.setOnClickListener {
            googleSignIn()
        }

        /**
         * Implementing Facebook SignIn,
         * 1. Creating a callback manager.
         * 2. Setting up the read permissions.
         * 3. Registering the callback with facebook login button.
         * */
        callbackManager = CallbackManager.Factory.create()
        btnLoginFacebook.setReadPermissions(Arrays.asList("email","public_profile"))

        btnLoginFacebook.registerCallback(callbackManager, object: FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) { TODO("not needed according to the asked functionality") }

            override fun onCancel() { TODO("not needed according to the asked functionality") }

            override fun onError(error: FacebookException?) { TODO("not needed according to the asked functionality") }
        })

        btnSignUp.setOnClickListener {
            intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        ltLoginActivity.setOnClickListener {
            ltOtherLoginOptions.animate().alpha(0f).duration = 500
            ltLoginActivity.animate().alpha(1f).duration = 500
        }

        tiPassword.editText?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                login()
            }
            false
        }
    }

    private fun googleSignIn(){
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        googleApiClient = GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build()

        val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(intent, RC_GOOGLE_SIGNIN)
    }

    override fun onConnectionFailed(p0: ConnectionResult) { }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_GOOGLE_SIGNIN) {

            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                val userAccount = result.signInAccount
                val username = userAccount?.displayName
                Toast.makeText(this, "Successfully logged in $username", Toast.LENGTH_LONG).show()
            }
        } else {
            // Transferring the activity result back to Facebook SDK
            callbackManager.onActivityResult(requestCode,resultCode,data)
        }
    }

    /**
     * Tracks the access token of the Facebook logged in account.
     * */
    val accessTokenTracker = object: AccessTokenTracker(){
        override fun onCurrentAccessTokenChanged(oldAccessToken: AccessToken?, currentAccessToken: AccessToken?) {
            if (currentAccessToken == null) {
                // if user is logged out
            } else {

                val graphRequest = GraphRequest.newMeRequest(currentAccessToken) { `object`, _ ->

                    try {
                        val firstName = `object`?.getString("first_name")
                        val lastName = `object`?.getString("last_name")
                        Toast.makeText(applicationContext, "Successfully logged in $firstName $lastName", Toast.LENGTH_LONG).show()
                    } catch (ex: JSONException) {
                        ex.printStackTrace()
                        Toast.makeText(applicationContext, ex.message, Toast.LENGTH_LONG).show()
                    }
                }

                val parameters = Bundle()
                parameters.putString("fields", "first_name,last_name")
                graphRequest.parameters = parameters
                graphRequest.executeAsync()
            }
        }
    }

    private fun validateCredentials() : Boolean {
        var validCredentials = true
        if (username.isBlank()) {
            tiUsername.error = getString(R.string.error_empty_username)
            validCredentials = false
        } else {
            tiUsername.error = null
        }
        if (password.isBlank()) {
            tiPassword.error = getString(R.string.error_empty_password)
            validCredentials = false
        } else {
            tiPassword.error = null
        }
        return validCredentials
    }

    private fun login() {
        username = tiUsername.editText?.text.toString()
        password = tiPassword.editText?.text.toString()
        if (validateCredentials()) {
            loginViewModel.login(Login(username, password))
            showProgressDialog(getString(R.string.logging_in))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        loginViewModel.successful.removeObservers(this)
        loginViewModel.successful.value = null
    }
}

