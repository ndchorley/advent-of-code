(require '[clojure.set :as set])

(defn parse-rucksack-items [lines]
  (map
   (fn [line]
     (split-at (/ (count line) 2) line))
   lines))

(defn item-in-common [compartments]
  (->>
   compartments
   (map (fn [compartment] (into #{} compartment)))
   (apply set/intersection)
   first))

(defn find-items-in-both-compartments [rucksacks]
  (map item-in-common rucksacks))

(defn priority [item]
  (if (Character/isLowerCase item)
    (+ 1 (- (int item) (int \a)))
    (+ 27 (- (int item) (int \A)))))

(defn convert-to-priorities [items]
  (map priority items))

(defn group-rucksacks [lines]
  (partition 3 lines))

(defn find-common-item-per-group [groups]
  (map item-in-common groups))

(let [lines (-> "day_3_input" (read-lines))]
  (->
   lines
   (parse-rucksack-items)
   (find-items-in-both-compartments)
   (convert-to-priorities)
   (total))

  (->
   lines
   (group-rucksacks)
   (find-common-item-per-group)
   (convert-to-priorities)
   (total)))
