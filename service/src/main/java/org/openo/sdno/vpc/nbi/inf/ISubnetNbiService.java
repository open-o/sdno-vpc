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

package org.openo.sdno.vpc.nbi.inf;

import java.util.List;
import java.util.Map;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.service.IService;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Subnet;

/**
 * VPC Subnet NBI service interface.<br>
 *
 * @param <T> Resource Class
 * @author
 * @version SDNO 0.5 21-07-2016
 */
public interface ISubnetNbiService extends IService {

    /**
     * Creates the subnet
     * <br>
     *
     * @param subnet Subnet
     * @return
     * @throws ServiceException
     * @since SDNO 0.5
     */
    Subnet create(Subnet subnet) throws ServiceException;

    /**
     * Deletes subnet
     * <br>
     *
     * @param subnetId Subnet Id
     * @throws ServiceException
     * @since SDNO 0.5
     */
    void delete(String subnetId) throws ServiceException;

    /**
     * Retrieves subnet.
     * <br>
     *
     * @param subnetId Subnet id.
     * @return Subnet object
     * @throws ServiceException
     * @since SDNO 0.5
     */
    Subnet get(String subnetId) throws ServiceException;

    /**
     * Batch Retrieves subnets.
     * <br>
     *
     * @param filterMap filter condition map
     * @return List of subnets queried out
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    List<Subnet> batchGet(Map<String, Object> filterMap) throws ServiceException;
}
