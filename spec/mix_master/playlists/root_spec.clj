(ns mix-master.playlists.root-spec
  (:require [speclj.core :refer :all]
            [mix-master.playlists.root :refer :all]
            [ring.mock.request :refer [request]]
            [mix-master.db.core :as db]))

(describe "playlists-routes"
  (context "/"
    (it "responds with a 200"
      (let [response (playlists-handler (request :get "/"))]
        (should= 200 (:status response)))))

  (context "/new"
    (it "responds with a 200"
      (let [response (playlists-handler (request :get "/new"))]
        (should= 200 (:status response)))))

  (context "/create"
    (before
      (db/create :artists {:name "Bob Marley" :id 1})
      (db/create :songs {:title "I Shot the Sheriff" :artist-id 1 :id 10})
      (db/create :songs {:title "One Love" :artist-id 1 :id 11})
      (db/create :songs {:title "Buffalo Solder" :artist-id 1 :id 12}))

    (after
      (db/delete-all :playlist-songs)
      (db/delete-all :playlists)
      (db/delete-all :songs)
      (db/delete-all :artists))

    (it "creates a playlist with selected songs"
      (let [req (assoc (request :post "/create")
                       :params
                       {:playlist-name "My Jamz", 10 "on", 12 "on"})
            response (playlists-handler req)]
        (should= "My Jamz" (:name (db/find-first :playlists {:name "My Jamz"})))
        (should= #{10 12} ()))
      )))
