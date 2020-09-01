<!--
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->

# Geode temporal data in replicated region example

This is a simple example that demonstrates queries for temporal data.
See https://en.wikipedia.org/wiki/Temporal_database
This is a uni-temporal example with timestamp as the valid time represented in UTC milliseconds also known as Java time. The example data has undeclared composite key consisting of city, country and localdate. This composite key cannot be used as the Geode record key, because of the temporal axis.

This example assumes you have installed Java and Geode.

## Steps

1. From the `geode-examples/temporal` directory, build the example and
   run unit tests

        $ ../gradlew build

2. Next start the locator and two servers

        $ gfsh run --file=scripts/start.gfsh

3. Run the example to create entries in the region

        $ ../gradlew run

4. Run a gfsh query, see all the entries ordered by timestamp

        $ gfsh -e "connect --locator=127.0.0.1[10334]" -e "query --query='select * from /example-region r order by r.getTimestamp()'"

5. Run a gfsh query, that retrieves the active record for a certain time for the specified city and date

        $ gfsh -e "connect --locator=127.0.0.1[10334]" -e "query --query=\"select * from /example-region r where r.getCity() = 'Toronto' and r.getCountry() = 'Canada' and r.getTimestamp() <= 1596913688000L and r.getLocaldate() = '2020-08-08' order by r.getTimestamp() desc limit 1\""

5. Run a gfsh query, that retrieves the active records at a certain time for all available cities for the specified date

        $ gfsh -e "connect --locator=127.0.0.1[10334]" -e "query --query=\"select rec from (select max(r.getTimestamp()) as ts, r.getCity() as city, r.getCountry() as country, r.getLocaldate() as localdate from /example-region r where r.getTimestamp() <= 1596913688000L and r.getLocaldate() = '2020-08-08' group by r.getCity(), r.getCountry(), r.getLocaldate()) rank, /example-region rec where rec.getTimestamp() = rank.ts and rec.getCity() = rank.city and rec.getCountry() = rank.country and rec.getLocaldate() = rank.localdate\""

6. Shut down the system:

        $ gfsh run --file=scripts/stop.gfsh
