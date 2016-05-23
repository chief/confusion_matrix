(ns confusion-matrix.core
  (:use [clojure.java.io :only (reader)])
  (:require [clojure.data.csv :as csv]
            [clojure.string :as string]
            [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

(def ^:dynamic data (atom []))
(def ^:dynamic classes (atom []))

(defn load-data
  [filename]
  (with-open [in-file (reader filename)]
    (doall
     (csv/read-csv in-file))))

(defn metrics-from
  [filename]
  (reset! data (load-data filename))
  (reset! classes (distinct (map first @data))))

(defn true-positives
  [klass]
  (count (filter #(and (= (first %) klass) (apply = %)) @data)))

(defn false-positives
  [klass]
  (count (filter #(and (= (last %) klass) (not= (first %) klass)) @data)))

(defn false-negatives
  [klass]
  (count (filter #(and (= (first %) klass) (not= (last %) klass)) @data)))

(defn true-negatives
  [klass]
  (count (filter #(and (not= (first %) klass) (not= (last %) klass)) @data)))

(defn N [klass]
  (+ (false-positives klass) (true-negatives klass)))

(defn P [klass]
  (+ (true-positives klass) (false-negatives klass)))

(defn sensitivity [klass]
  (/ (true-positives klass) (P klass)))

(defn recall [klass]
  (sensitivity klass))

(defn specificity [klass]
  (/ (true-negatives klass) (N klass)))

(defn precision [klass]
  (/ (true-positives klass) (+ (true-positives klass) (false-negatives klass))))

(defn negative-predictive [klass]
  (/ (true-negatives klass) (+ (true-negatives klass) (false-negatives klass))))

(defn fall-out [klass]
  (- 1 (specificity klass)))

(defn false-discovery-rate [klass]
  (- 1 (precision klass)))

(defn miss-rate [klass]
  (/ (false-negatives klass) (P klass)))

(defn accuracy [klass]
  (/ (+ (true-positives klass) (true-negatives klass)) (+ (P klass) (N klass))))

(defn harmonic-mean [x y]
  (/ (* 2 x y) (+ x y)))

(defn f1-score [klass]
  (harmonic-mean (precision klass) (recall klass)))

(defn all [metric-fn]
  (reduce + (map metric-fn @classes)))

(defn micro-average-precision []
  (/ (all true-positives) (+ (all true-positives) (all true-negatives))))

(defn micro-average-recall []
  (/ (all true-positives) (+ (all true-positives) (all false-negatives))))

(defn micro-average-fscore []
  (harmonic-mean (micro-average-precision) (micro-average-recall)))

(defn macro-average-precision []
  (/ (all precision) (count @classes)))

(defn macro-average-recall []
  (/ (all recall) (count @classes)))

(defn macro-average-fscore[]
  (harmonic-mean (macro-average-precision) (macro-average-recall)))

(def cli-options
  [["-f" "--file FILE" "a CSV file with the actual and predicted data"]
   ["-h" "--help"]])

(defn usage [options-summary]
  (->> ["Generates confusion matrix metrics."
        ""
        "Usage: confusion-matrix [options]"
        ""
        "Options:"
        options-summary
        ""
        "Please refer to the manual page for more information."]
       (string/join \newline)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (string/join \newline errors)))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn display []
  (->> ["macro-average fscore:" (macro-average-fscore)
        "macro-average recall:" (macro-average-recall)
        "macro-average precision:" (macro-average-precision)
        "micro-average fscore:" (micro-average-fscore)
        "micro-average recall:" (micro-average-recall)
        "micro-average precision:" (micro-average-precision)
        ]
       (string/join \newline)))

(defn -main [& args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    ;; Handle help and error conditions
    (cond
      (:help options) (exit 0 (usage summary))
      (not= (count options) 1) (exit 1 (usage summary))
      errors (exit 1 (error-msg errors)))
    ;; Execute program with options
    (metrics-from (:file options))
    (exit 0 (display))
    ))
