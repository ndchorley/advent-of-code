(ns advent-of-code.day4.part2
  (:require
   [clojure.string]
   [advent-of-code.core :refer :all]
   [advent-of-code.day4.part1
    :refer
    [count-valid-passports passports]]))

(defn valid-year-between? [min max year-string]
  (let [year (Integer/parseInt year-string)]
    (and
     (>= year min)
     (<= year max))))

(defn parse-height [height-string]
  (Integer/parseInt
   (first
    (clojure.string/split
     height-string
     (re-pattern "[a-z]+")))))

(defn valid-height? [height-string]
  (let [height (parse-height height-string)]
    (or
     (and
      (clojure.string/ends-with? height-string "cm")
      (>= height 150)
      (<= height 193))
     (and
      (clojure.string/ends-with? height-string "in")
      (>= height 59)
      (<= height 76)))))

(defn valid? [passport]
  (let [expected-fields-to-valid
        {"byr" (partial valid-year-between? 1920 2002)
         "iyr" (partial valid-year-between? 2010 2020)
         "eyr" (partial valid-year-between? 2020 2030)
         "hgt" valid-height?
         "hcl" #(not (nil? (re-matches #"#[0-9a-f]{6}" %)))
         "ecl" #(contains?
                 #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"}
                 %)
         "pid" #(not (nil? (re-matches #"[0-9]{9}" %)))}]
    (every?
     true?
     (map
      (fn [[field valid-value?]]
        (and (contains? passport field)
             (valid-value? (passport field))))
      (seq expected-fields-to-valid)))))

(count-valid-passports valid? (passports (read-lines "day4")))
