/*
 * Copyright (c) 2018 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.jberet.rest.commons._private;

import jakarta.batch.operations.BatchRuntimeException;

import org.jboss.logging.Messages;
import org.jboss.logging.annotations.Cause;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageBundle;
import org.jboss.logging.annotations.ValidIdRange;

@MessageBundle(projectCode = "JBERET")
@ValidIdRange(min = 71000, max = 71999)
public interface RestCommonsMessages {
    RestCommonsMessages MESSAGES = Messages.getBundle(RestCommonsMessages.class);

    @Message(id = 71000, value = "Expecting JSON element '%s' within '%s'")
    IllegalStateException expectingJsonElement(String name, String... parents);

    @Message(id = 71001, value = "Failed to read batch job definition.")
    BatchRuntimeException failToReadJobDefinition(@Cause Throwable cause);

}
