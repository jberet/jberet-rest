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
import jakarta.batch.runtime.StepExecution;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

/**
 * Represents a step execution, which includes fields such as step execution id,
 * step name, and those fields inherited from {@link AbstractExecutionEntity}
 * (start time, end time, batch status, and exit status).
 *
 * @see AbstractExecutionEntity
 *
 * @since 1.3.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder =
//        {"stepExecutionId", "stepName", "batchStatus", "exitStatus", "startTime", "endTime", "metrics"})
public class StepExecutionEntity extends AbstractExecutionEntity implements StepExecution, Serializable {
    private static final long serialVersionUID = -8528930845788535109L;

    @XmlElement
    private long stepExecutionId;

    @XmlElement
    private String stepName;

    //unused property, kept here to satisfy Jackson mapping.  Otherwise will get error:
    //UnrecognizedPropertyException: Unrecognized field "persistentUserData"
    //we don't want to annotate Jackson-specific annotations either (@JsonIgnoreProperties(ignoreUnknown = true)
    @XmlTransient
    private Serializable persistentUserData;

    @XmlElement
    private MetricEntity[] metrics;

    public StepExecutionEntity() {
    }

    public StepExecutionEntity(final StepExecution stepExe) {
        super(stepExe.getStartTime(), stepExe.getEndTime(), stepExe.getBatchStatus(), stepExe.getExitStatus());
        this.stepExecutionId = stepExe.getStepExecutionId();
        this.stepName = stepExe.getStepName();
        this.metrics = MetricEntity.copyOf(stepExe.getMetrics());
    }

    public long getStepExecutionId() {
        return stepExecutionId;
    }

    public void setStepExecutionId(final long stepExecutionId) {
        this.stepExecutionId = stepExecutionId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(final String stepName) {
        this.stepName = stepName;
    }

    public Serializable getPersistentUserData() {
        return null;
    }

    public MetricEntity[] getMetrics() {
        return metrics;
    }

    public void setMetrics(final MetricEntity[] metrics) {
        this.metrics = metrics;
    }
}
