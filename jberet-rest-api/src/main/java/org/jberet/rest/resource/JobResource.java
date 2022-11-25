/*
 * Copyright (c) 2015-2018 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.jberet.rest.resource;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import org.jberet.rest._private.RestAPIMessages;
import org.jberet.rest.entity.JobEntity;
import org.jberet.rest.entity.JobExecutionEntity;
import org.jberet.rest.entity.JobInstanceEntity;
import org.jberet.rest.service.JobService;
import org.jberet.schedule.JobSchedule;
import org.jberet.schedule.JobScheduleConfig;
import org.jberet.schedule.JobScheduler;

/**
 * REST resource class for batch job. This class supports job-related operations
 * such as starting by job XML name, restarting the latest job execution of a
 * job name (id), and listing currently known jobs.
 *
 * @since 1.3.0
 */
@Path("jobs")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class JobResource {
    /**
     * Starts a new job execution for the specified {@code jobXmlName}.
     * Job parameters can be taken from query parameters, obtained from {@code uriInfo},
     * or {@code jobParamsAsProps} as {@code java.util.Properties}, or both.
     * When extracting query parameters from {@code uriInfo}, only the first value of
     * each key is used. When a key exists in both query parameters and {@code props},
     * the latter takes precedence.
     *
     * @param jobXmlName job xml name, which usually is the same as job id
     * @param uriInfo {@code javax.ws.rs.core.UriInfo} that contains query parameters and other info
     * @param jobParamsAsProps job parameters properties
     *
     * @return {@code javax.ws.rs.core.Response}, which includes response status and newly
     * started job execution of type {@link JobExecutionEntity}
     */
    @Path("{jobXmlName}/start")
    @POST
    public Response start(final @PathParam("jobXmlName") String jobXmlName,
                          final @Context UriInfo uriInfo,
                          final Properties jobParamsAsProps) {
        JobExecutionEntity jobExecutionData = JobService.getInstance()
                .start(jobXmlName, jobParametersFromUriInfoAndProps(uriInfo, jobParamsAsProps));
        final URI jobExecutionDataUri = uriInfo.getBaseUriBuilder().path(JobExecutionResource.class).
                path(String.valueOf(jobExecutionData.getExecutionId())).
                build();
        jobExecutionData.setHref(jobExecutionDataUri.toString());
        return Response.created(jobExecutionDataUri).entity(jobExecutionData).build();
    }

    /**
     * Starts a new job execution for the submitted job definition content.
     * Job parameters are taken from query parameters, obtained from {@code uriInfo}.
     * When extracting query parameters from {@code uriInfo}, only the first value of
     * each key is used.
     *
     * @param uriInfo {@code javax.ws.rs.core.UriInfo} that contains query parameters and other info
     * @param jobDefinition the job definition content
     *
     * @return {@code javax.ws.rs.core.Response}, which includes response status and newly
     * started job execution of type {@link JobExecutionEntity}
     *
     * @since 1.3.0.Final
     */
    @Path("submit")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response submit(final @Context UriInfo uriInfo,
                           final String jobDefinition) {
        JobExecutionEntity jobExecutionData = JobService.getInstance()
                .submit(jobDefinition, jobParametersFromUriInfoAndProps(uriInfo, null));
        final URI jobExecutionDataUri = uriInfo.getBaseUriBuilder().path(JobExecutionResource.class).
                path(String.valueOf(jobExecutionData.getExecutionId())).
                build();
        jobExecutionData.setHref(jobExecutionDataUri.toString());
        return Response.created(jobExecutionDataUri).entity(jobExecutionData).build();
    }

    /**
     * Schedules an execution of the job indicated by {@code jobXmlName}, with the specified
     * job schedule configuration.
     *
     * @param jobXmlName the name of the job to schedule execution
     * @param scheduleConfig the job schedule configuration
     * @return a {@code org.jberet.schedule.JobSchedule} instance
     */
    @Path("{jobXmlName}/schedule")
    @POST
    public JobSchedule schedule(final @PathParam("jobXmlName") String jobXmlName, final JobScheduleConfig scheduleConfig) {
        final JobScheduler jobScheduler = JobScheduler.getJobScheduler();
        if (scheduleConfig.getJobName() == null) {
            scheduleConfig.setJobName(jobXmlName);
        }
        return jobScheduler.schedule(scheduleConfig);
    }

    /**
     * Restarts the most recent job execution of a job name/id, with optional restart
     * job parameters.
     * <p>
     * Job parameters can be taken from query parameters, obtained from {@code uriInfo},
     * or {@code jobParamsAsProps} as {@code java.util.Properties}, or both.
     * When extracting query parameters from {@code uriInfo}, only the first value of
     * each key is used. When a key exists in both query parameters and {@code props},
     * the latter takes precedence.
     * <p>
     * Job parameters in the previous job execution that is to be restarted will continue
     * to be used in the restart job execution. Job parameters (as query parameters or
     * {@code java.util.Properties}) in the current invocation will complement and
     * override any same-keyed job parameters.
     *
     * @param jobXmlName job name/id, whose most recent job execution will be restarted
     * @param uriInfo {@code javax.ws.rs.core.UriInfo} that contains additional query parameters and other info
     * @param jobParamsAsProps additional job parameters properties
     *
     * @return {@code org.jberet.rest.entity.JobExecutionEntity} for the new job execution
     *
     * @see #start(String, UriInfo, Properties)
     * @see JobExecutionResource#restart(long, UriInfo, Properties)
     */
    @Path("{jobXmlName}/restart")
    @POST
    public JobExecutionEntity restart(final @PathParam("jobXmlName") String jobXmlName,
                          final @Context UriInfo uriInfo,
                          final Properties jobParamsAsProps) {
        final JobInstanceEntity[] jobInstances = JobService.getInstance().getJobInstances(jobXmlName, 0, 1);
        if (jobInstances.length > 0) {
            final long latestJobExecutionId = jobInstances[0].getLatestJobExecutionId();
            final JobExecutionEntity jobExecutionEntity = JobService.getInstance().restart(
                    latestJobExecutionId, JobResource.jobParametersFromUriInfoAndProps(uriInfo, jobParamsAsProps));
            JobExecutionResource.setJobExecutionEntityHref(uriInfo, jobExecutionEntity);
            return jobExecutionEntity;
        } else {
            throw RestAPIMessages.MESSAGES.invalidQueryParamValue("jobXmlName", jobXmlName);
        }
    }

    /**
     * Gets all jobs known to the current batch runtime.
     * Note that historical jobs that are not currently loaded in the batch runtime
     * will not be included in the result.
     *
     * @return array of {@code org.jberet.rest.entity.JobEntity} known to the batch runtime
     */
    @GET
    public JobEntity[] getJobs() {
        return JobService.getInstance().getJobs();
    }

    /**
     * Combines the properties from query parameters in {@code uriInfo} with the
     * {@code java.util.Properties} object {@code props}. When extracting
     * query parameters from {@code uriInfo}, only the first value of each key is
     * used. When a key exists in both query parameters and {@code props}, the latter
     * takes precedence.
     *
     * @param uriInfo the {@code javax.ws.rs.core.UriInfo} to extract query parameters
     * @param props the {@code java.util.Properties} object
     * @return a combined {@code java.util.Properties}
     */
    static Properties jobParametersFromUriInfoAndProps(final UriInfo uriInfo, final Properties props) {
        final MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters(true);
        if (queryParameters.isEmpty()) {
            return props;
        }

        final Properties p = new Properties();
        for (final Map.Entry<String, List<String>> e : queryParameters.entrySet()) {
            p.setProperty(e.getKey(), e.getValue().get(0));
        }
        if (props != null) {
            for (final String k : props.stringPropertyNames()) {
                p.setProperty(k, props.getProperty(k));
            }
        }
        return p;
    }

}
