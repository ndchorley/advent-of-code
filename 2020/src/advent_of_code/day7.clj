(ns advent-of-code.day7
  (:require
   [advent-of-code.core :refer :all]
   [clojure.string :as str]))

(defn split-bag-colour-from-contents [line]
  (map
   #(str/trim %)
   (str/split
    (str/replace line #"bags?\.?" "")
    #" contain ")))

(defn parse-bag [bag]
  (let [count-and-colour 
        (rest (re-matches #"(\d+) ([a-z ]+)" bag))]
    (if (seq count-and-colour)
      [(Integer/parseInt (first count-and-colour))
       (second count-and-colour)]
      nil)))

(defn parse-line [line]
  (let [[colour contents] 
        (split-bag-colour-from-contents line)]
    [colour
     (if (= contents "no other")
       nil 
       (map
        (comp parse-bag str/trim)
        (str/split contents #",")))]))


(defn build-graph [file]
  (into {} (map parse-line (read-lines file))))

(build-graph "day7")
