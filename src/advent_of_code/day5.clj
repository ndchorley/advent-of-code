(ns advent-of-code.day5
  (:require [advent-of-code.day2 :as day2]))

(defn extract-digits [value]
  (loop [rest value digits []]
    (if (zero? rest)
      digits
      (let [digit (mod rest 10)]
        (recur (/ (- rest digit) 10) (conj digits digit))))))

(defn default-missing-mode-bits [bits expected-count]
  (let [actual-count (count bits)]
    (if (= actual-count expected-count) bits
        (concat bits (repeat (- expected-count actual-count) 0)))))

(defn to-position-mode [bit] (if (= 0 bit) :position :immediate))

(defn parse-operation [value]
  (let [digits (extract-digits value)
        [opcode-digits parameter-mode-bits] (split-at 2 digits)
        opcode (+ (first opcode-digits) (* (second opcode-digits) 10))]
    (cond
      (= opcode 1) {:operation +
                    :parameter-modes (map to-position-mode (default-missing-mode-bits parameter-mode-bits 3))}
      (= opcode 2) {:operation *
                    :parameter-modes (map to-position-mode (default-missing-mode-bits parameter-mode-bits 3))}
      (= opcode 99) :stop)))

(defn parameter-values [program operation-index modes]
  (map-indexed
   (fn [i mode]
     (if (= :position mode)
       (nth program (nth program (+ operation-index i 1)))
       (nth program (+ operation-index i 1))))
   modes))

(defn intcode-computer [program]
  (loop [current-program program operation-index 0]
    (let [operation (parse-operation (nth current-program operation-index))]
      (if (= operation :stop) current-program
          (let [parameters (parameter-values current-program operation-index (butlast (:parameter-modes operation)))
                result-index (+ operation-index (count (:parameter-modes operation)) 1)
                result (apply (:operation operation) parameters)]
            (recur
             (day2/replace-at result-index current-program result)
             (+ operation-index (count (:parameter-modes operation)) 1)))))))

(intcode-computer (day2/to-program "1002,4,3,4,33"))
