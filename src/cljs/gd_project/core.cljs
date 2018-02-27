(ns gd-project.core
  (:require
   [reagent.core :as r]
   [antizer.reagent :as ant]
   ))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Vars

(defonce app-state
  (r/atom {}))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn side-menu []
  [ant/menu {:mode "inline" :theme "dark" :style {:height "100%"}}
   [ant/menu-item {:disabled true} "Menu without icons"]
   [ant/menu-item "Menu Item"]
   [ant/menu-sub-menu {:title "Sub Menu"}
    [ant/menu-item "Item 1"]
    [ant/menu-item "Item 2"]]
   [ant/menu-item {:disabled true} "Menu with Icons"]
   [ant/menu-item (r/as-element [:span [ant/icon {:type "home"}] "Menu Item"])]
   [ant/menu-sub-menu {:title (r/as-element [:span [ant/icon {:type "setting"}] "Sub Menu"])}
    [ant/menu-item (r/as-element [:span [ant/icon {:type "user"}] "Item 1"])]
    [ant/menu-item (r/as-element [:span [ant/icon {:type "notification"}] "Item 2"])]]])


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
        ]]]]))

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
