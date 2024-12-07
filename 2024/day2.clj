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

(defn remove-at [index report]
  (let [parts (split-at index report)]
    (concat (first parts) (rest (second parts)))))

(defn reports-after-removing-one-level [report]
  (let [indexes (range (count report))]
    (map
     (fn [index] (remove-at index report))
     indexes)))

(defn contains-safe-report? [reports]
  (->> reports
       (map is-safe?)
       (filter true?)
       (not-empty?)))

(defn is-safe-with-problem-dampener? [report]
  (->> report
       (reports-after-removing-one-level)
       (contains-safe-report?)))

(defn only-the-safe-reports [reports]
  (filter
   (fn [report]
     (or
      (is-safe? report)
      (is-safe-with-problem-dampener? report)))
   reports))

(->> (read-lines "day2-input")
     (parse-reports)
     (only-the-safe-reports)
     (count))
