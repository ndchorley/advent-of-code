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


(defn abs [x]
  (if (neg? x) (* -1 x) x))

(defn manhattan-distance [p q]
  (reduce + (map (fn [p-i q-i] (abs (- p-i q-i))) p q)))

(apply
 min
 (map (fn [p] (manhattan-distance [0 0] p))
      (set/intersection
       (to-points "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51")
       (to-points "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"))))
