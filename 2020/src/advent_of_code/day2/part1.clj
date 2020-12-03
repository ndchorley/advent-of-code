(ns advent-of-code.day2.part1
  (:require [advent-of-code.core :refer :all]))

(defn parse-line [line]
  (let [[min-str max-str letter password] 
        (rest (re-matches
               (re-pattern "(\\d+)-(\\d+) ([a-z]): ([a-z]+)")
               line))]

    {:policy
     {:min (Integer/parseInt min-str)
      :max (Integer/parseInt max-str)
      :letter (first letter)}
     :password password}))

(defn valid-password? [password-and-policy]
  (let [{password :password policy :policy} password-and-policy
        letter-count (count
                      (filter
                       #(= (policy :letter) %)
                       password))]

    (and (>= letter-count (policy :min))
         (<= letter-count (policy :max)))))

(count
 (filter
  valid-password?
  (map
   parse-line
   (read-lines "day2"))))
