(ns mix-master.artists.root
  (:require [compojure.core :refer :all]
            [compojure.route :as route ]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [ring.util.response :as ring]
            [mix-master.util :refer [render-with-layout
                                     response]]
            [mix-master.db.core :as db]
            [mix-master.artists.songs.root :refer [songs-handler]]))

(defn create-artist [{form-params :form-params}]
  (let [artist-name (get form-params "artist-name")
        image (get form-params "artist-image")]
    (if (and artist-name image)
      (db/create :artists
                 {:name artist-name :image image}))

    (ring/redirect "/artists")))

(defroutes artists-routes
  (GET "/" [] (response (render-with-layout "artists/index"
                                            {:artists (db/all :artists)})))
  (GET "/new" [] (response (render-with-layout "artists/new"
                                               {:anti-forgery-field (anti-forgery-field)})))
  (POST "/create" request (create-artist request))

  (context "/:artist-id" [artist-id]
    (GET "/" [artist-id] {:status 200})
    (context "/songs" [artist-id] songs-handler))

  (route/not-found "Not Found"))

(def artists-handler
  artists-routes)
