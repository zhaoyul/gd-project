(ns test.data)

(defn comparison [data1 data2 field]
  (compare (get (js->clj data1 :keywordize-keys true) field)
           (get (js->clj data2 :keywordize-keys true) field)))

;; we need to use dataIndex instead of data-index, see README.md
(def columns [{:title "区" :dataIndex "area" :sorter #(comparison %1 %2 :area)}
              {:title "地址" :dataIndex "address" :sorter #(comparison %1 %2 :address)}
              {:title "状态" :dataIndex "status" :sorter #(comparison %1 %2 :status )}])

(def cameras [{:id 1 :area "广电" :status "没有服务" :address "城阳食药监>国城小学>后厨炒菜间南"
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
