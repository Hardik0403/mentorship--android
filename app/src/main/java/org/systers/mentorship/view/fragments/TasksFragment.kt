package org.systers.mentorship.view.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_mentorship_tasks.*
import kotlinx.android.synthetic.main.fragment_mentorship_tasks.view.*
import kotlinx.android.synthetic.main.task_list_item.*
import org.systers.mentorship.MentorshipApplication
import org.systers.mentorship.R
import org.systers.mentorship.models.Relationship
import org.systers.mentorship.models.Task
import org.systers.mentorship.remote.requests.Task_add
import org.systers.mentorship.view.activities.MainActivity
import org.systers.mentorship.view.adapters.AchievementsAdapter
import org.systers.mentorship.view.adapters.TasksAdapter
import org.systers.mentorship.viewmodels.TasksViewModel
import java.util.*

@SuppressLint("ValidFragment")
/**
 * The fragment is responsible for showing the all mentorship tasks
 * and achievements. It also allows to add new tasks.
 */
class TasksFragment(private var mentorshipRelation: Relationship) : BaseFragment() {

    companion object {
        /**
         * Creates an instance of [TasksFragment]
         */
        fun newInstance(mentorshipRelation: Relationship) = TasksFragment(mentorshipRelation)
        val TAG = TasksFragment::class.java.simpleName
    }

    val appContext = MentorshipApplication.getContext()

    private lateinit var taskViewModel: TasksViewModel

    override fun getLayoutResourceId(): Int = R.layout.fragment_mentorship_tasks;

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        taskViewModel = ViewModelProviders.of(this).get(TasksViewModel::class.java)
        setObservables()

        fabAddItem.setOnClickListener {
            showDialog()
        }

        taskViewModel.getTasks(mentorshipRelation.id)
    }

    private fun setObservables() {
        /**
         * Setting up the taskViewModel observe for the getTasks functionality.
         * */
        taskViewModel.successfulGet.observe(this, Observer {
            successful ->
            if (successful != null) {
                if (successful) {
                    if (taskViewModel.tasksList.isEmpty()) {
                        tvNoTask.visibility = View.VISIBLE
                        rvTasks.visibility = View.GONE
                       // tvNoAchievements.visibility = View.VISIBLE
                       // rvAchievements.visibility = View.GONE
                    } else {
//                        var tasks:MutableList<Task> = mutableListOf()
//                        var achievements:MutableList<Task> = mutableListOf()
//                        for (t in taskViewModel.tasksList){
//                            if (t.isDone){
//                                achievements.add(t)
//                            }else{
//                                tasks.add(t)
//                            }
//                        }
                        rvTasks.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter = TasksAdapter(taskViewModel.tasksList, markTask)
                        }
//                        rvAchievements.apply {
//                            layoutManager = LinearLayoutManager(context)
//                            adapter = AchievementsAdapter(achievements)
//                        }
                        tvNoTask.visibility = View.GONE
                       // tvNoAchievements.visibility=View.GONE
                       // rvAchievements.visibility=View.VISIBLE
                    }
                } else {
                    view?.let {
                        Snackbar.make(it, taskViewModel.message, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        })

        /**
         * Setting up the taskViewModel observe for the addTask functionality.
         * */
        taskViewModel.successfulAdd.observe(this, Observer {
            successful ->
            if (successful != null) {
                if (successful) {
                    Toast.makeText(context, taskViewModel.message, Toast.LENGTH_LONG).show()
                } else {
                    view?.let {
                        Snackbar.make(it, taskViewModel.message, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        })

        /**
         * Setting up the taskViewModel observe for the completeTask functionality.
         * */
        taskViewModel.successfulComplete.observe(this, Observer {
            successful ->
            if (successful != null) {
                if (successful) {
                    Toast.makeText(context, taskViewModel.message, Toast.LENGTH_LONG).show()
                    Toast.makeText(context, "Updated", Toast.LENGTH_LONG).show()
                   // taskViewModel.getTasks(mentorshipRelation.id)
                } else {
                    view?.let {
                        Snackbar.make(it, taskViewModel.message, Snackbar.LENGTH_LONG).show()
                        //taskViewModel.getTasks(mentorshipRelation.id)
                    }
                }
            }
        })
    }

    /**
     * The function creates a dialog box through whoch new tasks can be added
     */
    fun showDialog() {
        val builder = AlertDialog.Builder(context)
        val inflater = layoutInflater
        builder.setTitle(appContext.getString(R.string.add_new_task))
        val dialogLayout = inflater.inflate(R.layout.dialog_add_task, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.etAddTask)
        builder.setView(dialogLayout)
        builder.setPositiveButton(appContext.getString(R.string.save)) { dialogInterface, i ->
            val description = Task_add(
                    description = editText.text.toString()
            )
            taskViewModel.addTask(mentorshipRelation.id,description)
            /**
             * Calling GET method to display the newly added tasks on the screen.
             * */
            taskViewModel.getTasks(mentorshipRelation.id)
        }
        builder.setNegativeButton(appContext.getString(R.string.cancel)) { dialogInterface, i ->
            dialogInterface.dismiss()
        }
        builder.show()
    }

    private val markTask: (Int) -> Unit = { taskId:Int ->

            taskViewModel.updateTask(mentorshipRelation.id,taskId)
    }
}