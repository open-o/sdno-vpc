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

package org.openo.sdno.vpc.nbi;

import javax.annotation.Resource;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.model.common.enums.ActionStatus;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Subnet;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Vpc;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.vpc.nbi.inf.ISubnetNbiService;
import org.openo.sdno.vpc.nbi.inf.IVpcNbiService;
import org.openo.sdno.vpc.sbi.inf.ISubnetSbiService;
import org.openo.sdno.vpc.util.DaoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * VPC service NBI implementation class.
 * <br>
 *
 * @author
 * @version SDNO 0.5 2016-7-07
 */
public class SubnetNbiServiceImpl implements ISubnetNbiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubnetNbiServiceImpl.class);

    @Resource
    private ISubnetSbiService service;

    @Resource
    private IVpcNbiService vpcService;

    public void setService(ISubnetSbiService service) {
        this.service = service;
    }

    public void setVpcService(IVpcNbiService vpcService) {
        this.vpcService = vpcService;
    }

    @Override
    public Subnet create(Subnet subnet) throws ServiceException {

        // Insert DB
        subnet.setStatus(ActionStatus.CREATING.getName());
        DaoUtils.insert(subnet);

        // Get Vpc from DB
        Vpc vpc = this.vpcService.get(subnet.getVpcId());

        Subnet subnetRsp = null;
        try {
            subnetRsp = this.service.create(vpc.getOsControllerId(), subnet);
        } catch(ServiceException e) {
            LOGGER.error("Failed to create subnet " + subnet.getCidr());
            subnet.setStatus(ActionStatus.CREATE_EXCEPTION.getName());
            DaoUtils.update(subnet, "status");
            throw e;
        }

        subnet.setStatus(ActionStatus.NORMAL.getName());
        DaoUtils.update(subnet, "status");

        LOGGER.info("END " + subnetRsp.getUuid() + " is created successfully");
        return subnetRsp;
    }

    @Override
    public void delete(String subnetId) throws ServiceException {
        LOGGER.debug("START");

        Subnet subnet = this.get(subnetId);
        if(null == subnet) {
            LOGGER.info("Subnet not exist.");
            return;
        }

        Vpc vpc = this.vpcService.get(subnet.getVpcId());
        if(null == vpc) {
            LOGGER.info("Vpc not exist.");
            return;
        }

        subnet.setStatus(ActionStatus.DELETING.getName());
        DaoUtils.update(subnet, "status");

        try {
            // TODO(mrkanag) make this asynchronous
            this.service.delete(vpc.getOsControllerId(), subnetId);
        } catch(ServiceException e) {
            LOGGER.error("Failed to delete subnet " + subnetId);
            subnet.setStatus(ActionStatus.DELETE_EXCEPTION.getName());
            DaoUtils.update(subnet, "status");
            throw e;
        }

        DaoUtils.delete(Subnet.class, subnetId);

        LOGGER.debug("END " + subnetId + " is deleted successfully");
    }

    @Override
    public Subnet get(String subnetId) throws ServiceException {

        ResultRsp<Subnet> subnetRsp = DaoUtils.get(Subnet.class, subnetId);
        if(!subnetRsp.isSuccess()) {
            LOGGER.error("Failed to query subnet " + subnetId);
            throw new ServiceException("Failed to query subnet " + subnetId);
        }
        return subnetRsp.getData();
    }
}
