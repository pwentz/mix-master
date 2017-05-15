(ns mix-master.artists.songs.root
  (:require [compojure.core :refer :all]
            [compojure.route :as route ]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
            [ring.util.response :as ring]
            [mix-master.util :refer [render]]
            [mix-master.db.core :as db]))

(defn response [{body :body}]
  (assoc
  {:status 200
   :headers {"Content-Type" "text/html"}}
   :body body))

(defn create-song [{title :song-title artist-id :artist-id}]
  (when (and title artist-id)
    (db/create :songs
               {:title title :artist-id artist-id}))
  (response {:body {}}))

(defroutes songs-routes
  (GET "/" [artist-id] (response
                         {:body (render "artists/songs/index"
                                        {:artist-id artist-id})}))
  (GET "/new" [artist-id] (response
                            {:body (render
                                     "artists/songs/new"
                                     {:anti-forgery-token *anti-forgery-token*
                                      :artist-id artist-id})}))
  (POST "/create" {params :params} (create-song params))
  (route/not-found "Not Found"))

(def songs-handler
  songs-routes)
