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
  (let [conn (mg/connect)
        db   (mg/get-db conn db-name)]
    (if (nil? query) []
      (with-collection db "workouts"
        (sort (sorted-map :date -1))
        (find {:public_notes {$regex (str ".*" query ".*") $options "i"}})
        (limit 100)))))

(defn workout [id]
  (println "find workout " id)
  (let [conn (mg/connect)
        db   (mg/get-db conn db-name)]
      (mc/find-one-as-map db "workouts" {:id (read-string id)})))
