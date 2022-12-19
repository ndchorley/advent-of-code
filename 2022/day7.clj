(defn change-directory [state directory]
  (if (= directory "..")
    (update state :path pop)

    (update state :path conj directory)))

(defn parse-command [line]
  (rest (str/split line #" ")))

(defn execute-command [state command]
  (cond
    (= (first command) "ls") state

    (= (first command) "cd")
    (change-directory state (second command))

    true state))

(defn parse-directory [line]
  {:name (last (str/split line #" "))})

(defn parse-file [line]
  (let [[size-string name] (str/split line #" ")]
    {:name name :size (Integer/parseInt size-string)}))

(defn parse-item [line]
  (if (str/starts-with? line "dir")
    (parse-directory line)

    (parse-file line)))

(defn file? [item] (contains? item :size))

(defn add-file [state file]
  (update-in
   state
   (flatten [:tree (state :path)])
   assoc
   (file :name)
   (file :size)))

(defn add-directory [state directory]
  (update-in
   state
   (flatten [:tree (state :path)])
   assoc
   (directory :name)
   {}))

(defn add-to-tree [state item]
  (if (file? item)
    (add-file state item)

    (add-directory state item)))

(defn build-filesystem-tree [output]
  (reduce
   (fn [state line]
     (cond
       (str/starts-with? line "$")
       (->> line (parse-command) (execute-command state))

       true
       (->> line (parse-item) (add-to-tree state))))
   {:path [] :tree {"/" {}}}
   output))

(->>
 "day_7_input_small"
 (read-lines)
 (build-filesystem-tree))
