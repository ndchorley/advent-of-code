(ns advent-of-code.day1
  (:require [clojure.java.io :as io]))

(defn fuel [mass]
  (-
   (.longValue (/ mass 3))
   2))

(defn fuel-requirements []
  (let [url (io/resource "day_1/input")]
    (with-open [reader (io/reader url)]
      (reduce
       + 
       (map #(fuel (Integer/parseInt %)) (.toArray (.lines reader)))))))


