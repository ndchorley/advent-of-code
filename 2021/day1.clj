(require '[clojure.string :as str])

(let [lines (str/split-lines (slurp "day_1_input"))
      depths (map #(Integer/parseInt %) lines)]

  (count
   (filter
    (fn [pair] (> (second pair) (first pair)))
    (partition 2 1 depths))))
