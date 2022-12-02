(defn separate-letters [lines]
  (map (fn [line] (str/split line #" ")) lines))

(defn opponent-shape [letter]
  ({"A" :rock "B" :paper "C" :scissors} letter))

(defn my-shape [letter]
  ({"X" :rock "Y" :paper "Z" :scissors} letter))

(defn as-shapes [letters]
  (map
   (fn [[opponent mine]]
     [(opponent-shape opponent) (my-shape mine)])
   letters))

(defn shape-score [shape]
  ({:rock 1 :paper 2 :scissors 3} shape))

(defn defeats [[opponent-shape my-shape]]
  (or
   (and (= my-shape :rock) (= opponent-shape :scissors))
   (and (= my-shape :scissors) (= opponent-shape :paper))
   (and (= my-shape :paper) (= opponent-shape :rock))))

(defn outcome-score [[opponent-shape my-shape]]
  (cond
    (= opponent-shape my-shape) 3
    (defeats [opponent-shape my-shape]) 6
    true 0))

(defn score [[opponent-shape my-shape]]
  (+
   (shape-score my-shape)
   (outcome-score [opponent-shape my-shape])))

(defn scores-per-round [rounds] (map score rounds))

(->
 "day_2_input"
 (read-lines)
 (separate-letters)
 (as-shapes)
 (scores-per-round)
 (total))
