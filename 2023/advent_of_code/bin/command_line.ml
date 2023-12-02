open Puzzle

let get_puzzle () =
  {
    day = (Sys.argv.(1) |> int_of_string);
    part = (Sys.argv.(2) |> int_of_string)
  }
