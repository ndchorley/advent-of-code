let sum integers =
  List.fold_left Int.add 0 integers

let range n = List.init n Fun.id
