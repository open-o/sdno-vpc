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

package org.openo.sdno.vpc.service.nbi;

import java.io.IOException;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Subnet;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Vpc;
import org.openo.sdno.rest.ResponseUtils;
import org.openo.sdno.vpc.service.utils.Utils;
import org.openo.sdno.vpc.util.HttpUtils;

public class Test {

    public static final String URL = "http://localhost:8080/VPC/rest/svc/vpc/v1";

    @SuppressWarnings("deprecation")
    public void verifyVPC() throws ServiceException, IOException {

        String vpcJson = Utils.getSampleJson(Test.class, "sample_vpc.json");
        Vpc vpc = JsonUtil.fromJson(vpcJson, Vpc.class);

        // Test POST
        RestfulParametes restfulParametes = HttpUtils.formRestfulParams(vpc);
        RestfulResponse response = RestfulProxy.post(URL + "/vpcs", restfulParametes);
        String rspContent = ResponseUtils.transferResponse(response);
        vpc = JsonUtil.fromJson(rspContent, Vpc.class);

        // Test GET
        response = RestfulProxy.get(URL + "/vpcs/" + vpc.getUuid(), HttpUtils.formRestfulParams(vpc));

        // Test POST
        String subnetJson = Utils.getSampleJson(Test.class, "sample_subnet.json");
        Subnet subnet = JsonUtil.fromJson(subnetJson, Subnet.class);
        subnet.setVpcId(vpc.getUuid());
        restfulParametes = HttpUtils.formRestfulParams(subnet);
        response = RestfulProxy.post(URL + "/subnets", restfulParametes);
        rspContent = ResponseUtils.transferResponse(response);

        subnet = JsonUtil.fromJson(rspContent, Subnet.class);

        // Test GET
        response = RestfulProxy.get(URL + "/subnets/" + subnet.getUuid(), HttpUtils.formRestfulParams(null));

        // Test DELETE
        response = RestfulProxy.delete(URL + "/subnets/" + subnet.getUuid(), HttpUtils.formRestfulParams(null));

        // Test DELETE
        response = RestfulProxy.get(URL + "/vpcs/" + vpc.getUuid(), HttpUtils.formRestfulParams(null));

    }

}
