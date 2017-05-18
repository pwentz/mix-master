;; migrations/20170516181427946-create-playlist-songs.clj

(defn up []
  ["CREATE TABLE playlist_songs(id int, playlist_id int REFERENCES playlists(id), song_id int REFERENCES songs(id), PRIMARY KEY (id))"
   "CREATE INDEX playlist_songs_on_playlist_id ON playlist_songs(playlist_id)"
   "CREATE INDEX playlist_songs_on_song_id ON playlist_songs(song_id)"])

(defn down []
  ["DROP TABLE playlist_songs CASCADE"])
