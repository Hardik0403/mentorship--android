package org.systers.mentorship.remote.services

import io.reactivex.Observable
import org.systers.mentorship.models.Task
import org.systers.mentorship.remote.requests.Task_add
import org.systers.mentorship.remote.responses.CustomResponse
import retrofit2.http.*

/**
 * This interface describes the methods related to Mentorship Task REST API
 */
interface TaskService {

    /**
     * This function gets all the tasks from a mentorship relation
     * @param relationId id of the mentorship relation in question
     * @return an observable instance of a list of [Task]s
     */
    @GET("mentorship_relation/{relation_id}/tasks")
    fun getAllTasksFromMentorshipRelation(@Path("relation_id") relationId: Int): Observable<List<Task>>

    @POST("mentorship_relation/{relation_id}/task")
    fun addTaskToMentorshipRelation(@Path("relation_id") relationId: Int,@Body description: Task_add): Observable<CustomResponse>

    @PUT("mentorship_relation/{relation_id}/task/{task_id}/complete")
    fun updateTaskCompleted(@Path("relation_id") relationId: Int, @Body task:Task_add, @Path("task_id") taskId: Int): Observable<CustomResponse>

}