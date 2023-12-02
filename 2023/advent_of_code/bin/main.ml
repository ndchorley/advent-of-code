let puzzle = Command_line.get_puzzle ()

let solution = (Solutions.for_puzzle puzzle)

let () = print_endline (solution ())
