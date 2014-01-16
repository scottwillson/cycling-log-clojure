(ns clog.views.show
  (:use compojure.core)
  (:use hiccup.core
        hiccup.page))

(defn show-html [workout]
  (html5 {:lang "en" :ng-app "clogApp"}
    [:head
      [:meta {:charset "utf-8"}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
      [:title "Cycling Log: Workout"]
      (include-css "//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css"
                   "/css/application.css")]
    [:body {:ng-controller "WorkoutCtrl"}
      [:div.container
        [:h1 "Cycling Log"]
        [:div.row
          [:div.col-12
            [:form {:role "form"}
              [:div.form-group
                [:label "Name"]
                [:p (workout :name)]]
              [:div.form-group
                [:label "Date"]
                [:p (workout :date)]]
              [:div.form-group
                [:label "Activity"]
                [:p (workout :activity)]]
              [:div.form-group
                [:label "Distance"]
                [:p (workout :distance)]]
              [:div.form-group
                [:label "Minutes"]
                [:p (workout :minutes)]]
              [:div.form-group
                [:label "Notes"]
                [:p (workout :public_notes)]]
              [:div.form-group
                [:label "Speed"]
                [:p (workout :speed)]]
              [:div.form-group
                [:label "Intensity"]
                [:p (workout :intensity)]]
              [:div.form-group
                [:label "Weather"]
                [:p (workout :weather)]]
              [:div.form-group
                [:label "Equipment"]
                [:p (workout :equipment_name)]]
              [:div.form-group
                [:label "Route"]
                [:p (workout :route_name)]]
           ]]]]]))
