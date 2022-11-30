(defn parse-line [line]
  (let [[command amount_string] (str/split line #" ")]
    {:command command
     :amount (Integer/parseInt amount_string)}))

(defn change [current-coordinates which amount]
  (let [current-value (current-coordinates which)]
    (assoc
     current-coordinates
     which
     (+ current-value amount))))

(defn move-forwards [current-coordinates amount]
  (->
   current-coordinates
   (change :horizontal-position amount)
   (change :depth (* (current-coordinates :aim) amount))))

(defn move-up [current-coordinates amount]
  (change current-coordinates :aim (* -1 amount)))

(defn move-down [current-coordinates amount]
  (change current-coordinates :aim amount))

(let [commands
      {"forward" move-forwards
       "up" move-up
       "down" move-down}

      parsed-lines
      (map parse-line (read-lines "day_2_input"))

      final-coordinates
      (reduce
       (fn [current-coordinates next]
         (let [command (commands (next :command))
               result (command current-coordinates (next :amount))]
           (do
             result)))
       {:horizontal-position 0 :depth 0 :aim 0}
       parsed-lines)]

  (*
   (final-coordinates :horizontal-position)
   (final-coordinates :depth)))
