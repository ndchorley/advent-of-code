(ns advent-of-code.day2
  (:require [clojure.string :as str]))


(defn execute [program opcode-index]
  (let [opcode (nth program opcode-index)
        op1 (nth program (nth program (+ opcode-index 1)))
        op2 (nth program (nth program (+ opcode-index 2)))
        result-index (nth program (+ opcode-index 3))]
    [opcode op1 op2 result-index]
    ))

(defn intcode-computer []
  (let [program-string "1,0,0,0,99"
        program (map #(Integer/parseInt %) (str/split program-string #","))]
    (execute program 0)))
