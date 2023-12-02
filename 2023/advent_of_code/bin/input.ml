let read_input file = 
  let channel = open_in file in
    In_channel.input_lines channel

