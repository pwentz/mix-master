(defproject mix-master "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [ring/ring-defaults "0.2.1"]
                 [org.clojure/java.jdbc "0.7.0-alpha3"]
                 [org.postgresql/postgresql "9.3-1100-jdbc4"]
                 [stencil "0.5.0"]
                 [ring/ring-anti-forgery "1.1.0"]
                 [korma "0.4.3"]
                 [environ "1.1.0"]
                 [camel-snake-kebab "0.4.0"]]
  :plugins [[lein-ring "0.9.7"]
            [speclj "3.3.2"]
            [clj-sql-up "0.3.7"]
            [lein-environ "1.1.0"]]
  :ring {:handler mix-master.root/app}
  :test-paths ["spec"]
  :main mix-master.core
  :clj-sql-up {:database "postgresql://localhost:5432/mix-master"
               :database-test "postgresql://localhost:5432/mix-master-test"
               :deps [[org.postgresql/postgresql "9.3-1100-jdbc4"]]}
  :aliases {"spec" ["with-profile" "+spec" "spec"]})
