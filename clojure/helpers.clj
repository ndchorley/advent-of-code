(require '[clojure.string :as str])

(defn read-lines [file-name]
  (str/split-lines (slurp file-name)))

(defn total [values] (apply + values))

(defn not-empty? [collection]
  (not (nil? (seq collection))))
