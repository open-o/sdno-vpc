/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.vpc.nbi;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Subnet;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Vpc;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Vpc.UnderlayResources;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.vpc.nbi.VpcNbiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/applicationContext.xml",
                "classpath*:META-INF/spring/service.xml", "classpath*:spring/service.xml"})

public class VpcNbiServiceImplTest {

    @Mocked
    HttpServletRequest request;

    @Mocked
    HttpServletResponse response;

    @Autowired
    VpcNbiServiceImpl vpcSvc;

    @Before
    public void setUp() throws Exception {
        new MockInventoryDao();
    }

    @Test(expected = ServiceException.class)
    public void testCreate() throws ServiceException {

        Vpc vpc = buildVpc();
        vpcSvc.create(vpc);
    }

    @Test(expected = ServiceException.class)
    public void testdelete() throws ServiceException {

        vpcSvc.delete("Vpc");
    }

    @Test
    public void testdeleteVpc() throws ServiceException {

        new MockUp<InventoryDao<T>>() {

            @Mock
            ResultRsp<T> query(Class clazz, String uuid, String tenantId) {
                if (Subnet.class.equals(clazz)) {
                    Subnet subnet = new Subnet();
                    subnet.setUuid("11111");
                    return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, null);
                } else if (Vpc.class.equals(clazz)) {
                    Vpc vpc = new Vpc();
                    return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, null);
                }

                return null;
            }
        };

        vpcSvc.delete("Vpc");
        assertTrue(true);
    }

    @Test(expected = ServiceException.class)
    public void testGet() throws ServiceException {

        new MockUp<MockInventoryDao<T>>() {

            @Mock
            ResultRsp<T> query(Class clazz, String uuid, String tenantId) {
                Vpc vpc = new Vpc();
                return new ResultRsp(ErrorCode.OVERLAYVPN_FAILED, vpc);
            }

        };

        vpcSvc.get("Vpc");
    }

    @Test(expected = ServiceException.class)
    public void testBatchGet() throws ServiceException {

        new MockUp<MockInventoryDao<T>>() {

            @Mock
            ResultRsp<List<T>> queryByFilter(Class clazz, String filter, String queryResultFields)
                    throws ServiceException {

                return new ResultRsp(ErrorCode.OVERLAYVPN_FAILED);
            }

        };
        Map<String, Object> map = new HashMap<>();
        vpcSvc.batchGet(map);
    }

    private final class MockInventoryDao<T> extends MockUp<InventoryDao<T>> {

        @Mock
        ResultRsp queryByFilter(Class clazz, String filter, String queryResultFields) throws ServiceException {

            return null;
        }

        @Mock
        ResultRsp<T> query(Class clazz, String uuid, String tenantId) {
            Vpc vpc = new Vpc();
            return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, vpc);
        }

        @Mock
        ResultRsp<String> batchDelete(Class clazz, List<String> uuids) throws ServiceException {
            return new ResultRsp<String>();
        }

        @Mock
        public ResultRsp update(Class clazz, List oriUpdateList, String updateFieldListStr) {
            return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS);
        }

        @Mock
        public ResultRsp<T> insert(T data) throws ServiceException {
            return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS);
        }

        @Mock
        public ResultRsp<List<T>> batchInsert(List<T> dataList) {
            return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS);
        }
    }

    private final class MockRestfulProxy extends MockUp<RestfulProxy> {

        @Mock
        RestfulResponse post(String uri, RestfulParametes restParametes) throws ServiceException {
            RestfulResponse response = new RestfulResponse();

            response.setStatus(HttpStatus.SC_OK);
            response.setResponseJson(JsonUtil.toJson(JsonUtil.fromJson(restParametes.getRawData(), Vpc.class)));

            return response;
        }

        @Mock
        RestfulResponse delete(String uri, RestfulParametes restParametes) throws ServiceException {
            RestfulResponse response = new RestfulResponse();

            ResultRsp<String> sbiRsp = new ResultRsp<String>(ErrorCode.OVERLAYVPN_SUCCESS);
            response.setStatus(HttpStatus.SC_OK);
            response.setResponseJson(JsonUtil.toJson(sbiRsp));

            return response;
        }

    }

    private Vpc buildVpc() {
        Vpc vpc = new Vpc();

        vpc.setUuid("vpcid");
        vpc.setName("default/project4/r4");
        vpc.setExternalIp("1.1.1.1");
        vpc.setOsControllerId("controllerid");

        UnderlayResources attributes = new UnderlayResources();
        attributes.setParentId("vpcid");
        attributes.setProjectId("projectid");
        attributes.setRouterId("routerid");
        attributes.setUuid("attributesid");
        vpc.setAttributes(attributes);

        return vpc;
    }

}
