(ns advent-of-code.day5)

(defn extract-digits [value]
  (loop [rest value digits []]
    (if (zero? rest)
      digits
      (let [digit (mod rest 10)]
        (recur (/ (- rest digit) 10) (conj digits digit))))))

(defn parse-operation [value]
  (let [digits (extract-digits value)
        [opcode-digits parameter-mode-digits] (split-at 2 digits)
        opcode (+ (first opcode-digits) (* (second opcode-digits) 10))]
    {:operation
     (cond
       (= opcode 1) +
       (= opcode 2) *
       ; Might need something else here!
       (= opcode 99) identity)
     :parameter-modes parameter-mode-digits
     }))

(map parse-operation [1001 1102 1099])
