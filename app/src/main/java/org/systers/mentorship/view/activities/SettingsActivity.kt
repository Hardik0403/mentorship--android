package org.systers.mentorship.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.activity_settings.*
import org.systers.mentorship.R
import org.systers.mentorship.utils.PreferenceManager
import org.systers.mentorship.view.fragments.ChangePasswordFragment
import java.text.SimpleDateFormat
import java.util.*

class SettingsActivity : BaseActivity() {

    companion object {
        fun logout(context: Context) {


            }
    }

    val preferenceManager: PreferenceManager = PreferenceManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar?.title = getString(R.string.settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        var c = Calendar.getInstance().time
        var sdf = SimpleDateFormat("dd-M-yyyy")
        var date=sdf.format(c)
        Toast.makeText(applicationContext,date.toString(),Toast.LENGTH_LONG).show()

        tvLogout.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.confirm_logout)
            builder.setMessage(R.string.confirm_logout_msg)
            builder.setPositiveButton(R.string.logout) { _, _ ->
                preferenceManager.clear()
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
            }
            builder.setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        tvResetPassword.setOnClickListener {
            ChangePasswordFragment.newInstance().show(supportFragmentManager, null)
        }
        tvAbout.setOnClickListener {
            startActivity(Intent(baseContext,AboutActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
