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
import org.openo.sdno.overlayvpn.model.uuid.AbstUuidModel;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * VPC encapsulate an external network IP for the given project in a domain.
 * <br/>
 * <p>
 * </p>
 *
 * @author
 * @version     SDNO 0.5  2016-7-07
 */

@MOResType(infoModelName = "vpcservice_vpc")
public class Vpc extends AbstUuidModel {
    /**
     * VPC Name in the form of domain/project name.
     */
    @AString(require = true)
    private String name = null;

    /**
     * VPC description.
     */
    @AString()
    private String description = null;

    /**
     * VPC status.
     */
    @AString(require = true)
    private String status = ActionStatus.NONE.getName();

    /**
     * VPC status reason.
     */
    @NONInvField
    @AString(require = false)
    private String statusReason = null;

    /**
     * VPC external Ip address in v4.
     */
    @AString(require = true)
    private String externalIp = null;

    /**
     * VPC OpenStack controller uuid.
     */
    @AUuid(require = true)
    private String osControllerId = null;

    @NONInvField
    private Date createdAt = null;

    @NONInvField
    private Date updatedAt = null;

    @NONInvField
    private Vpc.UnderlayResources attributes = null;

    public String getName() {
        return this.name;
    }

    public Vpc setName(String name) {
        this.name = name;
        if (name.split("/").length != 2) {
            throw new IllegalArgumentException("Name should be in the form of domain/project.");
        }
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public Vpc setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getStatus() {
        return this.status;
    }

    public Vpc setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getStatusReason() {
        return this.statusReason;
    }

    public Vpc setStatusReason(String statusReason) {
        this.statusReason = statusReason;
        return this;
    }

    public Vpc.UnderlayResources getAttributes() {
        return this.attributes;
    }

    public Vpc setAttributes(Vpc.UnderlayResources attributes) {
        this.attributes = attributes;
        return this;
    }

    public String getExternalIp() {
        return this.externalIp;
    }

    public Vpc setExternalIp(String externalIp) {
        this.externalIp = externalIp;
        return this;
    }

    public String getOsControllerId() {
        return this.osControllerId;
    }

    public Vpc setOsControllerId(String osControllerId) {
        this.osControllerId = osControllerId;
        return this;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Vpc setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public Vpc setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @JsonIgnore
    public String getProjectName() {
        return this.getName().split("/")[1];
    }

    @JsonIgnore
    public String getDomainName() {
        return this.getName().split("/")[0];
    }

    /**
     * VPC underlay resources.
     * @author
     * @Since SDNO 0.5
     */
    @MOResType(infoModelName = "vpcunderlaysmodel")
    @JsonRootName(value="underlay_resources")
    public static class UnderlayResources extends AbstUuidModel {
        /**
         * VPC uuid.
         */
        @AUuid(require = true)
        private String parentId = null;

        /**
         * VPC project uuid.
         */
        @AUuid(require = true)
        private String projectId = null;

        /**
         * VPC router uuid.
         */
        @AUuid(require = true)
        private String routerId = null;

        //TODO(mrkanag) Add additional properties to mark the underlay
        //resources are whether created by OpenO or existing one. during
        //cleanup operations, remove those underlays which are created
        //only by OpenO.
        public String getProjectId() {
            return this.projectId;
        }

        public Vpc.UnderlayResources setProjectId(String projectId) {
            this.projectId = projectId;
            return this;
        }

        public String getRouterId() {
            return this.routerId;
        }

        public Vpc.UnderlayResources setRouterId(String routerId) {
            this.routerId = routerId;
            return this;
        }

        public String getParentId() {
            return this.parentId;
        }

        public Vpc.UnderlayResources setParentId(String parentId) {
            this.parentId = parentId;
            return this;
        }
    }
}