let parseRotation (line: string) =
    let direction = line.Chars 0
    let amount =
        line.Substring 1
        |> System.Convert.ToInt32

    if direction.Equals 'L' then
        amount * -1
    else
        amount
 
System.IO.File.ReadLines "day1-input"
|> Seq.map parseRotation
|> Seq.iter (fun rotation -> printfn "%d" rotation) 
