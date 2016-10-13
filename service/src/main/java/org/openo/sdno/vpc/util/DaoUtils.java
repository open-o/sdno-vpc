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

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.inventory.sdk.util.InventoryDaoUtil;
import org.openo.sdno.overlayvpn.model.uuid.AbstUuidModel;
import org.openo.sdno.overlayvpn.result.ResultRsp;

/**
 * DaoUtils class
 *
 * @author
 * @version SDNO 0.5 August 6, 2016
 */
public class DaoUtils {

    private DaoUtils() {
    }

    /**
     * Stores given object like VPC or Subnet.<br>
     * 
     * @param data VPC or Subnet object
     * @throws ServiceException if insert data failed.
     * @since SDNO 0.5
     */
    public static <T extends AbstUuidModel> void insert(T data) throws ServiceException {
        new InventoryDaoUtil<T>().getInventoryDao().insert(data);
    }

    /**
     * Deletes given VPC or Subnet object. <br>
     *
     * @param clazz VPC.class or Subnet.class
     * @param uuid Id of VPC or Subnet
     * @return Deleting result
     * @throws ServiceException if delete data failed.
     * @since SDNO 0.5
     */
    public static <T> ResultRsp<String> delete(Class<T> clazz, String uuid) throws ServiceException {
        return new InventoryDaoUtil<T>().getInventoryDao().delete(clazz, uuid);
    }

    /**
     * Retrieves given VPC or Subnet.<br>
     *
     * @param clazz VPC.class or Subnet.class
     * @param uuid Id of VPC or Subnet
     * @return Querying result
     * @throws ServiceException if query data failed.
     * @since SDNO 0.5
     */
    public static <T> ResultRsp<T> get(Class<T> clazz, String uuid) throws ServiceException {
        return new InventoryDaoUtil<T>().getInventoryDao().query(clazz, uuid, null);
    }

    /**
     * Updates given VPC or Subnet for the given fieldName.<br>
     *
     * @param data VPC or Subnet
     * @param filedName field name
     * @return Update result
     * @throws ServiceException if update data failed.
     * @since SDNO 0.5
     */
    public static <T extends AbstUuidModel> ResultRsp<T> update(T data, String filedName) throws ServiceException {
        return new InventoryDaoUtil<T>().getInventoryDao().update(data, filedName);
    }
}
