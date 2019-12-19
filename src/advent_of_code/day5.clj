(ns advent-of-code.day5)

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

(defn parse-operation [value]
  (let [digits (extract-digits value)
        [opcode-digits parameter-mode-bits] (split-at 2 digits)
        opcode (+ (first opcode-digits) (* (second opcode-digits) 10))
        operation (cond
                    (= opcode 1) {:operation +
                                  :parameter-modes (default-missing-mode-bits parameter-mode-bits 3)}
                    (= opcode 2) {:operation *
                                  :parameter-modes (default-missing-mode-bits parameter-mode-bits 3)}
                    ; Might need something else here!
                    (= opcode 99) {:operation identity :parameter-modes []})]
    (assoc
     operation
     :parameter-modes
     (map (fn [bit] (if (= 0 bit) :position :immediate)) (:parameter-modes operation)))))

(map parse-operation [1001 1102 1099])

(default-missing-mode-bits (second (split-at 2 (extract-digits 1099))) 3)
