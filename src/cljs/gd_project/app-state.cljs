(ns app.state
  (:require [reagent.core :as r]
            [test.data :as test]))



(def app-state
  (r/atom {:user {:user-name "kevin"
                  :role :admin}
           :login? false
           :show-video false
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

;;; user

(defn user-name []
  (get-in @app-state [:user :user-name]))

(defn user-role []
  (get-in @app-state [:user :role]))




(defn login? []
  (:login? @app-state)
  )

(defn login! []
  (swap! app-state conj {:login? true}))

(defn logout! []
  (swap! app-state conj {:login? false}))


(defn admin? []
  (if (= :admin
         (user-role))
    true
    false))
