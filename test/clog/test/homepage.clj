(ns clog.test.homepage
  (:use clojure.test
        clj-webdriver.taxi
        [ring.adapter.jetty :only [run-jetty]]
        [monger.core :only [connect get-db]]
        [monger.collection :only [insert insert-batch]])
  (:import [org.bson.types ObjectId]
                      [com.mongodb DB WriteConcern])
  (:require [clj-webdriver.taxi]
            [clojure.test :refer :all]))

(deftest ^:browser home-page
  (testing "home page search"
    (let [conn (connect)
          db   (get-db conn "cycling-log-test")]
    (monger.collection/remove db "workouts")
    (insert db "workouts" { :_id (ObjectId.) :date "2009-07-22" :public_notes "Raced Tabor again" })
    (to "http://localhost:3333/")
    (input-text ".query" "Tabor")
    (Thread/sleep 1000)
    (is (-> "table.workouts" visible?)))))

(defn ^:browser start-server [f]
  (loop [server (run-jetty #'clog.handler/app {:port 3333, :join? false})]
    (if (.isStarted server)
      (do
        (f)
        (.stop server))
      (recur server))))

(defn ^:browser start-browser-fixture
  [f]
  (set-driver! {:browser :phantomjs})
  (f))

(defn ^:browser quit-browser-fixture
  [f]
  (f)
  (quit))

(use-fixtures :once start-server)
(use-fixtures :each start-browser-fixture quit-browser-fixture)

