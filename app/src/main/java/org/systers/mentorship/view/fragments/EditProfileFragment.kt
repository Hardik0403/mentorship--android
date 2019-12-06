package org.systers.mentorship.view.fragments


import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_edit_profile.view.*
import org.systers.mentorship.R
import org.systers.mentorship.databinding.FragmentEditProfileBinding
import org.systers.mentorship.models.User
import org.systers.mentorship.utils.EditProfileFragmentErrorStates
import org.systers.mentorship.view.activities.MainActivity
import org.systers.mentorship.viewmodels.ProfileViewModel
import java.io.InputStream

/**
 * The fragment is responsible for editing the User's profile
 */
class EditProfileFragment: DialogFragment() {

    companion object {
        private lateinit var tempUser: User
        /**
         * Creates an instance of EditProfileFragment
         */
        fun newInstance(user: User): EditProfileFragment {
            tempUser = user.copy(id = null, username = null, email = null)
            return EditProfileFragment()
        }
    }

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var editProfileBinding: FragmentEditProfileBinding

    private lateinit var currentUser: User

    private val PERMISSION_REQUEST_CODE=1
    private val INTENT_REQUEST_CODE=2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        profileViewModel = ViewModelProviders.of(activity!!).get(ProfileViewModel::class.java)
        profileViewModel.successfulUpdate.observe(this, Observer { successful ->
            (activity as MainActivity).hideProgressDialog()
            if (successful != null) {
                if (successful) {
                    Toast.makeText(context, getText(R.string.update_successful), Toast.LENGTH_LONG).show()
                    profileViewModel.getProfile()
                    dismiss()
                } else {
                    Toast.makeText(activity, profileViewModel.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        isCancelable = false
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        editProfileBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.fragment_edit_profile, null, false)

        editProfileBinding.user = tempUser.copy()
        currentUser = tempUser.copy()

        /**
         * When the upload button is clicked, first it checks whether the permission is allowed to Read from external storage or not.
         * If true, then it calls the uploadProfileImage() method, else first asks for the permission.
         * */
        editProfileBinding.imgCameraUpload.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this!!.activity!!, Manifest.permission.READ_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
                }
            } else {
                uploadProfileImage()
            }
        }

        val dialogBuilder = AlertDialog.Builder(context!!)
        dialogBuilder.setView(editProfileBinding.root)
        dialogBuilder.setTitle(R.string.fragment_title_edit_profile)
        dialogBuilder.setPositiveButton(getString(R.string.save), null)
        dialogBuilder.setNegativeButton(getString(R.string.cancel)) { _, _ -> }

        return dialogBuilder.create()
    }

    /**
     * This method sets up the intent for the Image to be choosen, from gallery and camera.
     * The intentChooser merges up the two intents, and the activity is then called for both intents.
     * */
    private fun uploadProfileImage(){
        val gallery_intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        gallery_intent.setType("image/*")
        val camera_intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val intentChooser = Intent.createChooser(camera_intent, "Choose from : ")
        intentChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(gallery_intent))
        startActivityForResult(intentChooser, INTENT_REQUEST_CODE)
    }

    /**
     * This method checks the result of whether the user has granted permission or not.
     * */
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.size > 0) {
                uploadProfileImage()
            }
        } else {
            Toast.makeText(context, "Permission Denied!", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * This method takes the result from the activity called through intentChooser, i.e. takes the image chosen by user
     * and transforms it into the required type to upload the image on the ImageView 'imgUserAvatar'
     * */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == INTENT_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data!=null){
                val bundle = data?.extras
                if (bundle == null) {
                    editProfileBinding.imgUserAvatar.setImageURI(data?.data)
                } else {
                    editProfileBinding.imgUserAvatar.setImageBitmap(bundle.get("data") as Bitmap)
                }
            }else{
                Toast.makeText(context, "Please choose an image", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(context, "Could not upload image.\nTry again", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()

        val editProfileDialog = dialog as AlertDialog

        editProfileDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val errors = validateProfileInput(editProfileBinding.user?.name?.trim())

            with(editProfileBinding.tiName) {
                this.error = when (errors.firstOrNull()) {
                    is EditProfileFragmentErrorStates.EmptyNameError ->
                        context.getString(R.string.error_empty_name)
                    is EditProfileFragmentErrorStates.NameTooShortError -> {
                        val minLength = resources.getInteger(R.integer.min_name_length)
                        context.getString(R.string.error_name_too_short, minLength)
                    }
                    is EditProfileFragmentErrorStates.NameTooLongError -> {
                        val maxLength = resources.getInteger(R.integer.max_name_length)
                        context.getString(R.string.error_name_too_long, maxLength)
                    }
                    else -> {
                        this.isErrorEnabled = false
                        null
                    }
                }
            }
            if (currentUser != editProfileBinding.user && errors.isEmpty()) {
                profileViewModel.updateProfile(editProfileBinding.user!!)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        profileViewModel.successfulUpdate.removeObservers(activity!!)
        profileViewModel.successfulUpdate.value = null
    }

    private fun validateProfileInput(name: String?): Array<EditProfileFragmentErrorStates> {

        var errors = arrayOf<EditProfileFragmentErrorStates>()

        if (name.isNullOrEmpty()) errors += EditProfileFragmentErrorStates.EmptyNameError

        if (name?.length ?: 0 < resources.getInteger(R.integer.min_name_length)) {
            errors += EditProfileFragmentErrorStates.NameTooShortError
        }

        if (name?.length ?: 0 > resources.getInteger(R.integer.max_name_length)) {
            errors += EditProfileFragmentErrorStates.NameTooLongError
        }
        return errors
    }
}
