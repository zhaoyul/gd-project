(ns gd-backend.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [mount.core :as mount]
            [ring.middleware.cors :refer [wrap-cors]]
            [gd-backend.db.core :as db]))

(defn k-test []
  (db/get-cameras)
  )



(defapi service-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "爱青岛城市直播云平台后台API"
                           :description "爱青岛城市直播云平台API列表测试"}}}} 
  (context "/api" []
           :tags ["摄像头相关API"]

           (GET "/cameras" []
                                        ;:return      Long
                                        ;:query-params [x :- Long, {y :- Long 1}]
                :summary      "x+y with query-parameters. y defaults to 1."
                (ok (k-test)))

           (POST "/minus" []
                 :return      Long
                 :body-params [x :- Long, y :- Long]
                 :summary     "x-y with body-parameters."
                 (ok (- x y)))

           ))

(def final-service-routes
  (wrap-cors service-routes :access-control-allow-origin [#"http://.*"]
             :access-control-allow-methods [:get :put :post :delete]
             :access-control-allow-credentials true))



