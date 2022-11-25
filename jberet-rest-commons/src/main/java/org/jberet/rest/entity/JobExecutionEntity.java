/*
 * Copyright (c) 2015 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.jberet.rest.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import jakarta.batch.runtime.JobExecution;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

/**
 * Represents a job execution, which includes fields such as its href,
 * job execution id, job name (id), create time, last update time,
 * job parameters, job instance id, and those fields inherited from
 * {@link AbstractExecutionEntity} (start time, end time, batch status, and exit status).
 *
 * @see AbstractExecutionEntity
 *
 * @since 1.3.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder =
//        {"executionId", "href", "jobName", "jobInstanceId", "jobParameters", "batchStatus", "exitStatus", "createTime", "startTime", "lastUpdatedTime", "endTime"})
public final class JobExecutionEntity extends AbstractExecutionEntity implements JobExecution, Serializable {
    private static final long serialVersionUID = -8566764098276314827L;

    @XmlElement
    private long executionId;

    @XmlElement
    private String href;

    @XmlElement
    private Date createTime;

    @XmlElement
    private Date lastUpdatedTime;

    @XmlElement
    private Properties jobParameters;

    @XmlElement
    private String jobName;

    @XmlElement
    private long jobInstanceId;

    @XmlTransient
    private List<StepExecutionEntity> stepExecutions = new ArrayList<StepExecutionEntity>();

    public JobExecutionEntity() {
    }

    public JobExecutionEntity(final JobExecution jobExecution, final long jobInstanceId) {
        super(jobExecution.getStartTime(), jobExecution.getEndTime(),
                jobExecution.getBatchStatus(), jobExecution.getExitStatus());
        this.executionId = jobExecution.getExecutionId();
        this.createTime = jobExecution.getCreateTime();
        this.lastUpdatedTime = jobExecution.getLastUpdatedTime();
        this.jobName = jobExecution.getJobName();
        this.jobParameters = jobExecution.getJobParameters();
        this.jobInstanceId = jobInstanceId;
    }

    public static JobExecutionEntity[] fromJobExecutions(final List<JobExecution> jobExecutions, final long instanceId) {
        final int len = jobExecutions.size();
        JobExecutionEntity[] result = new JobExecutionEntity[len];

        for (int i = len - 1; i >= 0; i--) {
            result[len - 1 - i] = new JobExecutionEntity(jobExecutions.get(i), instanceId);
        }
        return result;
    }

    public long getExecutionId() {
        return executionId;
    }

    public void setExecutionId(long executionId) {
        this.executionId = executionId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public Properties getJobParameters() {
        return jobParameters;
    }

    public void setJobParameters(Properties jobParameters) {
        this.jobParameters = jobParameters;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public List<StepExecutionEntity> getStepExecutions() {
        return stepExecutions;
    }

    public void setStepExecutions(List<StepExecutionEntity> stepExecutions) {
        this.stepExecutions = stepExecutions;
    }

    public String getHref() {
        return href;
    }

    public void setHref(final String href) {
        this.href = href;
    }

    public long getJobInstanceId() {
        return jobInstanceId;
    }

    public void setJobInstanceId(final long jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
    }
}
