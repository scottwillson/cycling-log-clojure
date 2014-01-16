(ns clog.core
  (:use monger.operators)
  (:use monger.query)
  (:use clog.logging)
  (:use clog.environments.development)
  (:refer-clojure :exclude [sort find])
  (:require [cheshire.core :refer :all])
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import [com.mongodb MongoOptions ServerAddress]))

(defn workouts [query]
  (if (nil? query) []
    (with-collection "workouts"
      (sort (sorted-map :date -1))
      (find {:public_notes {$regex (str ".*" query ".*") $options "i"}})
      (limit 100))))

(defn workout [id]
  (println "find workout " id)
  (mc/find-one-as-map "workouts" {:id (read-string id)}))

(mg/connect!)
(mg/set-db! (mg/get-db db-name))

