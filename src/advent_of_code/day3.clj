(ns advent-of-code.day3
  (:require [clojure.string :as str]))

(defn to-points [path]
  (let [start [0 0]
        direction (first path)
        length (Integer/parseInt (subs path 1))]

    (reduce
     (fn [acc x] (conj acc [x (second start)]))
     (hash-set)
     (range 1 (inc length)))))

(to-points "R8")
