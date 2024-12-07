(defn to-integer [operand-string]
  (Integer/parseInt operand-string))

(defn operands [matches]
  (->> matches
       (map rest)
       (map
        (fn [operand-strings]
          (map to-integer operand-strings)))))

(defn extract-mul-instructions [lines]
  (->> lines
       (map
          (fn [line]
            (re-seq #"mul\((\d+),(\d+)\)" line)))
       (apply concat)))

(defn execute-mul-instructions [operands-per-instruction]
  (map
   (fn [operands] (apply * operands))
   operands-per-instruction))

(->> (read-lines "day3-input")
     (extract-mul-instructions)
     (operands)
     (execute-mul-instructions)
     (total))
