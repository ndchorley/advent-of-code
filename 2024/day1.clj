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

(->> (read-lines "day1-input")
     (parse-lists-of-location-ids)
     (sort-lists)
     (pair-ids)
     (distance-between)
     (total))
