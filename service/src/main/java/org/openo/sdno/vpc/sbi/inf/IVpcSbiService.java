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

package org.openo.sdno.vpc.sbi.inf;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.service.IService;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Vpc;

/**
 * VPC NBI service interface.<br>
 *
 * @param <T> Resource Class
 * @author
 * @version SDNO 0.5 21-07-2016
 */
public interface IVpcSbiService extends IService {

    /**
     * Creates VPC.
     * <br>
     *
     * @param controllerUuid Controller UUID
     * @param vpc VPC
     * @return
     * @throws ServiceException
     * @since SDNO 0.5
     */
    Vpc create(String controllerUuid, Vpc vpc) throws ServiceException;

    /**
     * Deletes VPC
     * <br>
     *
     * @param controllerUuid Controller UUID.
     * @param vpcId VPC id.
     * @throws ServiceException
     * @since SDNO 0.5
     */
    void delete(String controllerUuid, String vpcId) throws ServiceException;
}
