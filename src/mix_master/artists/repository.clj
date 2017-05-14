(ns mix-master.artists.repository
  (:require [korma.core :as k]
            [mix-master.db.core :refer :all]))

(k/defentity artists)

(defn generate-id []
  (rand-int 999999))

(defn all [] (k/select artists))

(defn create [{first-name :name image :image :as attrs}]
  (let [artist-data (assoc attrs :id (generate-id))]
    (k/insert artists (k/values artist-data))))

(defn find-first [attrs]
  (first
    (k/select artists
      (k/where attrs))))

(defn delete-all []
  (k/delete artists))

(defn entity-count []
  ((comp :cnt first)
    (k/select artists
      (k/aggregate (count :*) :cnt))))
