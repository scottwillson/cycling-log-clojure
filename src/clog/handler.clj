(ns clog.handler
  (:use compojure.core)
  (:use monger.operators)
  (:use monger.query)
  (:use clog.logging)
  (:use clog.environments.development)
  (:use clog.core)
  (:use ring.middleware.stacktrace)
  (:use ring.middleware.reload)
  (:use [ring.adapter.jetty :as ring])
  (:refer-clojure :exclude [sort find])
  (:require [cheshire.core :refer :all])
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [clog.views.home :as views/home]
            [clog.views.show :as views/show])
  (:import [com.mongodb MongoOptions ServerAddress]))

(defn json-response [data & [status]]
    {:status (or status 200)
        :headers {"Content-Type" "application/json"} 
        :body (generate-string data)})

(defn workouts-json [query]
  (json-response (
    map (fn [x] {
      :id (get x :id)
      :date (get x :date) 
      :activity (get x :activity) 
      :distance (get x :distance) 
      :minutes (get x :minutes) 
      :name(get x :name) 
      :public_notes (get x :public_notes)
      :notes (get x :notes)
    }) (workouts query))))

(defn workout-json [id]
  (json-response
    (dissoc (workout id) :_id)))


(defroutes app-routes
  (GET "/" [] views/home/show-html)
  (GET "/workouts.json" [query] (workouts-json query))
  (GET ["/workouts/:id.json", :id #"[0-9]+"] [id] (println "workouts#show.json" id) (workout-json id))
  (GET ["/workouts/:id", :id #"[0-9]+"] [id] (views/show/show-html (workout id)))
  (route/files "/")
  (route/not-found "Not Found"))

(def the-app
  (handler/site app-routes))

(def app
  (-> #'the-app
    (wrap-request-logging)
    (wrap-reload '[clog.handler])
    (wrap-stacktrace)))

(defn -main []
  (run-jetty #'app-routes {:port 3000 :join? false}))
