let first_and_last digits =
  let first = List.hd digits in
  let last = Lists.last digits in
    [first; last]

let calibration_value_part_1 line =
  Strings.to_char_list line
  |> List.filter Characters.is_digit
  |> first_and_last
  |> Strings.from_char_list
  |> int_of_string
  
let calibration_values calibration_value calibration_document =
  List.map calibration_value calibration_document

let part_1_solution () =
  let calibration_document = Input.read_input "input/day1_input" in
    calibration_document
    |> calibration_values calibration_value_part_1
    |> Integers.sum
    |> string_of_int
   