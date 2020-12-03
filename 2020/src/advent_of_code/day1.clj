(ns advent-of-code.day1
  (:require
   [clojure.set :as set]
   [advent-of-code.core :refer :all]))

(def values 
  (into
   #{}
   (map
    #(Integer/parseInt %)
    (read-lines "day1"))))

(defn pairs-with-sum [sum values]
  (map
   #(hash-set % (- sum %))
   values))

(apply
 *
 (first
  (filter
   #(set/subset? % values)
   (pairs-with-sum 2020 values))))

(apply
 *
 (first
  (filter
   #(= (count %) 3)
   (apply
    hash-set
    (map
     (fn [value]
       (let [rest (- 2020 value)
             values-remaining (disj values value)
             valid-pair-with-sum-rest
             (first (filter
                     #(set/subset? % values)
                     (pairs-with-sum rest values-remaining)))]
         (conj valid-pair-with-sum-rest value)))
     values)))))
