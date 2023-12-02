let is_digit character = 
  match character with
  | '0' | '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9' -> true
  | _ -> false

let first_and_last digits =
  let first = List.hd digits in
  let last = List.nth digits (List.length digits - 1) in
    [first; last]

let calibration_value line =
  Strings.to_char_list line
  |> List.filter is_digit
  |> first_and_last
  |> Strings.from_char_list
  |> int_of_string
  
let values_from calibration_document =
  List.map calibration_value calibration_document

let solution () = 
  let calibration_document = Input.read_input "input/day1_input" in
  let calibration_values = values_from calibration_document in
    List.fold_left Int.add 0 calibration_values |> string_of_int
