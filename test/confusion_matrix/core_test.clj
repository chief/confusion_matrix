(ns confusion-matrix.core-test
  (:require [clojure.test :refer :all]
            [confusion-matrix.core :refer :all]))

(metrics-from "test/fixtures/result.csv")

(deftest test-true-positives
  (testing "when passing category"
    (is (= 5 (true-positives "cat")))))

(deftest test-false-positives
  (testing "when passing category"
    (is (= 2 (false-positives "cat")))))

(deftest test-false-negatives
  (testing "when passing category"
    (is (= 3 (false-negatives "cat")))))

(deftest test-true-negatives
  (testing "when passing category"
    (is (= 17 (true-negatives "cat")))))

(deftest test-sensitivity
  (testing "when passing category"
    (is (= (/ 5 (+ 5 3)) (sensitivity "cat") ))))

(deftest test-specificity
  (testing "when passing category"
    (is (= (/ 17 (+ 2 17)) (specificity "cat") ))))

(deftest test-precision
  (testing "when passing category"
    (is (= (/ 5 (+ 5 3)) (precision "cat") ))))

(deftest test-negative-predictive
  (testing "when passing category"
    (is (= (/ 17 (+ 17 3)) (negative-predictive "cat") ))))

(deftest test-fall-out
  (testing "when passing category"
    (is (= (- 1 (specificity "cat")) (fall-out "cat") ))))

(deftest test-miss-rate
  (testing "when passing category"
    (is (= (/ 3 (+ 3  5)) (miss-rate "cat") )) ))

(deftest test-accuracy
  (testing "when passing category"
    (is (= (/  (+ 5 17) (+ 3 5 2 17)) (accuracy "cat")))))

(deftest test-f1-score
  (testing "when passing category"
    (is (= 5/8 (f1-score  "cat")))))

(deftest test-classes
  (is (= '("cat" "dog" "rabbit") @classes)))

(deftest test-all
  (is (= (+ (true-positives "cat")
            (true-positives "dog")
            (true-positives "rabbit"))
         (all true-positives))))

(deftest test-micro-average-precision
  (is (= 19/65 (micro-average-precision))))

(deftest test-micro-average-recall
  (is (= 19/27 (micro-average-recall))))
