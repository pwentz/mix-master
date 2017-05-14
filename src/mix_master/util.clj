(ns mix-master.util
  (:require [stencil.core :as stncl]))

(defn render
  ([file-name] (render file-name {}))
  ([file-name map-to-render]
  (stncl/render-file (str "public/" file-name ".html")
                     map-to-render)))
