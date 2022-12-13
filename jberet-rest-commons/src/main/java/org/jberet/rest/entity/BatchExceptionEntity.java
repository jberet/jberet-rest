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
import jakarta.batch.operations.BatchRuntimeException;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import com.google.common.base.Throwables;

/**
 * Represents a batch exception, {@code BatchRuntimeException}, which includes
 * exception type (class), message, and stack trace text.
 *
 * @since 1.3.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"type", "message", "stackTrace"})
public final class BatchExceptionEntity implements Serializable {
    private static final long serialVersionUID = 810435611118287431L;

    @XmlElement
    private final Class<? extends BatchRuntimeException> type;

    @XmlElement
    private final String message;

    @XmlElement
    private final String stackTrace;

    public BatchExceptionEntity(final BatchRuntimeException ex) {
        type = ex.getClass();
        message = ex.getMessage();
        stackTrace = Throwables.getStackTraceAsString(ex);
    }

    public Class<? extends BatchRuntimeException> getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getStackTrace() {
        return stackTrace;
    }
}
