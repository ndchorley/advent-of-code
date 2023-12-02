let first_and_last digits =
  let first = List.hd digits in
  let last = Lists.last digits in
    [first; last]

let calibration_value line =
  Strings.to_char_list line
  |> List.filter Characters.is_digit
  |> first_and_last
  |> Strings.from_char_list
  |> int_of_string
  
let values_from calibration_document =
  List.map calibration_value calibration_document

let solution () = 
  let calibration_document = Input.read_input "input/day1_input" in
  let calibration_values = values_from calibration_document in
    List.fold_left Int.add 0 calibration_values |> string_of_int
