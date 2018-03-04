(ns gd-project.core
  (:require
   [reagent.core :as r]
   [camera.list :as c]
   [antizer.reagent :as ant]
   ))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Vars

(defonce app-state
  (r/atom {:user {:user-name "kevin"
                  :role :admin}
           :show-video false
           }))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn side-menu []
  [ant/menu {:mode "inline" :theme "light" :style {:height "100%"}}
   [ant/menu-item {:disabled true} "摄像头相关"]
   [ant/menu-item "摄像头列表"]
   [ant/menu-sub-menu {:title "Sub Menu"}
    [ant/menu-item "Item 1"]
    [ant/menu-item "Item 2"]]
   [ant/menu-item {:disabled true} "Menu with Icons"]
   [ant/menu-item (r/as-element [:span [ant/icon {:type "home"}] "Menu Item"])]
   [ant/menu-sub-menu {:title (r/as-element [:span [ant/icon {:type "setting"}] "Sub Menu"])}
    [ant/menu-item (r/as-element [:span [ant/icon {:type "user"}] "Item 1"])]
    [ant/menu-item (r/as-element [:span [ant/icon {:type "notification"}] "Item 2"])]]])

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

(defn content-area []
  [ant/layout-content {:class "content-ant"}
   [c/datatable]
   [c/video-modal @app-state]
   ])


(defn render-layout []
  (fn []
    [ant/locale-provider {:locale (ant/locales "zh_CN")}
     [ant/layout
      [ant/affix
       [ant/layout-header {:class "banner"}
        (r/as-element
         [ant/row
          [ant/col {:span 12} [:h2.banner-header "健康厨房"]]
          [ant/col {:span 1 :offset 11}
           [:a {:href "http://iqd.qtv.com.cn/"}
            [ant/icon {:class "banner-log" :type "compass"}]]]])]]
      [ant/layout
       [ant/layout-sider [side-menu]]
       [ant/layout {:style {:width "60%"}}
        [content-area]]]]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Page

(defn page [ratom]

  [ant/button {:on-click #(ant/message-info "Hello Reagent!")} "Click me"])



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Initialize App

(defn dev-setup []
  (when ^boolean js/goog.DEBUG
    (enable-console-print!)
    (println "dev mode")
    ))

(defn reload []
  (r/render [render-layout]
            (.getElementById js/document "app")))

(defn ^:export main []
  (dev-setup)
  (reload))
