(ns gd-project.core
  (:require
   [reagent.core :as r]
   [camera.list :as c]
   [antizer.reagent :as ant]
   [test.data]
   [app.state :as state]
   ))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Vars


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn side-menu []
  [ant/menu {:mode "inline" :theme "light" :style {:height "100%"}}
   [ant/menu-item {:disabled true} "标题"]
   [ant/menu-item "列表"]
   [ant/menu-sub-menu {:title "Sub Menu"}
    [ant/menu-item "Item 1"]
    [ant/menu-item "Item 2"]]
   [ant/menu-item {:disabled true} "Menu with Icons"]
   [ant/menu-item (r/as-element [:span [ant/icon {:type "home"}] "Menu Item"])]
   [ant/menu-sub-menu {:title (r/as-element [:span [ant/icon {:type "setting"}] "Sub Menu"])}
    [ant/menu-item (r/as-element [:span [ant/icon {:type "user"}] "Item 1"])]
    [ant/menu-item (r/as-element [:span [ant/icon {:type "notification"}] "Item 2"])]]])



(defn content-area [app-state]
  [ant/layout-content {:class "content-ant"}
   [c/datatable app-state]

   [ant/modal {:visible (state/modal-visable?)
               :footer nil
               :on-cancel (fn [] (state/flip-modal!))
               :width 436
               }
    [:video {:autoPlay "true"
             :width 400
             :height 400
             :controls true
             :preload "auto"}
     [:source {:src "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8"
               :type "application/x-mpegURL"}]]]
   ])


(defn render-layout []
  (fn []
    [ant/locale-provider {:locale (ant/locales "zh_CN")}
     [ant/layout
      [ant/affix
       [ant/layout-header {:class "banner"}
        (r/as-element
         [ant/row
          [ant/col {:span 12} [:h2.banner-header "标题"]]
          [ant/col {:span 1 :offset 11}
           [:a {:href "http://iqd.qtv.com.cn/"}
            [ant/icon {:class "banner-log" :type "compass"}]]]])]]
      [ant/layout
       [ant/layout-sider [side-menu]]
       [ant/layout {:style {:width "60%"}}
        [content-area state/app-state]]]]]))

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
