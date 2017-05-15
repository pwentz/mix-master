(ns mix-master.artists.root
  (:require [compojure.core :refer :all]
            [compojure.route :as route ]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
            [ring.util.response :as ring]
            [mix-master.util :refer [render]]
            [mix-master.artists.repository :as db]
            [mix-master.artists.songs.root :refer [songs-handler]]))

(defn create-artist [{form-params :form-params}]
  (let [artist-name (get form-params "artist-name")
        image (get form-params "artist-image")]
    (if (and artist-name image)
      (db/create {:name artist-name :image image}))

    (ring/redirect "/artists")))

(defroutes artists-routes
  (GET "/" [] {:status 200
               :headers {"Content-Type" "text/html"}
               :body (render "artists/index"
                             {:artists (db/all)})})
  (GET "/new" [] {:status 200
                  :headers {"Content-Type" "text/html"}
                  :body (render "artists/new"
                                {:anti-forgery-field *anti-forgery-token*})})
  (POST "/create" request (create-artist request))

  (context "/:artist-id" [artist-id]
    (GET "/" [artist-id] {:status 200})
    (context "/songs" [artist-id] songs-handler))

  (route/not-found "Not Found"))

(def artists-handler
  artists-routes)
