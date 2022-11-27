(require '[clojure.string :as str])

(defn read-lines [file-name] (str/split-lines (slurp file-name)))

(defn number-of-increasing-measurements [depths]
  (count
   (filter
    (fn [pair] (> (second pair) (first pair)))
    (partition 2 1 depths))))

(let [lines (read-lines "day_1_input")
      depths (map #(Integer/parseInt %) lines)]
  (number-of-increasing-measurements depths)

  (number-of-increasing-measurements
   (map #(apply + %) (partition 3 1 depths))))
