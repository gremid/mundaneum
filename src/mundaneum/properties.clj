(ns mundaneum.properties
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.data.json :as json]))

;; Properties fetched using the wikibase command line tool:
;; https://github.com/maxlath/wikibase-cli
;; ... example invocation:
;; $ wb props > props-2021-11-04.json
(def wdt
  (->> (json/read (io/reader (io/resource "props-2022-04-19.json")))
       (reduce (fn [m [id text]]
                 (assoc m
                        (-> text
                            (string/replace #"[ /]" "-")
                            (string/replace #"[\(\)\'\,;\"]" "")
                            keyword)
                        (keyword (str "wdt/" id))))
               {})))

;; reverse loookup
(def wdt->readable
  (reduce (fn [m [readable wdt]] (assoc m wdt readable))
          {}
          wdt))
