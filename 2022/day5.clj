(require '[clojure.string :as str])

(defn separate-stacks-from-rearrangement-procedure [lines]
  (reduce
   (fn [state line]
     (cond
       (= line "") (assoc state :still-on-stacks false)

       (true? (state :still-on-stacks))
       (update state :stacks (fn [old] (conj old line)))

       true
       (update
        state :rearrangement-procedure
        (fn [old] (conj old line)))))

   {:still-on-stacks true :stacks [] :rearrangement-procedure []}
   lines))

(defn characters-with-index [line]
  (map-indexed
   (fn [index character] [character index])
   line))

(defn keep-only-digits [character-index-pairs]
  (filter
   (fn [[character index]] (Character/isDigit character))
   character-index-pairs))

(defn parse-digits [character-index-pairs]
  (map
   (fn [[character index]]
     [(Character/getNumericValue character) index])
   character-index-pairs))

(defn stack-numbers-and-positions [position-line]
  (->>
   position-line
   (characters-with-index)
   (keep-only-digits)
   (parse-digits)
   (into (hash-map))))

(defn empty-stacks [how-many]
  (zipmap
   (range 1 (inc how-many))
   (repeat how-many ())))

(defn only-positions-with-crates [stack-positions line]
  (filter
   (fn [entry]
     (let [position (val entry)]
       (Character/isLetter (.charAt line position))))
   stack-positions))

(defn which-crate-in-which-stack [stack-positions line]
  (->
   stack-positions
   (only-positions-with-crates line)
   (update-vals (fn [position]  (.charAt line position)))))

(defn add-crate-to-stack [stacks crate-in-stack]
  (let [stack (key crate-in-stack)
        crate (val crate-in-stack)]
    (update
     stacks
     stack
     (fn [old-stack] (conj old-stack crate)))))

(defn add-crates-to-stacks [stacks which-crate-in-which-stack]
  (reduce
   (fn [stacks crate-in-stack]
     (add-crate-to-stack stacks crate-in-stack))
   stacks
   which-crate-in-which-stack))

(defn put-crates-into-stacks [positions lines]
  (reduce
   (fn [stacks line]
     (add-crates-to-stacks
      stacks
      (which-crate-in-which-stack positions line)))

   (empty-stacks (count positions))
   lines))

(defn parse-initial-stacks [lines]
  (let [reversed-lines (reverse lines)
        positions-of-stacks
        (stack-numbers-and-positions (first reversed-lines))]

    (put-crates-into-stacks
     positions-of-stacks
     (rest reversed-lines))))

(defn to-instruction [[how-many-string from-string to-string]]
  {:how-many (Integer/parseInt how-many-string)
   :from (Integer/parseInt from-string)
   :to (Integer/parseInt to-string)})

(defn parse-rearrangement-procedure [lines]
  (map
   (fn [line]
     (->>
      line
      (re-matches #"move (\d+) from (\d+) to (\d+)")
      rest
      to-instruction))
   lines))

(defn remove-crate-from [stacks which]
  (update stacks which (fn [stack] (pop stack))))

(defn add-crate-to [stacks which crate]
  (update stacks which (fn [stack] (conj stack crate))))

(defn move-crates [stacks instruction]
  (if (zero? (instruction :how-many))
    stacks

    (let [source (stacks (instruction :from))
          destination (stacks (instruction :to))
          crate (peek source)]

      (->
       stacks
       (remove-crate-from (instruction :from))
       (add-crate-to (instruction :to) crate)
       (move-crates (update instruction :how-many dec))))))

(defn rearrange-crates [initial-stacks instructions]
  (reduce move-crates initial-stacks instructions))

(defn top-crates [stacks]
  (map
   (fn [stack] (first (stacks stack)))
   (sort (keys stacks))))

(defn message [crates] (str/join crates))

(let [separated-lines
      (separate-stacks-from-rearrangement-procedure
       (read-lines "day_5_input"))

      initial-stacks
      (->
       (separated-lines :stacks)
       (parse-initial-stacks))

      rearrangement-procedure
      (->
       (separated-lines :rearrangement-procedure)
       (parse-rearrangement-procedure))]

  (->
   initial-stacks
   (rearrange-crates rearrangement-procedure)
   (top-crates)
   (message)))
