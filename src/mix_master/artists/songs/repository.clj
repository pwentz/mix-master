(ns mix-master.artists.songs.repository
  (:require [korma.core :as k]
            [mix-master.db.core :refer :all]
            [mix-master.util :refer [snake-case-keys
                                     kebab-case-keys]]))

(defn generate-id []
  (rand-int 999999))

(defn all []
  (k/select songs))

(defn format-params [attrs]
  (->> (generate-id)
       (assoc attrs :id)
       (snake-case-keys)))

(defn create [attrs]
  (k/insert songs (k/values (format-params attrs))))

(defn delete-all []
  (k/delete songs))

(defn find-first [attrs]
  (kebab-case-keys
    (first
      (k/select songs (k/where attrs)))))
