/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.gatein.benchmark;

import java.io.Serializable;
import java.util.logging.Logger;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.cache.CacheService;
import org.exoplatform.services.cache.impl.infinispan.AbstractExoCache;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

/**
 * @author Lucas Ponce
 */
public class BenchMark {

    private static final Log LOG = ExoLogger.getLogger("BenchMark");

    public static void main(String[] args) throws Exception {
        String name = null;
        int delay = 1;
        int nThreads = 100;

        if (args.length != 3) {
            System.out.println("\n\n Use: run.sh <name> <delay_to_start> <clients_per_node> <duration_of_test>\n\n");
            System.exit(1);
        } else {
            name = args[0];
            delay = new Integer(args[1]);
            nThreads = new Integer(args[2]);
        }

        Thread.currentThread().setName(name);

        CacheService service = PortalContainer.getInstance().getComponentInstanceOfType(CacheService.class);
        AbstractExoCache<Serializable, Object> cache = (AbstractExoCache<Serializable, Object>)service.getCacheInstance("BenchMarkCache");
        MyListener myListener = new MyListener(name);
        cache.addCacheListener(myListener);

        LOG.info("Waiting " + delay + " seconds before to start test...");

        Thread.sleep(delay * 1000);

        LOG.info("Starting test...");

        final String myKey = "myKey";

        while (true) {

            String data = (String)cache.get(myKey);
            LOG.info("Key: " + myKey + " Value: " + data);

            cache.put(myKey, name);
            Thread.sleep((long)(Math.random() * 1000 * 3));
        }

    }
}
