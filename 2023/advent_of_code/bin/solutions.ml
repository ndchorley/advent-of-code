open Puzzle

let for_day day = 
  match day with
  | 1 -> Day1.solution
  | _ -> fun () -> "Not implemented yet"


let for_puzzle puzzle =
  match puzzle with
  | {day = 1; part = 1} -> Day1.solution
  | _ -> fun () -> "Not implemented yet"
