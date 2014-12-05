(ns clog.test.handler
  (:use clojure.test
        ring.mock.request
        clog.handler))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))
      (is (re-find #"Home" (:body response)))))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))

  (testing "show workout"
    (let [response (app (request :get "/1"))]
      (is (= (:status response 200)))))
