(ns advent-of-code.day6
  (:require
   [advent-of-code.core :refer :all]
   [clojure.set]))

(defn count-distinct-questions [group]
  (count
   (apply
    clojure.set/union
    (map
     #(into #{} %)
     group))))

(apply
 +      
 (map
  count-distinct-questions
  (split-at-separator "" (read-lines "day6"))))

(defn count-common-questions [group]
  (count 
   (apply
    clojure.set/intersection
    (map #(into #{} %) group))))

(apply
 +
 (map
  count-common-questions
  (split-at-separator "" (read-lines "day6"))))
