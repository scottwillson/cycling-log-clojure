(ns clog.test.core
  (:use clojure.test
        ring.mock.request
        clog.core
        [monger.core :only [connect get-db]]))

(deftest test-workouts
 (testing "search"
   (let [conn (connect)
         db   (get-db conn "cycling-log-test")]
     (let [results (seq (workouts db "tabor"))]
       (monger.collection/remove db "workouts")
       (is (nil? results))))))

(deftest test-workouts
  (testing "not found"
    (let [conn (connect)
          db   (get-db conn "cycling-log-test")]
      (let [result (workout db "1")]
        (monger.collection/remove db "workouts")
        (is (nil? result))))))
