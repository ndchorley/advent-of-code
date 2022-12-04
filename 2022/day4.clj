(defn parse-section-assignment [sections-string]
  (let [start-string (first (str/split sections-string #"-"))
        end-string (second (str/split sections-string #"-"))]
    [(Integer/parseInt start-string)
     (Integer/parseInt end-string)]))

(defn parse-section-assignments-per-pair [lines]
  (map
   (fn [line]
     (let [section-assignments (str/split line #",")]
       (map parse-section-assignment section-assignments)))
   lines))

(defn start [range] (first range))

(defn end [range] (second range))

(defn within? [first-range second-range]
  (and
   (>= (start first-range) (start second-range))
   (<= (end first-range) (end second-range))))

(defn fully-contained? [[first-range second-range]]
  (or
   (within? second-range first-range)
   (within? first-range second-range)))

(defn fully-contained-ranges [section-assignments]
  (filter fully-contained? section-assignments))

(defn overlap? [[first-range second-range]]
  (or
   (and
    (<= (start first-range) (start second-range))
    (<= (start second-range) (end first-range)))

   (and
    (<= (start second-range) (start first-range))
    (<= (start first-range) (end second-range)))))

(defn overlapping-ranges [ranges]
  (filter overlap? ranges))

(let [ranges
      (->
       "day_4_input"
       (read-lines)
       (parse-section-assignments-per-pair))]
  (->
   ranges
   (fully-contained-ranges)
   (count))

  (->
   ranges
   (overlapping-ranges)
   (count)))
