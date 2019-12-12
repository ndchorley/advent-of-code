(ns advent-of-code.day3
  (:require [clojure.string :as str]))

(defn to-points [path]
  (let [start [0 0]
        direction (first path)
        length (Integer/parseInt (subs path 1))
        [dx dy] (cond
                  (= direction \R) [1 0]
                  (= direction \L) [-1 0]
                  (= direction \U) [0 1]
                  (= direction \D) [0 -1])]

    (reduce
     (fn [acc n] (conj acc [(+ (first start) (* n dx))
                            (+ (second start) (* n dy))]))
     (hash-set)
     (range 1 (inc length)))))

(to-points "D6")
