(ns mix-master.root
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [mix-master.artists.root :refer [artists-handler]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            ))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (context "/artists" [] artists-handler)
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-defaults site-defaults)))
