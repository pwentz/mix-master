(require '[clojure.java.jdbc :as j])

(def pg-uri
  {:connection-uri (str "postgresql://localhost:5432/mix-master")})
