(ns mix-master.playlists.root
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :as ring]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [mix-master.util :refer [render-with-layout]]
            [mix-master.db.core :as db]))

(defn response [{body :body}]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body body})

(defn create-playlist [params]
  (let [song-ids (filter #(= (get params %) "on") (keys params))
        new-playlist (db/create :playlists {:name (:playlist-name params)})]
    (for [id song-ids]
      (db/create :playlist-songs {:song-id id :playlist-id (:id new-playlist)}))
    (ring/redirect "/")))

(defroutes playlist-routes
  (GET "/" [] (response {:body (render-with-layout "playlists/index")}))
  (GET "/new" [] (response {:body (render-with-layout "playlists/new"
                                                      {:anti-forgery-field anti-forgery-field
                                                       :songs (db/all :songs)})}))
  (POST "/create" {params :params} (create-playlist params)))

(def playlists-handler
  playlist-routes)
