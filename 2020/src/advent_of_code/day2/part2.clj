(ns advent-of-code.day2.part2
  (:require [advent-of-code.core :refer :all]
            [advent-of-code.day2.part1 :refer [parse-line]]))

(defn update-policy [password-and-policy]
  (let [{old-policy :policy} password-and-policy
        new-policy
        (merge
         (select-keys old-policy [:letter])
         {:positions [(old-policy :max) (old-policy :min)]})]

    (merge password-and-policy {:policy new-policy})))

(defn letter-at-position [password position]
  (nth password (- position 1)))

(defn valid-password? [password-and-policy]
  (let [{password :password policy :policy} password-and-policy]
    (= 1
       (count
        (filter
         #(= % (policy :letter))
         (map
          (partial letter-at-position password)
          (policy :positions)))))))

(def parse-line-with-updated-policy (comp update-policy parse-line))

(count 
 (filter
  valid-password?
  (map
   parse-line-with-updated-policy
   (read-lines "day2"))))
