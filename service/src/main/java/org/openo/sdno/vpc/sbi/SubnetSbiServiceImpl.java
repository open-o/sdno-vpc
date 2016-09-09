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

package org.openo.sdno.vpc.sbi;

import java.text.MessageFormat;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Subnet;
import org.openo.sdno.rest.ResponseUtils;
import org.openo.sdno.vpc.config.Config;
import org.openo.sdno.vpc.sbi.inf.ISubnetSbiService;
import org.openo.sdno.vpc.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * VPC subnet service implementation class.
 * <br>
 * <p>
 * </p>
 *
 * @author
 * @version SDNO 0.5 2016-7-07
 */
public class SubnetSbiServiceImpl implements ISubnetSbiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubnetSbiServiceImpl.class);

    public static final String BASE_URI = "/rest/svc/drivers/dc-gateway/v1/{0}/subnets";

    private String getOsDriverVpcUrl() throws ServiceException {
        return Config.getOsDriverServiceUrl() + BASE_URI;
    }

    /**
     * Creates Subnet.
     * <br>
     *
     * @param controllerUuid Controller Uuid
     * @param subnet Subnet
     * @return
     * @throws ServiceException
     * @since SDNO 0.5
     */
    @Override
    public Subnet create(String controllerUuid, Subnet subnet) throws ServiceException {
        LOGGER.debug("START");
        String url = MessageFormat.format(this.getOsDriverVpcUrl(), controllerUuid);

        RestfulParametes restfulParametes = HttpUtils.formRestfulParams(subnet);
        RestfulResponse response = RestfulProxy.post(url, restfulParametes);
        // TODO(mrkanag) handle error

        String rspContent = ResponseUtils.transferResponse(response);
        Subnet subnetLocal = JsonUtil.fromJson(rspContent, Subnet.class);
        LOGGER.info("END " + subnetLocal.getUuid() + " is created successfully");
        // TODO(mrkanag) check for externalIp is set properly.
        return subnetLocal;
    }

    /**
     * Deletes Subnet.
     * <br>
     *
     * @param controllerUuid Controller Uuid
     * @param subnetId Subnet id.
     * @throws ServiceException
     * @since SDNO 0.5
     */
    @Override
    public void delete(String controllerUuid, String subnetId) throws ServiceException {
        LOGGER.debug("START");
        String url = MessageFormat.format(this.getOsDriverVpcUrl() + "/{1}", controllerUuid, subnetId);
        RestfulParametes restfulParametes = HttpUtils.formRestfulParams(null);
        RestfulProxy.delete(url, restfulParametes);

        // TODO(mrkanag) handle error
        LOGGER.info("END " + subnetId + " is deleted successfully");
    }
}
