let to_char_list string =
  string |> String.to_seq |> List.of_seq

let from_char_list characters =
  let characters_as_strings = 
    List.map (fun character -> Char.escaped character) characters in
      String.concat "" characters_as_strings
