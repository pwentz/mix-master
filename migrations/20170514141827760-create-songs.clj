;; migrations/20170514141827760-create-songs.clj

(defn up []
  ["CREATE TABLE songs(id int, title varchar(250), artist_id int REFERENCES artists(id), PRIMARY KEY (id))"
  "CREATE INDEX songs_index_on_artist_id ON songs(artist_id)"])

(defn down []
  ["DROP TABLE songs CASCADE"])
