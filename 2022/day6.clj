(defn characters-with-positions [datastream-buffer]
  (let [positions (range 1 (inc (count datastream-buffer)))]
    (->>
     datastream-buffer
     (interleave positions)
     (partition 2))))

(defn stream-of-4-character-chunks [characters-with-positions]
  (partition 4 1 characters-with-positions))

(defn only-characters [characters-with-positions]
  (map second characters-with-positions))

(defn less-than? [n x] (< x n))

(defn not-marker? [characters-with-positions]
  (->>
   characters-with-positions
   (only-characters)
   (into #{})
   (count)
   (less-than? 4)))

(defn marker-complete-at [characters-with-positions]
  (->
   characters-with-positions
   (last)
   (first)))

(let [input (first (read-lines "day_6_input"))]
  (->>
   input
   (characters-with-positions)
   (stream-of-4-character-chunks)
   (drop-while not-marker?)
   (first)
   (marker-complete-at)))
