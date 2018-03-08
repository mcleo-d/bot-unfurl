;
; Copyright © 2016, 2017 Symphony Software Foundation
; SPDX-License-Identifier: Apache-2.0
;
; Licensed under the Apache License, Version 2.0 (the "License");
; you may not use this file except in compliance with the License.
; You may obtain a copy of the License at
;
;     http://www.apache.org/licenses/LICENSE-2.0
;
; Unless required by applicable law or agreed to in writing, software
; distributed under the License is distributed on an "AS IS" BASIS,
; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
; See the License for the specific language governing permissions and
; limitations under the License.
;

(def jackson-version "2.9.4")

(defproject org.symphonyoss.symphony/bot-unfurl "1.0.2"
  :description      "A bot that looks for URIs in messages and 'unfurls' them into a new message."
  :url              "https://github.com/symphonyoss/bot-unfurl"
  :license          {:spdx-license-identifier "Apache-2.0"
                     :name                    "Apache License, Version 2.0"
                     :url                     "http://www.apache.org/licenses/LICENSE-2.0"}
  :min-lein-version "2.8.1"
  :repositories     [["sonatype-snapshots" {:url "https://oss.sonatype.org/content/groups/public" :snapshots true}]
                     ["jitpack"            {:url "https://jitpack.io"}]]
  :plugins          [
                      [org.noisesmith/git-info-edn "0.2.1"]
                    ]
  :dependencies     [
                      [org.clojure/clojure              "1.9.0"]
                      [org.apache.commons/commons-lang3 "3.7"]
                      [aero                             "1.1.3"]
                      [mount                            "0.1.12"]
                      [org.clojure/tools.cli            "0.3.5"]
                      [org.clojure/tools.logging        "0.4.0"]
                      [ch.qos.logback/logback-classic   "1.2.3"]
                      [org.slf4j/jcl-over-slf4j         "1.7.25"]
                      [org.slf4j/log4j-over-slf4j       "1.7.25"]
                      [org.slf4j/jul-to-slf4j           "1.7.25"]
                      [org.jolokia/jolokia-jvm          "1.5.0"]
                      [org.jolokia/jolokia-jvm          "1.5.0" :classifier "agent"]
                      [clj-time                         "0.14.2"]
                      [com.linkedin.urls/url-detector   "0.1.17" :exclusions [org.apache.commons/commons-lang3 org.beanshell/bsh junit org.yaml/snakeyaml]]
                      [org.clojars.pmonks/unfurl        "0.9.0"  :exclusions [org.clojure/clojure commons-logging]]
                      [org.symphonyoss/clj-symphony     "0.6.0"  :exclusions [org.clojure/clojure
                                                                              org.slf4j/slf4j-log4j12]]

                      ; The following dependencies are inherited but have conflicting versions, so we "pin" the versions here
                      [com.fasterxml.jackson.core/jackson-core                      ~jackson-version]
                      [com.fasterxml.jackson.core/jackson-databind                  ~jackson-version]
                      [com.fasterxml.jackson.core/jackson-annotations               ~jackson-version]
                      [com.fasterxml.jackson.dataformat/jackson-dataformat-yaml     ~jackson-version]
                      [com.fasterxml.jackson.dataformat/jackson-dataformat-cbor     ~jackson-version]
                      [com.fasterxml.jackson.dataformat/jackson-dataformat-smile    ~jackson-version]
                      [com.fasterxml.jackson.datatype/jackson-datatype-jsr310       ~jackson-version]
                      [com.fasterxml.jackson.module/jackson-module-jaxb-annotations ~jackson-version]
                      [joda-time/joda-time                                          "2.9.9"]
                      [org.hamcrest/hamcrest-core                                   "1.3"]
                    ]
  :profiles         {:dev {:dependencies [[midje         "1.9.1"]]
                           :plugins      [[lein-midje    "3.2.1"]
                                          [lein-licenses "0.2.2"]]}
                     :uberjar {:aot          :all
                               :uberjar-name "bot-unfurl-standalone.jar"}}
  :jvm-opts         ~(let [version     (System/getProperty "java.version")
                           [major _ _] (clojure.string/split version #"\.")]
                       (if (= major "9")
                         ["--add-modules" "java.xml.bind"]
                         []))
  :main             bot-unfurl.main
  )
