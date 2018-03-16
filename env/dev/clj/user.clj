(ns user
  (:require 
            [mount.core :as mount]
            [gd-backend.core :refer [start-app]]))

(defn start []
  (mount/start-without #'gd-backend.core/repl-server))

(defn stop []
  (mount/stop-except #'gd-backend.core/repl-server))

(defn restart []
  (stop)
  (start))


