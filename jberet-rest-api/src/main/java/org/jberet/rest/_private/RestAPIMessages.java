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

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import org.jboss.logging.Messages;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageBundle;
import org.jboss.logging.annotations.ValidIdRange;

@MessageBundle(projectCode = "JBERET")
@ValidIdRange(min = 70000, max = 70499)
public interface RestAPIMessages {
    RestAPIMessages MESSAGES = Messages.getBundle(RestAPIMessages.class);

    @Message(id = 70000, value = "Missing request query parameters: %s")
    BadRequestException missingQueryParams(String params);

    @Message(id = 70001, value = "Invalid request query parameter value: %s = %s")
    BadRequestException invalidQueryParamValue(String paramKey, String paramValue);

    @Message(id = 70002, value = "The resource identified is not found: %s = %s")
    NotFoundException notFoundException(String key, String value);

}
