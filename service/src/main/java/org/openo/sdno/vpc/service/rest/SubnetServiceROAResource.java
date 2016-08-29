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

package org.openo.sdno.vpc.service.rest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.vpc.model.Subnet;
import org.openo.sdno.vpc.nbi.inf.ISubnetNbiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * VPC Subnet service class for ROA.<br/>
 *
 * @author
 * @version SDNO 0.5 2016-7-07
 */
@Service
@Path("/sdnovpc/v1")
public class SubnetServiceROAResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubnetServiceROAResource.class);

    @Resource
    ISubnetNbiService service;

    public ISubnetNbiService getService() {
        return this.service;
    }

    public void setService(ISubnetNbiService service) {
        this.service = service;
    }

    /**
     * Rest interface to perform create subnet operation. <br/>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param subnet Subnet Object
     * @return Subnet Object created
     * @throws ServiceException When create subnet failed
     * @since SDNO 0.5
     */
    @POST
    @Path("/subnets")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Subnet create(@Context HttpServletRequest req, @Context HttpServletResponse resp, Subnet subnet)
            throws ServiceException {

        if(null == subnet) {
            LOGGER.error("Input subnet param is invalid!!");
            throw new ServiceException("Input subnet param is null");
        }

        return service.create(subnet);
    }

    /**
     * Rest interface to perform query subnet operation. <br/>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param subnetId The uuid of subnet
     * @return The object of ResultRsp
     * @throws ServiceException When query subnet failed
     * @since SDNO 0.5
     */
    @GET
    @Path("/subnets/{subnet_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Subnet query(@Context HttpServletRequest req, @Context HttpServletResponse resp,
            @PathParam("subnet_id") String subnetId) throws ServiceException {

        if(StringUtils.isEmpty(subnetId)) {
            LOGGER.error("Input subnetId param is invalid!!");
            throw new ServiceException("Input subnetId param is invalid");
        }

        return service.get(subnetId);
    }

    /**
     * Rest interface to perform delete Subnet operation. <br/>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param subnetId The uuid of Subnet
     * @throws ServiceException When delete Subnet failed
     * @since SDNO 0.5
     */
    @DELETE
    @Path("/subnets/{subnet_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@Context HttpServletRequest req, @Context HttpServletResponse resp,
            @PathParam("subnet_id") String subnetId) throws ServiceException {

        if(StringUtils.isEmpty(subnetId)) {
            LOGGER.error("Input subnetId param is invalid!!");
            throw new ServiceException("Input subnetId param is invalid");
        }

        this.service.delete(subnetId);
    }
}
