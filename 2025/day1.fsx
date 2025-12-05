let rotationFrom (line: string) =
    let direction = line.Chars 0
    let distance =
        line.Substring 1
        |> System.Convert.ToInt32

    if direction.Equals 'L' then
        distance * -1
    else
        distance

let parseRotations lines = Seq.map rotationFrom lines

System.IO.File.ReadLines "day1-input"
|> parseRotations
|> Seq.iter (fun rotation -> printfn "%d" rotation) 
