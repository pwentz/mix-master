(ns mix-master.artists.songs.root-spec
  (:require [speclj.core :refer :all]
            [mix-master.artists.songs.root :refer :all]
            [ring.mock.request :refer [request]]
            [mix-master.db.core :as db]))

(describe "songs-routes"
  (context "/"
    (it "responds with a 200 status"
      (let [request (assoc (request :get "/") :params {:artist-id "1"})
            response (songs-handler request)]
        (should= 200 (:status response)))))

  (context "/new"
    (it "responds with a 200 status"
      (let [response (songs-handler (request :get "/new"))]
        (should= 200 (:status response)))))

  (context "/create"
    (before
      (db/create :artists {:name "Bob Marley" :id 12}))

    (after
      (db/delete-all :songs)
      (db/delete-all :artists))

    (it "creates a song"
      (let [response (songs-handler (assoc (request :post "/create")
                                           :params
                                           {:song-title "One Love"
                                            :artist-id "12"}))]
        (should= 302 (:status response))
        (should= 12
                 (:artist-id (db/find-first :songs {:title "One Love"})))))))
