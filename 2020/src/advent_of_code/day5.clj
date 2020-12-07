(ns advent-of-code.day5
  (:require [advent-of-code.core :refer :all]))

(defn binary-space-partition
  [string range lower-char]
  (first
   (reduce
    (fn [[min max] value]
      (let [mid (+ min (/ (dec (- max min)) 2))]
        (if (= value lower-char)
          [min mid]
          [(inc mid) max])))
    range
    string)))

(defn to-seat-id [[row-string column-string]]
  (let [row
        (binary-space-partition
         row-string
         [0 127]
         \F)
        column
        (binary-space-partition
         column-string
         [0 7]
         \L)]
  (+ (* row 8) column)))


(def seat-ids
  (sort
   (map
    to-seat-id
    (map
     #(split-at 7 %)
     (read-lines "day5")))))

(last seat-ids)

(first
 (drop-while
  (fn [[first second]]
    (not (= (- second first) 2)))
  (partition
   2
   1
   seat-ids)))
