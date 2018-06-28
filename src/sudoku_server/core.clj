(ns sudoku-server.core
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [monger.core :as mg]
            [monger.collection :as mc]
            [monger.conversion :refer [from-db-object]]
            [schema.core :as s]))

(s/defschema Pizza
  {:name s/Str
   (s/optional-key :description) s/Str
   :size (s/enum :L :M :S)
   :origin {:country (s/enum :FI :PO)
            :city s/Str}})
(defn save-to-db [username score]
  (let [conn (mg/connect-via-uri "mongodb://127.0.0.1/sudoku")
      ;db   (mg/get-db conn "sudoku")]
        db (:db conn)]
   (mc/insert db "score" {:username username
                          :score score})))
(defn get-score [username]
  (let [conn (mg/connect-via-uri "mongodb://127.0.0.1/sudoku")
      ;db   (mg/get-db conn "sudoku")]
        db (:db conn)]
    (mc/find-maps db "score" {:username username})))

(def app
  (api
   {:swagger
    {:ui "/"
     :spec "/swagger.json"
     :data {:info {:title "Simple"
                   :description "Compojure Api example"}
            :tags [{:name "api", :description "some apis"}]}}}

   (context "/api" []
     :tags ["api"]

     (GET "/plus" []
       :return {:result Long}
       :query-params [x :- Long, y :- Long]
       :summary "adds two numbers together"
       (ok {:result (+ x y)}))
     (POST "/echo-str" []
       :query-params [x :- String]
       (ok x))
     (POST "/get-score" []
       :query-params [username :- String]
       (ok (get-score username)))
     (POST "/save-score" []
       :query-params [username :- String, score :- Long]
       (save-to-db username score)
       (ok {:username username
            :score   score})))))
    ;  (POST "/echo" []
      ;  :return Pizza
      ;  :body [pizza Pizza]
      ;  :summary "echoes a Pizza"
      ;  (ok pizza)))))

