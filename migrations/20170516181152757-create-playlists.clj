;; migrations/20170516181152757-create-playlists.clj

(defn up []
  ["CREATE TABLE playlists(id int, name varchar(250), PRIMARY KEY (id))"])

(defn down []
  ["DROP TABLE playlists CASCADE"])
