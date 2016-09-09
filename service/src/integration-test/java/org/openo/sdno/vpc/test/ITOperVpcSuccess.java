/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.vpc.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.testframework.checker.IChecker;
import org.openo.sdno.testframework.http.model.HttpModelUtils;
import org.openo.sdno.testframework.http.model.HttpRequest;
import org.openo.sdno.testframework.http.model.HttpResponse;
import org.openo.sdno.testframework.http.model.HttpRquestResponse;
import org.openo.sdno.testframework.replace.PathReplace;
import org.openo.sdno.testframework.testmanager.TestManager;
import org.openo.sdno.vpc.mocoserver.SbiAdapterSuccessServer;

public class ITOperVpcSuccess extends TestManager {

    private static final String CREATE_VPC_SUCCESS_TESTCASE =
            "src/integration-test/resources/testcase/createvpcsuccess.json";

    private static final String DELETE_VPC_SUCCESS_TESTCASE =
            "src/integration-test/resources/testcase/deletevpcsuccess.json";

    private static final String QUERY_VPC_SUCCESS_TESTCASE =
            "src/integration-test/resources/testcase/queryvpcsuccess.json";

    private static final String CREATE_SUBNET_SUCCESS_TESTCASE =
            "src/integration-test/resources/testcase/createsubnetsuccess.json";

    private static final String DELETE_SUBNET_SUCCESS_TESTCASE =
            "src/integration-test/resources/testcase/deletesubnetsuccess.json";

    private static final String QUERY_SUBNET_SUCCESS_TESTCASE =
            "src/integration-test/resources/testcase/querysubnetsuccess.json";

    private static SbiAdapterSuccessServer sbiAdapterServer = new SbiAdapterSuccessServer();

    @BeforeClass
    public static void setup() throws ServiceException {
        sbiAdapterServer.start();
    }

    @AfterClass
    public static void tearDown() throws ServiceException {
        sbiAdapterServer.stop();
    }

    private void checkVpcCreate() throws ServiceException {
        HttpRquestResponse httpCreateObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_VPC_SUCCESS_TESTCASE);
        HttpRequest createRequest = httpCreateObject.getRequest();
        execTestCase(createRequest, new SuccessChecker());
    }

    private void checkVpcGet() throws ServiceException {
        HttpRquestResponse queryHttpObject = HttpModelUtils.praseHttpRquestResponseFromFile(QUERY_VPC_SUCCESS_TESTCASE);
        HttpRequest queryRequest = queryHttpObject.getRequest();
        queryRequest.setUri(
                PathReplace.replaceUuid("vpc_id", queryRequest.getUri(), "9b00d6b0-6c93-4ca5-9747-b8ade7bb514h"));
        execTestCase(queryRequest, new SuccessChecker());
    }

    private void checkVpcDelete() throws ServiceException {
        HttpRquestResponse deleteHttpObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_VPC_SUCCESS_TESTCASE);
        HttpRequest deleteRequest = deleteHttpObject.getRequest();
        deleteRequest.setUri(
                PathReplace.replaceUuid("vpc_id", deleteRequest.getUri(), "9b00d6b0-6c93-4ca5-9747-b8ade7bb514h"));
        execTestCase(deleteRequest, new SuccessChecker());
    }

    private void checkSubnetCreate() throws ServiceException {
        HttpRquestResponse httpCreateObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_SUBNET_SUCCESS_TESTCASE);
        HttpRequest createRequest = httpCreateObject.getRequest();
        execTestCase(createRequest, new SuccessChecker());
    }

    private void checkSubnetGet() throws ServiceException {
        HttpRquestResponse queryHttpObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(QUERY_SUBNET_SUCCESS_TESTCASE);
        HttpRequest queryRequest = queryHttpObject.getRequest();
        queryRequest.setUri(
                PathReplace.replaceUuid("subnet_id", queryRequest.getUri(), "6fc00cf5-ff29-4f45-a8a6-f03d73674412"));
        execTestCase(queryRequest, new SuccessChecker());
    }

    private void checkSubnetDelete() throws ServiceException {
        HttpRquestResponse deleteHttpObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_SUBNET_SUCCESS_TESTCASE);
        HttpRequest deleteRequest = deleteHttpObject.getRequest();
        deleteRequest.setUri(
                PathReplace.replaceUuid("subnet_id", deleteRequest.getUri(), "6fc00cf5-ff29-4f45-a8a6-f03d73674412"));
        execTestCase(deleteRequest, new SuccessChecker());
    }

    @Test
    public void testCreateVpcSuccess() throws ServiceException {

        try {
            this.checkVpcCreate();
            this.checkVpcGet();
            this.checkSubnetCreate();
            this.checkSubnetGet();
            this.checkSubnetDelete();
            this.checkVpcDelete();
        } finally {

        }
    }

    private class SuccessChecker implements IChecker {

        @Override
        public boolean check(HttpResponse response) {
            if(response.getStatus() >= 200 && response.getStatus() <= 204) {
                return true;
            }
            return false;
        }
    }
}
