(defproject confusion_matrix "0.1.0-SNAPSHOT"
  :description "Metrics for classification algorithms performance."
  :url "https://github.com/chief/confusion_matrix"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.csv "0.1.3"]
                 [org.clojure/tools.cli "0.3.5"]]
  :main confusion-matrix.core
  :aot [confusion-matrix.core])
