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
import org.openo.sdno.rest.ResponseUtils;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Vpc;
import org.openo.sdno.vpc.sbi.inf.IVpcSbiService;
import org.openo.sdno.vpc.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * VPC service implementation class.
 * <br>
 *
 * @author
 * @version SDNO 0.5 2016-7-07
 */

public class VpcSbiServiceImpl implements IVpcSbiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpcSbiServiceImpl.class);

    private static final String BASE_URI = "/openoapi/sbi-vpc/v1/vpcs";

    /**
     * Creates VPC.
     * <br>
     *
     * @param controllerUuid Controller uuid.
     * @param vpc VPC
     * @return
     * @throws ServiceException
     * @since SDNO 0.5
     */
    @Override
    public Vpc create(String controllerUuid, Vpc vpc) throws ServiceException {
        LOGGER.debug("START");
        String url = BASE_URI;

        RestfulParametes restfulParametes = HttpUtils.formRestfulParams(vpc);
        restfulParametes.putHttpContextHeader("X-Driver-Parameter", "extSysID=" + controllerUuid);
        RestfulResponse response = RestfulProxy.post(url, restfulParametes);
        String rspContent = ResponseUtils.transferResponse(response);
        Vpc vpcLocal = JsonUtil.fromJson(rspContent, Vpc.class);
        LOGGER.debug("END " + vpcLocal.getUuid() + " is created successfully");
        return vpcLocal;
    }

    /**
     * Deletes VPC
     * <br>
     *
     * @param controllerUuid Cotroller Uuid.
     * @param vpcId VPC id.
     * @throws ServiceException
     * @since SDNO 0.5
     */
    @Override
    public void delete(String controllerUuid, String vpcId) throws ServiceException {
        LOGGER.debug("START");
        String url = MessageFormat.format(BASE_URI + "/{0}", vpcId);
        RestfulParametes restfulParametes = HttpUtils.formRestfulParams(null);
        restfulParametes.putHttpContextHeader("X-Driver-Parameter", "extSysID=" + controllerUuid);
        RestfulResponse response = RestfulProxy.delete(url, restfulParametes);
        ResponseUtils.checkResonseAndThrowException(response);
        LOGGER.debug("END " + vpcId + " is deleted successfully");
    }
}
