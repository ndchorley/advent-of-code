(ns advent-of-code.day3
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn to-points [path]
  (defn build-points [path-segments points start]
    (if (empty? path-segments) points
        (let [current-segment (first path-segments)
              direction (first current-segment)
              length (Integer/parseInt (subs current-segment 1))
              [dx dy] (cond
                        (= direction \R) [1 0]
                        (= direction \L) [-1 0]
                        (= direction \U) [0 1]
                        (= direction \D) [0 -1])
              new-points (reduce
                          (fn [acc n] (conj acc [(+ (first start) (* n dx))
                                                 (+ (second start) (* n dy))]))
                          points
                          (range 1 (inc length)))
              new-start [(+ (first start) (* length dx)) (+ (second start) (* length dy))]]

          (build-points (rest path-segments) new-points new-start))))
  (build-points (str/split path #",") (hash-set) [0 0]))


(set/intersection (to-points "R8,U5,L5,D3") (to-points "U7,R6,D4,L4"))
