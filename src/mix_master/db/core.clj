(ns mix-master.db.core
  (:require [korma.core :refer :all]
            [mix-master.db.config :refer :all]
            [mix-master.util :refer [kebab-case-keys
                                     snake-case-keys]]))

(defentity artists)
(defentity songs
  (belongs-to artists))

(def tables
  {:artists artists
   :songs songs})

(defn generate-id []
  (rand-int 999999))

(defn format-params [attrs]
  (->> (generate-id)
       (assoc attrs :id)
       (snake-case-keys)))

(defn all [ent]
  (select (ent tables)))

(defn find-first [ent attrs]
  (kebab-case-keys
    (first
      (select (ent tables)
        (where attrs)))))

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
