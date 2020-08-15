/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.geode_examples.temporal;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;

public class Example {
  private final Region<Integer, WeatherData> region;

  public Example(Region<Integer, WeatherData> region) {
    this.region = region;
  }

  public static void main(String[] args) {
    // connect to the locator using default port 10334
    ClientCache cache = new ClientCacheFactory().addPoolLocator("127.0.0.1", 10334)
        .set("log-level", "WARN").create();

    // create a local region that matches the server region
    Region<Integer, WeatherData> region =
        cache.<Integer, WeatherData>createClientRegionFactory(ClientRegionShortcut.PROXY)
            .create("example-region");

    region.clear();

    Example example = new Example(region);
    example.insertValues(12, "Toronto", "Canada", "2020-08-07", 1596802088000L);
    example.insertValues(12, "Montreal", "Canada", "2020-08-07", 1596803288000L);
    example.insertValues(12, "Toronto", "Canada", "2020-08-08", 1596888488000L);
    example.insertValues(12, "Montreal", "Canada", "2020-08-08", 1596889688000L);
    example.printValues(example.getValues());

    cache.close();
  }

  void insertValues(int limit, String city, String country, String date, long starttime) {
    int shift = region.sizeOnServer();
    IntStream.rangeClosed(1, limit).forEach(i -> {
      int k = shift + i;
      region.put(k, new WeatherData(city, country, date, starttime + i * 3600000,
          30 - Math.abs(7 - i), 30 + i));
    });
  }

  Set<Integer> getValues() {
    return new HashSet<>(region.keySetOnServer());
  }

  void printValues(Set<Integer> values) {
    values.forEach(key -> System.out.println(String.format("%2d: %s", key, region.get(key))));
  }
}
