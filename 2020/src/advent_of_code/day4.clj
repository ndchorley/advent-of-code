(ns advent-of-code.day4
  (:require
   [clojure.set]
   [advent-of-code.core :refer :all]))

(defn indexes-of [what coll]
  (loop [index 0 result []]
    (if (>= index (count coll))
      result
      (recur
       (inc index)
       (if (= (coll index) what)
         (conj result index)
         result)))))

(defn passport-boundaries [batch-file]
  (let [blank-line-indexes (indexes-of "" batch-file)]
    (partition
     2
     (concat
      [0] 
      (interleave
       blank-line-indexes
       (map inc blank-line-indexes))
      [(count batch-file)]))))

(defn passports [batch-file]
  (map
   (fn [[start end]] (subvec batch-file start end))
   (passport-boundaries batch-file)))

(defn extract-fields-in-line [line]
  (let [matcher
        (re-matcher #"((?<field>[a-z]+):[^\s]+)" line)]
    (map
     last
     (take-while
      #(not (nil? %))
      (repeatedly #(re-find matcher))))))

(defn fields [passport-lines]
  (into
   #{}
   (mapcat extract-fields-in-line passport-lines)))

(defn valid? [fields]
  (let [expected-fields
        #{"byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid"}]
    (=
     expected-fields
     (clojure.set/intersection fields expected-fields))))

(count 
 (filter
  valid?
  (map
   fields
   (passports (read-lines "day4")))))
