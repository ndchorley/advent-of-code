(ns advent-of-code.day4.part1
  (:require
   [clojure.set]
   [advent-of-code.core :refer :all]))

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

(defn passports [batch-file]
  (map
   parse-passport
   (split-at-separator "" batch-file)))

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
