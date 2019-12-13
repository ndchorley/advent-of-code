(ns advent-of-code.day4
  (:require [clojure.string :as str]))

(defn to-digits [password]
  (map #(Integer/parseInt %) (str/split password #"")))

(defn contains-double? [digits]
  (boolean
   (some
    (fn [pair] (= (first pair) (last pair)))
    (partition 2 1 digits))))

(defn valid? [password]
  (let [digits (to-digits password)]
    (and
     (contains-double? digits)
     (apply <= digits))))

(count
 (filter
  #(valid? (str %))
  (range 168630 (inc 718098))))
