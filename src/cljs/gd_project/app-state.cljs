(ns app.state
  (:require [reagent.core :as r]
            [test.data :as test]))


(defonce app-state
  (r/atom {:user {:user-name "kevin"
                  :role :admin}
           :show-video true
           :cameras test/cameras
           }))

(defn update-cameras! [f & args]
  (apply swap! app-state update-in [:cameras] f args))

(defn add-camera! [c]
  (update-cameras! conj c))

(defn remove-camera! [c]
  (update-cameras! (fn [cs]
                     (vec (remove #(= % c) cs)))
                   c))

(defn modal-visable? []
  (get-in @app-state [:show-video]))
(defn flip-modal! []
  (swap! app-state update-in [:show-video] not))
