(ns gd-backend.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [gd-backend.layout :refer [error-page]]
            [gd-backend.routes.home :refer [home-routes]]
            [gd-backend.routes.services :refer [final-service-routes]]
            [compojure.route :as route]
            [gd-backend.env :refer [defaults]]
            [mount.core :as mount]
            [gd-backend.middleware :as middleware]))

(mount/defstate init-app
  :start ((or (:init defaults) identity))
  :stop  ((or (:stop defaults) identity)))

(mount/defstate app
  :start
  (middleware/wrap-base
   (routes
    (-> #'home-routes
        (wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    #'final-service-routes
    (route/not-found
     (:body
      (error-page {:status 404
                   :title "page not found"}))))))
