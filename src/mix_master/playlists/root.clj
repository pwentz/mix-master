(ns mix-master.playlists.root
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :as ring]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [mix-master.util :refer [render-with-layout]]
            [mix-master.db.core :as db]
            [mix-master.middleware :as middleware]
            [mix-master.util :refer [response]]))

(defn create-show-map [playlist-id]
  (let [playlist-id (Integer. playlist-id)]
    (merge (db/find-first :playlists {:id playlist-id})
           {:songs (db/songs-by-playlist {:id playlist-id})})))

(defn create-playlist-songs [song-ids playlist-id]
  (doall (for [id song-ids]
           (db/create :playlist-songs {:song-id id :playlist-id playlist-id}))))

(defn create-playlist [params]
  (let [song-ids (filter #(= (get params %) "on") (keys params))
        new-playlist (db/create :playlists {:name (:playlist-name params)})]
    (create-playlist-songs song-ids (:id new-playlist))
    (ring/redirect (str "/playlists/" (:id new-playlist)))))

(defroutes playlist-routes
  (GET "/" [] (response (render-with-layout "playlists/index")))
  (GET "/new" [] (response (render-with-layout "playlists/new"
                                               {:anti-forgery-field anti-forgery-field
                                                :songs (db/all :songs)})))
  (GET "/:playlist-id" [playlist-id] (response (render-with-layout "playlists/show"
                                                                   (create-show-map playlist-id))))
  (POST "/create" {params :params} (create-playlist params)))

(def playlists-handler
  (-> playlist-routes
      (middleware/wrap-integerize-params)))
