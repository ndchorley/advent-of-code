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
       (mapv
        (comp parse-bag str/trim)
        (str/split contents #",")))]))


(defn build-graph [file]
  (into {} (map parse-line (read-lines file))))

(defn can-bag-contain-colour?
  [graph colour wanted-colour]
  (loop [queue
         (conj
          (clojure.lang.PersistentQueue/EMPTY)
          colour)
         contains-wanted-colour false]
    
    (if (or contains-wanted-colour (empty? queue))
      contains-wanted-colour
      (let [current-colour (peek queue)
            [found next-colours]
            (loop [bags (graph current-colour)
                   result #{}]
              (if (empty? bags)
                [false result]
                (let [[_ contained-bag-colour]
                      (peek bags)]
                  (if (= contained-bag-colour
                         wanted-colour)
                    [true #{}]
                    (recur
                     (pop bags)
                     (conj
                      result
                      contained-bag-colour))))
                ))]
        (recur
         (apply conj (pop queue) next-colours)
         found)))))

(defn find-bags-containing [graph colour]
  (let [other-colours
        (disj (into #{} (keys graph)) colour)]

    (filter 
     #(can-bag-contain-colour? graph % colour)
     other-colours
     )))

(find-bags-containing 
 (build-graph "day7_small")
 "shiny gold"
 )
