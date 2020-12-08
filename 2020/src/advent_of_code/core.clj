(ns advent-of-code.core
 (:require
   [clojure.java.io :as io]
   [clojure.string :as str]))

(defn read-lines [file]
  (str/split-lines (slurp (io/resource file))))
