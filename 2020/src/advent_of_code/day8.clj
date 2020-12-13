(ns advent-of-code.day8
  (:require
   [advent-of-code.core :refer :all]
   [clojure.string :as str]))

(defn parse-line [line]
  (let [[operation argument-string]
        (str/split line #" ")]
    [operation
     (Integer/parseInt argument-string)]))

(defn execute [instructions]
  (loop [offset 0 accumulator 0 offsets-seen #{}]
    (if (contains? offsets-seen offset)
      accumulator
      (let [[operation argument]
            (instructions offset)
            [next-offset new-accumulator]
            (cond
              (= operation "nop") [(inc offset)
                                   accumulator]
              (= operation "acc") [(inc offset)
                                   (+
                                    accumulator
                                    argument)]
              (= operation "jmp") [(+ offset argument)
                                   accumulator]
              )]
        (recur
         next-offset
         new-accumulator
         (conj offsets-seen offset))))))

(execute
 (mapv
  parse-line
  (read-lines "day8")))
