(ns mix-master.db.config
  (:require [clojure.java.jdbc :as j]
            [environ.core :refer [env]]
            [korma.db :refer :all]))

(def database-url
  (env :database-url))

(def db-name
  (subs database-url (.indexOf database-url "mix-master")))

(def pg-uri
  {:connection-uri (str database-url)})

(def pg-map {:host "localhost"
             :port "5432"
             :db db-name
             :make-pool? true})

(defdb korma-db (postgres pg-map))
