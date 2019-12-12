(ns advent-of-code.day3
  (:require [clojure.string :as str]))

(defn to-points [path]
  (let [start [0 0]
        direction (first path)
        length (Integer/parseInt (subs path 1))
        [dx dy] [1 0]]

    (reduce
     (fn [acc n] (conj acc [(+ (first start) (* n dx))
                            (+ (second start) (* n dy))]))
     (hash-set)
     (range 1 (inc length)))))

(to-points "R8")
