(ns advent-of-code.day1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def values 
  (into
   #{}
   (map
    #(Integer/parseInt %)
    (str/split-lines (slurp (io/resource "day1"))))))

(defn pair-values-with-rest [values]
  (map
   (fn [value] {:value value :rest (- 2020 value)})
   values))

(apply
 *
 (vals 
  (first
   (filter
    #(contains? values (% :rest))
    (pair-values-with-rest values)))))
