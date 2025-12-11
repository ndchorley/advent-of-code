let toGrid (lines: string seq) =
    lines
    |> Seq.map (fun line -> line.ToCharArray())
    |> Array.ofSeq

let toCoordinates grid =
    grid
    |> Array.mapi
        (fun rowNumber row ->
            row
            |> Array.mapi (fun columnNumber _ -> [rowNumber; columnNumber])
        )
    |> Array.concat

let butOnlyTheRolls (grid: char array array) (allCoordinatesOnTheGrid: int list array) =
    allCoordinatesOnTheGrid
    |> Array.filter
        (fun coordinates ->
            let rowNumber = coordinates.Item 0
            let columnNumber = coordinates.Item 1

            grid[rowNumber][columnNumber] = '@'
        )


type GridDimensions = { width: int; height: int }

let onlyThoseWithin gridDimensions (potentialCoordinates: int list list) =
    potentialCoordinates
    |> List.filter
        (fun coordinates ->
            let rowNumber = coordinates.Item 0
            let columnNumber = coordinates.Item 1

            rowNumber >= 0 && rowNumber < gridDimensions.height &&
            columnNumber >= 0 && columnNumber < gridDimensions.width
        )

let adjacentCoordinates (gridDimensions: GridDimensions) (roll: int list) =
    let rowNumber = roll.Item 0
    let columnNumber = roll.Item 1

    let potentialCoordinates =
        [ 
            [rowNumber; columnNumber - 1];
            [rowNumber - 1; columnNumber - 1];
            [rowNumber - 1; columnNumber];
            [rowNumber - 1; columnNumber + 1]
            [rowNumber; columnNumber + 1];
            [rowNumber + 1; columnNumber + 1];
            [rowNumber + 1; columnNumber];
            [rowNumber + 1; columnNumber - 1]
        ]

    potentialCoordinates
    |> onlyThoseWithin gridDimensions

let dimensionsOf (grid: char array array) =
    { height = grid.Length; width = grid[0].Length }

let onlyTheRolls (grid: char array array) (coordinates: int list list) =
    coordinates
    |> List.filter
        (fun coordinates ->
            let rowNumber = coordinates.Item 0
            let columnNumber = coordinates.Item 1

            grid[rowNumber][columnNumber] = '@'
        )

let countThem coordinates =
    List.length coordinates

let adjacentRolls grid roll =
    adjacentCoordinates (dimensionsOf grid) roll
    |> onlyTheRolls grid
    |> countThem

let andOnlyThoseWithFewerThanFourAdjacentRolls (grid: char array array) (rolls: int list array) =
    rolls
    |> Array.filter
        (fun roll ->
            let rowNumber = roll.Item 0
            let columnNumber = roll.Item 1

            adjacentRolls grid roll < 4
        )
    |> List.ofArray

let findTheRollsWithFewerThanFourAdjacentRolls grid =
    grid
    |> toCoordinates
    |> butOnlyTheRolls grid
    |> andOnlyThoseWithFewerThanFourAdjacentRolls grid

System.IO.File.ReadLines "day4-input"
|> toGrid
|> findTheRollsWithFewerThanFourAdjacentRolls
|> countThem
