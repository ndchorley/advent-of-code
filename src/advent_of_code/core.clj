(ns advent-of-code.core
  (:gen-class)
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

(defn -main
  [& args]
  (run! println (map fuel [12 14 1969 100756])))
