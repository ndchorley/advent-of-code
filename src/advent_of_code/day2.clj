(ns advent-of-code.day2
  (:require [clojure.string :as str]))


(defn execute [program opcode-index]
  (let [opcode (nth program opcode-index)]
    (defn do-operation [operation]
      (let [op1 (nth program (nth program (+ opcode-index 1)))
            op2 (nth program (nth program (+ opcode-index 2)))
            result-index (nth program (+ opcode-index 3))
            result (operation op1 op2)
            parts (split-at (+ 1 result-index) program)]
        (concat
         (butlast (first parts))
         [result]
         (last parts))
        )
      )
    (cond
      (= opcode 1) (execute (do-operation +) (+ opcode-index 4))
      (= opcode 2) (execute (do-operation *) (+ opcode-index 4))
      (= opcode 99) program)))

(defn intcode-computer [program-string]
  (let [program (map #(Integer/parseInt %) (str/split program-string #","))]
    (execute program 0)))
