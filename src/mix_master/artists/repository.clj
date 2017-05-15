(ns mix-master.artists.repository
  (:require [korma.core :as k]
            [mix-master.db.core :refer :all]))

(defn generate-id []
  (rand-int 999999))

(defn all [] (k/select artists))

(defn create [{first-name :name image :image id :id :as attrs}]
  (if id
    (k/insert artists (k/values attrs))
    (k/insert artists (k/values (assoc attrs :id (generate-id))))))

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
