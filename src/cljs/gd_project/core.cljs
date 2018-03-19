(ns gd-project.core
  (:require
   [reagent.core :as r]
   [camera.list :as c]
   [antizer.reagent :as ant]
   [test.data]
   [app.state :as state]
   [gd-http-client.core :as http]
   ))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(defn login-form []
  (fn []
    (let [my-form    (ant/get-form)
          form-style {:label-col {:span 3}
                      :wrapper-col {:span 20}}]
      [ant/form {:on-submit (fn [e]
                              (.preventDefault e)
                              (state/login!)
                              (http/build-camera-list))}
       [ant/form-item (merge form-style {:label "用户名："})
        (ant/decorate-field my-form "name" {:rules [{:required true}]}
                            [ant/input])]
       [ant/form-item (merge form-style {:label "密码："})
        (ant/decorate-field my-form "password" {:rules [{:required true}]}
                            [ant/input {:type "password"}])]
       [ant/form-item {:wrapper-col {:offset 6}}
        [ant/col {:span 4}
         [ant/button {:type "primary"
                      :size "large"
                      :html-type "submit"
                      }
          "登录"]]
        [ant/col {:offset 10}
         [ant/button {:size "large"
                      :on-click (fn []
                                  (ant/reset-fields my-form))}
          "取消"]]]])))

(defn login-modal-form []
  [ant/modal {:visible (not (state/login?))
              :title "登录"
              :footer nil
              :on-cancel #(state/login!)
              }
   (ant/create-form (login-form))])



(defn side-menu []
  [ant/menu {:mode "inline" :theme "dark" :style {:height "100%"}
             :on-click (fn [e]
                         (let [cljs-e (js->clj e)
                               item (cljs-e "key")]
                           (prn item)
                           (cond
                             (= (name :camera-item) item) (state/set-route! ::state/route-camera)
                             (= (name :management-item1) item) (state/set-route! ::state/item1)
                             )
                           )
                         )
             }
   [ant/menu-item {:disabled true} "Functions"]
   [ant/menu-item {:key :camera-item}
    (r/as-element [:span
                   [ant/icon {:type "home"}] "摄像头"])]
   [ant/menu-sub-menu {:title (r/as-element [:span [ant/icon {:type "setting"}] "管理"])}
    [ant/menu-item {:key :management-item1}
     (r/as-element [:span [ant/icon {:type "user"}] "Item 1"])]
    [ant/menu-item {:key :management-item2}
     (r/as-element [:span [ant/icon {:type "notification"}] "Item 2"])]]])

(defn content-area [app-state]
  [ant/layout-content {:class "content-ant"}
   [c/datatable app-state]
   [login-modal-form]

   (if (state/modal-visable?)
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
                 :type "application/x-mpegURL"}]]])
   ])


(defn render-layout []
  (fn []
    [ant/locale-provider {:locale (ant/locales "zh_CN")}
     [ant/layout
      [ant/affix
       [ant/layout-header {:class "banner"}
        (r/as-element
         [ant/row
          [ant/col {:span 12} [:h2.banner-header "爱青岛城"]]
          [ant/col {:span 2 :offset 10}
           [ant/dropdown {:overlay
                          (r/as-element [ant/menu
                                         [ant/menu-item {:target "_blank"
                                                         :rel="noopener noreferrer"
                                                         :href "http://www.alipay.com/"
                                                         }
                                          "登出"]])}
            [:a {:className "ant-dropdown-link" :href "#"} (state/user-name)
             [ant/icon {:type "down"}]]]]])]]

      [ant/layout
       [ant/layout-sider [side-menu]]
       [ant/layout {:style {:width "60%"}}
        (if (= ::state/route-camera (state/route))
          [content-area state/app-state])]]]]))

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
