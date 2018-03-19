(ns camera.list
  (:require
   [reagent.core :as r]
   [antizer.reagent :as ant]
   [test.data :as t]
   [app.state :as state]
   ))

;;test address
;; http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8
;; http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8

(defn preview-column [data-atom]
  {:title "播放"
   :render
   #(r/as-element
     [ant/button {:icon "play-circle-o"
                  :on-click
                  (fn []
                    (state/flip-modal!))}]
     )})

(defn delete-column [data-atom]
  (if (state/admin?)
    {:title "删除"
     :render
     #(r/as-element
       [ant/button {:icon "delete" :type "danger"
                    :on-click  (fn []
                                 (reset! data-atom
                                         (remove (fn [d] (= (get (js->clj %2) "id")
                                                           (:id d))) @data-atom)))}])}))

(defn remove-nil [input]
  (filter (complement nil?) input))


(defn add-actions-column [columns data-atom]
  (remove-nil(conj columns
                   (preview-column data-atom)
                   (delete-column data-atom)
                   )))

(def pagination {:show-size-changer true
                 :page-size-options ["5" "10" "20"]
                 :show-total #(str "共计: " % " 个摄像头")})

(defn datatable [app-state]
  (let [data (r/atom (@app-state :cameras))]
    (fn []
      [:div
       [:h2 "摄像头列表"]
       [ant/row {:span 24}
        [ant/col {:span 4 :offset 20}
         [ant/button {:icon "plus-circle-o" } "增加新摄像头"]]]

       [ant/table
        {:columns (add-actions-column t/columns (r/atom (@app.state :cameras)))
         :dataSource (@app-state :cameras) :pagination pagination :row-key "id"
         :row-selection
         {:on-change
          #(let [selected (js->clj %2 :keywordize-keys true)]
             (ant/message-info (str "You have selected: " (map :area selected))))}}]])))
