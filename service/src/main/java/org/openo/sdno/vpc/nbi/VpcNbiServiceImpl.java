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
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Vpc;
import org.openo.sdno.vpc.nbi.inf.IVpcNbiService;
import org.openo.sdno.vpc.sbi.VpcSbiServiceImpl;
import org.openo.sdno.vpc.sbi.inf.IVpcSbiService;
import org.openo.sdno.vpc.util.DaoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * VPC service NBI implementation class.
 * <br>
 * <p>
 * </p>
 *
 * @author
 * @version SDNO 0.5 2016-7-07
 */
// TODO(mrkanag) kindly add interface :)
public class VpcNbiServiceImpl implements IVpcNbiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpcNbiServiceImpl.class);

    @Resource
    private IVpcSbiService service = new VpcSbiServiceImpl();

    public IVpcSbiService getService() {
        return this.service;
    }

    public void setService(IVpcSbiService service) {
        this.service = service;
    }

    /**
     * Creates VPC.
     * <br>
     *
     * @param vpc VPC
     * @return
     * @throws ServiceException
     * @since SDNO 0.5
     */
    @Override
    public Vpc create(Vpc vpc) throws ServiceException {
        LOGGER.debug("START");
        vpc.setStatus(ActionStatus.CREATING.getName());
        Vpc vpcLocal = DaoUtils.insert(vpc);
        // TODO(mrkanag) set the createdAt time.

        try {
            // TODO(mrkanag) make this async
            vpcLocal = this.service.create(vpcLocal.getOsControllerId(), vpcLocal);
        } catch(ServiceException e) {
            LOGGER.error("Failed to create vpc " + vpcLocal.getName());
            vpcLocal.setStatus(ActionStatus.CREATE_EXCEPTION.getName());
            DaoUtils.update(vpcLocal, "status");
            throw e;
        }

        vpcLocal.setStatus(ActionStatus.NORMAL.getName());
        vpcLocal = DaoUtils.update(vpcLocal, "status");

        LOGGER.debug("END " + vpcLocal.getUuid() + " is created successfully");

        return vpcLocal;
    }

    /**
     * Deletes VPC.
     * <br>
     *
     * @param vpcId VPC id.
     * @throws ServiceException
     * @since SDNO 0.5
     */
    @Override
    public void delete(String vpcId) throws ServiceException {
        LOGGER.debug("START");

        Vpc vpc = this.get(vpcId);
        vpc.setStatus(ActionStatus.DELETING.getName());
        vpc = DaoUtils.insert(vpc);
        try {
            // TODO(mrkanag) make this async
            this.service.delete(vpc.getOsControllerId(), vpcId);
        } catch(ServiceException e) {
            LOGGER.error("Failed to delete vpc " + vpcId);
            vpc.setStatus(ActionStatus.DELETE_EXCEPTION.getName());
            DaoUtils.update(vpc, "status");
            throw e;
        }

        // TODO(mrkanag) handle error
        LOGGER.debug("END " + vpcId + " is deleted successfully");
    }

    /**
     * Retrieves VPC.
     * <br>
     *
     * @param vpcId VPC id.
     * @return
     * @throws ServiceException
     * @since SDNO 0.5
     */
    @Override
    public Vpc get(String vpcId) throws ServiceException {
        return DaoUtils.get(Vpc.class, vpcId);
    }
}
