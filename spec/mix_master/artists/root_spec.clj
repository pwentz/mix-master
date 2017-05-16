(ns mix-master.artists.root-spec
  (:require [speclj.core :refer :all]
            [mix-master.artists.root :refer :all]
            [ring.mock.request :refer [request]]
            [mix-master.db.core :as db]))

(describe "artists-routes"
  (context "/"
    (it "responds with a 200"
      (let [response (artists-handler (request :get "/"))]
        (should= 200 (:status response)))))

  (context "/new"
    (it "responds with a 200 status"
      (let [response (artists-handler (request :get "/new"))]
        (should= 200 (:status response)))))

  (context "/create"
    (after
      (db/delete-all :artists))

    (it "responds with a 302 status"
        (let [response (artists-handler (assoc (request :post "/create")
                                               :form-params
                                               {"name" "U2" "image" "www.u2.com"}))]
          (should= 302 (:status response))))

    (it "creates a new artist instance if fields are valid"
        (let [form-params {"artist-name" "U2" "artist-image" "www.u2.com"}
              response (artists-handler (assoc (request :post "/create")
                                               :form-params
                                               form-params))
              new-artist (db/find-first :artists {:name "U2"
                                                  :image "www.u2.com"})]
          (should= "U2" (:name new-artist))
          (should= "www.u2.com" (:image new-artist))
          (should= 1 (db/count-of :artists)))))

  (context "/:artist-id/songs"
    (before
      (db/create :artists {:name "Foo Fighters"}))

    (after
      (db/delete-all :artists))

    (it "responds with a 200 status"
      (let [artist-id (:id (db/find-first :artists {:name "Foo Fighters"}))
            response (artists-handler (request :get (str "/" artist-id)))]
        (should= 200 (:status response))))))
