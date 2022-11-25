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
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Represents a batch job, which includes information such as job name (id),
 * number of job instances, and number of running job executions.
 *
 * @since 1.3.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"jobName", "numberOfJobInstances", "numberOfRunningJobExecutions"})
public final class JobEntity implements Serializable {
    private static final long serialVersionUID = -7252231657018200476L;

    @XmlElement
    private String jobName;

    @XmlElement
    private int numberOfJobInstances;

    @XmlElement
    private int numberOfRunningJobExecutions;

    public JobEntity() {
    }

    public JobEntity(final String jobName, final int numberOfJobInstances,
                     final int numberOfRunningJobExecutions) {
        this.jobName = jobName;
        this.numberOfJobInstances = numberOfJobInstances;
        this.numberOfRunningJobExecutions = numberOfRunningJobExecutions;
    }

    public String getJobName() {
        return jobName;
    }

    public int getNumberOfJobInstances() {
        return numberOfJobInstances;
    }

    public int getNumberOfRunningJobExecutions() {
        return numberOfRunningJobExecutions;
    }
}
