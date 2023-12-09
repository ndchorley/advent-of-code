open Puzzle

let for_puzzle puzzle =
  match puzzle with
  | {day = 1; part = 1} -> Day1.part_1_solution
  | {day = 1; part = 2} -> Day1.part_2_solution
  | _ -> fun () -> "Not implemented yet"
