/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.ts.tests.jaxrs.platform.container.asyncresponse;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.container.Suspended;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Path("resource")
public class Resource {

    public static final String RESUMED = "Response resumed";

    public static final String ISE = "Illegal State Exception Thrown";

    public static final String NOE = "No Exception Thrown";

    public static final String FALSE = "A method returned false";

    public static final String TRUE = "A method return true";

    //
    private static final AsyncResponseBlockingQueue[] stage = { new AsyncResponseBlockingQueue(1), new AsyncResponseBlockingQueue(1),
            new AsyncResponseBlockingQueue(1) };

    @GET
    @Path("suspend")
    public void suspend(@Suspended AsyncResponse asyncResponse) {
        stage[0].add(asyncResponse);
    }

    @GET
    @Path("clear")
    public void clear() {
        for (AsyncResponseBlockingQueue element : stage) {
            element.clear();
        }
    }

    @GET
    @Path("cancelvoid")
    public String cancel(@QueryParam("stage") String stage) {
        AsyncResponse response = takeAsyncResponse(stage);
        boolean ret = response.cancel();
        // Invoking a cancel(...) method multiple times to cancel request
        // processing has the same effect as canceling the request processing
        // only once.
        ret &= response.cancel();
        addResponse(response, stage);
        return ret ? TRUE : FALSE;
    }

    @POST
    @Path("cancelretry")
    public String cancelretry(@QueryParam("stage") String stage, String sRetryAfter) {
        AsyncResponse response = takeAsyncResponse(stage);
        int retryAfter = Integer.parseInt(sRetryAfter);
        boolean b = response.cancel(retryAfter);
        // Invoking a cancel(...) method multiple times to cancel request
        // processing has the same effect as canceling the request processing
        // only once.
        b &= response.cancel(retryAfter * 2);
        addResponse(response, stage);
        return b ? TRUE : FALSE;
    }

    @POST
    @Path("canceldate")
    public String cancelDate(@QueryParam("stage") String stage, String sRetryAfter) {
        AsyncResponse response = takeAsyncResponse(stage);
        long retryAfter = Long.parseLong(sRetryAfter);
        boolean b = response.cancel(new Date(retryAfter));
        // Invoking a cancel(...) method multiple times to cancel request
        // processing has the same effect as canceling the request processing
        // only once.
        b &= response.cancel(new Date(retryAfter + 20000));
        addResponse(response, stage);
        return b ? TRUE : FALSE;
    }

    @GET
    @Path("iscanceled")
    public String isCanceled(@QueryParam("stage") String stage) {
        AsyncResponse response = takeAsyncResponse(stage);
        boolean is = response.isCancelled();
        addResponse(response, stage);
        return is ? TRUE : FALSE;
    }

    @GET
    @Path("isdone")
    public String isDone(@QueryParam("stage") String stage) {
        AsyncResponse response = takeAsyncResponse(stage);
        boolean is = response.isDone();
        addResponse(response, stage);
        return is ? TRUE : FALSE;
    }

    @GET
    @Path("issuspended")
    public String isSuspended(@QueryParam("stage") String stage) {
        AsyncResponse response = takeAsyncResponse(stage);
        boolean is = response.isSuspended();
        addResponse(response, stage);
        return is ? TRUE : FALSE;
    }

    @POST
    @Path("resume")
    public String resume(@QueryParam("stage") String stage, String response) {
        AsyncResponse async = takeAsyncResponse(stage);
        boolean b = resume(async, response);
        addResponse(async, stage);
        return b ? TRUE : FALSE;
    }

    @GET
    @Path("resumechecked")
    public String resumeWithCheckedException(@QueryParam("stage") String stage) {
        AsyncResponse async = takeAsyncResponse(stage);
        boolean b = async.resume(new IOException(RESUMED));
        addResponse(async, stage);
        return b ? TRUE : FALSE;
    }

    @GET
    @Path("resumeruntime")
    public String resumeWithRuntimeException(@QueryParam("stage") String stage) {
        AsyncResponse async = takeAsyncResponse(stage);
        boolean b = async.resume(new RuntimeException(RESUMED));
        addResponse(async, stage);
        return b ? TRUE : FALSE;
    }

    @POST
    @Path("settimeout")
    public void setTimeOut(@QueryParam("stage") String stage, String milis) {
        AsyncResponse async = takeAsyncResponse(stage);
        async.setTimeout(Long.parseLong(milis), TimeUnit.MILLISECONDS);
        addResponse(async, stage);
    }

    @POST
    @Path("timeouthandler")
    public void setTimeoutHandler(@QueryParam("stage") String stage, int handlerValue) {
        TckTimeoutHandler handler = new TckTimeoutHandler(handlerValue);
        AsyncResponse async = takeAsyncResponse(stage);
        async.setTimeoutHandler(handler);
        async.setTimeout(200L, TimeUnit.MILLISECONDS);
        addResponse(async, stage);
    }

    // ////////////////////////////////////////////////////////////////////////

    protected static AsyncResponse takeAsyncResponse(String stageId) {
        return takeAsyncResponse(Integer.parseInt(stageId));
    }

    protected static AsyncResponse takeAsyncResponse(int stageId) {
        final ResponseBuilder error = createErrorResponseBuilder();
        AsyncResponse asyncResponse = null;
        try {
            asyncResponse = stage[stageId].take();
        } catch (InterruptedException e) {
            throw new WebApplicationException(error.entity("ArrayBlockingQueue#take").build());
        }
        return asyncResponse;
    }

    protected static final void addResponse(AsyncResponse response, String stageId) {
        int id = Integer.parseInt(stageId) + 1;
        if (id != stage.length) {
            stage[id].add(response);
        }
    }

    protected static boolean resume(AsyncResponse takenResponse, Object response) {
        return takenResponse.resume(response);
    }

    protected static ResponseBuilder createErrorResponseBuilder() {
        return Response.status(Status.EXPECTATION_FAILED);
    }
}
