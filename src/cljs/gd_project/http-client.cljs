(ns http-client.core
  (:require [ajax.core :refer [GET POST PUT]]
            [cljs.core.async :refer [put! chan <!]]))

(def base-url "http://127.0.0.1:3000/api")



(defn get-all-cameras
  []
  (GET (str base-url "/cameras")
       {:headers {"Access-Control-Allow-Headers" "Content-Type"
                  "Access-Control-Allow-Origin" "*"}}))

(prn (get-all-cameras))

(defn get-article
  [id]
  (GET (str "/admin2/articles/" id)))

(defn save-article
  [article]
  (PUT  (str "/admin2/articles/" (:id article)) {:params article}))
