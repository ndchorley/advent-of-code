(ns advent-of-code.day3
  (:require [advent-of-code.core :refer :all]))

(def input-map (read-lines "day3"))

(defn points-visited [input-map]
  (let [width (count (first input-map))
        height (count input-map)]

    (take-while
     (fn [[x y]] (< y height))
     (map
      (fn [n] [(mod (* 3 n) width) n])
      (range)))))

(defn character-at [input-map [x y]]
  (nth (nth input-map y) x))

(defn tree? [character] (= character \#))

(count
 (filter
  tree? 
  (map
   (partial character-at input-map)
   (points-visited input-map))))
