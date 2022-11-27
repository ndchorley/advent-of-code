(require '[clojure.string :as str])

(defn read-lines [file-name] (str/split-lines (slurp file-name)))

(let [lines (read-lines "day_1_input")
      depths (map #(Integer/parseInt %) lines)]

  (count
   (filter
    (fn [pair] (> (second pair) (first pair)))
    (partition 2 1 depths))))
