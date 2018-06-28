(defproject sudoku-server "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                  [org.clojure/clojure "1.8.0"]
                  [com.novemberain/monger "3.1.0"]
                  [metosin/compojure-api "2.0.0-alpha21"]]
  :main ^:skip-aot sudoku-server.core
  :target-path "target/%s"
  :ring {:handler sudoku-server.core/app
         :nrepl {:start? true}}
  :profiles {:dev {:plugins [[lein-ring "0.12.4"]]}})
