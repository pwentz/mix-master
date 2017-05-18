(ns mix-master.artists.songs.root
  (:require [compojure.core :refer :all]
            [compojure.route :as route ]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [ring.util.response :as ring]
            [mix-master.util :refer [render-with-layout
                                     response]]
            [mix-master.db.core :as db]
            [mix-master.middleware :refer [wrap-integerize-params]]))

(defn create-song [{title :song-title artist-id :artist-id}]
  (db/create :songs {:title title :artist-id artist-id})
  (ring/redirect (str "/artists/" artist-id "/songs")))

(defroutes songs-routes
  (GET "/" [artist-id] (response
                         (render-with-layout "artists/songs/index"
                                             {:artist-id artist-id
                                              :artist-name (:name (db/find-by-id :artists artist-id))
                                              :songs (db/artist-songs artist-id)})))
  (GET "/new" [artist-id] (response
                            (render-with-layout "artists/songs/new"
                                                {:anti-forgery-field (anti-forgery-field)
                                                 :artist-id artist-id})))
  (POST "/create" {params :params} (create-song params))
  (route/not-found "Not Found"))

(def songs-handler
  (-> songs-routes
      (wrap-integerize-params)))
