/*
 * Copyright (c) 2014 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.jberet.rest._private;

import org.jboss.logging.BasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.annotations.Cause;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.ValidIdRange;

@MessageLogger(projectCode = "JBERET")
@ValidIdRange(min = 70500, max = 70999)
public interface RestAPILogger extends BasicLogger {
    RestAPILogger LOGGER = Logger.getMessageLogger(RestAPILogger.class, "org.jberet.rest-api");

    @Message(id = 70500, value = "Exception occurred when accessing JBeret Rest API:")
    @LogMessage(level = Logger.Level.WARN)
    void exceptionAccessingRestAPI(@Cause Throwable ex);

}
