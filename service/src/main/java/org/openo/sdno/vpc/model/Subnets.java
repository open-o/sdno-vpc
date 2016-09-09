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

package org.openo.sdno.vpc.model;

import java.util.ArrayList;
import java.util.List;

import org.openo.sdno.overlayvpn.model.netmodel.vpc.Subnet;
import org.openo.sdno.overlayvpn.model.uuid.AbstUuidModel;

/**
 * List of Subnet.<br>
 *
 * @author
 * @version SDNO 0.5 2016-7-07
 */

public class Subnets extends AbstUuidModel {

    /**
     * List of Subnet.
     */
    private List<Subnet> subnets = new ArrayList<>();

    public List<Subnet> getSubnets() {
        return this.subnets;
    }

    public void setSubnets(List<Subnet> subnets) {
        this.subnets = subnets;
    }

}
