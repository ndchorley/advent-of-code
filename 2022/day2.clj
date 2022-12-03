(defn into-rounds [lines]
  (map (fn [line] (str/split line #" ")) lines))

(defn opponent-shape [letter]
  ({"A" :rock "B" :paper "C" :scissors} letter))

(defn my-shape [letter]
  ({"X" :rock "Y" :paper "Z" :scissors} letter))

(defn decrypt [strategy-guide]
  (map
   (fn [[opponent mine]]
     [(opponent-shape opponent) (my-shape mine)])
   strategy-guide))

(defn needed-outcome [letter]
  ({"X" :lose "Y" :draw "Z" :win} letter))

(defn decrypt-correctly [strategy-guide]
  (map
   (fn [round]
     [(opponent-shape (first round))
      (needed-outcome (second round))])
   strategy-guide))

(defn shape-score [shape]
  ({:rock 1 :paper 2 :scissors 3} shape))

(defn defeats? [[opponent-shape my-shape]]
  (or
   (and (= my-shape :rock) (= opponent-shape :scissors))
   (and (= my-shape :scissors) (= opponent-shape :paper))
   (and (= my-shape :paper) (= opponent-shape :rock))))

(defn outcome-score [[opponent-shape my-shape]]
  (cond
    (= opponent-shape my-shape) 3
    (defeats? [opponent-shape my-shape]) 6
    true 0))

(defn score [[opponent-shape my-shape]]
  (+
   (shape-score my-shape)
   (outcome-score [opponent-shape my-shape])))

(defn scores-per-round [rounds] (map score rounds))

(defn what-defeats [shape]
  ({:rock :paper :paper :scissors :scissors :rock} shape))

(defn what-loses-to [shape]
  ({:rock :scissors :paper :rock :scissors :paper} shape))

(defn choose-shape [[opponent-shape needed-outcome]]
  (cond
    (= needed-outcome :draw) opponent-shape
    (= needed-outcome :win) (what-defeats opponent-shape)
    (= needed-outcome :lose) (what-loses-to opponent-shape)))

(defn choose-shape-based-on-outcome [rounds]
  (map
   (fn [round] [(first round) (choose-shape round)])
   rounds))

(let [strategy-guide
      (->
       "day_2_input"
       (read-lines)
       (into-rounds))]
  (->
   strategy-guide
   (decrypt)
   (scores-per-round)
   (total))

  (->
   strategy-guide
   (decrypt-correctly)
   (choose-shape-based-on-outcome)
   (scores-per-round)
   (total)))
