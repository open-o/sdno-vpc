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

package org.openo.sdno.vpc.service.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Vpc;
import org.openo.sdno.overlayvpn.util.check.CheckStrUtil;
import org.openo.sdno.vpc.nbi.inf.IVpcNbiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * VPC service class for ROA.<br>
 *
 * @author
 * @version SDNO 0.5 2016-7-07
 */
@Service
@Path("/sdnovpc/v1")
public class VpcServiceROAResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpcServiceROAResource.class);

    @Resource
    IVpcNbiService service;

    public void setService(IVpcNbiService service) {
        this.service = service;
    }

    /**
     * Rest interface to perform create VPC operation. <br>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param inputVpc VPC Object
     * @return Vpc Object created
     * @throws ServiceException When create VPC failed
     * @since SDNO 0.5
     */
    @POST
    @Path("/vpcs")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Vpc create(@Context HttpServletRequest req, @Context HttpServletResponse resp, Vpc inputVpc)
            throws ServiceException {

        if(null == inputVpc) {
            LOGGER.error("Input vpc param is invalid!!");
            throw new ServiceException("Input vpc param is null");
        }

        CheckStrUtil.checkUuidStr(inputVpc.getUuid());

        Vpc vpcLocal = service.create(inputVpc);

        resp.setStatus(HttpStatus.SC_CREATED);

        return vpcLocal;
    }

    /**
     * Rest interface to perform query VPC operation. <br>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param vpcId The UUID of VPC
     * @return The object of ResultRsp
     * @throws ServiceException When query VPC failed
     * @since SDNO 0.5
     */
    @GET
    @Path("/vpcs/{vpc_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Vpc query(@Context HttpServletRequest req, @Context HttpServletResponse resp,
            @PathParam("vpc_id") String vpcId) throws ServiceException {

        if(StringUtils.isEmpty(vpcId)) {
            LOGGER.error("Input vpcId param is invalid!!");
            throw new ServiceException("Input vpcId param is invalid!!");
        }

        return service.get(vpcId);
    }

    /**
     * Batch query vpcs.<br>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param name Vpc name
     * @return List of vpcs queried out
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    @GET
    @Path("/vpcs")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vpc> batchQuery(@Context HttpServletRequest req, @Context HttpServletResponse resp,
            @QueryParam("name") String name) throws ServiceException {

        Map<String, Object> filterMap = new HashMap<String, Object>();

        if(StringUtils.isNotEmpty(name)) {
            filterMap.put("name", name);
        }

        return service.batchGet(filterMap);
    }

    /**
     * Rest interface to perform delete VPC operation. <br>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param vpcId The UUID of VPC
     * @throws ServiceException When delete VPC failed
     * @since SDNO 0.5
     */
    @DELETE
    @Path("/vpcs/{vpc_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@Context HttpServletRequest req, @Context HttpServletResponse resp,
            @PathParam("vpc_id") String vpcId) throws ServiceException {

        if(StringUtils.isEmpty(vpcId)) {
            LOGGER.error("Input vpcId param is invalid!!");
            throw new ServiceException();
        }

        service.delete(vpcId);
    }
}
