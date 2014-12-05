(ns clog.core
  (:use monger.operators)
  (:use monger.query)
  (:use clog.logging)
  (:refer-clojure :exclude [sort find])
  (:require [cheshire.core :refer :all])
  (:require [monger.core :as mg]
            [monger.collection :as mc]))

(defn workouts [db query]
    (if (nil? query) []
      (with-collection db "workouts"
        (sort (sorted-map :date -1))
        (find {:public_notes {$regex (str ".*" query ".*") $options "i"}})
        (limit 100))))

(defn workout [db id]
  (println "find workout " id)
      (mc/find-one-as-map db "workouts" {:id (read-string id)}))
