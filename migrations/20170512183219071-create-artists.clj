;; migrations/20170512183219071-create-artists.clj

(defn up []
  ["CREATE TABLE artists(id int, name varchar(50), image varchar(250), PRIMARY KEY (id))"])

(defn down []
  ["DROP TABLE artists CASCADE"])
