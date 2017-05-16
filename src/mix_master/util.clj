(ns mix-master.util
  (:require [stencil.core :as stncl]
            [camel-snake-kebab.core :refer [->snake_case
                                            ->kebab-case]]))

(defn render
  ([file-name] (render file-name {}))
  ([file-name map-to-render]
  (stncl/render-file (str "public/" file-name ".html")
                     map-to-render)))

(defn- case-format [to-case attrs]
  (into {}
    (map vector (map to-case (keys attrs))
                (vals attrs))))

(defn snake-case-keys [attrs]
  (case-format ->snake_case attrs))

(defn kebab-case-keys [attrs]
  (case-format ->kebab-case attrs))
