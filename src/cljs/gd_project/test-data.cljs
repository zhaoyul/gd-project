(ns test.data)

(defn comparison [data1 data2 field]
  (compare (get (js->clj data1 :keywordize-keys true) field)
           (get (js->clj data2 :keywordize-keys true) field)))

;;;  (:vendor :unit :equipment :main_category :id :url :sub_category :room :cdn_url

;; we need to use dataIndex instead of data-index, see README.md
(def columns [{:title "ID" :dataIndex "id" :sorter #(comparison %1 %2 :id)}
              {:title "单位名称" :dataIndex "unit" :sorter #(comparison %1 %2 :unit)}
              #_{:title "区" :dataIndex "unit" :sorter #(comparison %1 %2 :unit)}
              {:title "大分类" :dataIndex "main_category" :sorter #(comparison %1 %2 :main_category)}
              {:title "小分类" :dataIndex "sub_category" :sorter #(comparison %1 %2 :sub_category)}
              {:title "标签" :dataIndex "room" :sorter #(comparison %1 %2 :room)}
              {:title "原地址" :dataIndex "url" :sorter #(comparison %1 %2 :url)}
              {:title "CDN地址" :dataIndex "cdn_url" :sorter #(comparison %1 %2 :cdn_url)}
              {:title "设备编号" :dataIndex "equipment" :sorter #(comparison %1 %2 :equipment)}
              ])

#_(def cameras [{:id 1 :area "广电" :status "没有服务" :address "城阳食药监>国城小学>后厨炒菜间南"
                 :service "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8"}
                {:id 2 :area "李沧" :status "没有服务" :address "三期>大枣园幼儿园1"
                 :service "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8"}
                {:id 3 :area "平度" :status "没有服务" :address "平度市万福肉食>万福肉食"
                 :service "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8"}
                ])


(def users [{:name "Kevin Li1" :unit "Haier Group1" :role :admin}
            {:name "Kevin Li2" :unit "Haier Group2" :role :city-admin}
            {:name "Kevin Li3" :unit "Haier Group3" :role :unit-admin}
            {:name "Kevin Li4" :unit "Haier Group4" :role :audit-admin}
            {:name "Kevin Li5" :unit "Haier Group5" :role :admin}
            ])
