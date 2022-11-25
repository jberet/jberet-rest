/*
 * Copyright (c) 2016-2017 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.jberet.rest.resource;

import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.jberet.schedule.JobSchedule;
import org.jberet.schedule.JobScheduler;

/**
 * REST resource class for batch job schedules.
 *
 * @since 1.3.0
 */
@Path("schedules")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class JobScheduleResource {
    /**
     * Gets all job schedules.
     *
     * @return all job schedules as array
     */
    @GET
    @Path("")
    public JobSchedule[] getJobSchedules() {
        final JobScheduler jobScheduler = JobScheduler.getJobScheduler();
        final List<JobSchedule> jobScheduleList = jobScheduler.getJobSchedules();
        return jobScheduleList.toArray(new JobSchedule[jobScheduleList.size()]);
    }

    /**
     * Cancels a job schedule.
     *
     * @param scheduleId the job schedule id to cancel
     * @return true if the job schedule is cancelled successfully; false otherwise
     */
    @POST
    @Path("{scheduleId}/cancel")
    public boolean cancel(final @PathParam("scheduleId") String scheduleId) {
        final JobScheduler jobScheduler = JobScheduler.getJobScheduler();
        return jobScheduler.cancel(scheduleId);
    }

    /**
     * Deletes a job schedule.
     *
     * @param scheduleId the job schedule id to delete
     *
     * @since 1.3.0.Beta7
     */
    @DELETE
    @Path("{scheduleId}")
    public void delete(final @PathParam("scheduleId") String scheduleId) {
        final JobScheduler jobScheduler = JobScheduler.getJobScheduler();
        jobScheduler.delete(scheduleId);
    }

    /**
     * Gets a job schedule by its id.
     *
     * @param scheduleId the job schedule id to get
     * @return the job schedule, and null if the job schedule is not found
     */
    @GET
    @Path("{scheduleId : .*\\d+.*}")
    public JobSchedule getJobSchedule(final @PathParam("scheduleId") String scheduleId) {
        final JobScheduler jobScheduler = JobScheduler.getJobScheduler();
        return jobScheduler.getJobSchedule(scheduleId);
    }

    /**
     * Gets all available timezone ids, and the first element of the result is the default timezone id.
     *
     * @return timezone ids as a string array
     */
    @GET
    @Path("timezones")
    public String[] getTimezoneIds() {
        final String[] availableIDs = TimeZone.getAvailableIDs();
        Arrays.sort(availableIDs);
        final int i = Arrays.binarySearch(availableIDs, TimeZone.getDefault().getID());
        final String[] result = new String[availableIDs.length];
        result[0] = availableIDs[i];
        System.arraycopy(availableIDs, 0, result, 1, i);
        System.arraycopy(availableIDs, i + 1, result, i + 1, availableIDs.length - (i + 1));

        return result;
    }

    /**
     * Gets the scheduling features supported by the current job scheduler.
     *
     * @return supported features as a string array
     */
    @GET
    @Path("features")
    public String[] getFeatures() {
        return JobScheduler.getJobScheduler().getFeatures();
    }
}

