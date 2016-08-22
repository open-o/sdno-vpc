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

package org.openo.sdno.vpc.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.model.common.enums.ActionStatus;
import org.openo.sdno.overlayvpn.model.common.enums.AdminStatus;
import org.openo.sdno.overlayvpn.model.uuid.AbstUuidModel;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * VPC Subnet.
 * <br/>
 * <p>
 * </p>
 *
 * @author
 * @version     SDNO 0.5  2016-7-07
 */
@MOResType(infoModelName = "vpcservice_subnet")
public class Subnet extends AbstUuidModel {
    /**
     * VPC subnet name.
     */
    @AString(require = true)
    private String name = null;

    /**
     * VPC subnet description.
     */
    @AString(require = false)
    private String description = null;

    /**
     * VPC subnet status.
     */
    @AString(require = true)
    private String status = ActionStatus.NONE.getName();

    /**
     * VPC subnet status reason.
     */
    @AString(require = false)
    @NONInvField
    private String statusReason = null;

    /**
     * VPC subnet admin status.
     */
    @AString(require = true)
    private String adminStatus = AdminStatus.NONE.getName();

    /**
     * VPC subnet CIDR.
     */
    @AString(require = true)
    private String cidr = null;

    /**
     * VPC uuid.
     */
    @AUuid(require = true)
    private String vpcId = null;

    /**
     * VPC subnet gateway IP.
     */
    @AString(require = true)
    private String gatewayIp = null;

    @NONInvField
    private Date createdAt = null;

    @NONInvField
    private Date updatedAt = null;

    @JsonIgnore
    @NONInvField
    private Subnet.UnderlayResources attributes = null;

    public String getName() {
        return this.name;
    }

    public Subnet setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public Subnet setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getStatus() {
        return this.status;
    }

    public Subnet setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getStatusReason() {
        return this.statusReason;
    }

    public Subnet setStatusReason(String statusReason) {
        this.statusReason = statusReason;
        return this;
    }

    public Subnet.UnderlayResources getAttributes() {
        return this.attributes;
    }

    public Subnet setAttributes(Subnet.UnderlayResources attributes) {
        this.attributes = attributes;
        return this;
    }

    public String getAdminStatus() {
        return this.adminStatus;
    }

    public Subnet setAdminStatus(String adminStatus) {
        this.adminStatus = adminStatus;
        return this;
    }

    public String getCidr() {
        return this.cidr;
    }

    public Subnet setCidr(String cidr) {
        this.cidr = cidr;
        return this;
    }

    public String getVpcId() {
        return this.vpcId;
    }

    public Subnet setVpcId(String vpcId) {
        this.vpcId = vpcId;
        return this;
    }

    public String getGatewayIp() {
        return this.gatewayIp;
    }

    public Subnet setGatewayIp(String gatewayIp) {
        this.gatewayIp = gatewayIp;
        return this;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Subnet setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public Subnet setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    /**
     * VPC subnet underlay resources.
     * @author
     * @since SDNO 0.5
     */
    @MOResType(infoModelName = "subnetunderlaysmodel")
    @JsonRootName(value="underlay_resources")
    public static class UnderlayResources extends AbstUuidModel {
        /**
         * Subnet uuid
         */
        @AUuid(require = true)
        private String parentId = null;

        /**
         * Subnet network uuid.
         */
        @AUuid(require = true)
        private String networkId = null;

        /**
         * Subnet uuid.
         */
        @AUuid(require = true)
        private String subnetId = null;

        //TODO(mrkanag) Add additional properties to mark the underlay
        //resources are whether created by OpenO or existing one. during
        //cleanup operations, remove those underlays which are created
        //only by OpenO.
        public String getNetworkId() {
            return this.networkId;
        }

        public Subnet.UnderlayResources setNetworkId(String networkId) {
            this.networkId = networkId;
            return this;
        }

        public String getSubnetId() {
            return this.subnetId;
        }

        public Subnet.UnderlayResources setSubnetId(String subnetId) {
            this.subnetId = subnetId;
            return this;
        }

        public String getParentId() {
            return this.parentId;
        }

        public Subnet.UnderlayResources setParentId(String parentId) {
            this.parentId = parentId;
            return this;
        }
    }
}