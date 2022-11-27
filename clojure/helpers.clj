(require '[clojure.string :as str])

(defn read-lines [file-name]
  (str/split-lines (slurp file-name)))
