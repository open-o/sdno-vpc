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

package org.openo.sdno.vpc.service.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Subnet.UnderlayResources;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Vpc;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/applicationContext.xml",
                "classpath*:META-INF/spring/service.xml", "classpath*:spring/service.xml"})
public class SubnetServiceROAResourceTest {

    @Mocked
    HttpServletRequest request;

    @Mocked
    HttpServletResponse response;

    @Autowired
    SubnetServiceROAResource subnetSvc;

    @Before
    public void setUp() throws Exception {
        new MockInventoryDao();
        new MockRestfulProxy();
    }

    @Test
    public void testCreateSuccess() throws ServiceException {
        Subnet subnet = buildSubnet();

        try {
            Subnet resp = subnetSvc.create(request, response, subnet);
            assertEquals(subnet.getUuid(), resp.getUuid());
            assertTrue(true);
        } catch(ServiceException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testQuerySuccess() throws ServiceException {

        try {
            Subnet resp = subnetSvc.query(request, response, "11111");
            assertEquals("11111", resp.getUuid());
            assertTrue(true);
        } catch(ServiceException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testBatchQuerySuccess() throws ServiceException {

        new MockUp<InventoryDao<T>>() {

            @Mock
            ResultRsp queryByFilter(Class clazz, String filter, String queryResultFields) throws ServiceException {

                List<Subnet> subNetList = new ArrayList<>();
                Subnet subnet = new Subnet();
                subnet.setVpcId("vpcId");
                subnet.setName("name");
                subNetList.add(subnet);

                ResultRsp<List<Subnet>> resp = new ResultRsp<List<Subnet>>(ErrorCode.OVERLAYVPN_SUCCESS, subNetList);
                return resp;
            }

        };

        List<Subnet> resp = subnetSvc.batchQuery(request, response, "vpcId", "name");
        assertEquals(resp.get(0).getName(), "name");
        assertEquals(resp.get(0).getVpcId(), "vpcId");
    }

    @Test
    public void testDeleteSuccess() throws ServiceException {

        try {
            subnetSvc.delete(request, response, "11111");
            assertTrue(true);
        } catch(ServiceException e) {
            assertTrue(false);
        }
    }

    private final class MockInventoryDao<T> extends MockUp<InventoryDao<T>> {

        @Mock
        ResultRsp queryByFilter(Class clazz, String filter, String queryResultFields) throws ServiceException {

            return null;
        }

        @Mock
        ResultRsp<T> query(Class clazz, String uuid, String tenantId) {
            if(Subnet.class.equals(clazz)) {
                Subnet subnet = new Subnet();
                subnet.setUuid("11111");
                return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, subnet);
            } else if(Vpc.class.equals(clazz)) {
                Vpc vpc = new Vpc();
                return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, vpc);
            }

            return null;
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
            response.setResponseJson(JsonUtil.toJson(JsonUtil.fromJson(restParametes.getRawData(), Subnet.class)));

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

    private Subnet buildSubnet() {
        Subnet subnet = new Subnet();

        subnet.setUuid("subnetid");
        subnet.setName("default/project4");
        subnet.setCidr("10.10.0.0/24");
        subnet.setVni(25);
        subnet.setVpcId("vpcid");
        subnet.setGatewayIp("2.2.2.2");

        UnderlayResources attributes = new UnderlayResources();
        attributes.setParentId("subnetid");
        attributes.setSubnetId("subnetid");
        attributes.setNetworkId("networkid");
        attributes.setUuid("attributesid");
        subnet.setAttributes(attributes);

        return subnet;
    }
}
