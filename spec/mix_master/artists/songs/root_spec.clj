(ns mix-master.artists.songs.root-spec
  (:require [speclj.core :refer :all]
            [mix-master.artists.songs.root :refer :all]
            [ring.mock.request :refer [request]]
            [mix-master.artists.repository :as artists]
            [mix-master.artists.songs.repository :as db]))

(describe "songs-routes"
  (context "/"
    (it "responds with a 200 status"
      (let [response (songs-handler (request :get "/"))]
        (should= 200 (:status response)))))

  (context "/new"
    (it "responds with a 200 status"
      (let [response (songs-handler (request :get "/new"))]
        (should= 200 (:status response)))))

  (context "/post"
    (before
      (artists/create {:name "Bob Marley" :id 12}))

    (after
      (db/delete-all)
      (artists/delete-all))

    (it "creates a song"
      (let [response (songs-handler (assoc (request :post "/create")
                                           :params
                                           {:song-title "One Love"
                                            :artist-id 12}))]
        (should= 200 (:status response))
        (should= 12
                 (:artist-id (db/find-first {:title "One Love"})))))))
