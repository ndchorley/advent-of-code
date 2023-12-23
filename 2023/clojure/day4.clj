(defn extract-numbers-from [string]
  (->>
   string
   (re-seq #"\d+")
   (map (fn [s] (Integer/parseInt s)))))

(defn matches [winning-numbers numbers-you-have]
  (clojure.set/intersection
   (into #{} winning-numbers)
   (into #{} numbers-you-have)))

(defn points-for [matches]
  (if (= 0 (count matches))
    0
    (int (Math/pow 2 (dec (count matches))))))

(defn find-points [line]
  (let [either-colon-or-pipe #":|\|"
        parts
        (clojure.string/split line either-colon-or-pipe)

        winning-numbers
        (extract-numbers-from (second parts))

        numbers-you-have
        (extract-numbers-from (last parts))]

    (points-for
     (matches winning-numbers numbers-you-have))))

(defn find-points-per-card [lines]
  (map find-points lines))

(->>
 (read-lines "day4_input")
 (find-points-per-card)
 (total))
