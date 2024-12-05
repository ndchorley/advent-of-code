(require '[clojure.string :as str])

(defn parse-report [line]
  (let [levels (str/split line #" ")]
    (map
     (fn [level-string] (Integer/parseInt level-string))
     levels)))

(defn parse-reports [lines] (map parse-report lines))

(defn all-increasing? [levels] (apply < levels))

(defn all-decreasing? [levels] (apply > levels))

(defn adjacent-levels [levels] (partition 2 1 levels))

(defn differences [adjacent-levels]
  (map
   (fn [[first-level second-level]]
     (abs (- first-level second-level)))
   adjacent-levels))

(defn adjacent-levels-differ-by-at-least-one-and-at-most-three [report]
  (->> report
       (adjacent-levels)
       (differences)
       (every?
        (fn [difference]
          (and (>= difference 1) (<= difference 3))))))

(defn is-safe? [report]
  (and
   (or
    (all-increasing? report)
    (all-decreasing? report))

   (adjacent-levels-differ-by-at-least-one-and-at-most-three
    report)))


(defn only-the-safe-reports [reports]
  (filter is-safe? reports))

(->> (read-lines "day2-input")
     (parse-reports)
     (only-the-safe-reports)
     (count))
