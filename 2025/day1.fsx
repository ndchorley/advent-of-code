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

let newPosition currentPosition rotation =
    let positionThatMightBeNegative = currentPosition + rotation

    let unboundedNewPosition =
        if positionThatMightBeNegative < 0 then
            100 + positionThatMightBeNegative
        else
            positionThatMightBeNegative

    unboundedNewPosition % 100

let applyRotations startingFrom rotations =
    Seq.fold
        (fun dialPositions rotation ->
            let currentPosition = Seq.head dialPositions

            Seq.insertAt 0 (newPosition currentPosition rotation) dialPositions
        )
        (Seq.singleton startingFrom)
        rotations

let countTheZeros dialPositions =
    Seq.filter (fun position -> position = 0) dialPositions
    |> Seq.length

System.IO.File.ReadLines "day1-input"
|> parseRotations
|> applyRotations 50
|> countTheZeros
