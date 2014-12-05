(defproject clog "0.1.0-SNAPSHOT"
  :description "Cycling Log. Big in Portland for a few years. This is the Clojure version."
  :url "http://rocketsurgeryllc.com"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.novemberain/monger "1.6.0"]
                 [cheshire "5.2.0"]
                 [ring-json-params "0.1.3"]
                 [ring "1.2.0"]
                 [compojure "1.1.5"]
                 [hiccup "1.0.4"]]
  :main clog.handler
  :uberjar-name "clog-standalone.jar"
  :plugins [[lein-ring "0.8.6"]]
  :ring {:handler clog.handler/app}
  :profiles
    {:dev {:dependencies [[ring-mock "0.1.5"]
                        [org.mortbay.jetty/jetty "6.1.26"]
                        [clj-webdriver "0.6.0"]]}}
  :test-selectors {:default (complement :browser)
                                      :browser :browser
                                      :all (constantly true)})

