(ns gd-backend.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [gd-backend.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[gd-backend started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[gd-backend has shut down successfully]=-"))
   :middleware wrap-dev})
