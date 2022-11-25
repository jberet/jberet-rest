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
import java.util.List;
import jakarta.batch.runtime.JobExecution;
import jakarta.batch.runtime.JobInstance;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Represents a job instance, which includes job instance id, job name (id),
 * number of job executions, and latest job execution id.
 *
 * @since 1.3.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"instanceId", "jobName", "numberOfJobExecutions", "latestJobExecutionId"})
public final class JobInstanceEntity implements JobInstance, Serializable {
    private static final long serialVersionUID = 2427272964201557394L;

    @XmlElement
    private long instanceId;

    @XmlElement
    private String jobName;

    @XmlElement
    private int numberOfJobExecutions;

    @XmlElement
    private long latestJobExecutionId;

    public JobInstanceEntity() {
    }

    public JobInstanceEntity(final JobInstance jobInstance, final List<JobExecution> jobExecutions) {
        this.instanceId = jobInstance.getInstanceId();
        this.jobName = jobInstance.getJobName();
        this.numberOfJobExecutions = jobExecutions.size();
        if (this.numberOfJobExecutions > 0) {
            this.latestJobExecutionId = jobExecutions.get(this.numberOfJobExecutions - 1).getExecutionId();
        }
    }

    public long getInstanceId() {
        return instanceId;
    }

    public String getJobName() {
        return jobName;
    }

    public int getNumberOfJobExecutions() {
        return numberOfJobExecutions;
    }

    public long getLatestJobExecutionId() {
        return latestJobExecutionId;
    }
}
