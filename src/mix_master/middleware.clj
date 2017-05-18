(ns mix-master.middleware
  (:require [clojure.string :as string]))

(defn- is-string-wrapped-int? [x]
  (if (string? x)
    (= (count (str (re-find #"\d+" x)))
       (count (str x)))
    false))

(defn- integerize [map-entry]
  (cond (string/includes? (key map-entry) "id") [(keyword (key map-entry)) (Integer. (val map-entry))]
        (is-string-wrapped-int? (key map-entry)) [(Integer. (key map-entry)) (val map-entry)]
        :else [(keyword (key map-entry)) (val map-entry)]))

(defn- integerize-params [params]
  (into {} (map integerize params)))


(defn wrap-integerize-params [handler]
  (fn [request]
    (->> (:params request)
         (integerize-params)
         (assoc request :params)
         (handler))))
