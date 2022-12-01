(defn separator? [calorie-strings-per-elf]
  (= (first calorie-strings-per-elf) ""))

(defn calories-per-elf [lines]
  (->>
   lines
   (partition-by empty?)
   (filter
    (fn [calorie-strings-per-elf]
      (not (separator? calorie-strings-per-elf))))
   (map
    (fn [calorie-strings-per-elf]
      (map
       #(Integer/parseInt %)
       calorie-strings-per-elf)))))

(defn total-per-elf [calories-per-elf]
  (map #(apply + %) calories-per-elf))

(defn most-calories [total-per-elf]
  (apply max total-per-elf))

(->
 "day_1_input"
 (read-lines)
 (calories-per-elf)
 (total-per-elf)
 (most-calories))
