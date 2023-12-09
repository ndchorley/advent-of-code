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

type digit_to_position = {digit: string; position: int}

let find_all_occurrences_of string to_find =
  let indices = Integers.range (String.length string) in
    indices
    |> List.map (fun index ->
      try (Str.search_forward (Str.regexp to_find) string index) with
        Not_found -> -1
    )
  
let find_digit_in_string string digit =
    find_all_occurrences_of string digit
    |> List.filter (fun index -> index != -1)
    |> List.map (fun index -> {digit=digit; position=index})

let to_number word =
  match word with
  | "one" -> "1"
  | "two" -> "2"
  | "three" -> "3"
  | "four" -> "4"
  | "five" -> "5"
  | "six" -> "6"
  | "seven" -> "7"
  | "eight" -> "8"
  | "nine" -> "9"
  | _ -> ""

let find_digits_with_positions line =
  let positions_of_numbers =
    [1; 2; 3; 4; 5; 6; 7; 8; 9]
    |> List.map string_of_int
    |> List.map (find_digit_in_string line)
    |> List.flatten
  in
  let positions_of_words =
    ["one"; "two"; "three"; "four"; "five"; "six"; "seven"; "eight"; "nine"]
    |> List.map (find_digit_in_string line)
    |> List.flatten
    |> List.map (
      fun digit_to_position ->
        {digit=(to_number digit_to_position.digit); position=digit_to_position.position}
      )
  in
    List.append positions_of_numbers positions_of_words

let sorted_by_position digits_to_positions =
  List.sort
    (fun d1 d2 -> (compare d1.position d2.position))
    digits_to_positions

let only_the_digits digits_to_positions =
  List.map
    (fun digit_to_position -> digit_to_position.digit)
    digits_to_positions

let join_them_together digits = String.concat "" digits

let calibration_value_part_2 line =
  line
  |> find_digits_with_positions
  |> sorted_by_position
  |> only_the_digits
  |> first_and_last
  |> join_them_together
  |> int_of_string

let calibration_values calibration_value calibration_document =
  List.map calibration_value calibration_document

let solution calibration_value =
  let calibration_document = Input.read_input "input/day1_input" in
    calibration_document
    |> calibration_values calibration_value
    |> Integers.sum
    |> string_of_int
   
let part_1_solution () = solution calibration_value_part_1

let part_2_solution () = solution calibration_value_part_2
