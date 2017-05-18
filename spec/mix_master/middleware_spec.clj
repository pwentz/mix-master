(ns mix-master.middleware-spec
  (:require [speclj.core :refer :all]
            [mix-master.playlists.root :refer :all]
            [mix-master.middleware :as sut]
            [ring.mock.request :as mock]))

(defn valid-handler [req]
  (:params req))

(def test-integerize-params
  (sut/wrap-integerize-params valid-handler))

(describe "Mix Master Middleware"
  (context "wrap-integerize-params"
    (it "can reformat params with string ids"
      (let [request (assoc (mock/request :get "/")
                           :params {"id" "12" "artist-id" "123" "name" "Cool Fred"})
            req-params (test-integerize-params request)]
        (should= {:id 12 :artist-id 123 :name "Cool Fred"}
                 req-params)))))
