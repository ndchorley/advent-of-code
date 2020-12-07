(ns advent-of-code.core
 (:require
   [clojure.java.io :as io]
   [clojure.string :as str]))

(defn read-lines [file]
  (str/split-lines (slurp (io/resource file))))

(defn indexes-of [what coll]
  (loop [index 0 result []]
    (if (>= index (count coll))
      result
      (recur
       (inc index)
       (if (= (coll index) what)
         (conj result index)
         result)))))

(defn boundaries [sep coll]
  (let [indexes (indexes-of sep coll)]
    (partition
     2
     (concat
      [0]
      (interleave
       indexes
       (map inc indexes))
      [(count coll)]))))

(defn split-at-separator [sep coll]
  (map
   (fn [[start end]]
     (subvec coll start end))
   (boundaries sep coll)))
