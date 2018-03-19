(ns gd-http-client.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [app.state :as state]))

(def base-url "http://127.0.0.1:3000/api")

;;; (:status :success :body :headers :trace-redirects :error-code :error-text)
;;; (:vendor :unit :equipment :main_category :id :url :sub_category :room :cdn_url)



(def cors-headers {"Access-Control-Allow-Headers" "Content-Type"
                   "Access-Control-Allow-Credentials" true
                   "Access-Control-Allow-Origin" "*"})

(go (let [response (<! (http/get (str base-url "/cameras")
                                 {:header cors-headers}))]
      (prn (keys response))
      (prn (keys (first (:body response))))
      #_(prn (map :login (:body response)))))


(defn build-camera-list []
  (go (let [response (<! (http/get (str base-url "/cameras")
                                   {:header cors-headers}))]
        (prn (keys response))
        (prn (keys (first (:body response))))
        (state/populate-cameras! (:body response))
        #_(prn (map :login (:body response)))))
  )


#_(defn get-all-cameras
    []
    (GET (str base-url "/cameras")
         {:headers {"Access-Control-Allow-Headers" "Content-Type"
                    "Access-Control-Allow-Origin" "*"}
          :handler (fn [respond]
                     (prn (keys respond)))}))
