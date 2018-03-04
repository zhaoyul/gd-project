(ns camera.list
  (:require
   [reagent.core :as r]
   [antizer.reagent :as ant]
   [test.data :as t]
   ))

;;test address
;; http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8
;; http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8




(defn modal []
  (let [modal1 (r/atom false)
        modal-form (r/atom false)
        modal-loading (r/atom false)]
    (fn []
      [:div.example-button
       [:h2 "Modal"]
       [ant/button {:on-click #(reset! modal1 true)} "模式对话"]
       [ant/modal {:visible @modal1 :title "Title of modal"
                   :confirm-loading @modal-loading
                   :on-ok (fn []
                            (reset! modal-loading true)
                            (prn "cancel")
                            (js/setTimeout (fn []
                                             (reset! modal1 false)) 1000))
                   :on-cancel #(reset! modal1 false)}

        (r/as-element [:p "Some content 1"])]
       [ant/button {:on-click #(ant/modal-confirm {:title "Are you sure?" :content "Some content"})} "Confirmation Modal"]
       [ant/button {:on-click #(reset! modal-form true)} "Modal Form"]
       #_[ant/modal {:visible @modal-form :title "Modal Form" :width 600
                     :on-ok #(reset! modal-form false) :on-cancel #(reset! modal-form false)}
          (ant/create-form (user-form false))]])))

(defn video-modal [app-state]
  (let [show-modal (get-in [:show-video] app-state)]
    [ant/modal {:visible show-modal :title "video"
                :on-ok (fn []
                         (prn "cancel"))
                :on-cancel #(reset! modal1 false)}

     (r/as-element [:p "Some content 1"])]))


(defn add-actions-column [columns data-atom]
  (conj columns
        {:title "播放"
         :render
         #(r/as-element
           [ant/button {:icon "play-circle-o"
                        :on-click
                        (fn []
                          (reset! data-atom
                                  (remove (fn [d] (= (get (js->clj %2) "id")
                                                    (:id d))) @data-atom)))}]
           )}
        {:title "删除"
         :render
         #(r/as-element
           [ant/button {:icon "delete" :type "danger"
                        :on-click
                        (fn []
                          (reset! data-atom
                                  (remove (fn [d] (= (get (js->clj %2) "id")
                                                    (:id d))) @data-atom)))}])}))
(def pagination {:show-size-changer true
                 :page-size-options ["5" "10" "20"]
                 :show-total #(str "共计: " % " 个摄像头")})

(defn datatable [app-state]
  (let [data (r/atom t/people)]
    (fn []
      [:div
       [:h2 "摄像头列表"]
       [ant/table
        {:columns (add-actions-column t/columns data)
         :dataSource @data :pagination pagination :row-key "id"
         :row-selection
         {:on-change
          #(let [selected (js->clj %2 :keywordize-keys true)]
             (ant/message-info (str "You have selected: " (map :area selected))))}}]])))
