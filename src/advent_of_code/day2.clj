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

(let [input "1,0,0,0,99"]
  (intcode-computer (to-program input)))
