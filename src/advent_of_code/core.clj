(ns advent-of-code.core
  (:gen-class))

(defn fuel [mass]
  (-
   (.longValue (/ mass 3))
   2))

(defn -main
  [& args]
  (run! println (map fuel [12 14 1969 100756])))
