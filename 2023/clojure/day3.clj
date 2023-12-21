(defn with-line-numbers [lines]
  (map-indexed
   (fn [index line] [index line])
   lines))

(defn to-number-and-position [matcher index results]
  (map
   (fn [_]
     (let [start [(.start matcher) index]
           end [(dec (.end matcher)) index]]
       {:number (Integer/parseInt (.group matcher))
        :start start
        :end end}))
   results))

(defn find-next-match [matcher] (re-find matcher))

(defn has-a-match [match-result] (not (nil? match-result)))

(defn find-matches-per-line
  [regex to-result numbered-lines]
  (let [results-per-line
        (map
         (fn [[index line]]
           (let [matcher (re-matcher regex line)]
             (->>
              (repeatedly (partial find-next-match matcher))
              (take-while has-a-match)
              (to-result matcher index))))
         numbered-lines)]

    (->> results-per-line (filter not-empty))))

(defn find-all-numbers-and-their-positions [lines]
  (->>
   lines
   (with-line-numbers)
   (find-matches-per-line #"\d+" to-number-and-position)
   (flatten)))

(defn in-bounds? [lines position]
  (let [[x y] position]
    (and
     (>= x 0)
     (< x (count (first lines)))

     (>= y 0)
     (< y (count lines)))))

(defn character-at [lines position]
  (let [[x y] position
        line (nth lines y)]
    (nth line x)))

(defn adjacent-characters-of [position lines]
  (let [[x y] position
        adjacents
        #{[(- x 1) y]
          [(- x 1) (- y 1)]
          [x (- y 1)]
          [(+ x 1) (- y 1)]
          [(+ x 1) y]
          [(+ x 1) (+ y 1)]
          [x (+ y 1)]
          [(- x 1) (+ y 1)]}]

    (->>
     adjacents
     (filter (partial in-bounds? lines))
     (map (partial character-at lines))
     (into #{}))))

(defn is-symbol? [character]
  (and
   (not (Character/isDigit character))
   (not= character \.)))

(defn keep-only-the-symbols [characters]
  (filter is-symbol? characters))

(defn is-adjacent-to-a-symbol? [lines number]
  (let [adjacent-characters
        (clojure.set/union
         (adjacent-characters-of (:start number) lines)
         (adjacent-characters-of (:end number) lines))]
    (->>
     adjacent-characters
     (keep-only-the-symbols)
     (not-empty?))))

(defn only-those-adjacent-to-a-symbol [lines all-numbers]
  (filter
   (partial is-adjacent-to-a-symbol? lines)
   all-numbers))

(defn part-numbers-without-positions [numbers]
  (map :number numbers))

(defn keep-only-valid-part-numbers [lines all-numbers]
  (->>
   all-numbers
   (only-those-adjacent-to-a-symbol lines)
   (part-numbers-without-positions)))

(let [lines (read-lines "day3_input")]
  (->>
   lines
   (find-all-numbers-and-their-positions)
   (keep-only-valid-part-numbers lines)
   (total)))
