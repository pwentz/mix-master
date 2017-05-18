(ns mix-master.db.core
  (:require [korma.core :refer :all]
            [mix-master.db.config :refer :all]
            [mix-master.util :refer [kebab-case-keys
                                     snake-case-keys]]))

(defentity artists)
(defentity songs
  (belongs-to artists))

(defentity playlists)
(defentity playlist_songs)

(def tables
  {:artists artists
   :songs songs
   :playlists playlists
   :playlist-songs playlist_songs})

(defn generate-id []
  (rand-int 999999))

(defn format-params [attrs]
  (->> (generate-id)
       (assoc attrs :id)
       (snake-case-keys)))

(defn all [ent]
  (select (ent tables)))

(defn find-all [ent attrs]
  (let [attrs (snake-case-keys attrs)]
    (map kebab-case-keys
      (select (ent tables)
        (where attrs)))))

(defn find-first [ent attrs]
  (first (find-all ent attrs)))

(defn find-by-id [ent id]
  (find-first ent {:id (Integer. id)}))

(defn artist-songs [artist-id]
  (find-all :songs {:artist-id (Integer. artist-id)}))

(defn delete-all [ent]
  (delete (ent tables)))

(defn- create-record [table attrs]
  (->> attrs
       (snake-case-keys)
       (values)
       (insert table)))

(defn create [ent {id :id, :or {id (generate-id)} :as attrs}]
  (create-record (ent tables)
                 (assoc attrs :id id)))

(defn count-of [ent]
  ((comp :cnt first)
    (select (ent tables)
      (aggregate (count :*) :cnt))))

(defn prepend-entity-to-key [entry]
  [(keyword (str "playlists." (name (key entry)))) (val entry)])

(defn songs-by-playlist [attrs]
  (let [playlist-attrs (into {} (map prepend-entity-to-key attrs))]
    (select songs
      (join playlist_songs (= :playlist_songs.song_id :id))
      (join playlists (= :playlists.id :playlist_songs.playlist_id))
      (where playlist-attrs))))
