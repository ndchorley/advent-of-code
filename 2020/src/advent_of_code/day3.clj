(ns advent-of-code.day3
  (:require [advent-of-code.core :refer :all]))

(def input-map (read-lines "day3"))

(defn points-visited [input-map [dx dy]]
  (let [width (count (first input-map))
        height (count input-map)]

    (take-while
     (fn [[x y]] (< y height))
     (map
      (fn [n] [(mod (* n dx) width) (* n dy)])
      (range)))))

(defn character-at [input-map [x y]]
  (nth (nth input-map y) x))

(defn tree? [character] (= character \#))

(defn count-trees [input-map slope]
  (count
   (filter
    tree? 
    (map
     (partial character-at input-map)
     (points-visited input-map slope)))))

(count-trees input-map [3 1])

(def slopes [[1 1] [3 1] [5 1] [7 1] [1 2]])

(apply
 *
 (map
  (partial count-trees input-map)
  slopes))
