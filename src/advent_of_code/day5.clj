(ns advent-of-code.day5
  (:require [advent-of-code.day2 :as day2]))

(defn extract-digits [value]
  (loop [rest value digits []]
    (if (zero? rest)
      (conj digits 0)
      (let [digit (mod rest 10)]
        (recur (/ (- rest digit) 10) (conj digits digit))))))

(defn default-missing-mode-bits [bits expected-count]
  (let [actual-count (count bits)]
    (if (= actual-count expected-count) bits
        (concat bits (repeat (- expected-count actual-count) 0)))))

(defn to-position-mode [bit] (if (= 0 bit) :position :immediate))

(defn parse-operation [value inputs]
  (let [digits (extract-digits value)
        [opcode-digits parameter-mode-bits] (split-at 2 digits)
        opcode (+ (first opcode-digits) (* (second opcode-digits) 10))]
    (cond
      (= opcode 1) {:operation +
                    :parameter-modes (map to-position-mode (default-missing-mode-bits parameter-mode-bits 3))}
      (= opcode 2) {:operation *
                    :parameter-modes (map to-position-mode (default-missing-mode-bits parameter-mode-bits 3))}
      (= opcode 3) {:operation (fn [] (first inputs)) :parameter-modes [:position]}
      (= opcode 4) :output
      (= opcode 99) :stop)))

(defn parameter-values [program operation-index modes]
  (map-indexed
   (fn [i mode]
     (if (= :position mode)
       (nth program (nth program (+ operation-index i 1)))
       (nth program (+ operation-index i 1))))
   modes))

(defn intcode-computer [inputs program]
  (loop [current-program program operation-index 0]
    (let [operation (parse-operation (nth current-program operation-index) inputs)]
      (cond
        (= operation :stop) current-program
        (= operation :output) (do
                                (println (first (parameter-values current-program operation-index [:position])))
                                (recur current-program (+ operation-index 2)))
        true (let [parameters (parameter-values current-program operation-index (butlast (:parameter-modes operation)))
              result-index (nth current-program (+ operation-index (count (:parameter-modes operation))))
              result (apply (:operation operation) parameters)]
               (recur
                (day2/replace-at result-index current-program result)
                (+ operation-index (count (:parameter-modes operation)) 1)))))))

(map
 (comp (partial intcode-computer []) day2/to-program)
 ["1002,4,3,4,33" "1101,100,-1,4,0"])

(intcode-computer [21] (day2/to-program "3,0,4,0,99"))

(intcode-computer [1] (day2/to-program "3,225,1,225,6,6,1100,1,238,225,104,0,1102,46,47,225,2,122,130,224,101,-1998,224,224,4,224,1002,223,8,223,1001,224,6,224,1,224,223,223,1102,61,51,225,102,32,92,224,101,-800,224,224,4,224,1002,223,8,223,1001,224,1,224,1,223,224,223,1101,61,64,225,1001,118,25,224,101,-106,224,224,4,224,1002,223,8,223,101,1,224,224,1,224,223,223,1102,33,25,225,1102,73,67,224,101,-4891,224,224,4,224,1002,223,8,223,1001,224,4,224,1,224,223,223,1101,14,81,225,1102,17,74,225,1102,52,67,225,1101,94,27,225,101,71,39,224,101,-132,224,224,4,224,1002,223,8,223,101,5,224,224,1,224,223,223,1002,14,38,224,101,-1786,224,224,4,224,102,8,223,223,1001,224,2,224,1,223,224,223,1,65,126,224,1001,224,-128,224,4,224,1002,223,8,223,101,6,224,224,1,224,223,223,1101,81,40,224,1001,224,-121,224,4,224,102,8,223,223,101,4,224,224,1,223,224,223,4,223,99,0,0,0,677,0,0,0,0,0,0,0,0,0,0,0,1105,0,99999,1105,227,247,1105,1,99999,1005,227,99999,1005,0,256,1105,1,99999,1106,227,99999,1106,0,265,1105,1,99999,1006,0,99999,1006,227,274,1105,1,99999,1105,1,280,1105,1,99999,1,225,225,225,1101,294,0,0,105,1,0,1105,1,99999,1106,0,300,1105,1,99999,1,225,225,225,1101,314,0,0,106,0,0,1105,1,99999,1008,677,226,224,1002,223,2,223,1005,224,329,1001,223,1,223,107,677,677,224,102,2,223,223,1005,224,344,101,1,223,223,1107,677,677,224,102,2,223,223,1005,224,359,1001,223,1,223,1108,226,226,224,1002,223,2,223,1006,224,374,101,1,223,223,107,226,226,224,1002,223,2,223,1005,224,389,1001,223,1,223,108,226,226,224,1002,223,2,223,1005,224,404,1001,223,1,223,1008,677,677,224,1002,223,2,223,1006,224,419,1001,223,1,223,1107,677,226,224,102,2,223,223,1005,224,434,1001,223,1,223,108,226,677,224,102,2,223,223,1006,224,449,1001,223,1,223,8,677,226,224,102,2,223,223,1006,224,464,1001,223,1,223,1007,677,226,224,1002,223,2,223,1006,224,479,1001,223,1,223,1007,677,677,224,1002,223,2,223,1005,224,494,1001,223,1,223,1107,226,677,224,1002,223,2,223,1006,224,509,101,1,223,223,1108,226,677,224,102,2,223,223,1005,224,524,1001,223,1,223,7,226,226,224,102,2,223,223,1005,224,539,1001,223,1,223,8,677,677,224,1002,223,2,223,1005,224,554,101,1,223,223,107,677,226,224,102,2,223,223,1006,224,569,1001,223,1,223,7,226,677,224,1002,223,2,223,1005,224,584,1001,223,1,223,1008,226,226,224,1002,223,2,223,1006,224,599,101,1,223,223,1108,677,226,224,102,2,223,223,1006,224,614,101,1,223,223,7,677,226,224,102,2,223,223,1005,224,629,1001,223,1,223,8,226,677,224,1002,223,2,223,1006,224,644,101,1,223,223,1007,226,226,224,102,2,223,223,1005,224,659,101,1,223,223,108,677,677,224,1002,223,2,223,1006,224,674,1001,223,1,223,4,223,99,226"))
