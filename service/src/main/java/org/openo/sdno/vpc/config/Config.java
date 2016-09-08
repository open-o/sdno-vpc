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

package org.openo.sdno.vpc.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * VPC service configurable parameters.
 * <br/>
 * <p>
 * </p>
 *
 * @author
 * @version     SDNO 0.5  Aug 5, 2016
 */
public class Config {

    /**
     * VPC configuration file name.
     */
    public static final String VPC_CONF = "vpcconfig.properties";

    private static final String OSDRIVER_URL = "osdriver_url";

    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);

    private Config() {

    }


    private static Properties readConfig() throws ServiceException {
        try {
            Properties prop = new Properties();
            InputStream in = Config.class.getClassLoader().getResourceAsStream(VPC_CONF);
            prop.load(in);
            in.close();
            return prop;
        } catch(IOException e) {
            LOGGER.error("Failed to read " + VPC_CONF);
            throw new ServiceException(e);
        }
    }

    public static String getOsDriverServiceUrl() throws ServiceException {
        Properties prp = readConfig();
        return prp.getProperty(OSDRIVER_URL);
    }
}
