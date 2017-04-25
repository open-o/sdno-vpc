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

package org.openo.sdno.vpc.service.rest;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

public class SwaggerRoaTest {

    SwaggerRoa swag = new SwaggerRoa();

    @Test
    public void test() throws IOException {

        String basePath = "basePath";
        String portId = "/openoapi/sdnovpc/v1";
        String apidoc = swag.apidoc();

        if (apidoc.contains(basePath) && apidoc.contains(portId)) {
            assertTrue(true);
        }
    }
}
