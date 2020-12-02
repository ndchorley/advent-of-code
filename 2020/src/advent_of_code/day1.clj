(ns advent-of-code.day1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]))

(def values 
  (into
   #{}
   (map
    #(Integer/parseInt %)
    (str/split-lines (slurp (io/resource "day1"))))))

(defn pairs-with-sum-of-2020 [values]
  (apply
   hash-set
   (map
    #(hash-set % (- 2020 %))
    values)))

(apply
 *
 (first
  (filter
   #(set/subset? % values)
   (pairs-with-sum-of-2020 values))))
