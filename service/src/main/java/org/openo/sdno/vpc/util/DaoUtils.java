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

import java.util.HashMap;
import java.util.Map;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.model.uuid.AbstUuidModel;

/**
 * DaoUtils class
 *
 * @author
 * @version     SDNO 0.5  Aug 6, 2016
 */
public class DaoUtils {

    // TODO(mrkanag) Make it plug-able and write new one of BRS!!!
    private static Map<String, Object> memCache = new HashMap<>();

    private DaoUtils() {

    }

    /**
     * Stores given object like VPC or Subnet <br>
     *
     * @param entity
     *            VPC or Subnet object
     * @return
     * @throws ServiceException
     * @since SDNO 0.5
     */
    public static <T extends AbstUuidModel> T insert(T entity) throws ServiceException {
        entity.setUuid(entity.getUuid());
        memCache.put(entity.getUuid(), entity);
        return entity;
    }

    /**
     * Deletes given VPC or Subnet object. <br>
     *
     * @param clazz
     *            VPC.class or Subnet.class
     * @param uuid
     *            Id of VPC or Subnet
     * @throws ServiceException
     * @since SDNO 0.5
     */
    public static <T> void delete(Class<T> clazz, String uuid) throws ServiceException {
        memCache.remove(uuid);
    }

    /**
     * Retrieves given VPC or Subnet. <br>
     *
     * @param clazz
     *            VPC.class or Subnet.class
     * @param uuid
     *            Id of VPC or Subnet
     * @return
     * @throws ServiceException
     * @since SDNO 0.5
     */
    public static <T> T get(Class<T> clazz, String uuid) throws ServiceException {
        return (T)memCache.get(uuid);
    }

    /**
     * Updates given VPC or Subnet for the given fieldName <br>
     *
     * @param entity
     *            VPC or Subnet
     * @param filedName
     *            field name
     * @return
     * @throws ServiceException
     * @since SDNO 0.5
     */
    public static <T extends AbstUuidModel> T update(T entity, String filedName) throws ServiceException {
        memCache.put(entity.getUuid(), entity);
        return entity;
    }
}
