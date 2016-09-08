/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import org.openo.sdno.vpc.nbi.inf.ISubnetNbiService;
import org.openo.sdno.vpc.nbi.inf.IVpcNbiService;
import org.openo.sdno.vpc.sbi.SubnetSbiServiceImpl;
import org.openo.sdno.vpc.sbi.inf.ISubnetSbiService;
import org.openo.sdno.vpc.util.DaoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * VPC service NBI implementation class.
 * <br/>
 * <p>
 * </p>
 *
 * @author
 * @version SDNO 0.5 2016-7-07
 */
// TODO(mrkanag) kindly add interface :)
public class SubnetNbiServiceImpl implements ISubnetNbiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubnetNbiServiceImpl.class);

    @Resource
    private ISubnetSbiService service = new SubnetSbiServiceImpl();

    @Resource
    private IVpcNbiService vpcService = new VpcNbiServiceImpl();

    public ISubnetSbiService getService() {
        return this.service;
    }

    public void setService(ISubnetSbiService service) {
        this.service = service;
    }

    public IVpcNbiService getVpcService() {
        return this.vpcService;
    }

    public void setVpcService(IVpcNbiService vpcService) {
        this.vpcService = vpcService;
    }

    /**
     * Creates the subnet
     * <br/>
     *
     * @param subnet Subnet
     * @return
     * @throws ServiceException
     * @since SDNO 0.5
     */
    @Override
    public Subnet create(Subnet subnet) throws ServiceException {
        LOGGER.debug("START");

        // TODO(mrkanag) make sure subnet.getVpcId() already exist in db
        subnet.setStatus(ActionStatus.CREATING.getName());
        Subnet subnetLocal = DaoUtils.insert(subnet);

        Vpc vpc = this.vpcService.get(subnetLocal.getVpcId());
        // TODO(mrkanag) set the createdAt time.

        try {
            // TODO(mrkanag) make this async
            subnetLocal = this.service.create(vpc.getOsControllerId(), subnetLocal);
        } catch(ServiceException e) {
            LOGGER.error("Failed to create subnet " + subnetLocal.getCidr());
            subnetLocal.setStatus(ActionStatus.CREATE_EXCEPTION.getName());
            DaoUtils.update(subnet, "status");
            throw e;
        }

        subnetLocal.setStatus(ActionStatus.NORMAL.getName());
        subnetLocal = DaoUtils.update(subnetLocal, "status");

        LOGGER.debug("END " + subnetLocal.getUuid() + " is created successfully");

        return subnetLocal;
    }

    /**
     * Deletes subnet
     * <br/>
     *
     * @param subnetId Subnet Id
     * @throws ServiceException
     * @since SDNO 0.5
     */
    @Override
    public void delete(String subnetId) throws ServiceException {
        LOGGER.debug("START");

        Subnet subnet = this.get(subnetId);
        Vpc vpc = this.vpcService.get(subnet.getVpcId());

        subnet.setStatus(ActionStatus.DELETING.getName());
        subnet = DaoUtils.insert(subnet);
        try {
            // TODO(mrkanag) make this async
            this.service.delete(vpc.getOsControllerId(), subnetId);
        } catch(ServiceException e) {
            LOGGER.error("Failed to delete subnet " + subnetId);
            subnet.setStatus(ActionStatus.DELETE_EXCEPTION.getName());
            DaoUtils.update(subnet, "status");
            throw e;
        }

        // TODO(mrkanag) handle error
        LOGGER.debug("END " + subnetId + " is deleted successfully");
    }

    /**
     * Retrieves subnet.
     * <br/>
     *
     * @param subnetId Subnet id.
     * @return
     * @throws ServiceException
     * @since SDNO 0.5
     */
    @Override
    public Subnet get(String subnetId) throws ServiceException {
        return DaoUtils.get(Subnet.class, subnetId);
    }
}
