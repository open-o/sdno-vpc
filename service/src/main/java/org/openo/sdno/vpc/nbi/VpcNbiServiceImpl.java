/*
 * Copyright 2016-2017 Huawei Technologies Co., Ltd.
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

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.model.common.enums.ActionStatus;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Vpc;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.vpc.nbi.inf.IVpcNbiService;
import org.openo.sdno.vpc.sbi.inf.IVpcSbiService;
import org.openo.sdno.vpc.util.DaoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * VPC service NBI implementation class.<br>
 *
 * @author
 * @version SDNO 0.5 2016-7-07
 */
public class VpcNbiServiceImpl implements IVpcNbiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpcNbiServiceImpl.class);

    @Resource
    private IVpcSbiService service;

    public void setService(IVpcSbiService service) {
        this.service = service;
    }

    @Override
    public Vpc create(Vpc vpc) throws ServiceException {
        LOGGER.debug("START");

        // Insert DB
        vpc.setStatus(ActionStatus.CREATING.getName());
        DaoUtils.insert(vpc);

        Vpc vpcRsp = null;
        try {
            vpcRsp = this.service.create(vpc.getOsControllerId(), vpc);
        } catch(ServiceException e) {
            LOGGER.error("Failed to create vpc " + vpc.getName());
            vpc.setStatus(ActionStatus.CREATE_EXCEPTION.getName());
            DaoUtils.update(vpc, "status");
            throw e;
        }

        vpc.setStatus(ActionStatus.NORMAL.getName());
        vpc.setExternalIp(vpcRsp.getExternalIp());
        DaoUtils.update(vpc, "status,externalIp");

        LOGGER.debug("END " + vpcRsp.getUuid() + " is created successfully");
        return vpcRsp;
    }

    @Override
    public void delete(String vpcId) throws ServiceException {
        LOGGER.debug("START");

        Vpc vpc = this.get(vpcId);
        if(null == vpc) {
            LOGGER.info("Vpc not exist.");
            return;
        }

        vpc.setStatus(ActionStatus.DELETING.getName());
        DaoUtils.update(vpc, "status");

        try {
            this.service.delete(vpc.getOsControllerId(), vpcId);
        } catch(ServiceException e) {
            LOGGER.error("Failed to delete vpc " + vpcId);
            vpc.setStatus(ActionStatus.DELETE_EXCEPTION.getName());
            DaoUtils.update(vpc, "status");
            throw e;
        }

        DaoUtils.delete(Vpc.class, vpcId);

        LOGGER.debug("END " + vpcId + " is deleted successfully");
    }

    @Override
    public Vpc get(String vpcId) throws ServiceException {

        ResultRsp<Vpc> vpcRsp = DaoUtils.get(Vpc.class, vpcId);
        if(!vpcRsp.isSuccess()) {
            LOGGER.error("Failed to query vpc " + vpcId);
            throw new ServiceException("Failed to query vpc " + vpcId);
        }
        return vpcRsp.getData();
    }

    @Override
    public List<Vpc> batchGet(Map<String, Object> filterMap) throws ServiceException {
        ResultRsp<List<Vpc>> queryResultRsp = DaoUtils.batchGet(Vpc.class, JsonUtil.toJson(filterMap));
        if(!queryResultRsp.isSuccess()) {
            LOGGER.error("Batch query vpcs failed");
            throw new ServiceException("Batch query vpcs failed");
        }

        return queryResultRsp.getData();
    }
}
