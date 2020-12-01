(ns advent-of-code.day2
  (:require [clojure.string :as str]))

(defn replace-at [n coll replacement]
  (let [parts (split-at (inc n) coll)]
    (concat (butlast (first parts)) [replacement] (last parts))))

(defn execute [program opcode-index]
  (let [opcode (nth program opcode-index)]
    (defn do-operation [operation]
      (let [op1 (nth program (nth program (+ opcode-index 1)))
            op2 (nth program (nth program (+ opcode-index 2)))
            result-index (nth program (+ opcode-index 3))
            result (operation op1 op2)]
        (replace-at result-index program result)))
    (cond
      (= opcode 1) (execute (do-operation +) (+ opcode-index 4))
      (= opcode 2) (execute (do-operation *) (+ opcode-index 4))
      (= opcode 99) program)))

(defn intcode-computer [program] (execute program 0))

(defn to-program [program-string]
   (map #(Integer/parseInt %) (str/split program-string #",")))

(let [input "1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,13,1,19,1,19,10,23,1,23,6,27,1,6,27,31,1,13,31,35,1,13,35,39,1,39,13,43,2,43,9,47,2,6,47,51,1,51,9,55,1,55,9,59,1,59,6,63,1,9,63,67,2,67,10,71,2,71,13,75,1,10,75,79,2,10,79,83,1,83,6,87,2,87,10,91,1,91,6,95,1,95,13,99,1,99,13,103,2,103,9,107,2,107,10,111,1,5,111,115,2,115,9,119,1,5,119,123,1,123,9,127,1,127,2,131,1,5,131,0,99,2,0,14,0"]
  (intcode-computer (replace-at 2 (replace-at 1 (to-program input) 12) 2)))
