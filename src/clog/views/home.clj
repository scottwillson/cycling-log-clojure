(ns clog.views.home
  (:use compojure.core)
  (:use hiccup.core
        hiccup.page))

(def show-html
  (html5 {:lang "en" :ng-app "clogApp"}
    [:head
      [:meta {:charset "utf-8"}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
      [:title "Cycling Log: Home"]
      (include-css "//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css"
                   "/css/application.css")]
    [:body
      [:div.container
        [:h1 "Cycling Log"]
        [:div.row
          [:div.col-12
            [:form {:ng-controller "WorkoutsCtrl"}
              [:input.query.form-control {:type "text" :placeholder "Search workout notes" :ng-model "query" :autofocus "autofocus" :ng-change "search()"}]
              [:table.table.table-striped.workouts {:ng-show "workouts"}
                [:thead
                  [:tr
                    [:th.date "Date"]
                    [:th.activity "Activity"]
                    [:th.minutes "Minutes"]
                    [:th.distance "Miles"]
                    [:th.person_name "Name"]
                    [:th]]]
                [:tbody
                  [:tr {:ng-repeat "workout in workouts"}
                    [:td.date 
                     [:a {:href "/workouts/{{workout.id}}"} "{{workout.date | date:'mediumDate'}}"]]
                    [:td.activity "{{workout.activity}}"]
                    [:td.minutes "{{workout.minutes}}"]
                    [:td.distance "{{workout.distance | number:'1'}}"]
                    [:td.name "{{workout.name}}"]
                    [:td.public_notes "{{workout.public_notes}}"]]]]]]]]
    (include-js "https://ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js"
                "js/app.js"
                "js/controllers.js")]))
