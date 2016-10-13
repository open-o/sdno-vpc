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

package org.openo.sdno.vpc.util;

import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.security.authentication.HttpContext;

/**
 * Provide utility methods for HTTP operations
 * <br>
 *
 * @author
 * @version SDNO 0.5 August 5, 2016
 */
public class HttpUtils {

    private HttpUtils() {

    }

    /**
     * Helps to form the RestfulParameters with common
     * request headers and given object as request body.
     * <br>
     *
     * @param o
     * @return
     * @since SDNO 0.5
     */
    public static RestfulParametes formRestfulParams(Object o) {
        RestfulParametes restfulParametes = new RestfulParametes();

        // TODO(mrkanag) uncomment the below line once this support enabled
        // TODO(mrkanag) Use TokenDataHolder.

        restfulParametes.putHttpContextHeader(HttpContext.CONTENT_TYPE_HEADER, HttpContext.MEDIA_TYPE_JSON);
        if(o != null) {
            String requestJsonString = JsonUtil.toJson(o);
            restfulParametes.setRawData(requestJsonString);
        }
        return restfulParametes;
    }
}
