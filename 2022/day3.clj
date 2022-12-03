(require '[clojure.set :as set])

(defn parse-rucksack-items [lines]
  (map
   (fn [line]
     (split-at (/ (count line) 2) line))
   lines))

(defn find-items-in-both-compartments [rucksacks]
  (map
   (fn [rucksack]
     (first
      (set/intersection
       (into #{} (first rucksack))
       (into #{} (second rucksack)))))
   rucksacks))

(defn convert-to-priorities [items]
  (map
   (fn [item]
     (if (Character/isLowerCase item)
       (+ 1 (- (int item) (int \a)))
       (+ 27 (- (int item) (int \A)))))
   items))

(->
 "day_3_input"
 (read-lines)
 (parse-rucksack-items)
 (find-items-in-both-compartments)
 (convert-to-priorities)
 (total))
