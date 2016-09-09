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

package org.openo.sdno.vpc.service.utils;

import java.util.Arrays;

import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Subnet;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Vpc;
import org.openo.sdno.vpc.model.Subnets;
import org.openo.sdno.vpc.model.Vpcs;

public class TestModel {

    public void testVpcs() {
        Vpcs vpcs = new Vpcs();
        vpcs.setVpcs(Arrays.asList(new Vpc()));
        System.out.println(JsonUtil.toJson(vpcs));

        Subnets subnets = new Subnets();
        subnets.setSubnets(Arrays.asList(new Subnet()));
        System.out.println(JsonUtil.toJson(subnets));

    }

    public static void main(String[] args) {
        new TestModel().testVpcs();
    }
}
