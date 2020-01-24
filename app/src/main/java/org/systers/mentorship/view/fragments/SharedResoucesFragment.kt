package org.systers.mentorship.view.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.systers.mentorship.MentorshipApplication

import org.systers.mentorship.R
import org.systers.mentorship.models.Relationship
import org.systers.mentorship.viewmodels.TasksViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
@SuppressLint("ValidFragment")
class SharedResoucesFragment (private var mentorshipRelation: Relationship) : BaseFragment() {

    companion object {
        /**
         * Creates an instance of [TasksFragment]
         */
        fun newInstance(mentorshipRelation: Relationship) = SharedResoucesFragment(mentorshipRelation)
        val TAG = SharedResoucesFragment::class.java.simpleName
    }

    override fun getLayoutResourceId(): Int = R.layout.fragment_shared_resouces

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shared_resouces, container, false)
    }


}
