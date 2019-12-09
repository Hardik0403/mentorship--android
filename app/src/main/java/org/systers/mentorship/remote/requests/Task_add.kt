package org.systers.mentorship.remote.requests

import com.google.gson.annotations.SerializedName

/**
 * This data class represents all data necessary to send a mentorship relation request
 * @param mentorId represents mentor user id
 * @param menteeId represents mentee user id
 * @param notes represents a description of the mentorship relation
 * @param endDate represents end date of the mentorship relation
 */
data class Task_add (
        val description: String
)