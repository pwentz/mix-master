(defproject mix-master "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [ring/ring-defaults "0.2.1"]
                 [org.clojure/java.jdbc "0.7.0-alpha3"]
                 [org.postgresql/postgresql "9.3-1100-jdbc4"]]
  :plugins [[lein-ring "0.9.7"]
            [speclj "3.3.2"]
            [clj-sql-up "0.3.7"]]
  :ring {:handler mix-master.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [speclj "3.3.2"]
                        [ring/ring-mock "0.3.0"]]}}
  :test-paths ["spec"]
  :main mix-master.core
  :clj-sql-up {:database "postgresql://localhost:5432/mix-master"
               :deps [[org.postgresql/postgresql "9.3-1100-jdbc4"]]})
