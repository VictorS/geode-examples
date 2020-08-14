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

import java.io.Serializable;

public class WeatherData implements Serializable {
  private static final long serialVersionUID = 349582745L;

  private final String city;
  private final String country;
  private final String localdate;
  private final long timestamp;
  private final int temperature;
  private final int humidity;

  public WeatherData(String city, String country, String localdate, long timestamp, int temperature,
      int humidity) {
    this.city = city;
    this.country = country;
    this.localdate = localdate;
    this.timestamp = timestamp;
    this.temperature = temperature;
    this.humidity = humidity;
  }

  public String getCity() {
    return this.city;
  }

  public String getCountry() {
    return this.country;
  }

  public String getLocaldate() {
    return this.localdate;
  }

  public long getTimestamp() {
    return this.timestamp;
  }

  public int getTemperature() {
    return this.temperature;
  }

  public int getHumidity() {
    return this.humidity;
  }

  @Override
  public String toString() {
    return "[" + " city='" + getCity() + "'" + ", country='" + getCountry() + "'" + ", localdate='"
        + getLocaldate() + "'" + ", timestamp='" + getTimestamp() + "'" + ", temperature='"
        + getTemperature() + "'" + ", humidity='" + getHumidity() + "'" + "]";
  }
}
