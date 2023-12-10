type subset = {red: int; green: int; blue: int}

type game = {id: int; subsets: subset list}

let parse_id id_part =
  id_part
  |> Str.split (Str.regexp " ")
  |> Lists.last
  |> int_of_string

let count_of colour subset_string =
  let regex = Str.regexp ("\\([0-9]+\\) " ^ colour) in
    try
      let _ = Str.search_forward regex subset_string 0 in
        Str.matched_group 1 subset_string |> int_of_string
    with
      Not_found -> 0

let parse_subset subset_string =
  {
    red=(count_of "red" subset_string);
    blue=(count_of "blue" subset_string);
    green=(count_of "green" subset_string)
  }


let parse_subsets subsets_part =
  subsets_part
  |> Str.split (Str.regexp ";")
  |> List.map parse_subset

let parse_game line =
  let parts = Str.split (Str.regexp ":") line in
    {
      id=parse_id (List.hd parts);
      subsets=parse_subsets (Lists.last parts)
    }

let parse_games lines = List.map parse_game lines

let record_of_games =
  Input.read_input "input/day2"
  |> parse_games

let is_possible game =
  List.for_all
    (fun subset ->
      subset.red <= 12 && subset.green <= 13 && subset.blue <= 14)
    game.subsets

let only_possible_games games =
  List.filter is_possible games

let only_the_ids games =
  List.map (fun game -> game.id) games

let part_1_solution () =
  record_of_games
  |> only_possible_games
  |> only_the_ids
  |> Integers.sum
  |> string_of_int

let fewest_cubes game =
  List.fold_left
    (fun result_so_far subset ->
      {
        red=(Int.max subset.red result_so_far.red);
        green=(Int.max subset.green result_so_far.green);
        blue=(Int.max subset.blue result_so_far.blue)
      }
    )
    {red=0; green=0; blue=0}
    game.subsets

let find_fewest_cubes_per_game games =
  List.map fewest_cubes games

let powers subsets =
  List.map
    (fun subset -> subset.red * subset.green * subset.blue)
    subsets

let part_2_solution () =
    record_of_games
    |> find_fewest_cubes_per_game
    |> powers
    |> Integers.sum
    |> string_of_int
