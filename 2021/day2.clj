(defn parse-line [line]
  (let [[command amount_string] (str/split line #" ")]
    {:command command
     :amount (Integer/parseInt amount_string)}))

(defn move-forwards [current-coordinates amount]
  (let [new-horizontal-position
        (+ (current-coordinates :horizontal-position) amount)]
    (assoc
     current-coordinates
     :horizontal-position
     new-horizontal-position)))

(defn change-depth [current-coordinates delta]
  (let [new-depth (+ (current-coordinates :depth) delta)]
    (assoc current-coordinates :depth new-depth)))

(defn move-up [current-coordinates amount]
  (let [delta (* -1 amount)]
    (change-depth current-coordinates delta)))

(defn move-down [current-coordinates amount]
  (let [delta amount]
    (change-depth current-coordinates delta)))

(let [commands
      {"forward" move-forwards
       "up" move-up
       "down" move-down}

      parsed-lines
      (map parse-line (read-lines "day_2_input"))

      final-coordinates
      (reduce
       (fn [current-coordinates next]
         (let [command (commands (next :command))]
           (command current-coordinates (next :amount))))
       {:horizontal-position 0 :depth 0}
       parsed-lines)]

  (*
   (final-coordinates :horizontal-position)
   (final-coordinates :depth)))
