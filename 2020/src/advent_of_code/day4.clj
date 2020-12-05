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
  (let [blank-line-indexes
        (indexes-of "" batch-file)]
    (partition
     2
     (concat
      [0] 
      (interleave
       blank-line-indexes
       (map inc blank-line-indexes))
      [(count batch-file)]))))

(defn parse-line [line]
  (let [matcher
        (re-matcher
         #"((?<field>[a-z]+):(?<value>[^\s]+))"
         line)]
    (into
     {}
     (map
      #(subvec % (- (count %) 2) (count %))
      (take-while
       #(not (nil? %))
       (repeatedly #(re-find matcher)))))))

(defn parse-passport [lines]
  (apply
   merge
   (map parse-line lines)))

(defn group-passport-lines
  [batch-file boundaries]
  (map
   (fn [[start end]]
     (subvec batch-file start end))
   boundaries))

(defn passports [batch-file]
  (map
   parse-passport
   (group-passport-lines
    batch-file
    (passport-boundaries batch-file))))

(defn valid? [passport]
  (let [expected-fields
        #{"byr" "iyr" "eyr" "hgt"
          "hcl" "ecl" "pid"}]
    (=
     expected-fields
     (clojure.set/intersection
      (into #{} (keys passport))
      expected-fields))))

(defn count-valid-passports [valid? passports]
  (count
   (filter
    valid?
    passports)))

(count-valid-passports
 valid?
 (passports (read-lines "day4")))
