(ns advent-of-code.day5)

(defn parse-operation [value]
  (let [ones (mod value 10)
        tens (mod (/ (- value ones) 10) 10)
        opcode (+ ones (* tens 10))]
    (cond
      (= opcode 1) +
      (= opcode 2) *
      ; Might need something else here!
      (= opcode 99) identity)))

(map parse-operation [1001 1102 1099])
