(require '[clojure.string :as str])

(defn parse-location-ids [line]
  (let [id-strings (str/split line #"\s+")]
    (map (fn [s] (Integer/parseInt s)) id-strings)))

(defn parse-lists-of-location-ids [lines]
  (let [location-ids (map parse-location-ids lines)
        left-list (map first location-ids)
        right-list (map last location-ids)]
    [left-list right-list]))

(defn sort-lists [lists] (map sort lists))

(defn pair-ids [[left-list right-list]]
  (partition
   2
   2
   (interleave left-list right-list)))

(defn distance-between [paired-ids]
  (map
   (fn [[left-id right-id]] (abs(- right-id left-id)))
   paired-ids))

(defn count-of [value list]
  (->> list
       (filter (fn [id] (= id value)))
       (count)))

(defn pair-id-with-count-in-right [[left-list right-list]]
  (map
   (fn [id] [id (count-of id right-list)])
   left-list))

(defn changes-in-score [ids-and-counts]
  (map
   (fn [[id count]] (* id count))
   ids-and-counts))

(defn similarity-score [ids-and-counts]
  (->> ids-and-counts
       (changes-in-score)
       (total)))

(->> (read-lines "day1-input")
     (parse-lists-of-location-ids)
     (sort-lists)
     (pair-ids)
     (distance-between)
     (total))

(->> (read-lines "day1-input")
     (parse-lists-of-location-ids)
     (pair-id-with-count-in-right)
     (similarity-score))
